package com.edercatini.spring.service;

import com.edercatini.spring.domain.Customer;
import com.edercatini.spring.dto.CustomerDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    Customer findById(Long id);

    List<Customer> findAll();

    Page<CustomerDto> findByPage(Integer page, Integer size, String direction, String properties);

    Customer save(CustomerDto dto);

    void update(Long id, CustomerDto dto);

    void delete(Long id);
}