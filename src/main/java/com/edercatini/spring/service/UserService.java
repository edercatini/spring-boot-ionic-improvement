package com.edercatini.spring.service;

import com.edercatini.spring.security.UserSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSecurity authenticated() {
        try {
            return (UserSecurity) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception exception) {
            return null;
        }
    }
}