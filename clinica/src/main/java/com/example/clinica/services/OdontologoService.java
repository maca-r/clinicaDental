package com.example.clinica.services;

import com.example.clinica.dto.OdontologoDto;
import com.example.clinica.entity.Odontologo;
import com.example.clinica.exception.ExistingDataException;
import com.example.clinica.exception.WrongDataException;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OdontologoService implements EntityService<Odontologo, OdontologoDto> {
    @Autowired
    OdontologoRepository repository;

    public OdontologoDto save(Odontologo odontologo) throws WrongDataException, ExistingDataException {

        if (nullOrEmpty(odontologo)){
            throw new WrongDataException("Algun campo es nulo o esta vacio");
        }
        if(odontologo.getMatricula() < 0){
            throw new WrongDataException("La matricula no puede ser negativa");
        }
        if (existingData(odontologo)){
            throw new ExistingDataException("El odontologo con la matricula " + odontologo.getMatricula() + " ya existe");
        }

        Odontologo odontologoGuardado = repository.save(odontologo);
        OdontologoDto odontologoDto;
        odontologoDto = entityToDto(odontologoGuardado);

        return odontologoDto;
    }

    public Set<OdontologoDto> findAll() throws ResourceNotFoundException {
        Set<OdontologoDto> odontologos = new HashSet<>();

        for (Odontologo odontologo: repository.findAll()){
            OdontologoDto odontologoDto = entityToDto(odontologo);
            odontologos.add(odontologoDto);
        }

        if (odontologos.isEmpty()){
            throw new ResourceNotFoundException("No se encontraron odontologos");
        }

        return odontologos;

    }

    public OdontologoDto findById(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = repository.findById(id);
        OdontologoDto odontologoDto;

        if(odontologo.isPresent()){
            odontologoDto = entityToDto(odontologo.get());
        } else {
            throw new ResourceNotFoundException("No se encontro odontologo con el id " + id);
        }

        return odontologoDto;
    }


    public OdontologoDto update(Odontologo odontologoModificado) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = repository.findById(odontologoModificado.getId());
        OdontologoDto odontologoDto;

        if(odontologo.isPresent()){
            odontologoDto = entityToDto(repository.save(odontologoModificado));
        } else {
            throw new ResourceNotFoundException("No se pudo modificar al odontologo porque aun no existe");
        }

        return odontologoDto;
    }

    public void delete(Integer id) throws ResourceNotFoundException {
        Optional<Odontologo> odontologo = repository.findById(id);

        if(odontologo.isPresent()){
            repository.delete(odontologo.get());
        } else {
            throw new ResourceNotFoundException("No se pudo eliminar al odontologo con id " + id + " porque no existe");
        }
    }

    public OdontologoDto entityToDto(Odontologo odontologo) {
        OdontologoDto odontologoDto = new OdontologoDto();

        odontologoDto.setId(odontologo.getId());
        odontologoDto.setNombre(odontologo.getNombre());
        odontologoDto.setApellido(odontologo.getApellido());

        return odontologoDto;

    }

    public boolean nullOrEmpty(Odontologo odontologo){
        if ( (odontologo.getNombre() == null || odontologo.getNombre().equals("")) ||
                (odontologo.getApellido() == null || odontologo.getApellido().equals("")) ){
            return true;
        }
        return false;
    }
    public boolean existingData (Odontologo odontologoNuevo){
        List<Odontologo> odontologos = repository.findAll();

        for (Odontologo odontologoGuardado : odontologos) {
            if (odontologoGuardado.getMatricula().equals(odontologoNuevo.getMatricula())){
                return true;
            }
        }
        return false;
    }
}
