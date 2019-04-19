package com.edercatini.spring.service;

import com.edercatini.spring.model.State;
import com.edercatini.spring.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService extends AbstractService<State> {

    private StateRepository repository;

    @Autowired
    public StateService(StateRepository repository) {
        super(repository);
    }
}