package com.example.clinica.controller;

import com.example.clinica.dto.OdontologoDto;
import com.example.clinica.entity.Odontologo;
import com.example.clinica.exception.ExistingDataException;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.exception.WrongDataException;
import com.example.clinica.services.OdontologoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OdontologoControllerTest {

    @Mock
    OdontologoService service;
    @InjectMocks
    OdontologoController controller;

    //para que los mocks esten inicializados correctamente antes de cada prueba
    // (sino da error por service null)
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() throws WrongDataException, ExistingDataException {
        //ARRANGE
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1);
        odontologo.setNombre("Marcos");
        odontologo.setApellido("Galperin");
        odontologo.setMatricula(123);

        OdontologoDto odontologoDto = service.save(odontologo);

        when(service.save(odontologo)).thenReturn(odontologoDto);

        //ACT
        ResponseEntity<OdontologoDto> response = controller.save(odontologo);

        //ASSERTION
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(odontologoDto),response);
    }

    @Test
    public void save_null_field_odontologo() throws WrongDataException, ExistingDataException{
        // ARRANGE
        Odontologo odontologo = new Odontologo(2,"","Perez",657);

        when(service.save(odontologo)).thenThrow(new WrongDataException("Algun campo es nulo o esta vacio"));

        // ACT - ASSERTION
        try {
            controller.save(odontologo);
        } catch (WrongDataException e) {
            assertEquals("Algun campo es nulo o esta vacio", e.getMessage());
        }
    }

    @Test
    public void save_negative_number_odontologo() throws WrongDataException, ExistingDataException{
        // ARRANGE
        Odontologo odontologo = new Odontologo(1,"Juan","Garcia",-1005);

        when(service.save(any())).thenThrow(new WrongDataException("La matricula no puede ser negativa"));
        // ACT - ASSERTION
        try {
            controller.save(odontologo);
        } catch (WrongDataException e) {
            assertEquals("La matricula no puede ser negativa", e.getMessage());
        }

    }

    @Test
    public void save_existing_data_odontologo() throws WrongDataException, ExistingDataException{
        // ARRANGE
        Odontologo odontologo = new Odontologo(1,"Juan","Perez",657);

        when(service.save(odontologo)).thenThrow(new ExistingDataException("El odontologo con la matricula "+ odontologo.getMatricula() + " ya existe"));
        // ACT - ASSERTION
        try {
            controller.save(odontologo);
        } catch (ExistingDataException e) {
            assertEquals("El odontologo con la matricula 657 ya existe", e.getMessage());
        }
    }

    @Test
    void findAll() throws ResourceNotFoundException {
        //ARRANGE
        OdontologoDto odontologo = new OdontologoDto(1,"Marcos","Galperin");
        OdontologoDto odontologo1 = new OdontologoDto(2, "Gaston", "Jofre");
        Set<OdontologoDto> odontologos = new HashSet<>();
        odontologos.add(odontologo);
        odontologos.add(odontologo1);

        when(service.findAll()).thenReturn(odontologos);

        //ACT
        ResponseEntity<Set< OdontologoDto>> response = controller.findAll();

        //ASSERTION
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(odontologos),response);


    }

    @Test
    void findAll_excepcion() throws ResourceNotFoundException {
        when(service.findAll()).thenThrow(new ResourceNotFoundException("No se encontraron odontologos"));
        // ACT - ASSERTION
        try {
            controller.findAll();
        } catch (ResourceNotFoundException e) {
            assertEquals("No se encontraron odontologos", e.getMessage());
        }
    }

    @Test
    void findById() throws ResourceNotFoundException {
        //ARRANGE
        OdontologoDto odontologoDto = new OdontologoDto(1,"Gabriela","Marin");

        when(service.findById(any())).thenReturn(odontologoDto);

        //ACT
        ResponseEntity<OdontologoDto> response = controller.findById(odontologoDto.getId());

        //ASSERTION
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(odontologoDto),response);
    }

    @Test
    void findById_excepcion() throws ResourceNotFoundException {
        OdontologoDto odontologoDto = new OdontologoDto(9,"Juan","Garcia");
        when(service.findById(any())).thenThrow(new ResourceNotFoundException("No se encontro odontologo con el id " + odontologoDto.getId()));
        // ACT - ASSERTION
        try {
            controller.findById(9);
        } catch (ResourceNotFoundException e) {
            assertEquals("No se encontro odontologo con el id 9", e.getMessage());
        }
    }

    @Test
    void update() throws ResourceNotFoundException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1,"Gabriel","Marin",6523);
        OdontologoDto odontologoDto = new OdontologoDto(1,"Gabriela","Marin");

        when(service.update(odontologo)).thenReturn(odontologoDto);

        //ACT
        ResponseEntity<OdontologoDto> response = controller.update(odontologo);

        //ASSERTION
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(odontologoDto),response);
    }

    @Test
    void update_excepcion() throws ResourceNotFoundException {
        Odontologo odontologo = new Odontologo(1,"Gabriel","Marin",6523);
        when(service.update(any())).thenThrow(new ResourceNotFoundException("No se pudo modificar al odontologo porque aun no existe"));
        // ACT - ASSERTION
        try {
            controller.update(odontologo);
        } catch (ResourceNotFoundException e) {
            assertEquals("No se pudo modificar al odontologo porque aun no existe", e.getMessage());
        }
    }

    @Test
    void delete() throws ResourceNotFoundException {
        //ARRANGE
        doNothing().when(service).delete(any());

        //ACT
        ResponseEntity<OdontologoDto> response = controller.delete(1);

        //ASSERTION
        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),response);
    }

    @Test
    void delete_excepcion() throws ResourceNotFoundException {
        //ARRANGE
        doNothing().when(service).delete(any());

        //ACT
        try {
            controller.delete(1);
        } catch (ResourceNotFoundException e) {
            assertEquals("No se pudo eliminar al odontologo con id 1 porque no existe", e.getMessage());
        }
    }

}