package com.edercatini.spring.domain;

import com.edercatini.spring.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@JsonTypeName("credit_card_payment")
@Data
public class CreditCardPayment extends Payment implements Serializable {

    private static final long serialVersionUID = -7285089655852248349L;

    private Integer numberOfBills;

    public CreditCardPayment() {
    }

    public CreditCardPayment(PaymentStatus paymentStatus, Integer numberOfBills) {
        super(paymentStatus);
        this.numberOfBills = numberOfBills;
    }

    public CreditCardPayment(PaymentStatus paymentStatus, Purchase purchase, Integer numberOfBills) {
        super(paymentStatus);
        this.numberOfBills = numberOfBills;
        this.setPurchase(purchase);
    }
}