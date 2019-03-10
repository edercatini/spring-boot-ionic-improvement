package com.edercatini.spring.service;

import com.edercatini.spring.domain.Product;
import com.edercatini.spring.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Product findById(Long id);

    List<Product> findAll();

    List<Product> save(ProductDto dtu);

    void update(Long id, ProductDto dto);

    void delete(Long id);
}