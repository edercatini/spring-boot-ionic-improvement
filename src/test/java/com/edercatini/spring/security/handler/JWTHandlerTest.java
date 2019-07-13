package com.edercatini.spring.security.handler;

import com.edercatini.spring.ComponentTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JWTHandlerTest extends ComponentTest {

    private static final String USERNAME = "test@test.com";

    @Autowired
    private JWTHandler jwtHandler;

    @Test
    public void mustAssertValidToken() {
        assertTrue(jwtHandler.isValidToken(mockToken()));
    }

    @Test
    public void mustRetrieveTokenUserName() {
        assertEquals(USERNAME, jwtHandler.getUsername(mockToken()));
    }

    private String mockToken() {
        return jwtHandler.generateToken(USERNAME);
    }
}