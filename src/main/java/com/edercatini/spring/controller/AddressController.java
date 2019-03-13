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

import static org.springframework.http.ResponseEntity.*;

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
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        Address object = service.findById(id);
        return ok().body(object);
    }

    @ApiOperation(value = "Returns a complete set of Categories")
    @GetMapping
    public ResponseEntity<List<Address>> findAll() {
        List<Address> objects = service.findAll();
        return ok().body(objects);
    }

    @ApiOperation(value = "Returns a complete set of Categories with paginated data")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<AddressDto>> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        Page<AddressDto> list = service.findByPage(page, size, direction, properties);
        return ok().body(list);
    }

    @ApiOperation(value = "Persist a Address Entity")
    @PostMapping
    public ResponseEntity<Address> save(@Valid @RequestBody AddressDto dto) {
        List<Address> object = service.save(dto);

        if (object != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.get(0).getId()).toUri();
            return created(uri).build();
        } else {
            return badRequest().build();
        }
    }

    @ApiOperation(value = "Updates an existing Address Entity")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody AddressDto dto) {
        service.update(id, dto);
        return noContent().build();
    }

    @ApiOperation(value = "Deletes a specific Address Entity")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent().build();
    }
}