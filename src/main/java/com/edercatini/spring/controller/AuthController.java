package com.edercatini.spring.controller;

import com.edercatini.spring.exception.AuthorizationException;
import com.edercatini.spring.security.UserSecurity;
import com.edercatini.spring.security.handler.JWTHandler;
import com.edercatini.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JWTHandler jwtHandler;

    @Autowired
    public AuthController(JWTHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @PostMapping("/refresh_token")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void refreshToken(HttpServletResponse response) {
        UserSecurity user = UserService.authenticated();

        if (Objects.isNull(user)) {
            throw new AuthorizationException("Access Denied");
        }

        response.addHeader("Authorization", "Bearer " + jwtHandler.generateToken(user.getUsername()));
    }
}
