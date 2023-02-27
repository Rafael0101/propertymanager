package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PeopleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PeopleRepository extends PagingAndSortingRepository<PeopleModel, UUID>, JpaRepository<PeopleModel, UUID> {
}
