package com.edercatini.spring.dataBuilder.dto;

import com.edercatini.spring.dto.CustomerDto;
import com.edercatini.spring.enums.CustomerType;
import com.edercatini.spring.util.CryptUtils;

import java.util.HashSet;
import java.util.Set;

import static com.edercatini.spring.enums.CustomerType.toEnum;

public class CustomerDtoDataBuilder {

    private static final String OBJECT_NAME = "Customer";
    private static final String OBJECT_MAIL = "test@test.com";
    private static final String OBJECT_DOCUMENT = "9999999999";
    private static final Long OBJECT_TYPE = CustomerType.PHYSICAL_PERSON.getId();
    private static final String OBJECT_PASSWORD = "test";
    private static final Set<String> PHONES = new HashSet<>();

    private CustomerDto entity;

    private CustomerDtoDataBuilder() {
    }

    public static CustomerDtoDataBuilder dto() {
        CustomerDtoDataBuilder builder = new CustomerDtoDataBuilder();
        builder.entity = new CustomerDto(OBJECT_NAME, OBJECT_MAIL, OBJECT_DOCUMENT, OBJECT_TYPE, PHONES, CryptUtils.encrypt(OBJECT_PASSWORD));
        return builder;
    }

    public CustomerDtoDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public CustomerDtoDataBuilder withMail(String mail) {
        entity.setMail(mail);
        return this;
    }

    public CustomerDtoDataBuilder withDocument(String document) {
        entity.setDocument(document);
        return this;
    }

    public CustomerDtoDataBuilder withType(CustomerType type) {
        entity.setType(toEnum(type.getId()).getId());
        return this;
    }

    public CustomerDtoDataBuilder withPhones(Set<String> phones) {
        entity.setPhones(phones);
        return this;
    }

    public CustomerDto build() {
        return this.entity;
    }
}