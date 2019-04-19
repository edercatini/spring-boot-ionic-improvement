package com.edercatini.spring.service;

import com.edercatini.spring.model.City;
import com.edercatini.spring.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService extends AbstractService<City> {

    private CityRepository repository;

    @Autowired
    public CityService(CityRepository repository) {
        super(repository);
    }
}