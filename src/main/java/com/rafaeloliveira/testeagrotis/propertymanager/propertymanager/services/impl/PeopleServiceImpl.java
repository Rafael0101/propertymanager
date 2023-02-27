package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.impl;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.PeopleDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.LaboratoryModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PeopleModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PropertyModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories.PeopleRepository;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.LaboratoryService;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.PeopleService;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.PropertyService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;
    private final LaboratoryService laboratoryService;
    private final PropertyService propertyService;

    public PeopleServiceImpl(PeopleRepository peopleRepository, LaboratoryService laboratoryService, PropertyService propertyService) {
        this.peopleRepository = peopleRepository;
        this.laboratoryService = laboratoryService;
        this.propertyService = propertyService;
    }


    @Override
    public Page<PeopleModel> findAll(Pageable pageable) {
        return peopleRepository.findAll(pageable);
    }

    @Override
    public PeopleModel findById(UUID id) {
        Optional<PeopleModel> peopleOptional = peopleRepository.findById(id);
        if(peopleOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "People not found for id: " + id);
        }
        return peopleOptional.get();
    }

    @Override
    public PeopleModel save(PeopleDTO people) {
        LaboratoryModel laboratoryModel = laboratoryService.findById(people.getLaboratoryId());
        PropertyModel propertyModel = propertyService.findById(people.getPropertyId());

        PeopleModel peopleModel = new PeopleModel();
        BeanUtils.copyProperties(people, peopleModel);
        peopleModel.setLaboratory(laboratoryModel);
        peopleModel.setProperty(propertyModel);
        peopleModel = peopleRepository.save(peopleModel);
        return peopleModel;
    }

    @Override
    public PeopleModel update(UUID id, PeopleDTO people) {
        PeopleModel peopleModel = findById(id);
        BeanUtils.copyProperties(people, peopleModel);
        return peopleRepository.save(peopleModel);
    }

    @Override
    public void delete(PeopleModel peopleModel) {
        peopleRepository.delete(peopleModel);
    }
}
