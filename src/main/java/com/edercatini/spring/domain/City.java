package com.edercatini.spring.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
public class City extends AbstractEntity<Long> implements Serializable {

    private static final long serialVersionUID = 4529888219276554536L;

    private String name;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }
}