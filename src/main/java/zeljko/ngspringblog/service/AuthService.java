package zeljko.ngspringblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);

        // najosnovnija varijanta za vezbu. posto na user nismo stavili nikakva ogranicenja kao na post sve prolazi tj. nema nikakvih
        // validacija , u praksi se OBAVEZNO dolazni zahtev validira kako treba , pa se vrati poruka ako registerRequest nije ok
        
	}

    private String encode(String password) {
        return passwordEncoder.encode(password);
    }

    
}