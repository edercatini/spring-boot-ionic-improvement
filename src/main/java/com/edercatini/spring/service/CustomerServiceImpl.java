package com.edercatini.spring.service;

import com.edercatini.spring.domain.Customer;
import com.edercatini.spring.dto.CustomerDto;
import com.edercatini.spring.enums.CustomerTypes;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> object = repository.findById(id);
        return object.orElseThrow(() -> new ObjectNotFoundException("Customer not found"));
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<CustomerDto> findByPage(Integer page, Integer size, String direction, String properties) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), properties);
        Page<Customer> list = repository.findAll(pageRequest);
        return list.map(customer -> new CustomerDto(customer.getName(), customer.getMail(), customer.getDocument(), customer.getId() , customer.getPhones()));
    }

    @Override
    public List<Customer> save(CustomerDto dto) {
        Customer object = new Customer(dto.getName(), dto.getMail(), dto.getDocument(), CustomerTypes.toEnum(dto.getType()));
        return repository.saveAll(asList(object));
    }

    @Override
    public void update(Long id, CustomerDto dto) {
        Customer object = this.findById(id);
        object.setName(dto.getName());
        repository.saveAll(asList(object));
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}