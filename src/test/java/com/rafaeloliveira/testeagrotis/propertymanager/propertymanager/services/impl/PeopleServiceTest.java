package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.impl;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.PeopleDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.LaboratoryModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PeopleModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PropertyModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories.LaboratoryRepository;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories.PeopleRepository;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.LaboratoryService;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.PeopleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    @Mock
    private PeopleRepository peopleRepository;

    @Mock
    private LaboratoryServiceImpl laboratoryService;

    @Mock
    private PropertyServiceImpl propertyService;

    @InjectMocks
    private PeopleServiceImpl peopleServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        peopleServiceImpl = new PeopleServiceImpl(peopleRepository, laboratoryService, propertyService);
    }

    @DisplayName("FindAll OK")
    @Test
    void whenFindAll_thenReturnPeopleList() {
        List<PeopleModel> peopleList = new ArrayList<>();
        peopleList.add(new PeopleModel());
        peopleList.add(new PeopleModel());

        Page<PeopleModel> peoplePage = new PageImpl<>(peopleList);

        Pageable pageable = Pageable.unpaged();

        when(peopleRepository.findAll(pageable)).thenReturn(peoplePage);

        Page<PeopleModel> result = peopleServiceImpl.findAll(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(peopleList.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(peopleList.get(1).getId(), result.getContent().get(1).getId());
    }

    @DisplayName("FindById OK")
    @Test
    void givenPeopleId_whenFindById_thenReturnPeopleModel() {
        UUID id = UUID.randomUUID();
        PeopleModel peopleModel = new PeopleModel();

        when(peopleRepository.findById(id)).thenReturn(Optional.of(peopleModel));

        PeopleModel result = peopleServiceImpl.findById(id);

        assertEquals(peopleModel.getId(), result.getId());
        assertEquals(peopleModel.getName(), result.getName());
    }

    @DisplayName("FindById NOT_FOUND")
    @Test
    void givenPeopleId_whenFindById_thenReturnNotFound() {
        UUID id = UUID.randomUUID();

        when(peopleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> peopleServiceImpl.findById(id));
    }

    @DisplayName("Save OK")
    @Test
    void whenSavePeople_thenReturnPeople() {
        PeopleDTO peopleDTO = new PeopleDTO();
        peopleDTO.setName("string");
        peopleDTO.setObservation("string");
        peopleDTO.setInitialDate(LocalDate.now());
        peopleDTO.setFinalDate(LocalDate.now());
        peopleDTO.setObservation("string");
        peopleDTO.setLaboratoryId(UUID.randomUUID());
        peopleDTO.setPropertyId(UUID.randomUUID());

        PeopleModel peopleModel = new PeopleModel();

        when(propertyService.findById(peopleDTO.getPropertyId())).thenReturn(new PropertyModel());
        when(laboratoryService.findById(peopleDTO.getLaboratoryId())).thenReturn(new LaboratoryModel());
        given(peopleRepository.save(any(PeopleModel.class))).willReturn(peopleModel);

        PeopleModel result = peopleServiceImpl.save(peopleDTO);

        Assertions.assertEquals(peopleModel, result);
        verify(peopleRepository, times(1)).save(any());
    }

    @DisplayName("Update OK")
    @Test
    void givenPeopleId_whenFindById_thenReturnUpdatedPeople() {
        UUID id = UUID.randomUUID();
        PeopleDTO peopleDTO = new PeopleDTO();
        peopleDTO.setName("");
        peopleDTO.setObservation("");
        peopleDTO.setInitialDate(LocalDate.now());
        peopleDTO.setFinalDate(LocalDate.now());
        peopleDTO.setObservation("");

        PeopleModel peopleModel = new PeopleModel();
        peopleModel.setId(id);
        peopleModel.setLastUpdateDate(ZonedDateTime.now());

        when(peopleRepository.findById(id)).thenReturn(Optional.of(peopleModel));
        when(peopleRepository.save(any(PeopleModel.class))).thenReturn(peopleModel);

        PeopleDTO updatedPeopleDTO = new PeopleDTO();
        updatedPeopleDTO.setName("");
        updatedPeopleDTO.setObservation("");
        updatedPeopleDTO.setInitialDate(LocalDate.now());
        updatedPeopleDTO.setFinalDate(LocalDate.now());
        updatedPeopleDTO.setPropertyId(id);
        updatedPeopleDTO.setLaboratoryId(id);

        PeopleModel updatedPeopleModel = peopleServiceImpl.update(id, updatedPeopleDTO);

        assertEquals(updatedPeopleDTO.getName(), updatedPeopleModel.getName());
        assertEquals(updatedPeopleDTO.getObservation(), updatedPeopleModel.getObservation());
        assertEquals(updatedPeopleDTO.getInitialDate(), updatedPeopleModel.getInitialDate());
        assertEquals(updatedPeopleDTO.getFinalDate(), updatedPeopleModel.getFinalDate());
        assertEquals(peopleModel.getCreationDate(), updatedPeopleModel.getCreationDate());

        verify(peopleRepository, times(1)).findById(id);
        verify(peopleRepository, times(1)).save(any(PeopleModel.class));
    }

    @DisplayName("Update NOT_FOUND")
    @Test
    void testUpdateNotFound() {
        UUID id = UUID.randomUUID();
        PeopleDTO updatedPeopleDTO = new PeopleDTO();
        updatedPeopleDTO.setName("");
        updatedPeopleDTO.setInitialDate(LocalDate.now());
        updatedPeopleDTO.setFinalDate(LocalDate.now());
        updatedPeopleDTO.setObservation("");

        when(peopleRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> peopleServiceImpl.update(id, updatedPeopleDTO));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("People not found for id: " + id, exception.getReason());

        verify(peopleRepository, times(1)).findById(id);
        verify(peopleRepository, never()).save(any(PeopleModel.class));
    }

}

