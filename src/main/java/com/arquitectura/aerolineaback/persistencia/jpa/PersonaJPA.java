package com.arquitectura.aerolineaback.persistencia.jpa;

import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaJPA extends JpaRepository<PersonaORM, String> {

}
