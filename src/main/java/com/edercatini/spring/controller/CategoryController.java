package com.edercatini.spring.controller;

import com.edercatini.spring.domain.Category;
import com.edercatini.spring.dto.CategoryDto;
import com.edercatini.spring.service.CategoryService;
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
@RequestMapping("/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    private CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @ApiOperation(value = "Searches for a specific Category by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category object = service.findById(id);
        return ok().body(object);
    }

    @ApiOperation(value = "Returns a complete set of Categories")
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> objects = service.findAll();
        return ok().body(objects);
    }

    @ApiOperation(value = "Returns a complete set of Categories with paginated data")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoryDto>> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        Page<CategoryDto> list = service.findByPage(page, size, direction, properties);
        return ok().body(list);
    }

    @ApiOperation(value = "Persist a Category Entity")
    @PostMapping
    public ResponseEntity<Category> save(@Valid @RequestBody CategoryDto dto) {
        List<Category> object = service.save(dto);

        if (object != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.get(0).getId()).toUri();
            return created(uri).build();
        } else {
            return badRequest().build();
        }
    }

    @ApiOperation(value = "Updates an existing Category Entity")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody CategoryDto dto) {
        service.update(id, dto);
        return noContent().build();
    }

    @ApiOperation(value = "Deletes a specific Category Entity")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent().build();
    }
}