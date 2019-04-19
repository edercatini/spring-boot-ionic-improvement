package com.edercatini.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class CustomResponse<T> {

    private T entity;
    private URI resourceUri;
}