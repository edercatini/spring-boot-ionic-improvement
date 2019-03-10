package com.edercatini.spring.service;

import com.edercatini.spring.domain.State;
import com.edercatini.spring.dto.StateDto;

import java.util.List;

public interface StateService {

    State findById(Long id);

    List<State> findAll();

    List<State> save(StateDto dto);

    void update(Long id, StateDto dto);

    void delete(Long id);
}