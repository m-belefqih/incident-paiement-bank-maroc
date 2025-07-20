package com.al_akdar_bank_.solution_ctr.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/images/**", "/home", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/ctr_submit", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()           // autorise tout le monde à accéder à /logout
                        .logoutUrl("/logout")  // URL de logout (par défaut /logout)
                        .logoutSuccessUrl("/login") // redirige après logout
                        .invalidateHttpSession(true)       // invalide la session
                        .deleteCookies("JSESSIONID")       // supprime cookie session
                );

        return http.build();
    }

}