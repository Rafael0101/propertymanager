package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.LaboratoryDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.LaboratoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LaboratoryService {
    Page<LaboratoryModel> findAll(Pageable pageable);

    LaboratoryModel findById(UUID id);

    LaboratoryModel save(LaboratoryDTO laboratory);

    LaboratoryModel update(UUID id, LaboratoryDTO laboratoryDTO);

    void delete(LaboratoryModel laboratoryModel);
}
