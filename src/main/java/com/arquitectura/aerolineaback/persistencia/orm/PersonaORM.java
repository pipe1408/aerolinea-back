package com.arquitectura.aerolineaback.persistencia.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "PersonaORM")
@Table(name = "PERSONAS")
@Data
public class PersonaORM {
    @Id
    @Column(name = "PASSPORT_ID", nullable = false, length = 50)
    private String passportId;

    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", length = 50)
    private String lastName;
}