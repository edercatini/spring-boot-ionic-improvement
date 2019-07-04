package com.edercatini.spring.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptUtils {

    public static String encrypt(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}