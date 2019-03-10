package com.edercatini.spring.controller;

import com.edercatini.spring.domain.State;
import com.edercatini.spring.dto.StateDto;
import com.edercatini.spring.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/state")
@CrossOrigin(origins = "*")
public class StateController {

    private StateService service;

    @Autowired
    public StateController(StateService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable Long id) {
        State object = service.findById(id);
        return ok().body(object);
    }

    @GetMapping
    public ResponseEntity<List<State>> findAll() {
        List<State> objects = service.findAll();
        return ok().body(objects);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<StateDto>> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "properties", defaultValue = "name") String properties) {
        Page<StateDto> list = service.findByPage(page, size, direction, properties);
        return ok().body(list);
    }

    @PostMapping
    public ResponseEntity<State> save(@Valid @RequestBody StateDto dto) {
        List<State> object = service.save(dto);

        if (object != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.get(0).getId()).toUri();
            return created(uri).build();
        } else {
            return badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody StateDto dto) {
        service.update(id, dto);
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent().build();
    }
}