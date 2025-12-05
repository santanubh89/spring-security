package com.spring.security.springsecuritysection5.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("eazybank-error-reason", "Authentication failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        String payload = createPayload(request, authException);
        response.getWriter().write(payload);
    }

    private static String createPayload(HttpServletRequest request, AuthenticationException authException) {
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = authException != null && authException.getMessage() != null ? authException.getMessage() : "Unauthorized";
        String payload = String.format("""
                {"timestamp": "%s",
                    "statusCode": %d,
                    "errorCategory": "%s",
                    "message": "%s",
                    "path": "%s"
                }
                """, currentTimeStamp, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), message, request.getRequestURI());
        return payload;
    }
}
