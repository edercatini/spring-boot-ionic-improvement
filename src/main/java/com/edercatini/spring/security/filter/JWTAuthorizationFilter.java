package com.edercatini.spring.security.filter;

import com.edercatini.spring.security.handler.authorization.AuthorizationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final AuthorizationHandler authorizationHandler;

    @Autowired
    public JWTAuthorizationFilter(AuthorizationHandler authorizationHandler, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authorizationHandler = authorizationHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        authorizationHandler.authorize(request, response, chain);
    }
}