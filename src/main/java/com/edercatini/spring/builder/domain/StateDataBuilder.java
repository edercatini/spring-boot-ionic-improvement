package com.edercatini.spring.builder.domain;

import com.edercatini.spring.model.State;

public class StateDataBuilder {

    private static final String OBJECT_NAME = "State";

    private State entity;

    private StateDataBuilder() {
    }

    public static StateDataBuilder anObject() {
        StateDataBuilder builder = new StateDataBuilder();
        builder.entity = new State(OBJECT_NAME);
        return builder;
    }

    public StateDataBuilder withName(String name) {
        entity.setName(name);
        return this;
    }

    public State build() {
        return this.entity;
    }
}