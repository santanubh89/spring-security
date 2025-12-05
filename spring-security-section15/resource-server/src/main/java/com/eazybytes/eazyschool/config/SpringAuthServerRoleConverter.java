package com.eazybytes.eazyschool.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SpringAuthServerRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        List<String> scopes = source.getClaim("roles");
        if (scopes == null || scopes.isEmpty()) {
            return List.of();
        }
        return scopes.stream().map(scopeName -> "ROLE_" + scopeName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
