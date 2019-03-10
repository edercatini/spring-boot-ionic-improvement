package com.edercatini.spring.service;

import com.edercatini.spring.domain.City;
import com.edercatini.spring.dto.CityDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class CityServiceImpl implements CityService {

    private CityRepository repository;

    @Autowired
    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public City findById(Long id) {
        Optional<City> object = repository.findById(id);
        return object.orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    @Override
    public List<City> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<CityDto> findByPage(Integer page, Integer size, String direction, String properties) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), properties);
        Page<City> list = repository.findAll(pageRequest);
        return list.map(city -> new CityDto(city.getName()));
    }

    @Override
    public List<City> save(CityDto dto) {
        City object = new City(dto.getName());
        return repository.saveAll(asList(object));
    }

    @Override
    public void update(Long id, CityDto dto) {
        City object = this.findById(id);
        object.setName(dto.getName());
        repository.saveAll(asList(object));
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}