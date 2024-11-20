package com.arquitectura.aerolineaback.persistencia.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PERSONAS")
public class PersonaORM {
    @Id
    @Size(max = 50)
    @Column(name = "PASSPORT_ID", nullable = false, length = 50)
    private String passportId;

    @Size(max = 50)
    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

}