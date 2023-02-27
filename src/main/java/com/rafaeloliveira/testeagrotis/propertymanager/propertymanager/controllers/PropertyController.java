package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.controllers;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.PropertyDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PropertyModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.PropertyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private static final Logger log = LogManager.getLogger(PropertyController.class);

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<Page<PropertyModel>> getAll(@PageableDefault(sort = "name") Pageable pageable) {
        log.info("NEW FIND ALL PROPERTIES REQUEST");
        try {
            return ResponseEntity.ok(propertyService.findAll(pageable));
        } catch (Exception e) {
            log.warn("NOT FOUND PROPERTIES");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        log.info("NEW FIND PROPERTIES BY ID REQUEST");
        return ResponseEntity.ok(propertyService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated PropertyDTO property) {
        log.info("NEW SAVE PROPERTY REQUEST");
        try {
            return ResponseEntity.ok(propertyService.save(property));
        } catch (Exception e) {
            log.warn("CANNOT SAVE PROPERTY");
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody @Validated PropertyDTO property) {
        log.info("NEW UPDATE PROPERTY REQUEST");
        try {
            return ResponseEntity.ok(propertyService.update(id, property));
        } catch (Exception e) {
            log.warn("CANNOT UPDATE PROPERTY");
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        log.info("NEW DELETE PROPERTY REQUEST");
        try {
            PropertyModel property = propertyService.findById(id);
            propertyService.delete(property);
        } catch (Exception e) {
            log.warn("CANNOT DELETE PROPERTY");
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok("People deleted successfully");
    }
}
