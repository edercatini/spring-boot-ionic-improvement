package com.edercatini.spring.dto;

import com.edercatini.spring.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.edercatini.spring.enums.CustomerTypes.toEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable, DTO {

    private static final long serialVersionUID = -6885090840185178628L;

    @NotNull
    @Size(min = 4, max = 60, message = "Nome deve conter entre {min} e {max} caracteres")
    private String name;

    @NotNull
    @Email(message = "Formato inválido de e-mail")
    private String mail;

    @NotNull
    @Size(min = 9, max = 15, message = "Nome deve conter entre {min} e {max} caracteres")
    private String document;

    @NotNull
    private Long type;

    @NotNull
    private Set<String> phones = new HashSet<>();

    @Override
    public Object parseToObject(Object dto) {
        CustomerDto reference = (CustomerDto) dto;
        return new Customer(reference.getName(), reference.getMail(), reference.getDocument(), toEnum(reference.getType()));
    }
}