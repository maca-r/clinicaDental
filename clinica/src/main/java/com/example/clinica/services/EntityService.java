package com.example.clinica.services;

import com.example.clinica.exception.ExistingDataException;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.exception.WrongDataException;

import java.util.Set;

public interface EntityService <Entity, Dto>{

    Dto save(Entity entity) throws WrongDataException, ExistingDataException, ResourceNotFoundException;

    Set<Dto> findAll() throws ResourceNotFoundException;

    Dto findById(Integer id) throws ResourceNotFoundException;

    Dto update(Entity entity) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;

    Dto entityToDto(Entity entity) throws ResourceNotFoundException;


}
