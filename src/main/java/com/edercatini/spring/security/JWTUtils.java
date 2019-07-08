package com.edercatini.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.util.Objects.isNull;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(SignatureAlgorithm.HS512, this.secret.getBytes())
                .compact();
    }

    public boolean isValidToken(String token) {
        Claims claims = this.getClaims(token);

        if (!isNull(claims)) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();

            if (!isNull(username) && this.isTokenExpired(expirationDate)) {
                return true;
            }
        }

        return false;
    }

    public String getUsername(String token) {
        Claims claims = this.getClaims(token);

        if (!isNull(claims)) {
            return claims.getSubject();
        }

        return null;
    }

    private Boolean isTokenExpired(Date tokenExpirationDate) {
        return !isNull(tokenExpirationDate) && new Date(System.currentTimeMillis()).before(tokenExpirationDate);
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception exception) {
            return null;
        }
    }
}