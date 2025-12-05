package com.eazybytes.eazyschool.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CredentialController {

    @GetMapping("/username")
    public String getCurrentUser1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/authentication")
    public String getCurrentUser2(Authentication authentication) {
        return authentication.getName();
    }

}
