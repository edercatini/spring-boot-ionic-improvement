package com.edercatini.spring.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category extends AbstractEntity<Long> implements Serializable {

    private static final long serialVersionUID = 4620028651219672252L;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }
}