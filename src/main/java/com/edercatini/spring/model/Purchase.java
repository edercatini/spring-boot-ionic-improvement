package com.edercatini.spring.model;

import com.edercatini.spring.dto.PurchaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
public class Purchase extends AbstractEntity {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registeredIn;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @JsonIgnore
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

    @Override
    public Object parseToDto(Object object) {
        Purchase reference = (Purchase) object;
        return new PurchaseDto(reference.getRegisteredIn(), reference.getDeliveryAddress(), getCustomer());
    }

    public Purchase updateByDTO(Purchase reference, PurchaseDto dto) {
        reference.setRegisteredIn(dto.getRegisteredIn());
        reference.setDeliveryAddress(dto.getDeliveryAddress());
        reference.setCustomer(dto.getCustomer());
        return reference;
    }
}