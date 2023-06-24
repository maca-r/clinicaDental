package com.example.clinica.repository;

import com.example.clinica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo,Integer> {

//    @Query("SELECT Odontologo FROM Odontologo as o where o.matricula = ?1")
//    public Optional<Odontologo> findByMatricula(Integer matricula);
}
