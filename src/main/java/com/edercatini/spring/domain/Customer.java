package com.edercatini.spring.domain;

import com.edercatini.spring.enums.CustomerTypes;
import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer extends AbstractEntity<Long> {

    private String name;
    private String mail;
    private String document;
    private Long type;

    @ElementCollection
    @CollectionTable(name = "PHONES")
    private Set<String> phones = new HashSet<>();

    public Customer() {
    }

    public Customer(String name, String mail, String document, CustomerTypes type) {
        this.name = name;
        this.mail = mail;
        this.document = document;
        this.type = type.getId();
    }
}