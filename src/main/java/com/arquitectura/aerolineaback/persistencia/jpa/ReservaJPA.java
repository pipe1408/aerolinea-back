package com.arquitectura.aerolineaback.persistencia.jpa;

import com.arquitectura.aerolineaback.persistencia.orm.ReservaORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaJPA extends JpaRepository<ReservaORM, String> {

}
