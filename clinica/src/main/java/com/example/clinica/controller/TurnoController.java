package com.example.clinica.controller;


import com.example.clinica.dto.TurnoDto;
import com.example.clinica.entity.Turno;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.exception.WrongDataException;
import com.example.clinica.services.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class TurnoController {
    Logger logger = Logger.getLogger(OdontologoController.class);
    @Autowired
    private TurnoService service;

    @PostMapping("/turnos")
    public ResponseEntity<TurnoDto> save(@RequestBody Turno turno) throws WrongDataException,ResourceNotFoundException {
        ResponseEntity<TurnoDto> response;

        try{
            response = ResponseEntity.status(HttpStatus.CREATED).body(service.save(turno));
        } catch (WrongDataException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new WrongDataException(e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;
    }


    @GetMapping("/turnos")
    public ResponseEntity<Set<TurnoDto>> findAll() throws ResourceNotFoundException {
        ResponseEntity<Set<TurnoDto>> response;

        try{
            response = ResponseEntity.ok(service.findAll());
        } catch (ResourceNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;
    }

    @GetMapping("/turnos/{id}")
    public ResponseEntity<TurnoDto> findById(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<TurnoDto> response;

        try{
            response = ResponseEntity.ok(service.findById(id));

        } catch (Exception | ResourceNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;

    }

    @PutMapping("/turnos")
    public ResponseEntity<TurnoDto> update(@RequestBody Turno turno) throws ResourceNotFoundException {
        ResponseEntity<TurnoDto> response = null;

        try{
            response = ResponseEntity.ok(service.update(turno));
        } catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/turnos/{id}")
    public ResponseEntity<TurnoDto> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<TurnoDto> response;

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
