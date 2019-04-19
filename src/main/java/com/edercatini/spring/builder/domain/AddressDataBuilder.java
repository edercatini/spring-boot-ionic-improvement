package com.edercatini.spring.builder.domain;

import com.edercatini.spring.model.Address;
import com.edercatini.spring.model.Customer;

public class AddressDataBuilder {

    private static final String OBJECT_PUBLIC_PLACE = "Test";
    private static final String OBJECT_NUMBER = "999";
    private static final String OBJECT_COMPLEMENT = "Complement";
    private static final String OBJECT_NEIGHBORHOOD = "Neighborhood";
    private static final String OBJECT_POSTAL_CODE = "13800000";
    private static final Customer CUSTOMER = new Customer();

    private Address entity;

    private AddressDataBuilder() {
    }

    public static AddressDataBuilder anObject() {
        AddressDataBuilder builder = new AddressDataBuilder();
        builder.entity = new Address(OBJECT_PUBLIC_PLACE, OBJECT_NUMBER, OBJECT_COMPLEMENT, OBJECT_NEIGHBORHOOD, OBJECT_POSTAL_CODE, CUSTOMER);
        return builder;
    }

    public AddressDataBuilder withPublicPlace(String publicPlace) {
        entity.setPublicPlace(publicPlace);
        return this;
    }

    public AddressDataBuilder withNumber(String number) {
        entity.setNumber(number);
        return this;
    }

    public AddressDataBuilder withComplement(String complement) {
        entity.setComplement(complement);
        return this;
    }

    public AddressDataBuilder withNeighborhood(String neighborhood) {
        entity.setNeighborhood(neighborhood);
        return this;
    }

    public AddressDataBuilder withPostalCode(String postalCode) {
        entity.setPostalCode(postalCode);
        return this;
    }

    public AddressDataBuilder withCustomer(Customer customer) {
        entity.setCustomer(customer);
        return this;
    }

    public Address build() {
        return this.entity;
    }
}