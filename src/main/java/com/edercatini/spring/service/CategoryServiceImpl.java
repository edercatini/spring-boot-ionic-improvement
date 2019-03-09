package com.edercatini.spring.service;

import com.edercatini.spring.domain.Category;
import com.edercatini.spring.dto.CategoryDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> category = repository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Category> save(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getName());
        return repository.saveAll(asList(category));
    }

    @Override
    public void update(Long id, CategoryDto categoryDto) {
        Category category = this.findById(id);
        category.setName(categoryDto.getName());
        repository.saveAll(asList(category));
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}