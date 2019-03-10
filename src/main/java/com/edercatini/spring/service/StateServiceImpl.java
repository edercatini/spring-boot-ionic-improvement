package com.edercatini.spring.service;

import com.edercatini.spring.domain.Category;
import com.edercatini.spring.domain.State;
import com.edercatini.spring.dto.CategoryDto;
import com.edercatini.spring.dto.StateDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.CategoryRepository;
import com.edercatini.spring.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class StateServiceImpl implements StateService {

    private StateRepository repository;

    @Autowired
    public StateServiceImpl(StateRepository repository) {
        this.repository = repository;
    }

    @Override
    public State findById(Long id) {
        Optional<State> object = repository.findById(id);
        return object.orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    @Override
    public List<State> findAll() {
        return repository.findAll();
    }

    @Override
    public List<State> save(StateDto dto) {
        State object = new State(dto.getName());
        return repository.saveAll(asList(object));
    }

    @Override
    public void update(Long id, StateDto dto) {
        State object = this.findById(id);
        object.setName(dto.getName());
        repository.saveAll(asList(object));
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}