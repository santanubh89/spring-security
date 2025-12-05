package com.eazybytes.eazyschool.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Enumeration;

@Component
@Slf4j
public class AuditFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Filter Name: {}", getFilterName());
        Enumeration<String> initParameterNames = getFilterConfig().getInitParameterNames();
        while(initParameterNames.hasMoreElements()) {
            String initParameterName = initParameterNames.nextElement();
            log.info("Init Parameter: {} : {}", initParameterName, getFilterConfig().getInitParameter(initParameterName));

        };
        chain.doFilter(request, response);
    }

}
