package com.edercatini.spring.service;

import com.edercatini.spring.domain.State;
import com.edercatini.spring.dto.StateDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StateService {

    State findById(Long id);

    List<State> findAll();

    Page<StateDto> findByPage(Integer page, Integer size, String direction, String properties);

    State save(StateDto dto);

    void update(Long id, StateDto dto);

    void delete(Long id);
}