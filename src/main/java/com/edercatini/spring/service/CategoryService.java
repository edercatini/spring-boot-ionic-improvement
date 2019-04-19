package com.edercatini.spring.service;

import com.edercatini.spring.model.Category;
import com.edercatini.spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService<Category> {

    private CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }
}