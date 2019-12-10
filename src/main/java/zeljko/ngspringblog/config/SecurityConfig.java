package zeljko.ngspringblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityConfig
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // klasicna postavka kad nema rola nego samo autentikacija ( nema autorizacije po rolama ) bice samo signup(registracija)
    // signin(login) i signout(logout) iskljucuje se csrf - da bi primao requeste sa spolja , dozvoljava se auth/** sve (tu ce 
    // biti login i registarcija), a za sve ostalo se trazi autentikacija pri svakom requestu 
        
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/auth/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated() ;
    }

    // password encoder je OBAVEZAN ( tj. password se nikad ne cuva kao clear text). spring ima dosta njih ali se preporucuje bcrypt

    @Bean 
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
