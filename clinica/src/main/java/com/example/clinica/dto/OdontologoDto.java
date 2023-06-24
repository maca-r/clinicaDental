package com.example.clinica.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class OdontologoDto {

    private Integer id;
    private String nombre;
    private String apellido;


    public OdontologoDto() {
    }

    public OdontologoDto(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public OdontologoDto(Integer id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OdontologoDto that = (OdontologoDto) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido);
    }
}
