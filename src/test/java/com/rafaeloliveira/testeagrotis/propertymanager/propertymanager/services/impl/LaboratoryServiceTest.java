package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.impl;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.LaboratoryDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.LaboratoryModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories.LaboratoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LaboratoryServiceTest {

    @Mock
    private LaboratoryRepository laboratoryRepository;
    @InjectMocks
    private LaboratoryServiceImpl laboratoryService;
    private LaboratoryModel laboratoryModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        laboratoryService = new LaboratoryServiceImpl(laboratoryRepository);

        laboratoryModel = new LaboratoryModel();
        laboratoryModel.setId(UUID.randomUUID());
        laboratoryModel.setName("");
    }


    @DisplayName("FindAll OK")
    @Test
    void whenFindAll_thenReturnLaboratoryList() {
        Pageable pageable = Pageable.unpaged();
        Page<LaboratoryModel> expectedPage = mock(Page.class);
        when(laboratoryRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<LaboratoryModel> actualPage = laboratoryService.findAll(pageable);

        assertEquals(expectedPage, actualPage);
        verify(laboratoryRepository, times(1)).findAll(pageable);
    }

    @DisplayName("FindById OK")
    @Test
    void givenLaboratoryId_whenFindById_thenReturnLaboratoryModel() {
        UUID id = UUID.randomUUID();
        LaboratoryModel expectedLab = new LaboratoryModel();
        expectedLab.setId(id);
        when(laboratoryRepository.findById(id)).thenReturn(Optional.of(expectedLab));

        LaboratoryModel actualLab = laboratoryService.findById(id);

        assertEquals(expectedLab, actualLab);
        verify(laboratoryRepository, times(1)).findById(id);
    }

    @DisplayName("FindById NOT_FOUND")
    @Test
    void givenLaboratoryId_whenFindById_thenReturnNotFound() {
        UUID id = UUID.randomUUID();
        when(laboratoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> laboratoryService.findById(id));
        verify(laboratoryRepository, times(1)).findById(id);
    }

    @DisplayName("Save OK")
    @Test
    void whenSaveLaboratory_thenReturnLaboratory() {
        LaboratoryDTO labDto = new LaboratoryDTO();
        labDto.setName("");

        LaboratoryModel expectedLab = new LaboratoryModel();
        BeanUtils.copyProperties(labDto, expectedLab);
        expectedLab.setCreationDate(ZonedDateTime.now(ZoneId.of("UTC")));
        expectedLab.setLastUpdateDate(ZonedDateTime.now(ZoneId.of("UTC")));
        when(laboratoryRepository.save(any(LaboratoryModel.class))).thenReturn(expectedLab);

        LaboratoryModel actualLab = laboratoryService.save(labDto);

        assertEquals(expectedLab, actualLab);
        verify(laboratoryRepository, times(1)).save(any(LaboratoryModel.class));
    }

    @Test
    @DisplayName("Update OK")
    void givenLaboratoryId_whenFindById_thenReturnUpdatedLaboratory() {
        UUID id = UUID.randomUUID();
        LaboratoryDTO laboratoryDTO = new LaboratoryDTO();
        laboratoryDTO.setName("");

        LaboratoryModel existingLaboratory = new LaboratoryModel();
        existingLaboratory.setId(id);
        existingLaboratory.setName("");

        LaboratoryModel expectedLaboratory = new LaboratoryModel();
        BeanUtils.copyProperties(laboratoryDTO, expectedLaboratory);
        expectedLaboratory.setId(id);
        expectedLaboratory.setCreationDate(existingLaboratory.getCreationDate());
        expectedLaboratory.setLastUpdateDate(ZonedDateTime.now(ZoneId.of("UTC")));

        when(laboratoryRepository.findById(id)).thenReturn(Optional.of(existingLaboratory));
        when(laboratoryRepository.save(any())).thenReturn(expectedLaboratory);

        LaboratoryModel updatedLaboratory = laboratoryService.update(id, laboratoryDTO);

        assertNotNull(updatedLaboratory);
        assertEquals(expectedLaboratory, updatedLaboratory);
        verify(laboratoryRepository, times(1)).findById(id);
        verify(laboratoryRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Update NOT_FOUND")
    void givenLaboratory_whenFindById_thenReturnNotFound() {
        UUID id = UUID.randomUUID();
        LaboratoryDTO laboratoryDTO = new LaboratoryDTO();
        laboratoryDTO.setName("");

        when(laboratoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> laboratoryService.update(id, laboratoryDTO));
        verify(laboratoryRepository, times(1)).findById(id);
        verify(laboratoryRepository, times(0)).save(any());
    }


    @DisplayName("Delete OK")
    @Test
    void whenDeleteLaboratory_thenReturnOK() {
        doNothing().when(laboratoryRepository).delete(any(LaboratoryModel.class));
        laboratoryService.delete(laboratoryModel);
        verify(laboratoryRepository, times(1)).delete(laboratoryModel);
    }
}
