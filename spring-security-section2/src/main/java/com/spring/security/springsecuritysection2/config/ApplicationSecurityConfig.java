package com.spring.security.springsecuritysection2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import java.util.List;

@Configuration
public class ApplicationSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /*http.authorizeHttpRequests((requests) -> (requests.anyRequest()).permitAll());*/
        /*http.authorizeHttpRequests((requests) -> (requests.anyRequest()).denyAll());*/
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll()
                .anyRequest().denyAll());
        // Form Login
        http.formLogin(Customizer.withDefaults());
        /*http.formLogin(formLoginConfigurer -> formLoginConfigurer.disable());*/

        // HTTP Basic Authentication
        http.httpBasic(Customizer.withDefaults());
        /*http.httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable());*/
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // String password = passwordEncoder().encode("secret");
        List<UserDetails> users = List.of(
                User.withUsername("user").password("{bcrypt}$2a$10$X04W4W.7C8IfHEjNRqQWNOcBOtHFUjIY3v0Xnt3.8GTau/d.9x.tO").authorities("read").build(), //bcrypt: secret
                User.withUsername("admin").password("{noop}12345").authorities("admin").build() // plain text
        );
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
