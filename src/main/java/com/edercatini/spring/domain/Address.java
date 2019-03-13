package com.edercatini.spring.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
public class Address extends AbstractEntity<Long> {

    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address() {
    }

    public Address(String publicPlace, String number, String complement, String neighborhood, String postalCode, Customer customer) {
        this.publicPlace = publicPlace;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.postalCode = postalCode;
        this.customer = customer;
    }
}