package com.edercatini.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class State extends AbstractEntity<Long> implements Serializable {

    private static final long serialVersionUID = -7862451740938453628L;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "state")
    private List<City> cities = new ArrayList<>();

    public State() {
    }

    public State(String name) {
        this.name = name;
    }
}