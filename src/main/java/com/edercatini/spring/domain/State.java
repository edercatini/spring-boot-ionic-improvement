package com.edercatini.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class State extends AbstractEntity<Long> {

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "state")
    private List<City> cities;

    public State() {
    }

    public State(String name) {
        this.name = name;
    }
}