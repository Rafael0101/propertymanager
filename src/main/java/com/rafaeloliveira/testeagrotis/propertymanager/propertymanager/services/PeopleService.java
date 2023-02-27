package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.PeopleDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PeopleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PeopleService {
    Page<PeopleModel> findAll(Pageable pageable);
    PeopleModel findById(UUID id);
    PeopleModel save(PeopleDTO people);
    PeopleModel update(UUID id, PeopleDTO people);
    void delete(PeopleModel peopleModel);
}
