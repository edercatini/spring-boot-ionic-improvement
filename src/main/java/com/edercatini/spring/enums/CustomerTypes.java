package com.edercatini.spring.enums;

import java.io.Serializable;

public enum CustomerTypes implements Serializable {

    LEGAL_PERSON(1L, "Legal Person"), PHYSICAL_PERSON(2L, "Physical Person");

    private static final long serialVersionUID = -2036939753416081965L;
    private Long id;
    private String description;

    private CustomerTypes(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static CustomerTypes toEnum(Long id) {
        if (id == null) {
            return null;
        }

        for (CustomerTypes type : CustomerTypes.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Código inválido");
    }
}