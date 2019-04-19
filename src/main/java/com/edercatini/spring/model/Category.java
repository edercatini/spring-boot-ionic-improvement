package com.edercatini.spring.model;

import com.edercatini.spring.dto.CategoryDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Category extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 4620028651219672252L;

    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Override
    public Object parseToDto(Object object) {
        Category reference = (Category) object;
        return new CategoryDto(reference.getName());
    }

    public Category updateByDTO(Category reference, CategoryDto dto) {
        reference.setName(dto.getName());
        return reference;
    }
}