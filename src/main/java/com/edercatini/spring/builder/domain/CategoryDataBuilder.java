package com.edercatini.spring.builder.domain;

import com.edercatini.spring.domain.Category;

public class CategoryDataBuilder {

    private static final String OBJECT_NAME = "Category";

    private Category entity;

    private CategoryDataBuilder() {
    }

    public static CategoryDataBuilder anObject() {
        CategoryDataBuilder builder = new CategoryDataBuilder();
        builder.entity = new Category(OBJECT_NAME);
        return builder;
    }

    public CategoryDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public Category build() {
        return this.entity;
    }
}