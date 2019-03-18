package com.edercatini.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "id.purchase")
    private List<PurchaseItem> items = new ArrayList<>();

    public Purchase() {
    }

    public Purchase(Date registeredIn, Address deliveryAddress, Customer customer) {
        this.registeredIn = registeredIn;
        this.deliveryAddress = deliveryAddress;
        this.customer = customer;
    }
}