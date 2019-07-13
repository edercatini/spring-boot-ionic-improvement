package com.edercatini.spring.dataBuilder.domain;

import com.edercatini.spring.enums.Role;
import com.edercatini.spring.enums.CustomerType;
import com.edercatini.spring.model.Customer;
import com.edercatini.spring.util.CryptUtils;

import static com.edercatini.spring.enums.CustomerType.toEnum;

public class CustomerDataBuilder {

    private static final String OBJECT_NAME = "Customer";
    private static final String OBJECT_MAIL = "test@test.com";
    private static final String OBJECT_DOCUMENT = "9999999999";
    private static final String OBJECT_PASSWORD = "test";
    private static final CustomerType OBJECT_TYPE = CustomerType.PHYSICAL_PERSON;

    private Customer entity;

    private CustomerDataBuilder() {
    }

    public static CustomerDataBuilder anObject() {
        CustomerDataBuilder builder = new CustomerDataBuilder();
        builder.entity = new Customer(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE, CryptUtils.encrypt(OBJECT_PASSWORD));
        builder.entity.addRole(Role.ADMIN);
        return builder;
    }

    public CustomerDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public CustomerDataBuilder withMail(String mail) {
        entity.setMail(mail);
        return this;
    }

    public CustomerDataBuilder withDocument(String document) {
        entity.setDocument(document);
        return this;
    }

    public CustomerDataBuilder withType(CustomerType type) {
        entity.setType(toEnum(type.getId()).getId());
        return this;
    }

    public CustomerDataBuilder withRole(Role role) {
        entity.addRole(role);
        return this;
    }

    public Customer build() {
        return this.entity;
    }
}