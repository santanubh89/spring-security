package com.spring.security.springsecuritysection5.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("eazybank-error-reason", "Authorization failed - Forbidden");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        String payload = createPayload(request, accessDeniedException);
        response.getWriter().write(payload);
    }

    private static String createPayload(HttpServletRequest request, AccessDeniedException accessDeniedException) {
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = accessDeniedException != null && accessDeniedException.getMessage() != null ? accessDeniedException.getMessage() : "Unauthorized";
        String payload = String.format("""
                {"timestamp": "%s",
                    "status": %d,
                    "error": "%s",
                    "message": "%s",
                    "path": "%s"
                }
                """, currentTimeStamp, HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), message, request.getRequestURI());
        return payload;
    }
}
