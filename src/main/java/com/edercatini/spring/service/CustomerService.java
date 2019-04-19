package com.edercatini.spring.service;

import com.edercatini.spring.model.Customer;
import com.edercatini.spring.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer> {

    private CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        super(repository);
    }
}