package com.example.demo.Filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("Incoming request: " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());

        httpRequest.getParameterMap().forEach((key, value) ->
                System.out.println(key + " = " + String.join(",", value + value.getClass().getName()))
        );

        chain.doFilter(request, response);  // Continue with the next filter in the chain
    }
}

