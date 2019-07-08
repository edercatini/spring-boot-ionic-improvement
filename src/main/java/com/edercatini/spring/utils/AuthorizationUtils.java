package com.edercatini.spring.utils;

import static java.util.Objects.isNull;

public class AuthorizationUtils {

    private static final String BEARER_PREFIX = "Bearer ";

    public static Boolean isValidBearerPrefix(String authorizationHeader) {
        return !isNull(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX);
    }

    public static String getBearerToken(String authorizationHeader) {
        return authorizationHeader.replace(BEARER_PREFIX, "");
    }
}