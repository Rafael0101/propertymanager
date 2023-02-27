package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PropertyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PropertyRepository extends PagingAndSortingRepository<PropertyModel, UUID>, JpaRepository<PropertyModel, UUID> {
}
