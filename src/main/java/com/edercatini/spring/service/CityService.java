package com.edercatini.spring.service;

import com.edercatini.spring.domain.City;
import com.edercatini.spring.dto.CityDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CityService {

    City findById(Long id);

    List<City> findAll();

    Page<CityDto> findByPage(Integer page, Integer size, String direction, String properties);

    List<City> save(CityDto dto);

    void update(Long id, CityDto dto);

    void delete(Long id);
}