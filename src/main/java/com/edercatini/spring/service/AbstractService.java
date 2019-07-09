package com.edercatini.spring.service;

import com.edercatini.spring.exception.ObjectNotFoundException;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.Entity;
import com.edercatini.spring.model.MultipleCustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.edercatini.spring.util.ResourceUtils.createUri;
import static com.edercatini.spring.util.ResourceUtils.setResponse;
import static java.util.Arrays.asList;

public abstract class AbstractService<T> {

    private JpaRepository repository;

    public AbstractService(JpaRepository repository) {
        this.repository = repository;
    }

    public CustomResponse findById(Long id) {
        Optional<T> object = repository.findById(id);

        if (object.isPresent()) {
            return setResponse((Entity) object.get(), createUri((Entity) object.get()));
        }

        throw new ObjectNotFoundException("Object not found");
    }

    public MultipleCustomResponse findAll() {
        List<T> entities = repository.findAll();

        if (!entities.isEmpty()) {
            MultipleCustomResponse response = new MultipleCustomResponse();
            entities.forEach(object -> response.getEntities().add(setResponse((Entity) object, createUri((Entity) object))));
            return response;
        }

        throw new ObjectNotFoundException("No objects were found");
    }

    public Page<T> findByPage(Integer page, Integer size, String direction, String properties) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), properties);
        return repository.findAll(pageRequest);
    }

    public CustomResponse save(T object) {
        repository.saveAll(asList(object));
        return setResponse((Entity) object, createUri((Entity) object));
    }

    public void update(T object) {
        repository.saveAll(asList(object));
    }

    public void delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
    }
}