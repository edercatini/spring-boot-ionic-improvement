package com.edercatini.spring.controller;

import com.edercatini.spring.dto.AddressDto;
import com.edercatini.spring.model.Address;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.service.AddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

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
    public CustomResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of Categories")
    @GetMapping
    @ResponseStatus(OK)
    public MultipleCustomResponse findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of Categories with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    public Page<Address> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist an Address Entity")
    @PostMapping
    @ResponseStatus(CREATED)
    public CustomResponse save(@Valid @RequestBody AddressDto dto) {
        Address entity = new Address();
        return service.save(entity.updateByDTO(entity, dto));
    }

    @ApiOperation(value = "Updates an existing Address Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody AddressDto dto) {
        CustomResponse<Address> object = findById(id);
        Address reference = object.getEntity();
        service.update(reference.updateByDTO(reference, dto));
    }

    @ApiOperation(value = "Deletes a specific Address Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}