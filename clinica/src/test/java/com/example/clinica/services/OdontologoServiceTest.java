package com.example.clinica.services;

import com.example.clinica.dto.OdontologoDto;
import com.example.clinica.entity.Odontologo;
import com.example.clinica.exception.ExistingDataException;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.exception.WrongDataException;
import com.example.clinica.repository.OdontologoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OdontologoServiceTest {

    @InjectMocks
    OdontologoService service;
    @Mock
    OdontologoRepository repository;


    @Test
    void alta_odontologo() throws WrongDataException, ExistingDataException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1,"Juan","Perez",123);

        Odontologo odontologoEsperado = new Odontologo();
        odontologoEsperado.setNombre("Juan");
        odontologoEsperado.setApellido("Perez");
        odontologoEsperado.setMatricula(123);
        when(repository.save(any())).thenReturn(odontologoEsperado);
        //ACT
        OdontologoDto respuesta = service.save(odontologo);

        //ASSERT
        Assertions.assertEquals(odontologo.getApellido(),respuesta.getApellido());

    }


    @Test
    void alta_odontologo_dato_nulo_o_vacio() throws WrongDataException, ExistingDataException {
        //ARRANGE
        Odontologo odontologo = new Odontologo("","Perez",123);
        //ACT

        //ASSERT
        Assertions.assertThrows(WrongDataException.class, () -> service.save(odontologo));


    }

    @Test
    void alta_odontologo_matricula_negativa() throws WrongDataException, ExistingDataException {
        //ARRANGE
        Odontologo odontologo = new Odontologo("Fausto","Perez",-123);
        //ACT

        //ASSERT
        Assertions.assertThrows(WrongDataException.class, () -> service.save(odontologo));

    }

    @Test
    void alta_odontologo_matricula_repetida() throws WrongDataException, ExistingDataException, ResourceNotFoundException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1,"Fausto","Perez",123);
        Odontologo odontologo1 = new Odontologo(2,"Paula","Perez",123);

        List<Odontologo> odontologos = new ArrayList<>();
        odontologos.add(odontologo);
        odontologos.add(odontologo1);
        //ACT
        when(repository.findAll()).thenReturn(odontologos);

        //ASSERT
        Assertions.assertThrows(ExistingDataException.class, () -> service.save(odontologo1));

    }

    @Test
    void listar_odontologos() throws ResourceNotFoundException {
        //ARRANGE
        Odontologo unOdontologo = new Odontologo("Maria","Perez",101);
        Odontologo unOdontologo2 = new Odontologo("Juana","Rodriguez",102);

        List<Odontologo> odontologos = new ArrayList<>();
        odontologos.add(unOdontologo);
        odontologos.add(unOdontologo2);

        //ACT
        when(repository.findAll()).thenReturn(odontologos);

        //ASSERT
        Assertions.assertEquals(2,service.findAll().size());
    }

    @Test
    void listar_odontologos_vacio() throws ResourceNotFoundException {
        //ARRANGE
        String message;
        //ACT
        message = Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findAll()).getMessage();
        //ASSERT
        Assertions.assertEquals("No se encontraron odontologos", message);
    }

//    @Test
//    void buscar_odontologos_por_id() throws ResourceNotFoundException {
//        //ARRANGE
//        Odontologo odontologo = new Odontologo(1,"Emilia","Martinez",102);
//        OdontologoDto odontologoEsperado = new OdontologoDto(1,"Emilia","Martinez");
//
//        //ACT
//        when(repository.findById(any())).thenReturn(Optional.of(odontologo));
//        OdontologoDto odontologoDevuelto = service.findById(1);
//
//        //ASSERT
//        Assertions.assertEquals(odontologoEsperado,odontologoDevuelto);
//
//    }

        @Test
    void buscar_odontologos_por_id() throws ResourceNotFoundException {
        //ARRANGE
        Odontologo odontologo = new Odontologo();
        odontologo.setId(1);
        odontologo.setNombre("Emilia");
        odontologo.setApellido("Martin");
        odontologo.setMatricula(107);
        OdontologoDto odontologoEsperado = new OdontologoDto(1,"Emilia","Martin");

        //ACT
        when(repository.findById(odontologo.getId())).thenReturn(Optional.of(odontologo));
        OdontologoDto odontologoDevuelto = service.findById(odontologo.getId());

        //ASSERT
        Assertions.assertEquals(odontologoEsperado.getNombre(),odontologoDevuelto.getNombre());

    }

    @Test
    void buscar_odontologos_por_id_no_encontrado() throws ResourceNotFoundException {
        //ARRANGE
        String message;
        //ACT
        when(repository.findById(5)).thenReturn(Optional.empty());
        message = Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(5)).getMessage();
        //ASSERT
        Assertions.assertEquals("No se encontro odontologo con el id 5", message);
    }

    @Test
    void modificar_odontologo() throws ResourceNotFoundException, WrongDataException, ExistingDataException {
        //ARRANGE
       Odontologo odontologoAModificar = new Odontologo();
       odontologoAModificar.setId(1);
       odontologoAModificar.setNombre("Marcos");
       odontologoAModificar.setApellido("Gimenez");
       odontologoAModificar.setMatricula(103);

        when(repository.findById(odontologoAModificar.getId())).thenReturn(Optional.of(odontologoAModificar));
        when(repository.save(odontologoAModificar)).thenReturn(odontologoAModificar);

        Odontologo modificado = new Odontologo();
        modificado.setId(1);
        modificado.setNombre("Marcos");
        modificado.setApellido("Galperin");

        when(repository.save(modificado)).thenReturn(modificado);
        //ACT
       OdontologoDto odontologoModificado = service.update(modificado);

        //ASSERT
       Assertions.assertNotEquals(odontologoAModificar.getApellido(),odontologoModificado.getApellido());

    }

    @Test
    void modificar_odontologo_excepcion() throws ResourceNotFoundException, WrongDataException, ExistingDataException {
        //ARRANGE
        Odontologo odontologoAModificar = new Odontologo();
        odontologoAModificar.setId(1);
        odontologoAModificar.setNombre("Marcos");
        odontologoAModificar.setApellido("Gimenez");
        odontologoAModificar.setMatricula(103);

        when(repository.findById(odontologoAModificar.getId())).thenReturn(Optional.empty());


        Odontologo modificado = new Odontologo();
        modificado.setId(1);
        modificado.setNombre("Marcos");
        modificado.setApellido("Galperin");

        when(repository.save(modificado)).thenReturn(modificado);
        //ACT


        //ASSERT
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(modificado));

    }


    @Test
    void eliminar_odontologo() throws ResourceNotFoundException {
        //ARRANGE
        Odontologo odontologo = new Odontologo(1,"Maria","Garcia", 101);

        //ACT
        when(repository.findById(odontologo.getId())).thenReturn(Optional.of(odontologo));
        doNothing().when(repository).delete(odontologo);
        //ASSERT
        Assertions.assertDoesNotThrow(() -> service.delete(1));

    }

    @Test
    void eliminar_odontologo_excepcion() throws ResourceNotFoundException {
        //ARRANGE

        //ACT
        when(repository.findById(2)).thenReturn(Optional.empty());

        //ASSERT
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(2));

    }
}