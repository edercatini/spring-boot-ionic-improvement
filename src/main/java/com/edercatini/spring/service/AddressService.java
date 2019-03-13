package com.edercatini.spring.service;

import com.edercatini.spring.domain.Address;
import com.edercatini.spring.dto.AddressDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {

    Address findById(Long id);

    List<Address> findAll();

    Page<AddressDto> findByPage(Integer page, Integer size, String direction, String properties);

    List<Address> save(AddressDto dto);

    void update(Long id, AddressDto dto);

    void delete(Long id);
}