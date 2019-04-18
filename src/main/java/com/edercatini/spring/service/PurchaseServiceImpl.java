package com.edercatini.spring.service;

import com.edercatini.spring.domain.Purchase;
import com.edercatini.spring.dto.PurchaseDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private PurchaseRepository repository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Purchase findById(Long id) {
        Optional<Purchase> object = repository.findById(id);
        return object.orElseThrow(() -> new ObjectNotFoundException("Purchase not found"));
    }

    @Override
    public List<Purchase> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<PurchaseDto> findByPage(Integer page, Integer size, String direction, String properties) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), properties);
        Page<Purchase> list = repository.findAll(pageRequest);
        return list.map(purchase -> new PurchaseDto(purchase.getRegisteredIn(), purchase.getDeliveryAddress(), purchase.getCustomer()));
    }

    @Override
    public Purchase save(PurchaseDto dto) {
        Purchase object = new Purchase(dto.getRegisteredIn(), dto.getDeliveryAddress(), dto.getCustomer());
        repository.saveAll(asList(object));
        return object;
    }

    @Override
    public void update(Long id, PurchaseDto dto) {
        Purchase object = this.findById(id);
        object.setRegisteredIn(dto.getRegisteredIn());
        object.setDeliveryAddress(dto.getDeliveryAddress());
        object.setCustomer(dto.getCustomer());
        repository.saveAll(asList(object));
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}