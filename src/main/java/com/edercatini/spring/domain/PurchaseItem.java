package com.edercatini.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
public class PurchaseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private PurchaseItemPK id = new PurchaseItemPK();

    private Double discount;

    private Integer quantity;

    private Double price;

    public PurchaseItem() {
    }

    public PurchaseItem(Purchase purchase, Product product, Double discount, Integer quantity, Double price) {
        super();

        id.setPurchase(purchase);
        id.setProduct(product);

        this.discount = discount;
        this.quantity = quantity;
        this.price = price;
    }
}