package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.controllers;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.LaboratoryDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.LaboratoryModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.LaboratoryService;
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
@RequestMapping("/api/v1/laboratory")
public class LaboratoryController {

    private static final Logger log = LogManager.getLogger(LaboratoryController.class);

    private final LaboratoryService laboratoryService;


    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @GetMapping
    public ResponseEntity<Page<LaboratoryModel>> getAll(@PageableDefault(sort = "creationDate") Pageable pageable) {
        log.info("NEW FIND ALL LABORATORIES REQUEST");
        try {
            return ResponseEntity.ok(laboratoryService.findAll(pageable));
        } catch (Exception e) {
            log.warn("NOT FOUND LABORATORIES");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratoryModel> getById(@PathVariable UUID id) {
        log.info("NEW FIND BY ID REQUEST");
        return ResponseEntity.ok(laboratoryService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated LaboratoryDTO laboratory) {
        log.info("NEW SAVE LABORATORY REQUEST");
        try {
            return ResponseEntity.ok(laboratoryService.save(laboratory));
        } catch (Exception e) {
            log.warn("CANNOT SAVE LABORATORY");
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody @Validated LaboratoryDTO laboratory) {
        log.info("NEW UPDATE LABORATORY REQUEST");
        try {
            return ResponseEntity.ok(laboratoryService.update(id, laboratory));
        } catch (Exception e) {
            log.warn("CANNOT UPDATE LABORATORY");
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        log.info("NEW DELETE LABORATORY REQUEST");
        try {
            LaboratoryModel laboratoryModel = laboratoryService.findById(id);
            laboratoryService.delete(laboratoryModel);
        } catch (Exception e) {
            log.warn("CANNOT DELETE LABORATORY");
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok("Laboratory deleted successfully");
    }
}
