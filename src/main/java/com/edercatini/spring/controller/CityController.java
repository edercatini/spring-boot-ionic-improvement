package com.edercatini.spring.controller;

import com.edercatini.spring.domain.City;
import com.edercatini.spring.dto.CityDto;
import com.edercatini.spring.service.CityService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/city")
@CrossOrigin(origins = "*")
public class CityController {

    private CityService service;

    @Autowired
    public CityController(CityService service) {
        this.service = service;
    }

    @ApiOperation(value = "Searches for a specific City by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) {
        City object = service.findById(id);
        return ok().body(object);
    }

    @ApiOperation(value = "Returns a complete set of Cities")
    @GetMapping
    public ResponseEntity<List<City>> findAll() {
        List<City> objects = service.findAll();
        return ok().body(objects);
    }

    @ApiOperation(value = "Returns a complete set of Cities with paginated data")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<CityDto>> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        Page<CityDto> list = service.findByPage(page, size, direction, properties);
        return ok().body(list);
    }

    @ApiOperation(value = "Persist a City Entity")
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

    @ApiOperation(value = "Updates an existing City Entity")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CityDto dto) {
        service.update(id, dto);
        return noContent().build();
    }

    @ApiOperation(value = "Deletes a specific City Entity")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent().build();
    }
}