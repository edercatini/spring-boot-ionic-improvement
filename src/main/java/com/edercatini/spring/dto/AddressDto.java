package com.edercatini.spring.dto;

import com.edercatini.spring.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto implements Serializable {

    private static final long serialVersionUID = -6885090840185178628L;

    private String publicPlace;
    private String number;
    private String complement;
    private String neighborhood;
    private String postalCode;
    private Customer customer;
}