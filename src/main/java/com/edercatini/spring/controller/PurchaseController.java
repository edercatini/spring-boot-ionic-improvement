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

import static org.springframework.http.ResponseEntity.*;

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
    public ResponseEntity<Purchase> findById(@PathVariable Long id) {
        Purchase object = service.findById(id);
        return ok().body(object);
    }

    @ApiOperation(value = "Returns a complete set of Purchases")
    @GetMapping
    public ResponseEntity<List<Purchase>> findAll() {
        List<Purchase> objects = service.findAll();
        return ok().body(objects);
    }

    @ApiOperation(value = "Returns a complete set of Purchases with paginated data")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<PurchaseDto>> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "properties", defaultValue = "name") String properties) {
        Page<PurchaseDto> list = service.findByPage(page, size, direction, properties);
        return ok().body(list);
    }

    @ApiOperation(value = "Persist a Purchase Entity")
    @PostMapping
    public ResponseEntity<Purchase> save(@Valid @RequestBody PurchaseDto dto) {
        List<Purchase> object = service.save(dto);

        if (object != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(object.get(0).getId()).toUri();
            return created(uri).build();
        } else {
            return badRequest().build();
        }
    }

    @ApiOperation(value = "Updates an existing Purchase Entity")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody PurchaseDto dto) {
        service.update(id, dto);
        return noContent().build();
    }

    @ApiOperation(value = "Deletes a specific Purchase Entity")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return noContent().build();
    }
}