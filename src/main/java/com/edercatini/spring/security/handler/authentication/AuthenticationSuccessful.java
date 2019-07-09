package com.edercatini.spring.security.handler.authentication;

import com.edercatini.spring.security.UserSecurity;
import com.edercatini.spring.security.handler.JWTHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
public final class AuthenticationSuccessful {

    private JWTHandler jwtHandler;

    @Autowired
    public AuthenticationSuccessful(JWTHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    public void addTokenToResponseHeaders(HttpServletResponse response, Authentication authResult) {
        response.addHeader("Authorization","Bearer " + jwtHandler.generateToken(this.getUsername(authResult)));
    }

    private String getUsername(Authentication authResult) {
        return ((UserSecurity) authResult.getPrincipal()).getUsername();
    }
}