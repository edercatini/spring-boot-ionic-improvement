package com.edercatini.spring.controller;

import com.edercatini.spring.dto.CityDto;
import com.edercatini.spring.model.City;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.service.CityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/city")
@CrossOrigin(origins = "*")
public class CityController {

    private  CityService service;

    @Autowired
    public CityController(CityService service) {
        this.service = service;
    }

    @ApiOperation(value = "Searches for a specific City by its ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CustomResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of Cities")
    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public MultipleCustomResponse findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of Cities with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<City> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a City Entity")
    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CustomResponse save(@Valid @RequestBody CityDto dto) {
        City entity = new City();
        return service.save(entity.updateByDTO(entity, dto));
    }

    @ApiOperation(value = "Updates an existing City Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void update(@PathVariable Long id, @Valid @RequestBody CityDto dto) {
        CustomResponse<City> object = findById(id);
        City reference = object.getEntity();
        service.update(reference.updateByDTO(reference, dto));
    }

    @ApiOperation(value = "Deletes a specific City Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}