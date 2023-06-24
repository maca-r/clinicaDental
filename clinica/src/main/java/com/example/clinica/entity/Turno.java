package com.example.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Turno {
    @Id
    @SequenceGenerator(name = "turno_sequence", sequenceName = "turno_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turno_sequence")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="paciente_id")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name="odontologo_id")
    private Odontologo odontologo;
    private LocalTime hora;
    private LocalDate fecha;

    public Turno() {
    }
}
