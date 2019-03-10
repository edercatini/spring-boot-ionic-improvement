package com.edercatini.spring.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class City extends AbstractEntity<Long> {

    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }
}