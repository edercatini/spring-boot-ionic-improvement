package com.edercatini.spring.service;

import com.edercatini.spring.domain.City;
import com.edercatini.spring.dto.CityDto;

import java.util.List;

public interface CityService {

    City findById(Long id);

    List<City> findAll();

    List<City> save(CityDto dto);

    void update(Long id, CityDto dto);

    void delete(Long id);
}