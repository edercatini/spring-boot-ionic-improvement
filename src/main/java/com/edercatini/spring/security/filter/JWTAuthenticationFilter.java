package com.edercatini.spring.security.filter;

import com.edercatini.spring.security.handler.authentication.AuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationHandler authenticationHandler;
    private AuthenticationManager authenticationManager;

    @Autowired
    public JWTAuthenticationFilter(AuthenticationHandler authenticationHandler, AuthenticationManager authenticationManager) {
        setAuthenticationFailureHandler(authenticationHandler.failure);
        this.authenticationHandler = authenticationHandler;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return authenticationHandler.attempt.authenticate(authenticationManager, request);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        authenticationHandler.success.addTokenToResponseHeaders(response, authResult);
    }
}