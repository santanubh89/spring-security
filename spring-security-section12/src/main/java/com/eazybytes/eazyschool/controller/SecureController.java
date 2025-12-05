package com.eazybytes.eazyschool.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {

    @GetMapping("/secure")
    public String displaySecurePage(Authentication authentication) {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            System.out.println("Secure Page Requested: "+ token.getName());
            System.out.println("Secure Page Requested: "+ token.getDetails());
            return "secure.html";
        } else if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2User = (OAuth2AuthenticationToken) authentication;
            System.out.println("Secure Page Requested: "+ oauth2User.getPrincipal().getAttributes().get("login"));
            return "secure.html";
        }
        System.out.println("Secure Page Requested: "+ SecurityContextHolder.getContext().getAuthentication().getName());
        DefaultOAuth2User user = (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Logged in user: "+user.getAttributes().get("login"));
        System.out.println("Secure Page Requested: "+ SecurityContextHolder.getContext().getAuthentication().getDetails());
        return "secure.html";
    }

}
