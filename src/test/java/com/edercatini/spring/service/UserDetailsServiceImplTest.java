package com.edercatini.spring.service;

import com.edercatini.spring.repository.CustomerRepository;
import com.edercatini.spring.security.UserSecurity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.edercatini.spring.dataBuilder.domain.CustomerDataBuilder.anObject;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDetailsServiceImplTest extends ServiceTest {

    private static final String VALID_MAIL = "mail@mail.com";

    private static final String INVALID_MAIL = "invalid@invalid.com";

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private UserDetailsService service;

    @Before
    public void setUp() {
        repository.save(anObject().withMail(VALID_MAIL).build());
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void mustRetrieveValidUser() {
        UserSecurity user = (UserSecurity) service.loadUserByUsername(VALID_MAIL);
        assertThat(user.getUsername(), is(equalTo(VALID_MAIL)));
        assertThat(user.getAuthorities().isEmpty(), is(equalTo(false)));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void mustThrowExceptionDueToUserNotFound() {
        service.loadUserByUsername(INVALID_MAIL);
    }
}