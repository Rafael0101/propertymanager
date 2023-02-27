package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.impl;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.LaboratoryDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.LaboratoryModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories.LaboratoryRepository;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.LaboratoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaboratoryServiceImpl implements LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryServiceImpl(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    @Override
    public Page<LaboratoryModel> findAll(Pageable pageable) {
        return laboratoryRepository.findAll(pageable);
    }

    @Override
    public LaboratoryModel findById(UUID id) {
        Optional<LaboratoryModel> laboratoryOptional = laboratoryRepository.findById(id);
        if (laboratoryOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Laboratory not found for id: " + id);
        }
        return laboratoryOptional.get();
    }

    @Override
    public LaboratoryModel save(LaboratoryDTO laboratory) {

        var laboratoryModel = new LaboratoryModel();
        BeanUtils.copyProperties(laboratory, laboratoryModel);
        laboratoryModel.setCreationDate(ZonedDateTime.now(ZoneId.of("UTC")));
        laboratoryModel.setLastUpdateDate(ZonedDateTime.now(ZoneId.of("UTC")));
        laboratoryModel = laboratoryRepository.save(laboratoryModel);

        return laboratoryModel;
    }

    @Override
    public LaboratoryModel update(UUID id, LaboratoryDTO laboratory) {
        LaboratoryModel laboratoryModel = findById(id);
        BeanUtils.copyProperties(laboratory, laboratoryModel);
        return laboratoryRepository.save(laboratoryModel);
    }

    @Override
    public void delete(LaboratoryModel laboratoryModel) {
        laboratoryRepository.delete(laboratoryModel);
    }
}
