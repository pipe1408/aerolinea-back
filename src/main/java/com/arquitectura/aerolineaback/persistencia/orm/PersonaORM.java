package com.arquitectura.aerolineaback.persistencia.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "PersonaORM")
@Table(name = "PERSONAS")
public class PersonaORM {

    @Id
    @Column(name = "PASSPORT_ID", nullable = false, length = 50)
    private String passportId;

    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    public PersonaORM(String passportId, String firstName, String lastName) {
        this.passportId = passportId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PersonaORM() {

    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}