package com.edercatini.spring.service;

import com.edercatini.spring.model.Address;
import com.edercatini.spring.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends AbstractService<Address> {

    private AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        super(repository);
    }
}