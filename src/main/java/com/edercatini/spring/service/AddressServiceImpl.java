package com.edercatini.spring.service;

import com.edercatini.spring.domain.Address;
import com.edercatini.spring.dto.AddressDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository repository;

    @Autowired
    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address findById(Long id) {
        Optional<Address> object = repository.findById(id);
        return object.orElseThrow(() -> new ObjectNotFoundException("Address not found"));
    }

    @Override
    public List<Address> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<AddressDto> findByPage(Integer page, Integer size, String direction, String properties) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), properties);
        Page<Address> list = repository.findAll(pageRequest);

        return list.map(address -> new AddressDto(address.getPublicPlace(), address.getNumber(), address.getComplement(),
            address.getNeighborhood(), address.getPostalCode(), address.getCustomer()));
    }

    @Override
    public Address save(AddressDto dto) {
        Address object = new Address(dto.getPublicPlace(), dto.getNumber(), dto.getComplement(), dto.getNeighborhood(),
            dto.getPostalCode(), dto.getCustomer());

        repository.saveAll(asList(object));

        return object;
    }

    @Override
    public void update(Long id, AddressDto dto) {
        Address object = this.findById(id);
        object.setPublicPlace(dto.getPublicPlace());
        object.setNumber(dto.getNumber());
        object.setComplement(dto.getComplement());
        object.setNeighborhood(dto.getNeighborhood());
        object.setPostalCode(dto.getPostalCode());
        object.setCustomer(dto.getCustomer());
        repository.saveAll(asList(object));
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}