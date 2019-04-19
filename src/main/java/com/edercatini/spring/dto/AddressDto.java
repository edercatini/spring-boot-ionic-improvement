package com.edercatini.spring.dto;

import com.edercatini.spring.model.Address;
import com.edercatini.spring.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto implements Serializable, DTO {

    private static final long serialVersionUID = -6885090840185178628L;

    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String postalCode;
    private Customer customer;

    @Override
    public Object parseToObject(Object dto) {
        AddressDto reference = (AddressDto) dto;

        return new Address(reference.getPublicPlace(), reference.getNumber(), reference.getComplement(),
            reference.getNeighborhood(), reference.getPostalCode(), reference.getCustomer());
    }
}