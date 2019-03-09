package com.edercatini.spring.service;

import com.edercatini.spring.domain.Product;
import com.edercatini.spring.dto.ProductDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> object = repository.findById(id);
        return object.orElseThrow(() -> new ObjectNotFoundException("Product not found"));
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> save(ProductDto dto) {
        Product object = new Product(dto.getName(), dto.getPrice());
        return repository.saveAll(asList(object));
    }

    @Override
    public void update(Long id, ProductDto dto) {
        Product object = this.findById(id);
        object.setName(dto.getName());
        repository.saveAll(asList(object));
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}