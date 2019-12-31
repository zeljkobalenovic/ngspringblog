package zeljko.ngspringblog.service;

import java.security.Key;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * JwtProvider
 */

@Service
 public class JwtProvider {

    // ovaj deo ima na mom githubu u repo springsecurity objasnjeni svi jwt utilitiji 
    // tamo je koriscen obican string za sifrovanje , ova dole varijanta je bolja jer sama java daje key
    // i tako moze biti problema jer korisniku generisemo jwt sa key i on ga koristi , ali sta ako se server
    // restartuje - key se menja i validacija jwt ne prolazi. zato je prostije koristiti samo string secret key
    // kao na repo springsecurity , a za production neke varijante keystore ( sa public i private key - komplikovanije ali pravo)
    
    
    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // sad imamo key koji vise nije hardcodovana rec pa ga koristimo za pravljenje tokena 

	public String generateToken(Authentication authenticate) {
        UserDetails principal = (UserDetails)authenticate.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(key)
                .compact();
                
    }
    
    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);  // ako token nije validan bacice exception 
        // ako nebaci nijedan exception znaci da je validan jwt
        return true;
    }

	public String getUsernameFromJwt(String jwt) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getSubject();
	}

    
}