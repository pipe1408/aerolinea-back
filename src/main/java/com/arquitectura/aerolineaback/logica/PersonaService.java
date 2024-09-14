package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.jpa.PersonaJPA;
import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    private final PersonaJPA personaJPA;
    private PersonaORM personaORM;
    List<PersonaORM> personas;

    public PersonaService(PersonaJPA personaJPA) {
        this.personaJPA = personaJPA;
    }

    public List<PersonaORM> getPersonas() {
        return personaJPA.findAll();
    }

    public Optional<PersonaORM> getPersona(String passportId) {
        return personaJPA.findById(passportId);
    }

    public RespuestaDTO savePersona(PersonaDTO personaDTO) {

        if (getPersona(personaDTO.pasaporteId()).isPresent()) {
            return new RespuestaDTO(false, "La persona ya existe");
        }

        personaORM = new PersonaORM();
        personaORM.setPassportId(personaDTO.pasaporteId());
        personaORM.setFirstName(personaDTO.firstName());
        personaORM.setLastName(personaDTO.lastName());
        personaJPA.save(personaORM);

        return new RespuestaDTO(true, "Persona creada");
    }

    public RespuestaDTO updatePersona(PersonaDTO personaDTO) {
        Optional<PersonaORM> optionalPersona = getPersona(personaDTO.pasaporteId());
        if (optionalPersona.isEmpty()) {
            return new RespuestaDTO(false, "Persona no existe");
        }
        personaORM = optionalPersona.get();
        personaORM.setFirstName(personaDTO.firstName());
        personaORM.setLastName(personaDTO.lastName());
        personaJPA.save(personaORM);

        return new RespuestaDTO(true, "Persona actualizada");
    }

    public RespuestaDTO deletePersona(String passportId) {
        Optional<PersonaORM> persona = getPersona(passportId);

        if (persona.isEmpty()) {
            return new RespuestaDTO(false, "Persona no existe");
        }
        personaJPA.delete(persona.get());
        return new RespuestaDTO(true, "Persona eliminada");
    }
}
