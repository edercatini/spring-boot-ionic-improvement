package com.edercatini.spring.security.handler.authorization;

import static java.util.Objects.isNull;

class AuthorizationUtils {

    private static final String BEARER_PREFIX = "Bearer ";

    static Boolean isValidBearerPrefix(String authorizationHeader) {
        return !isNull(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX);
    }

    static String getBearerToken(String authorizationHeader) {
        return authorizationHeader.replace(BEARER_PREFIX, "");
    }
}