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
        personaORM = new PersonaORM();

        personaORM.setPassportId(personaDTO.pasaporteId());
        personaORM.setFirstName(personaDTO.firstName());
        personaORM.setLastName(personaDTO.lastName());
        personaJPA.save(personaORM);

        return new RespuestaDTO(true, "Persona creada");
    }

    public RespuestaDTO updatePersona(PersonaDTO personaDTO) {
        personas = personaJPA.findByPassportId(personaDTO.pasaporteId());
        if (personas.isEmpty()) {
            return new RespuestaDTO(false, "El pasaporte ingresado no existe");
        }
        personaORM = personas.getFirst();

        personaORM.setFirstName(personaDTO.pasaporteId());
        personaORM.setFirstName(personaDTO.firstName());
        personaORM.setLastName(personaDTO.lastName());
        personaJPA.save(personaORM);

        return new RespuestaDTO(true, "Persona actualizada");
    }
}
