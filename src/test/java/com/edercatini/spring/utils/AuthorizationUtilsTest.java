package com.edercatini.spring.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AuthorizationUtilsTest extends UtilsTest {

    private static final String VALID_TOKEN = "Bearer Token";
    private static final String INVALID_TOKEN = "Invalid Token";
    
    @Test
    public void mustAssertValidPrefix() {
        assertTrue(AuthorizationUtils.isValidBearerPrefix(VALID_TOKEN));
    }

    @Test
    public void mustAssertInvalidPrefix() {
        assertFalse(AuthorizationUtils.isValidBearerPrefix(INVALID_TOKEN));
    }

    @Test
    public void mustRemoveBearerPrefix() {
        assertThat(AuthorizationUtils.getBearerToken(VALID_TOKEN), is(equalTo("Token")));
    }

    @Test
    public void mustNotRemoveAnything() {
        assertThat(AuthorizationUtils.getBearerToken(INVALID_TOKEN), is(equalTo(INVALID_TOKEN)));
    }
}