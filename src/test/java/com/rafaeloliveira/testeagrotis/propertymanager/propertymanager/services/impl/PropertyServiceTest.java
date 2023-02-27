package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.services.impl;

import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos.PropertyDTO;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.models.PropertyModel;
import com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.repositories.PropertyRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @Mock
    private PropertyRepository propertyRepository;

    private PropertyModel propertyModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        propertyService = new PropertyServiceImpl(propertyRepository);

        propertyModel = new PropertyModel();
        propertyModel.setId(UUID.randomUUID());
        propertyModel.setName("");
        propertyModel.setCnpj("");

    }

    @DisplayName("FindAll OK")
    @Test
    void whenFindAll_thenReturnPropertyList() {
        List<PropertyModel> propertyModels = new ArrayList<>();
        propertyModels.add(new PropertyModel());
        propertyModels.add(new PropertyModel());
        Pageable pageable = PageRequest.of(0, 2);
        Page<PropertyModel> expectedPage = new PageImpl<>(propertyModels, pageable, propertyModels.size());

        when(propertyRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<PropertyModel> actualPage = propertyService.findAll(pageable);

        verify(propertyRepository, times(1)).findAll(pageable);
        Assertions.assertEquals(expectedPage, actualPage);
    }

    @DisplayName("FindById OK")
    @Test
    void givenPropertyId_whenFindById_thenReturnPropertyModel() {
        UUID id = UUID.randomUUID();
        PropertyModel expectedProperty = new PropertyModel();
        when(propertyRepository.findById(id)).thenReturn(Optional.of(expectedProperty));

        PropertyModel actualProperty = propertyService.findById(id);

        verify(propertyRepository, times(1)).findById(id);
        Assertions.assertEquals(expectedProperty, actualProperty);
    }

    @DisplayName("FindById NOT_FOUND")
    @Test
    void givenPropertyId_whenFindById_thenReturnNotFound() {
        UUID id = UUID.randomUUID();
        when(propertyRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> propertyService.findById(id));

        verify(propertyRepository, times(1)).findById(id);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        Assertions.assertEquals("Property not found for id: " + id, exception.getReason());
    }

    @DisplayName("Save OK")
    @Test
    void whenSaveProperty_thenReturnProperty() {
        PropertyDTO propertyDTO = new PropertyDTO();
        PropertyModel expectedProperty = new PropertyModel();

        when(propertyRepository.save(any(PropertyModel.class))).thenReturn(expectedProperty);

        PropertyModel actualProperty = propertyService.save(propertyDTO);

        verify(propertyRepository, times(1)).save(any(PropertyModel.class));
        Assertions.assertEquals(expectedProperty, actualProperty);
    }

    @DisplayName("Update OK")
    @Test
    void givenPropertyId_whenFindById_thenReturnUpdatedProperty() {
        UUID id = UUID.randomUUID();
        PropertyDTO propertyDTO = new PropertyDTO();
        PropertyModel expectedProperty = new PropertyModel();

        when(propertyRepository.findById(id)).thenReturn(Optional.of(expectedProperty));
        when(propertyRepository.save(any(PropertyModel.class))).thenReturn(expectedProperty);

        PropertyModel actualProperty = propertyService.update(id, propertyDTO);

        verify(propertyRepository, times(1)).findById(id);
        verify(propertyRepository, times(1)).save(any(PropertyModel.class));
        Assertions.assertEquals(expectedProperty, actualProperty);
    }

    @DisplayName("Update NOT_FOUND")
    @Test
    void givenPropertyId_whenFindById_thenReturnNotFoundProperty() {
        UUID id = UUID.randomUUID();
        PropertyDTO propertyDTO = new PropertyDTO();
        when(propertyRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> propertyService.update(id, propertyDTO));

        verify(propertyRepository, times(1)).findById(id);
        verify(propertyRepository, times(0)).save(any(PropertyModel.class));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        Assertions.assertEquals("Property not found for id: " + id, exception.getReason());
    }

    @DisplayName("Delete OK")
    @Test
    void whenDeleteProperty_thenReturnOK() {
        doNothing().when(propertyRepository).delete(any(PropertyModel.class));
        propertyService.delete(propertyModel);
        verify(propertyRepository, times(1)).delete(propertyModel);
    }

}
