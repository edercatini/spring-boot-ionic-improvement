package com.edercatini.spring.dataBuilder.dto;

import com.edercatini.spring.model.Address;
import com.edercatini.spring.model.Customer;
import com.edercatini.spring.dto.PurchaseDto;

import java.util.Date;

public class PurchaseDtoDataBuilder {

    private static final Date REGISTERED_IN = new Date();
    private static final Address ADDRESS = new Address();
    private static final Customer CUSTOMER = new Customer();
    
    private PurchaseDto entity;

    private PurchaseDtoDataBuilder() {
    }

    public static PurchaseDtoDataBuilder dto() {
        PurchaseDtoDataBuilder builder = new PurchaseDtoDataBuilder();
        builder.entity = new PurchaseDto(REGISTERED_IN, ADDRESS, CUSTOMER);
        return builder;
    }

    public PurchaseDtoDataBuilder withRegisterDate(Date date) {
        this.entity.setRegisteredIn(date);
        return this;
    }

    public PurchaseDtoDataBuilder withDeliveryAddress(Address address) {
        this.entity.setDeliveryAddress(address);
        return this;
    }

    public PurchaseDtoDataBuilder withCustomer(Customer customer) {
        this.entity.setCustomer(customer);
        return this;
    }
    
    public PurchaseDto build() {
        return this.entity;
    }
}