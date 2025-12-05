package com.eazybytes.eazyschool.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;

public class CustomRequestValidationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (null != authorization) {
            authorization = authorization.trim();
            if(StringUtils.startsWithIgnoreCase(authorization, "Basic ")) {
                String authorizationToken = authorization.substring(6); // Extract the token after "Basic "
                String decodedToken = new String(Base64.getDecoder().decode(authorizationToken));
                if (decodedToken.contains(":")) {
                    String[] credentials = decodedToken.split(":", 2);
                    String username = credentials[0];
                    if (username.contains("test")) {
                        httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username cannot contain 'test'");
                        return;
                    }
                }
            } else {
                throw new BadCredentialsException("Authorization header is not of type Basic");
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
