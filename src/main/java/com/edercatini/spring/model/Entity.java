package com.edercatini.spring.model;

import java.io.Serializable;

public interface Entity<T extends Serializable> {

    T getId();

    Object parseToDto(Object object);
}