package com.example.clinica.services;



import com.example.clinica.dto.PacienteDto;
import com.example.clinica.entity.Domicilio;
import com.example.clinica.entity.Paciente;
import com.example.clinica.exception.ExistingDataException;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.exception.WrongDataException;
import com.example.clinica.repository.DomicilioRepository;
import com.example.clinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PacienteService implements EntityService<Paciente, PacienteDto>{

    @Autowired
    PacienteRepository repository;

    @Autowired
    DomicilioRepository domicilioRepository;


    public PacienteDto save(Paciente paciente) throws WrongDataException, ExistingDataException {


        if (paciente.getNombre().length() > 20 || paciente.getApellido().length() > 20){
            throw new WrongDataException("El campo nombre o apellido no puede superar los 20 caracteres");
        }

        if(paciente.getDni() < 0){
            throw new WrongDataException("El dni no puede ser un numero negativo");
        }

        if(paciente.getDomicilio().getNroCalle() < 0){
            throw new WrongDataException("El nro de calle no puede ser un numero negativo");
        }

        if (String.valueOf(paciente.getDni()).length() > 8){
            throw new WrongDataException("El campo dni no puede superar los 8 caracteres");
        }

        if(paciente.getFechaIngreso().isBefore(LocalDate.now())){
            throw new WrongDataException("El paciente no puede ser ingresado con una fecha anterior a hoy");
        }

        if(nullOrEmpty(paciente)){
            throw new WrongDataException("Algun campo es nulo o esta vacio");
        }

        if(existingData(paciente)){
            throw new ExistingDataException("El paciente con el dni " + paciente.getDni() + "ya existe");
        }


        Paciente pacienteGuardado = repository.save(paciente);
        Domicilio domicilio = domicilioRepository.save(paciente.getDomicilio());
        PacienteDto pacienteDto;
        pacienteDto = entityToDto(pacienteGuardado);
        return pacienteDto;

    }

    public Set<PacienteDto> findAll() throws ResourceNotFoundException {
        Set<PacienteDto> pacientes = new HashSet<>();

        for (Paciente paciente: repository.findAll()){
            PacienteDto pacienteDto = entityToDto(paciente);
            pacientes.add(pacienteDto);
        }

        if (pacientes.isEmpty()){
            throw new ResourceNotFoundException("No se encontraron pacientes");
        }

            return pacientes;

    }

    public PacienteDto findById(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> paciente = repository.findById(id);
        PacienteDto pacienteDto;

        if (paciente.isPresent()){
            pacienteDto = entityToDto(paciente.get());
        } else {
            throw new ResourceNotFoundException("No se encontró paciente con el id " + id);
        }

        return pacienteDto;
    }

    public PacienteDto update(Paciente pacienteModificado) throws ResourceNotFoundException {
        Optional<Paciente> paciente = repository.findById(pacienteModificado.getId());
        PacienteDto pacienteDto;

        if(paciente.isPresent()){
            //domicilioRepository.save(pacienteModificado.getDomicilio());
            pacienteDto = entityToDto(repository.save(pacienteModificado));
        } else {
            throw new ResourceNotFoundException("No se pudo modificar al paciente porque aun no existe");
        }

        return pacienteDto;
    }

    public void delete(Integer id) throws ResourceNotFoundException {
        Optional<Paciente> paciente = repository.findById(id);

        if(paciente.isPresent()){
            repository.delete(paciente.get());
        } else {
            throw new ResourceNotFoundException("No se pudo eliminar al paciente con id " + id + " porque no existe");
        }

    }

    public PacienteDto entityToDto(Paciente paciente) {
        PacienteDto pacienteDto = new PacienteDto();

        pacienteDto.setId(paciente.getId());
        pacienteDto.setNombre(paciente.getNombre());
        pacienteDto.setApellido(paciente.getApellido());
        pacienteDto.setEmail(paciente.getEmail());
        pacienteDto.setFechaIngreso(paciente.getFechaIngreso());

        return pacienteDto;
    }

    public boolean nullOrEmpty(Paciente paciente){
        if ( (paciente.getApellido() == null || paciente.getApellido().equals("")) ||
                (paciente.getNombre() == null || paciente.getNombre().equals("")) ||
                (paciente.getEmail() == null || paciente.getEmail().equals("")) ||
                (paciente.getDni() == null || paciente.getDni().equals("")) ||
                (paciente.getFechaIngreso() == null || paciente.getFechaIngreso().equals(""))
                 ){
            return true;
        }
        Domicilio domicilio = paciente.getDomicilio();
        if (domicilio.getCalle() == null || domicilio.getCalle().equals("") ||
                domicilio.getNroCalle() == null || domicilio.getNroCalle().equals("") ||
                domicilio.getProvincia() == null || domicilio.getProvincia().equals("") ||
                domicilio.getLocalidad() == null || domicilio.getLocalidad().equals("") ){
            return true;
        }

        return false;
    }
    public boolean existingData (Paciente pacienteNuevo){
        List<Paciente> pacientes = repository.findAll();

        for (Paciente pacienteGuardado : pacientes) {
            if (pacienteGuardado.getDni().equals(pacienteNuevo.getDni())){
                return true;
            }
        }
        return false;
    }

}
