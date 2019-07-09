package com.edercatini.spring.security.handler.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHandler {

    public AuthenticationAttempt attempt;

    public AuthenticationSuccessful success;

    public AuthenticationFailure failure;

    @Autowired
    public AuthenticationHandler(AuthenticationAttempt attempt, AuthenticationSuccessful success, AuthenticationFailure failure) {
        this.attempt = attempt;
        this.success = success;
        this.failure = failure;
    }
}