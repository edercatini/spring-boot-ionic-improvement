package com.edercatini.spring.controller;

import com.edercatini.spring.dto.StateDto;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.model.State;
import com.edercatini.spring.service.StateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CustomResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of States")
    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public MultipleCustomResponse findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of States with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<State> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a State Entity")
    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CustomResponse save(@Valid @RequestBody StateDto dto) {
        State entity = new State();
        return service.save(entity.updateByDTO(entity, dto));
    }

    @ApiOperation(value = "Updates an existing State Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void update(@PathVariable Long id, @Valid @RequestBody StateDto dto) {
        CustomResponse<State> object = findById(id);
        State reference = object.getEntity();
        service.update(reference.updateByDTO(reference, dto));
    }

    @ApiOperation(value = "Deletes a specific State Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}