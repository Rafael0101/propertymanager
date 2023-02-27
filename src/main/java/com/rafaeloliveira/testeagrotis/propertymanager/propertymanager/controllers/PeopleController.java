package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.controllers;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.PeopleDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PeopleModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.PeopleService;
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
@RequestMapping("/api/v1/people")
public class PeopleController {

    private static final Logger log = LogManager.getLogger(PeopleController.class);

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public ResponseEntity<Page<PeopleModel>> getAll(@PageableDefault(sort = "creationDate") Pageable pageable) {

        log.info("NEW FIND ALL PEOPLES REQUEST");
        try {
            return ResponseEntity.ok(peopleService.findAll(pageable));
        } catch (Exception e) {
            log.warn("NOT FOUND PEOPLES");
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        log.info("NEW FIND PEOPLE BY ID REQUEST");
        return ResponseEntity.ok(peopleService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Validated PeopleDTO people) {
        log.info("NEW SAVE PEOPLE REQUEST");
        try {
            return ResponseEntity.ok(peopleService.save(people));
        } catch (Exception e) {
            log.warn("CANNOT SAVE PEOPLE. PEOPLEDTO:  {}", people);
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody @Validated PeopleDTO people) {
        log.info("NEW UPDATE PEOPLE REQUEST");
        try {
            return ResponseEntity.ok(peopleService.update(id, people));
        } catch (Exception e) {
            log.warn("CANNOT UPDATE PEOPLE");
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        log.info("NEW DELETE PEOPLE REQUEST");
        try {
            PeopleModel peopleModel = peopleService.findById(id);
            peopleService.delete(peopleModel);
        } catch (Exception e) {
            log.warn("CANNOT DELETE PEOPLE");
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok("People deleted successfully");
    }
}
