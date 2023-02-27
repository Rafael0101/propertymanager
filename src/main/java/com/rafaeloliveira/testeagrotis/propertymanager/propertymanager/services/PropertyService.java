package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.PropertyDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PeopleModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PropertyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PropertyService {
    Page<PropertyModel> findAll(Pageable pageable);

    PropertyModel findById(UUID id);

    PropertyModel save(PropertyDTO property);

    PropertyModel update(UUID id, PropertyDTO property);

    void delete(PropertyModel peopleModel);
}
