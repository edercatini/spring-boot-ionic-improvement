package com.edercatini.spring.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
public class Address extends AbstractEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1636590043603307268L;

    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

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

    public Address(String publicPlace, String number, String complement, String neighborhood, String postalCode, Customer customer, City city) {
        this.publicPlace = publicPlace;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.postalCode = postalCode;
        this.customer = customer;
        this.city = city;
    }
}