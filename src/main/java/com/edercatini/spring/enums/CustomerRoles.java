package com.edercatini.spring.enums;

import java.io.Serializable;

public enum CustomerRoles implements Serializable {

    ADMIN(1L, "ROLE_ADMIN"),
    CUSTOMER(2L, "ROLE_CUSTOMER");

    private static final long serialVersionUID = -2036939753416081965L;

    private Long code;
    private String description;

    private CustomerRoles(Long code, String description) {
        this.code = code;
        this.description = description;
    }

    public Long getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CustomerRoles toEnum(Long id) {
        if (id == null) {
            return null;
        }

        for (CustomerRoles type : CustomerRoles.values()) {
            if (type.getCode().equals(id)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid Code");
    }
}