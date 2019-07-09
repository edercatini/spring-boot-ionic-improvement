package com.edercatini.spring.security.handler.authentication;

import com.edercatini.spring.dto.CredentialsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@Component
public final class AuthenticationAttempt {

    public Authentication authenticate(AuthenticationManager authenticationManager, HttpServletRequest request) {
        try {
            CredentialsDto credentialsDto = new ObjectMapper().readValue(request.getInputStream(), CredentialsDto.class);
            return authenticationManager.authenticate(this.getAuthenticationToken(credentialsDto));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(CredentialsDto dto) {
        return new UsernamePasswordAuthenticationToken(dto.getMail(), dto.getPassword(), new ArrayList<>());
    }
}