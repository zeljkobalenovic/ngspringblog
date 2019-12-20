package zeljko.ngspringblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zeljko.ngspringblog.dto.AuthenticationResponse;
import zeljko.ngspringblog.dto.LoginRequest;
import zeljko.ngspringblog.dto.RegisterRequest;
import zeljko.ngspringblog.service.AuthService;


/**
 * AuthController
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public AuthenticationResponse login (@RequestBody LoginRequest loginRequest) {
       return authService.login(loginRequest);
        
    }
}