package com.edercatini.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Product extends AbstractEntity<Long> {

    private String name;
    private Double price;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "PRODUCT_CATEGORY", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id.product")
    private Set<PurchaseItem> items = new HashSet<>();

    public Product() {
    }

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    @JsonIgnore
    public List<Purchase> getPurchases() {
        List<Purchase> lista = new ArrayList<>();

        for (PurchaseItem purchaseItem : items) {
            lista.add(purchaseItem.getPurchase());
        }

        return lista;
    }
}