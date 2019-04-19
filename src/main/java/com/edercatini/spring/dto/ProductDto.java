package com.edercatini.spring.dto;

import com.edercatini.spring.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable, DTO {

    private static final long serialVersionUID = -6885090840185178628L;

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 60, message = "Nome deve conter entre {min} e {max} caracteres")
    private String name;

    @NotNull
    private Double price;

    @Override
    public Object parseToObject(Object dto) {
        ProductDto reference = (ProductDto) dto;
        return new Product(reference.getName(), reference.getPrice());
    }
}