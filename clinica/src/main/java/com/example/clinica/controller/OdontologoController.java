package com.example.clinica.controller;

import com.example.clinica.dto.OdontologoDto;
import com.example.clinica.entity.Odontologo;
import com.example.clinica.exception.ExistingDataException;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.exception.WrongDataException;
import com.example.clinica.services.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class OdontologoController {
    Logger logger = Logger.getLogger(OdontologoController.class);

    @Autowired
    private OdontologoService service;

    @PostMapping("/odontologos")
    public ResponseEntity<OdontologoDto> save(@RequestBody Odontologo odontologo) throws WrongDataException, ExistingDataException {
        ResponseEntity<OdontologoDto> response;

        try {
            OdontologoDto dtoOdontologo = service.save(odontologo);
            response = ResponseEntity.status(HttpStatus.CREATED).body(dtoOdontologo);
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


    @GetMapping("/odontologos")
    public ResponseEntity<Set<OdontologoDto>> findAll() throws ResourceNotFoundException{
        ResponseEntity<Set<OdontologoDto>> response;

        try{
            Set<OdontologoDto> odontologos = service.findAll();
            response = ResponseEntity.status(HttpStatus.OK).body(odontologos);
        } catch (ResourceNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;
    }

    @GetMapping("/odontologos/{id}")
    public ResponseEntity<OdontologoDto> findById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        ResponseEntity<OdontologoDto> response;

        try{
            OdontologoDto odontologoDto = service.findById(id);
            response = ResponseEntity.status(HttpStatus.OK).body(odontologoDto);

        } catch (ResourceNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;

    }

    @PutMapping("/odontologos")
    public ResponseEntity<OdontologoDto> update(@RequestBody Odontologo odontologo) throws ResourceNotFoundException{
        ResponseEntity<OdontologoDto> response;

        try{
            OdontologoDto odontologoDto = service.update(odontologo);
            response = ResponseEntity.status(HttpStatus.OK).body(odontologoDto);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/odontologos/{id}")
    public ResponseEntity<OdontologoDto> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        ResponseEntity<OdontologoDto> response;

        try {
            service.delete(id);
            response = ResponseEntity.status(HttpStatus.OK).build();

        } catch (ResourceNotFoundException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ResourceNotFoundException(e.getMessage());
        }

        return response;

    }


}
