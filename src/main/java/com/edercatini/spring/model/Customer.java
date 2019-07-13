package com.edercatini.spring.model;

import com.edercatini.spring.dto.CustomerDto;
import com.edercatini.spring.enums.Role;
import com.edercatini.spring.enums.CustomerType;
import com.edercatini.spring.util.CryptUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Customer extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -1235205585871107822L;

    private String name;
    private String mail;
    private String document;
    private Long type;
    private String password;

    @OneToMany(mappedBy = "customer", cascade = ALL, fetch = LAZY)
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "PHONES")
    private Set<String> phones = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES")
    private Set<Long> roles = new HashSet<>();

    public Customer() {
    }

    public Customer(String name, String mail, String document, CustomerType type, String password) {
        this.name = name;
        this.mail = mail;
        this.document = document;
        this.type = type.getId();
        this.password = password;

        this.addRole(Role.CUSTOMER);
    }

    public Set<Role> getRoles() {
        return this.roles
                .stream()
                .map(Role::toEnum)
                .collect(toSet());
    }

    public void addRole(Role role) {
        this.roles.add(role.getCode());
    }

    @Override
    public Object parseToDto(Object object) {
        Customer reference = (Customer) object;
        return new CustomerDto(reference.getName(), reference.getMail(), reference.getDocument(), reference.getType(), reference.getPhones(), CryptUtils.encrypt(reference.getPassword()));
    }

    public Customer updateByDTO(Customer reference, CustomerDto dto) {
        reference.setName(dto.getName());
        reference.setMail(dto.getMail());
        reference.setDocument(dto.getDocument());
        reference.setType(dto.getType());
        return reference;
    }
}