package com.edercatini.spring.security;

import com.edercatini.spring.enums.Role;
import com.edercatini.spring.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

public class UserSecurity implements UserDetails {

    private static final long serialVersionUID = 8330003156486441583L;

    private Long id;
    private String mail;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSecurity() {
    }

    public UserSecurity(Customer customer) {
        this.id = customer.getId();
        this.mail = customer.getMail();
        this.password = customer.getPassword();
        this.authorities = customer.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getDescription()))
                .collect(toList());
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Role role) {
        return getAuthorities().contains(new SimpleGrantedAuthority(role.getDescription()));
    }
}