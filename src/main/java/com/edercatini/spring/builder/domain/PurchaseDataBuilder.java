package com.edercatini.spring.builder.domain;

import com.edercatini.spring.domain.Address;
import com.edercatini.spring.domain.Customer;
import com.edercatini.spring.domain.Purchase;

import java.util.Date;

public class PurchaseDataBuilder {

    private static final Date REGISTERED_IN = new Date();
    private static final Address ADDRESS = new Address();
    private static final Customer CUSTOMER = new Customer();

    private Purchase entity;

    private PurchaseDataBuilder() {
    }

    public static PurchaseDataBuilder anObject() {
        PurchaseDataBuilder builder = new PurchaseDataBuilder();
        builder.entity = new Purchase(REGISTERED_IN, ADDRESS, CUSTOMER);
        return builder;
    }

    public PurchaseDataBuilder withRegisterDate(Date date) {
        this.entity.setRegisteredIn(date);
        return this;
    }

    public PurchaseDataBuilder withDeliveryAddress(Address address) {
        this.entity.setDeliveryAddress(address);
        return this;
    }

    public PurchaseDataBuilder withCustomer(Customer customer) {
        this.entity.setCustomer(customer);
        return this;
    }

    public Purchase build() {
        return this.entity;
    }
}