package com.edercatini.spring.service;

import com.edercatini.spring.domain.Category;
import com.edercatini.spring.dto.CategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    List<Category> findAll();

    Page<CategoryDto> findByPage(Integer page, Integer size, String direction, String properties);

    List<Category> save(CategoryDto dto);

    void update(Long id, CategoryDto dto);

    void delete(Long id);
}