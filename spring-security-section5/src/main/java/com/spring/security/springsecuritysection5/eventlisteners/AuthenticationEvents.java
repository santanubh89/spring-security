package com.spring.security.springsecuritysection5.eventlisteners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent authenticationSuccessEvent) {
        log.info("Login successful for user: {}", authenticationSuccessEvent.getAuthentication().getName());
        UsernamePasswordAuthenticationToken source = (UsernamePasswordAuthenticationToken) authenticationSuccessEvent.getSource();
        List authorities = (List) source.getCredentials();
        log.info("Authorities: {}", authorities);
        WebAuthenticationDetails details = (WebAuthenticationDetails) source.getDetails();
        log.info("Remote Address: {}", details.getRemoteAddress());
        log.info("Session Id: {}", details.getSessionId());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent authenticationFailureEvent) {
        log.info("Login failed for user: {} due to: {}",
                authenticationFailureEvent.getAuthentication().getName(),
                authenticationFailureEvent.getException().getMessage());
    }

}
