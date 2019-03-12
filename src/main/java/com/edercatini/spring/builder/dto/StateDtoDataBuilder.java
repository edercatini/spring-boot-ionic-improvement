package com.edercatini.spring.builder.dto;

import com.edercatini.spring.dto.StateDto;

public class StateDtoDataBuilder {

    private static final String OBJECT_NAME = "State";

    private StateDto entity;

    private StateDtoDataBuilder() {
    }

    public static StateDtoDataBuilder dto() {
        StateDtoDataBuilder builder = new StateDtoDataBuilder();
        builder.entity = new StateDto(OBJECT_NAME);
        return builder;
    }

    public StateDtoDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public StateDto build() {
        return this.entity;
    }
}