package com.edercatini.spring.service;

import com.edercatini.spring.model.Product;
import com.edercatini.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService<Product> {

    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        super(repository);
    }
}