package com.example.clinica.services;

import com.example.clinica.dto.OdontologoDto;
import com.example.clinica.dto.PacienteDto;
import com.example.clinica.dto.TurnoDto;
import com.example.clinica.entity.Turno;
import com.example.clinica.exception.ResourceNotFoundException;
import com.example.clinica.exception.WrongDataException;
import com.example.clinica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TurnoService implements EntityService<Turno, TurnoDto>{

    @Autowired
    TurnoRepository turnoRepository;

    @Autowired
    PacienteService pacienteService;

    @Autowired
    OdontologoService odontologoService;
    public TurnoDto save(Turno turno) throws WrongDataException, ResourceNotFoundException {

        if (nullOrEmpty(turno)){
            throw new WrongDataException("El turno no puede ser creado porque algun dato es nulo o esta vacio");
        }
        if (turno.getFecha().isBefore(LocalDate.now())) {
            throw new WrongDataException("El turno no puede ser creado porque la fecha asignada es anterior a la actual");
        }

        if (turno.getHora().isBefore(LocalTime.of(07,30)) || turno.getHora().isAfter(LocalTime.of(20,30))){
            throw new WrongDataException("El turno no puede ser creado, la hora debe ser entre las 07:30 y las 20:30");
        }


        pacienteService.findById(turno.getPaciente().getId());
        odontologoService.findById(turno.getOdontologo().getId());
        Turno turnoGuardado = turnoRepository.save(turno);
        TurnoDto turnoDto;
        turnoDto = entityToDto(turnoGuardado);

        return turnoDto;
    }

    public Set<TurnoDto> findAll() throws ResourceNotFoundException {
        Set<TurnoDto> turnos = new HashSet<>();

        for(Turno turno: turnoRepository.findAll()){
            TurnoDto turnoDto = entityToDto(turno);
            turnos.add(turnoDto);
        }

        if (turnos.isEmpty()){
            throw new ResourceNotFoundException("No se encontraron turnos");
        }

        return turnos;

    }

    public TurnoDto findById(Integer id) throws ResourceNotFoundException {
        Turno turno = turnoRepository.findById(id).orElse(null);
        TurnoDto turnoDto;

        if(turno != null){
            turnoDto = entityToDto(turno);
        } else {
            throw new ResourceNotFoundException("No se encontró turno con el id " + id);
        }

        return turnoDto;
    }



    public TurnoDto update(Turno turnoModificado) throws ResourceNotFoundException {
        Optional<Turno> turno = turnoRepository.findById(turnoModificado.getId());
        TurnoDto turnoDto;

        if(turno.isPresent()){
            turnoDto = entityToDto(turnoRepository.save(turnoModificado));
        } else {
            throw new ResourceNotFoundException("No se pudo modificar el turno porque no existe, intente crearlo primero");
        }

        return turnoDto;
    }

    public void delete(Integer id) throws ResourceNotFoundException {
        Optional<Turno> turno = turnoRepository.findById(id);
        if(turno.isPresent()){
            turnoRepository.delete(turno.get());
        } else {
          throw new ResourceNotFoundException("No se pudo eliminar el turno con id " + id);
        }
    }

    public TurnoDto entityToDto(Turno turno) throws ResourceNotFoundException {
        TurnoDto turnoDto = new TurnoDto();
        PacienteDto pacienteDto = pacienteService.findById(turno.getPaciente().getId());
        OdontologoDto odontologoDto = odontologoService.findById(turno.getOdontologo().getId());

        turnoDto.setId(turno.getId());
        turnoDto.setPaciente(pacienteDto);
        turnoDto.setOdontologo(odontologoDto);
        turnoDto.setHora(turno.getHora());
        turnoDto.setFecha(turno.getFecha());

        return turnoDto;
    }

    public boolean nullOrEmpty(Turno turno){
        if (    (turno.getHora() == null || turno.getHora().equals("")) ||
                (turno.getFecha() == null || turno.getFecha().equals("")) ){
            return true;
        }

        return false;
    }
}
