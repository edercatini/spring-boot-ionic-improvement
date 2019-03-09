package com.edercatini.spring.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -2036939753416081965L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private T id;
}