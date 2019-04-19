package com.edercatini.spring.controller;

import com.edercatini.spring.dto.CustomerDto;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.Customer;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

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
    public CustomResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of Customers")
    @GetMapping
    @ResponseStatus(OK)
    public MultipleCustomResponse findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of Customers with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    public Page<Customer> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a Customer Entity")
    @PostMapping
    @ResponseStatus(CREATED)
    public CustomResponse save(@Valid @RequestBody CustomerDto dto) {
        Customer entity = new Customer();
        return service.save(entity.updateByDTO(entity, dto));
    }

    @ApiOperation(value = "Updates an existing Customer Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody CustomerDto dto) {
        CustomResponse<Customer> object = findById(id);
        Customer reference = object.getEntity();
        service.update(reference.updateByDTO(reference, dto));
    }

    @ApiOperation(value = "Deletes a specific Customer Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}