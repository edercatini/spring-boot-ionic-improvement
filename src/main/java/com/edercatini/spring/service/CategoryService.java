package com.edercatini.spring.service;

import com.edercatini.spring.domain.Category;
import com.edercatini.spring.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    List<Category> findAll();

    List<Category> save(CategoryDto dto);

    void update(Long id, CategoryDto dto);

    void delete(Long id);
}