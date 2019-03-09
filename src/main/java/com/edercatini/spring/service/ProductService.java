package com.edercatini.spring.service;

import com.edercatini.spring.domain.Category;
import com.edercatini.spring.domain.Product;
import com.edercatini.spring.dto.CategoryDto;
import com.edercatini.spring.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Product findById(Long id);

    List<Product> findAll();

    List<Product> save(ProductDto categoryDto);

    void update(Long id, ProductDto categoryDto);

    void delete(Long id);
}