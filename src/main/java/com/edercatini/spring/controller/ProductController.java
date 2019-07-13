package com.edercatini.spring.controller;

import com.edercatini.spring.dto.ProductDto;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.model.Product;
import com.edercatini.spring.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @ApiOperation(value = "Searches for a specific Product by its ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public CustomResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of Products")
    @GetMapping
    @ResponseStatus(OK)
    public MultipleCustomResponse findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of Products with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    public Page<Product> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a Product Entity")
    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public CustomResponse save(@Valid @RequestBody ProductDto dto) {
        Product entity = new Product();
        return service.save(entity.updateByDTO(entity, dto));
    }

    @ApiOperation(value = "Updates an existing Product Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        CustomResponse<Product> object = findById(id);
        Product reference = object.getEntity();
        service.update(reference.updateByDTO(reference, dto));
    }

    @ApiOperation(value = "Deletes a specific Product Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}