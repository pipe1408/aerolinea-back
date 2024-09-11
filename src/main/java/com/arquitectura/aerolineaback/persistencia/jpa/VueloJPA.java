package com.arquitectura.aerolineaback.persistencia.jpa;

import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VueloJPA extends JpaRepository<VueloORM, Long> {
}
