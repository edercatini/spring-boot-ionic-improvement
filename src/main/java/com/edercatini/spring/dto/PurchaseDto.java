package com.edercatini.spring.dto;

import com.edercatini.spring.domain.Address;
import com.edercatini.spring.domain.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PurchaseDto implements Serializable {

    private static final long serialVersionUID = 5007958890255396314L;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registeredIn;

    private Customer customer;
    private Address deliveryAddress;

    public PurchaseDto() {
    }

    public PurchaseDto(Date registeredIn, Address deliveryAddress, Customer customer) {
        this.registeredIn = registeredIn;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
    }
}