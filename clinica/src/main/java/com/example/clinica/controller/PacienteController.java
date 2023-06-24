package com.example.clinica.controller;

import com.example.clinica.dto.PacienteDto;
import com.example.clinica.entity.Paciente;
import com.example.clinica.exception.ExistingDataException;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.exception.WrongDataException;
import com.example.clinica.services.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;


@RestController
public class PacienteController {
    Logger logger = Logger.getLogger(PacienteController.class);
    @Autowired
    private PacienteService service;

    @PostMapping("/pacientes")
    public ResponseEntity<PacienteDto> save(@RequestBody Paciente paciente) throws WrongDataException, ExistingDataException {
        ResponseEntity<PacienteDto> response;

        try{
            PacienteDto dtoPaciente = service.save(paciente);
            response = ResponseEntity.status(HttpStatus.CREATED).body(dtoPaciente);
        } catch (WrongDataException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new WrongDataException(e.getMessage());
        } catch (ExistingDataException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ExistingDataException(e.getMessage());
        }

        return response;
    }


    @GetMapping("/pacientes")
    public ResponseEntity<Set<PacienteDto>> findAll() throws ResourceNotFoundException {
        ResponseEntity<Set<PacienteDto>> response;

        try{
            Set<PacienteDto> pacientes = service.findAll();
            response = ResponseEntity.status(HttpStatus.OK).body(pacientes);
        } catch (ResourceNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<PacienteDto> findById(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<PacienteDto> response;

        try{
            PacienteDto pacienteDto = service.findById(id);
            response = ResponseEntity.status(HttpStatus.OK).body(pacienteDto);

        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;

    }

    @PutMapping("/pacientes")
    public ResponseEntity<PacienteDto> update(@RequestBody Paciente paciente) throws ResourceNotFoundException {
        ResponseEntity<PacienteDto> response;

        try{
            PacienteDto pacienteDto = service.update(paciente);
            response = ResponseEntity.status(HttpStatus.OK).body(pacienteDto);
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<PacienteDto> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<PacienteDto> response;

        try {
            service.delete(id);
            response = ResponseEntity.status(HttpStatus.OK).build();

        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;

    }

}
