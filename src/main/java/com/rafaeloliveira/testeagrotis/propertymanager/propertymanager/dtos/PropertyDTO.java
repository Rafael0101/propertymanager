package com.rafaeloliveira.testeagrotis.propertymanager.propertymanager.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
public class PropertyDTO {

    @NotBlank
    private String name;

    @CNPJ
    private String cnpj;

    private Set<PeopleDTO> peoples = new HashSet<>();
}
