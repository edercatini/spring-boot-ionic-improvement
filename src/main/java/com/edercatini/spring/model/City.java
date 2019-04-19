package com.edercatini.spring.model;

import com.edercatini.spring.dto.CityDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
public class City extends AbstractEntity implements Serializable {

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

    @Override
    public Object parseToDto(Object object) {
        City reference = (City) object;
        return new CityDto(reference.getName());
    }

    public City updateByDTO(City reference, CityDto dto) {
        reference.setName(dto.getName());
        return reference;
    }
}