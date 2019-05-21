package com.edercatini.spring.dataBuilder.dto;

import com.edercatini.spring.dto.ProductDto;

public class ProductDtoDataBuilder {

    private static final String OBJECT_NAME = "Product";
    private static final Double OBJECT_PRICE = 1000d;

    private ProductDto entity;

    private ProductDtoDataBuilder() {
    }

    public static ProductDtoDataBuilder dto() {
        ProductDtoDataBuilder builder = new ProductDtoDataBuilder();
        builder.entity = new ProductDto(OBJECT_NAME, OBJECT_PRICE);
        return builder;
    }

    public ProductDtoDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public ProductDtoDataBuilder withPrice(Double price) {
        entity.setPrice(price);
        return this;
    }

    public ProductDto build() {
        return this.entity;
    }
}