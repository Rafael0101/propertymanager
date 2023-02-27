package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.impl;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.PropertyDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PropertyModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories.PropertyRepository;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.PropertyService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Page<PropertyModel> findAll(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }

    @Override
    public PropertyModel findById(UUID id) {
        Optional<PropertyModel> propertyModel = propertyRepository.findById(id);
        if(propertyModel.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Property not found for id: " + id);
        }
        return propertyModel.get();
    }

    @Override
    public PropertyModel save(PropertyDTO property) {

        var propertyModel = new PropertyModel();
        BeanUtils.copyProperties(property, propertyModel);
        propertyModel.setCreationDate(ZonedDateTime.now(ZoneId.of("UTC")));
        propertyModel.setLastUpdateDate(ZonedDateTime.now(ZoneId.of("UTC")));
        propertyModel = propertyRepository.save(propertyModel);

        return propertyModel;
    }

    @Override
    public PropertyModel update(UUID id, PropertyDTO property) {
        PropertyModel propertyModel = findById(id);
        BeanUtils.copyProperties(property, propertyModel);
        return propertyRepository.save(propertyModel);
    }

    @Override
    public void delete(PropertyModel property) {
        propertyRepository.delete(property);
    }
}
