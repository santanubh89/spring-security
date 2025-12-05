package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requests -> requests
                .requestMatchers("/secure").authenticated()
                .anyRequest().permitAll());

        httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.oauth2Login(Customizer.withDefaults());

        return httpSecurity.build();

    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration githubClientRegistration = githubClientRegistration();
        // ClientRegistration facebookClientRegistration = facebookClientRegistration();
        return new InMemoryClientRegistrationRepository(githubClientRegistration);
    }

    /**
     * Get code by hitting the following URL in browser
     * https://github.com/login/oauth/authorize?client_id=***&scope=read:user
     * curl --location 'https://github.com/login/oauth/access_token' \
     * --header 'Accept: application/json' \
     * --header 'Content-Type: application/x-www-form-urlencoded' \
     * --header 'Cookie: _octo=GH1.1.1220835926.1764245782' \
     * --data-urlencode 'client_id=***' \
     * --data-urlencode 'client_secret=***' \
     * --data-urlencode 'code=***'
     */
    private ClientRegistration githubClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId("***").clientSecret("***").build();
    }

    /*private ClientRegistration facebookClientRegistration() {
        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook")
                .clientId("").clientSecret("").build();
    }*/
}
