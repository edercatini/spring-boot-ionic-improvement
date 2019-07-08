package com.edercatini.spring.model;

import com.edercatini.spring.dto.AddressDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
public class Address extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1636590043603307268L;

    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String postalCode;

    @JsonIgnore
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

    @Override
    public Object parseToDto(Object object) {
        Address reference = (Address) object;

        return new AddressDto(reference.getPublicPlace(), reference.getNumber(), reference.getComplement(),
            reference.getNeighborhood(), reference.getPostalCode(), reference.getCustomer());
    }

    public Address updateByDTO(Address reference, AddressDto dto) {
        reference.setPublicPlace(dto.getPublicPlace());
        reference.setNumber(dto.getNumber());
        reference.setComplement(dto.getComplement());
        reference.setNeighborhood(dto.getNeighborhood());
        reference.setPostalCode(dto.getPostalCode());
        reference.setCustomer(dto.getCustomer());

        return reference;
    }
}