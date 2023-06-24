package com.example.clinica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Domicilio {
    @Id
    @SequenceGenerator(name = "domicilios_sequence", sequenceName = "domicilios_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domicilios_sequence")
    private Integer id;
    private Integer nroCalle;
    private String calle;
    private String localidad;
    private String provincia;

    public Domicilio() {
    }
}
