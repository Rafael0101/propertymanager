package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PeopleDTO {


    @NotBlank
    private String name;
    @NotNull
    private LocalDate initialDate;
    @NotNull
    private LocalDate finalDate;
    @NotBlank
    private String observation;

    @NotNull
    private UUID propertyId;
    @NotNull
    private UUID laboratoryId;

}
