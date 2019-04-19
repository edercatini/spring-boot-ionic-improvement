package com.edercatini.spring.controller;

import com.edercatini.spring.dto.PurchaseDto;
import com.edercatini.spring.model.CustomResponse;
import com.edercatini.spring.model.MultipleCustomResponse;
import com.edercatini.spring.model.Purchase;
import com.edercatini.spring.service.PurchaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

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
    public CustomResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Returns a complete set of Purchases")
    @GetMapping
    @ResponseStatus(OK)
    public MultipleCustomResponse findAll() {
        return service.findAll();
    }

    @ApiOperation(value = "Returns a complete set of Purchases with paginated data")
    @GetMapping(value = "/page")
    @ResponseStatus(OK)
    public Page<Purchase> findByPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "properties", defaultValue = "name") String properties) {
        return service.findByPage(page, size, direction, properties);
    }

    @ApiOperation(value = "Persist a Purchase Entity")
    @PostMapping
    @ResponseStatus(CREATED)
    public CustomResponse save(@Valid @RequestBody PurchaseDto dto) {
        Purchase entity = new Purchase();
        return service.save(entity.updateByDTO(entity, dto));
    }

    @ApiOperation(value = "Updates an existing Purchase Entity")
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody PurchaseDto dto) {
        CustomResponse<Purchase> object = findById(id);
        Purchase reference = object.getEntity();
        service.update(reference.updateByDTO(reference, dto));
    }

    @ApiOperation(value = "Deletes a specific Purchase Entity")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}