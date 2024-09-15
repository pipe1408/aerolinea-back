package com.arquitectura.aerolineaback.persistencia.jpa;

import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import com.arquitectura.aerolineaback.persistencia.orm.ReservaORM;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaJPA extends JpaRepository<ReservaORM, String> {
    Optional<ReservaORM> findByPassportAndFlight(PersonaORM passport, VueloORM flight);
    List<ReservaORM> findAllByPassport(PersonaORM personaORM);
    List<ReservaORM> findAllByFlight(VueloORM flight);
}
