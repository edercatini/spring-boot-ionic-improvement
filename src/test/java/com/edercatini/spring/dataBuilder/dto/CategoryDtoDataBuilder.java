package com.edercatini.spring.dataBuilder.dto;

import com.edercatini.spring.dto.CategoryDto;

public class CategoryDtoDataBuilder {

    private static final String OBJECT_NAME = "Category";

    private CategoryDto entity;

    private CategoryDtoDataBuilder() {
    }

    public static CategoryDtoDataBuilder dto() {
        CategoryDtoDataBuilder builder = new CategoryDtoDataBuilder();
        builder.entity = new CategoryDto(OBJECT_NAME);
        return builder;
    }

    public CategoryDtoDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public CategoryDto build() {
        return this.entity;
    }
}