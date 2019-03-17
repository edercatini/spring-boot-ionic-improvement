package com.edercatini.spring.domain;

import com.edercatini.spring.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "@type")
@Data
public abstract class Payment extends AbstractEntity<Long> {

    private Long paymentStatus;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "purchase_id")
    @MapsId
    private Purchase purchase;

    public Payment() {
    }

    public Payment(PaymentStatus paymentStatus) {
        this.setId(null);
        this.paymentStatus = paymentStatus.getId();
    }
}