package com.example.clinica.repository;

import com.example.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}
