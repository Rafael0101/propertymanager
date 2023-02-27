package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.LaboratoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface LaboratoryRepository extends PagingAndSortingRepository<LaboratoryModel, UUID>, JpaRepository<LaboratoryModel, UUID> {
}
