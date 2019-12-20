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