package com.edercatini.spring.controller;

import com.edercatini.spring.domain.Address;
import com.edercatini.spring.dto.AddressDto;
import com.edercatini.spring.service.AddressService;
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
@RequestMapping("/address")
@CrossOrigin(origins = "*")
public class AddressController {

    private AddressService service;

    @Autowired
    public AddressController(AddressService service) {
        this.service = service;
    }

    @ApiOperation(value = "Searches for a specific Address by its ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Address findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of Categories")
    @GetMapping
    @ResponseStatus(OK)
    public List<Address> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of Categories with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    public Page<AddressDto> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist an Address Entity")
    @PostMapping
    public ResponseEntity<Address> save(@Valid @RequestBody AddressDto dto) {
        Address object = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.getId()).toUri();
        return created(uri).build();
    }

    @ApiOperation(value = "Updates an existing Address Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody AddressDto dto) {
        service.update(id, dto);
    }

    @ApiOperation(value = "Deletes a specific Address Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}