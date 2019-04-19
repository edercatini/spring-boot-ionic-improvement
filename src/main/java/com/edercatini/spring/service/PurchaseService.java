package com.edercatini.spring.service;

import com.edercatini.spring.model.Purchase;
import com.edercatini.spring.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService extends AbstractService<Purchase> {

    private PurchaseRepository repository;

    @Autowired
    public PurchaseService(PurchaseRepository repository) {
        super(repository);
    }
}