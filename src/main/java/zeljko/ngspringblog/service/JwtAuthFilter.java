package zeljko.ngspringblog.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtAuthFilter
 */

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService UserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
            // i ovo ima sve objasnjeno na moj github repo springsecurity ovo je malo drugacije

            String jwt = getJwtFromRequest(request);    // isecemo token iz request headera

            if ( jwt != null && jwtProvider.validateToken(jwt))  {       // vidimo da li je to validan jwt token
                String username = jwtProvider.getUsernameFromJwt(jwt);
                UserDetails userDetails = UserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // ovo je ??? - radi isti i sa ovim i bez ovog ispitati zasto se stavlja 
                // details pre je null                
                //  if (authentication.getDetails() == null){System.out.println("null je");}
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));             
                // details posle nije null tj. doda se ip adresa i session id koji je ovako null
                // if (authentication.getDetails() == null){System.out.println("null je");}               
                // if (authentication.getDetails() != null){System.out.println("null nije");}
                // System.out.println(authentication.getDetails().toString());

              // kod jwr autentikacije ovo mora jer ide na jos filtera
                SecurityContextHolder.getContext().setAuthentication(authentication);
                 }         

            filterChain.doFilter(request, response);


            }

            private String getJwtFromRequest (HttpServletRequest request) {
                String bearerToken = request.getHeader("Authorization");                 
                if ( bearerToken !=null && bearerToken.startsWith("Bearer")) {
                    return bearerToken.substring(7);
                }
                return bearerToken;

            }


            
                

    }



    
