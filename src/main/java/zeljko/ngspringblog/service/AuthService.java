package zeljko.ngspringblog.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import zeljko.ngspringblog.dto.AuthenticationResponse;
import zeljko.ngspringblog.dto.LoginRequest;
import zeljko.ngspringblog.dto.RegisterRequest;
import zeljko.ngspringblog.model.User;
import zeljko.ngspringblog.repository.UserRepository;


/**
 * AuthService
 */

@Service
 public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

       
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);

        // najosnovnija varijanta za vezbu. posto na user nismo stavili nikakva ogranicenja kao na post sve prolazi tj. nema nikakvih
        // validacija , u praksi se OBAVEZNO dolazni zahtev validira kako treba , pa se vrati poruka ako registerRequest nije ok
        
	}

 //   private String encode(String password) {
 //       return passwordEncoder.encode(password);
//    }

	public AuthenticationResponse login(LoginRequest loginRequest){
        
        // posto je autentikacija tipa username password koristicemo takav token spring security - ja.
        // VAZNO !!!
        // ima dva moguca konstruktora - prvi prima dva parametra ( principal - nas username i credentials - nas password)
        // ovaj konstruktor vraca isAuthenticated false , tj. predstavlja objekat authentication PRE logina
        // drugi konstruktor prima i treci parametar (authorities - listu odobrenih rola)
        // taj vraca isAuthenticated true i koristi se da predstavi objekat authentication POSLE logina (taj se koristi sa jwt )

        UsernamePasswordAuthenticationToken uToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        
        // za samu autentikaciju koristi se metoda autentikacionmanagera autenticate koja radi samu autentikaciju
        // koristeci userdetailsservice koji smo implementirali da nadje usera , pa ako ga nadje vrati userdetails koji
        // smo takodje implementirali , pa metoda uporedi passworde i to je to
        // ako je ok postavi objekat autenticatoin isauthenticated na true , a ako nije baci authentication exception   
          
        Authentication authentication = authenticationManager.authenticate(uToken);

        // za dalje koriscenje ovako cuvamo authentication objekat 
        // VAZNO - kod varijante sa jwt tokenom NESMEMO da sacuvamo authentication jer bi onda prolazilo sve slali mi ili ne jwt token
        
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        // ako smo stigli dovde nije bacen authentication exception pa mozemo pristupiti kreiranju tokena 
        // u praksi ovde ide try catch da vidimo sta ne valja (koji authentication exception je bacen ) pa da obavestimo usera
        // ovde radi jednostavnosti ako korisnik ne potrefi dobar username password nece dobiti jwt token
       
        //primeri sta se sve moze iscitati iz authentication objekta , gde se u principal cuva objekat tipa userdetails
        // koji ima sve zivo username, password, role ...
        /*
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        authentication.getCredentials();
        authentication.getName();              
        authentication.isAuthenticated();
        authentication.getDetails();
        userDetails.getAuthorities();
        System.out.println(authentication.isAuthenticated());
        System.out.println(userDetails.getUsername() + " password je " +userDetails.getPassword() + " role su :" + userDetails.getAuthorities());
        System.out.println(authentication.getCredentials());   
        System.out.println(authentication.getName());
        */ 

        // sad pravimo jwt token (nije bacen nijedan authentication exception pa imamo sve o useru gore opisano )
        String jwt = jwtProvider.generateToken(authentication);
        // sad pravimo username za authentication response
        String username = authentication.getName();

        // ovo su uproscene verzije u moj github - repo springsecurity ima  verzija za sve moguce varijante
        // tamo se prvo dobija userdetails iz authentication.getprincipal() pa se on koristi za jwt 
        // userdetails ima sve zivo , pa se mogu slati razne stvari koje ovakio ne , ali dobro je za prostu verziju
        // ovde ce zapravo jwtprovider izvaditi userdetails iz authentication objekta pa praviti jwt
        
        // vracamo response
        return new AuthenticationResponse(jwt,username);

    }
    
}