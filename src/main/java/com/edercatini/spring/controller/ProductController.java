package com.edercatini.spring.controller;

import com.edercatini.spring.domain.Product;
import com.edercatini.spring.dto.ProductDto;
import com.edercatini.spring.service.ProductService;
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
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product object = service.findById(id);
        return ok().body(object);
    }

    @ApiOperation(value = "Returns a complete set of Products")
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> objects = service.findAll();
        return ok().body(objects);
    }

    @ApiOperation(value = "Returns a complete set of Products with paginated data")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ProductDto>> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "properties", defaultValue = "name") String properties) {
        Page<ProductDto> list = service.findByPage(page, size, direction, properties);
        return ok().body(list);
    }

    @ApiOperation(value = "Persist a Product Entity")
    @PostMapping
    public ResponseEntity<Product> save(@Valid @RequestBody ProductDto dto) {
        List<Product> object = service.save(dto);

        if (object != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.get(0).getId()).toUri();
            return created(uri).build();
        } else {
            return badRequest().build();
        }
    }

    @ApiOperation(value = "Updates an existing Product Entity")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        service.update(id, dto);
        return noContent().build();
    }

    @ApiOperation(value = "Deletes a specific Product Entity")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent().build();
    }
}