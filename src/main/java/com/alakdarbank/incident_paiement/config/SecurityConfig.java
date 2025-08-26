package com.alakdarbank.incident_paiement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Bean for password encoding using BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuration of HTTP security for login and logout
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/images/**", "/home", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")  // Add this line to use email parameter
                        .defaultSuccessUrl("/import", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()           // allow everyone to access /logout
                        .logoutUrl("/logout")  // logout URL (default is /logout)
                        .logoutSuccessUrl("/login")   // redirect after logout
                        .invalidateHttpSession(true)   // invalidate the session
                        .deleteCookies("JSESSIONID")   // delete session cookie
                );

        return http.build();
    }
}