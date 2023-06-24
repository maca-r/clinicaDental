package com.example.clinica.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomicilioDto {

    private Integer id;
    private String calle;
    private Integer nroCalle;


    public DomicilioDto(Integer id, String calle, Integer nroCalle) {
        this.id = id;
        this.calle = calle;
        this.nroCalle = nroCalle;
    }

    public DomicilioDto() {
    }
}
