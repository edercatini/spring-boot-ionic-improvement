package com.edercatini.spring.controller;

import com.edercatini.spring.domain.State;
import com.edercatini.spring.dto.StateDto;
import com.edercatini.spring.service.StateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/state")
@CrossOrigin(origins = "*")
public class StateController {

    private StateService service;

    @Autowired
    public StateController(StateService service) {
        this.service = service;
    }

    @ApiOperation(value = "Searches for a specific State by its ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public State findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of States")
    @GetMapping
    @ResponseStatus(OK)
    public List<State> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of States with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    public Page<StateDto> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a State Entity")
    @PostMapping
    public ResponseEntity<State> save(@Valid @RequestBody StateDto dto) {
        State object = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.getId()).toUri();
        return created(uri).build();
    }

    @ApiOperation(value = "Updates an existing State Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody StateDto dto) {
        service.update(id, dto);
    }

    @ApiOperation(value = "Deletes a specific State Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}