package com.example.clinica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class TurnoDto {
    private Integer id;
    private PacienteDto paciente;
    private OdontologoDto odontologo;
    private LocalTime hora;
    private LocalDate fecha;

    public TurnoDto() {
    }
}
