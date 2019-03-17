package com.edercatini.spring.domain;

import com.edercatini.spring.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@JsonTypeName("bank_slip_payment")
@Data
public class BankSlipPayment extends Payment implements Serializable {

    private static final long serialVersionUID = -4866629838087294575L;

    private Date dueDate;
    private Date paymentDate;

    public BankSlipPayment() {
    }

    public BankSlipPayment(PaymentStatus paymentStatus, Date dueDate, Date paymentDate) {
        super(paymentStatus);
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
    }
}