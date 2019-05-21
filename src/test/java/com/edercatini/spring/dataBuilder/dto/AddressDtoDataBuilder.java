package com.edercatini.spring.dataBuilder.dto;

import com.edercatini.spring.model.Customer;
import com.edercatini.spring.dto.AddressDto;

public class AddressDtoDataBuilder {

    private static final String OBJECT_PUBLIC_PLACE = "Test";
    private static final String OBJECT_NUMBER = "999";
    private static final String OBJECT_COMPLEMENT = "Complement";
    private static final String OBJECT_NEIGHBORHOOD = "Neighborhood";
    private static final String OBJECT_POSTAL_CODE = "13800000";
    private static final Customer CUSTOMER = new Customer();

    private AddressDto entity;

    private AddressDtoDataBuilder() {
    }

    public static AddressDtoDataBuilder dto() {
        AddressDtoDataBuilder builder = new AddressDtoDataBuilder();
        builder.entity = new AddressDto(OBJECT_PUBLIC_PLACE, OBJECT_NUMBER, OBJECT_COMPLEMENT, OBJECT_NEIGHBORHOOD, OBJECT_POSTAL_CODE, CUSTOMER);
        return builder;
    }

    public AddressDtoDataBuilder withPublicPlace(String publicPlace) {
        entity.setPublicPlace(publicPlace);
        return this;
    }

    public AddressDtoDataBuilder withNumber(String number) {
        entity.setNumber(number);
        return this;
    }

    public AddressDtoDataBuilder withComplement(String complement) {
        entity.setComplement(complement);
        return this;
    }

    public AddressDtoDataBuilder withNeighborhood(String neighborhood) {
        entity.setNeighborhood(neighborhood);
        return this;
    }

    public AddressDtoDataBuilder withPostalCode(String postalCode) {
        entity.setPostalCode(postalCode);
        return this;
    }

    public AddressDtoDataBuilder withCustomer(Customer customer) {
        entity.setCustomer(customer);
        return this;
    }

    public AddressDto build() {
        return this.entity;
    }
}