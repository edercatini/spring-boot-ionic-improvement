package com.edercatini.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
public class Purchase extends AbstractEntity<Long> {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registeredIn;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = ALL, mappedBy = "purchase")
    private Payment payment;

    public Purchase() {
    }

    public Purchase(Date registeredIn, Address deliveryAddress, Customer customer) {
        this.registeredIn = registeredIn;
        this.deliveryAddress = deliveryAddress;
        this.customer = customer;
    }
}