package com.edercatini.spring.controller;

import com.edercatini.spring.domain.Customer;
import com.edercatini.spring.dto.CustomerDto;
import com.edercatini.spring.service.CustomerService;
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
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @ApiOperation(value = "Searches for a specific Customer by its ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Customer findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of Customers")
    @GetMapping
    @ResponseStatus(OK)
    public List<Customer> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of Customers with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    public Page<CustomerDto> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a Customer Entity")
    @PostMapping
    public ResponseEntity<Customer> save(@Valid @RequestBody CustomerDto dto) {
        Customer object = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.getId()).toUri();
        return created(uri).build();
    }

    @ApiOperation(value = "Updates an existing Customer Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody CustomerDto dto) {
        service.update(id, dto);
    }

    @ApiOperation(value = "Deletes a specific Customer Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}