package com.edercatini.spring.model;

import com.edercatini.spring.dto.StateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class State extends AbstractEntity implements Serializable {

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

    @Override
    public Object parseToDto(Object object) {
        State reference = (State) object;
        return new StateDto(reference.getName());
    }

    public State updateByDTO(State reference, StateDto dto) {
        reference.setName(dto.getName());
        return reference;
    }
}