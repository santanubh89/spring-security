package com.eazybytes.eazyschool.config;

import com.eazybytes.eazyschool.exception.CustomAccessDeniedHandler;
import com.eazybytes.eazyschool.exception.CustomBasicAuthenticationEntryPoint;
import com.eazybytes.eazyschool.filter.CsrfCookieFilter;
import com.eazybytes.eazyschool.filter.CustomRequestValidationFilter;
import com.eazybytes.eazyschool.filter.LoggingFilter;
import com.eazybytes.eazyschool.handler.CustomAuthenticationFailureHandler;
import com.eazybytes.eazyschool.handler.CustomAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class ApplicationSecurityConfig {

    private final CustomAuthenticationSuccessHandler successHandler;

    private final CustomAuthenticationFailureHandler failureHandler;

    public ApplicationSecurityConfig(CustomAuthenticationSuccessHandler successHandler,
                                     CustomAuthenticationFailureHandler failureHandler) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // API Access
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
                .requestMatchers("/notices", "/contact", "/error", "/contact", "/register",
                        "/invalidSession", "/username", "/authentication").permitAll());

        // CORS Configuration
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedOrigin("http://localhost:4200");
                corsConfiguration.addAllowedMethod("*");
                corsConfiguration.addAllowedHeader("*");
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }
        }));

        // Custom Filter Configuration
        http
            .addFilterBefore(new CustomRequestValidationFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new LoggingFilter(), BasicAuthenticationFilter.class);

        // Exception Handling Configuration
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc -> ehc
                .authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler()));

        // CSRF Configuration
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.csrf(csrfConfigurer -> csrfConfigurer
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/contact", "/register")
                .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler))
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.sessionManagement(sessionConfig -> sessionConfig
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        http.securityContext(contextConfig -> contextConfig.requireExplicitSave(false));

        // Login and Logout Configuration
        http.formLogin(flc -> flc
                .usernameParameter("userid").passwordParameter("secretpwd")
                .loginPage("/login")
                .successHandler(successHandler).failureHandler(failureHandler)
                .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true"));
        http.logout(loc -> loc
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true).clearAuthentication(true)
                .deleteCookies("JSESSIONID"));

        http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure());

        // HTTP Basic Configuration
        // http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    // @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}12345").authorities("read").build();
        UserDetails admin = User.withUsername("admin")
                .password("{noop}123") //EazyBytes@12345
                .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
