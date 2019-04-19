package com.edercatini.spring.model;

import com.edercatini.spring.dto.CustomerDto;
import com.edercatini.spring.enums.CustomerTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Customer extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = -1235205585871107822L;

    private String name;
    private String mail;
    private String document;
    private Long type;

    @OneToMany(mappedBy = "customer", cascade = ALL, fetch = LAZY)
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "PHONES")
    private Set<String> phones = new HashSet<>();

    @JsonIgnore
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

    @Override
    public Object parseToDto(Object object) {
        Customer reference = (Customer) object;
        return new CustomerDto(reference.getName(), reference.getMail(), reference.getDocument(), reference.getType(), reference.getPhones());
    }

    public Customer updateByDTO(Customer reference, CustomerDto dto) {
        reference.setName(dto.getName());
        reference.setMail(dto.getMail());
        reference.setDocument(dto.getDocument());
        reference.setType(dto.getType());
        return reference;
    }
}