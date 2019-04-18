package com.edercatini.spring.controller;

import com.edercatini.spring.domain.Purchase;
import com.edercatini.spring.dto.PurchaseDto;
import com.edercatini.spring.service.PurchaseService;
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
@RequestMapping("/purchase")
@CrossOrigin(origins = "*")
public class PurchaseController {

    private PurchaseService service;

    @Autowired
    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @ApiOperation(value = "Searches for a specific Purchase by its ID")
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Purchase findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of Purchases")
    @GetMapping
    @ResponseStatus(OK)
    public List<Purchase> findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of Purchases with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    public Page<PurchaseDto> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a Purchase Entity")
    @PostMapping
    public ResponseEntity<Purchase> save(@Valid @RequestBody PurchaseDto dto) {
        Purchase object = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.getId()).toUri();
        return created(uri).build();
    }

    @ApiOperation(value = "Updates an existing Purchase Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody PurchaseDto dto) {
        service.update(id, dto);
    }

    @ApiOperation(value = "Deletes a specific Purchase Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}