package com.edercatini.spring.enums;

import java.io.Serializable;

public enum PaymentStatus implements Serializable {

    PENDING(1L, "Pending"), PAID(2L, "Paid"), CANCELED(3L, "Canceled");

    private static final long serialVersionUID = -2036939753416081965L;

    private Long id;
    private String description;

    private PaymentStatus(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static PaymentStatus toEnum(Long id) {
        if (id == null) {
            return null;
        }

        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getId().equals(id)) {
                return paymentStatus;
            }
        }

        throw new IllegalArgumentException("Invalid Code");
    }
}