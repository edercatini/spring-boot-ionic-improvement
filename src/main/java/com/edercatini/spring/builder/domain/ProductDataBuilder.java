package com.edercatini.spring.builder.domain;

import com.edercatini.spring.model.Product;

public class ProductDataBuilder {

    private static final String OBJECT_NAME = "Product";
    private static final Double OBJECT_PRICE = 1000d;

    private Product entity;

    private ProductDataBuilder() {
    }

    public static ProductDataBuilder anObject() {
        ProductDataBuilder builder = new ProductDataBuilder();
        builder.entity = new Product(OBJECT_NAME, OBJECT_PRICE);
        return builder;
    }

    public ProductDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public ProductDataBuilder withPrice(Double price) {
        entity.setPrice(price);
        return this;
    }

    public Product build() {
        return this.entity;
    }
}