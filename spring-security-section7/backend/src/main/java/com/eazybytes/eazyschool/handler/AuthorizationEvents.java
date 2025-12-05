package com.eazybytes.eazyschool.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationEvents {

    @EventListener
    public void onSuccess(AuthorizationGrantedEvent event) {
        log.info("Authorization success for user: {} due to: {}", event.getAuthentication().get().getName(),
                event.getAuthorizationDecision().toString());
    }

    @EventListener
    public void onFailure(AuthorizationDeniedEvent event) {
        log.info("Authorization failed for user: {} due to: {}", event.getAuthentication().get().getName(),
                event.getAuthorizationDecision().toString());
    }

}
