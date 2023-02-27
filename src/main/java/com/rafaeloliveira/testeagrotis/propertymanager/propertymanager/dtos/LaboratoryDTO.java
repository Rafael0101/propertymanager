package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
public class LaboratoryDTO {

    @NotBlank
    private String name;
    private Set<PeopleDTO> peoples = new HashSet<>();
}
