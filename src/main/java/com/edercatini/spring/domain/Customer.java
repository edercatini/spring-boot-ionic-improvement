package com.edercatini.spring.domain;

import com.edercatini.spring.enums.CustomerTypes;
import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Customer extends AbstractEntity<Long> {

    private String name;
    private String mail;
    private String document;
    private Long type;

    @OneToMany(mappedBy = "customer", cascade = ALL, fetch = LAZY)
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "PHONES")
    private Set<String> phones = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String mail, String document, CustomerTypes type) {
        this.name = name;
        this.mail = mail;
        this.document = document;
        this.type = type.getId();
    }
}