package com.edercatini.spring.security.handler.authorization;

import com.edercatini.spring.security.handler.JWTHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

@Component
public class AuthorizationHandler {

    private static final String AUTHORIZATION = "Authorization";

    private final JWTHandler jwtHandler;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthorizationHandler(JWTHandler jwtHandler, UserDetailsService userDetailsService) {
        this.jwtHandler = jwtHandler;
        this.userDetailsService = userDetailsService;
    }

    public void authorize(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (AuthorizationUtils.isValidBearerPrefix(authorizationHeader)) {
            UsernamePasswordAuthenticationToken auth = this.getAuthentication(AuthorizationUtils.getBearerToken(authorizationHeader));

            if (!isNull(auth)) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (jwtHandler.isValidToken(token)) {
            UserDetails user = userDetailsService.loadUserByUsername(jwtHandler.getUsername(token));
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }

        return null;
    }
}