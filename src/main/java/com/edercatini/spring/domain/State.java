package com.edercatini.spring.domain;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class State extends AbstractEntity<Long> {

    private String name;

    public State() {
    }

    public State(String name) {
        this.name = name;
    }
}