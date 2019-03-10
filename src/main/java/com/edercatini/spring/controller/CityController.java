package com.edercatini.spring.controller;

import com.edercatini.spring.domain.City;
import com.edercatini.spring.dto.CityDto;
import com.edercatini.spring.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/city")
@CrossOrigin(origins = "*")
public class CityController {

    private CityService service;

    @Autowired
    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) {
        City object = service.findById(id);
        return ok().body(object);
    }

    @GetMapping
    public ResponseEntity<List<City>> findAll() {
        List<City> objects = service.findAll();
        return ok().body(objects);
    }

    @PostMapping
    public ResponseEntity<City> save(@Valid @RequestBody CityDto dto) {
        List<City> object = service.save(dto);

        if (object != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.get(0).getId()).toUri();
            return created(uri).build();
        } else {
            return badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CityDto dto) {
        service.update(id, dto);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent().build();
    }
}