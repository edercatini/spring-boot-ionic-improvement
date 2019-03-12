package com.edercatini.spring.builder.dto;

import com.edercatini.spring.dto.CityDto;

public class CityDtoDataBuilder {

    private static final String OBJECT_NAME = "City";

    private CityDto entity;

    private CityDtoDataBuilder() {
    }

    public static CityDtoDataBuilder dto() {
        CityDtoDataBuilder builder = new CityDtoDataBuilder();
        builder.entity = new CityDto(OBJECT_NAME);
        return builder;
    }

    public CityDtoDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public CityDto build() {
        return this.entity;
    }
}