package com.edercatini.spring.controller;

import com.edercatini.spring.dto.CategoryDto;
import com.edercatini.spring.model.Category;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

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
    public Page<Category> findByPage(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "24") Integer size,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a Category Entity")
    @PostMapping
    @ResponseStatus(CREATED)
    public CustomResponse save(@Valid @RequestBody CategoryDto dto) {
        Category entity = new Category();
        return service.save(entity.updateByDTO(entity, dto));
    }

    @ApiOperation(value = "Updates an existing Category Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody CategoryDto dto) {
        CustomResponse<Category> object = findById(id);
        Category reference = object.getEntity();
        service.update(reference.updateByDTO(reference, dto));
    }

    @ApiOperation(value = "Deletes a specific Category Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}