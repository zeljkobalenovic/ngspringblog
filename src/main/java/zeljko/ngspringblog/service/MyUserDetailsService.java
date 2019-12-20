package zeljko.ngspringblog.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import zeljko.ngspringblog.model.User;
import zeljko.ngspringblog.repository.UserRepository;

/**
 * MyUserDetailsService
 */

// posto spring security nema gotovo resenje mi pisemo nas userdetailsservice
// koji implementira trazenu metodu loaduserbyusername
// u nasen slucaju je to jpa mysql , pa metoda treba da u bazi nadje username i
// vrati instancu userdetails ( svi podaci o useru )
// za to nam treba user repository i metoda koja vraca usera po imenu

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        
        Optional<User> user = userRepository.findByUserName(username);

        user.orElseThrow(() -> new UsernameNotFoundException("No User found" + username));

        return user.map(MyUserDetails::new).get();      

    }
       
}