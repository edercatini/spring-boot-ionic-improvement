package com.edercatini.spring.dataBuilder.domain;

import com.edercatini.spring.model.City;

public class CityDataBuilder {

    private static final String OBJECT_NAME = "City";

    private City entity;

    private CityDataBuilder() {
    }

    public static CityDataBuilder anObject() {
        CityDataBuilder builder = new CityDataBuilder();
        builder.entity = new City(OBJECT_NAME);
        return builder;
    }

    public CityDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public City build() {
        return this.entity;
    }
}