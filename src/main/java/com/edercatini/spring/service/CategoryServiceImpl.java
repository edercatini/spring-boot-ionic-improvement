package com.edercatini.spring.service;

import com.edercatini.spring.domain.Category;
import com.edercatini.spring.dto.CategoryDto;
import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        Optional<Category> object = repository.findById(id);
        return object.orElseThrow(() -> new ObjectNotFoundException("Category not found"));
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<CategoryDto> findByPage(Integer page, Integer size, String direction, String properties) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), properties);
        Page<Category> list = repository.findAll(pageRequest);
        return list.map(category -> new CategoryDto(category.getName()));
    }

    @Override
    public Category save(CategoryDto dto) {
        Category object = new Category(dto.getName());
        repository.saveAll(asList(object));
        return object;
    }

    @Override
    public void update(Long id, CategoryDto dto) {
        Category object = this.findById(id);
        object.setName(dto.getName());
        repository.saveAll(asList(object));
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}