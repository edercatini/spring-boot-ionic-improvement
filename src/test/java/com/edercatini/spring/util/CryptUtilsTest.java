package com.edercatini.spring.util;

import com.edercatini.spring.ComponentTest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CryptUtilsTest extends ComponentTest {

    private static final Integer EXPECTED_LENGTH = 60;

    @Test
    public void mustEncryptAPassword() {
        assertThat(CryptUtils.encrypt("temp-password").length(), is(equalTo(EXPECTED_LENGTH)));
    }
}