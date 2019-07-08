package com.edercatini.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDto implements Serializable {

    private static final long serialVersionUID = 2205419532909332033L;

    private String mail;
    private String password;
}