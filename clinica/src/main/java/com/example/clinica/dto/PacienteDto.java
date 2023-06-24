package com.example.clinica.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PacienteDto {

    private Integer id;
    private String apellido;
    private String nombre;
    private String email;
    private LocalDate fechaIngreso;

    public PacienteDto() {
    }


}
