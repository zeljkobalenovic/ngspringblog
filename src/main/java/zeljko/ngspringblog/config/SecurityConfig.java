package zeljko.ngspringblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import zeljko.ngspringblog.service.JwtAuthFilter;

/**
 * SecurityConfig
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService; // za auth
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // klasicna postavka kad nema rola nego samo autentikacija ( nema autorizacije
    // po rolama ) bice samo signup(registracija)
    // signin(login) i signout(logout) iskljucuje se csrf - da bi primao requeste sa
    // spolja , dozvoljava se auth/** sve (tu ce
    // biti login i registarcija), a za sve ostalo se trazi autentikacija pri svakom
    // requestu

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests().antMatchers("/api/auth/**").permitAll().anyRequest()
                .authenticated()
                // mora se ovo dodati inace ce jednom kad prodje sa jwt posle prolaziti i bez jwt sto necemo tj.
                // moramo spreciti pravljenje i koriscenje sesija 
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);        
    }

    // password encoder je OBAVEZAN ( tj. password se nikad ne cuva kao clear text).
    // spring ima dosta njih ali se preporucuje bcrypt

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    // za login smo kreirali endpoint i dto i sad se podesava spring security
    // za autentikaciju je odgovoran authenticationmanager ( spring ih ima vise in
    // memory , ldap , jdbc ). svi se zasnivaju na tome da se
    // prvo nadje user na osnovu stiglog username , to radi userdetailservice ( isto
    // deo spring security) , on vraca user iz npr. baze
    // pa on dalje ide na autentifikaciju - uporedjuju se passwordi ...
    // za nasu klasicnu situaciju jpa + mysql baza nema gotovog resenja nego mi
    // moramo userdetailservice implementirati za nas slucaj
    // prakticno overload metoda loaduserbyusername

    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // npr hocemo inmemory auth provajder
        // auth.inMemoryAuthentication() pa dalje konfigurisemo

        // u nasem slucaju nema gotovog resenja pa pisemo nas userdetailservice i njega
        // dajemo auth provajderu
        // posao tog naseg userdetailservice je da na osnovu username koji mu se dostavi
        // vrati instancu objekta user
        // kod nas iz mysql baze , ali mozemo napraviti sta hocemo bitno da ima
        // loaduserbyusername

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    

    

}
