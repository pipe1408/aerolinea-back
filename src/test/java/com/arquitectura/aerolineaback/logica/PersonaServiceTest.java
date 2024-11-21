package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.logica.service.PersonaService;
import com.arquitectura.aerolineaback.persistencia.jpa.PersonaJPA;
import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonaServiceTest {

    @Mock
    private PersonaJPA personaJPA;

    @InjectMocks
    private PersonaService personaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void DadaNuevaPersona_CuandoGuardarPersona_EntoncesPersonaGuardada() {
        PersonaDTO personaDTO = new PersonaDTO("A123456", "John", "Doe");
        when(personaJPA.findById(personaDTO.pasaporteId())).thenReturn(Optional.empty());

        RespuestaDTO respuesta = personaService.savePersona(personaDTO);

        assertTrue(respuesta.successful());
        verify(personaJPA, times(1)).save(any(PersonaORM.class));
    }

    @Test
    void DadaPersonaExistente_CuandoGuardarPersona_EntoncesErrorPersonaDuplicada() {
        PersonaDTO personaDTO = new PersonaDTO("A123456", "John", "Doe");
        PersonaORM personaORM = new PersonaORM();
        when(personaJPA.findById(personaDTO.pasaporteId())).thenReturn(Optional.of(personaORM));

        RespuestaDTO respuesta = personaService.savePersona(personaDTO);

        assertFalse(respuesta.successful());
        assertEquals("La persona ya existe", respuesta.mensaje());
        verify(personaJPA, times(0)).save(any(PersonaORM.class));
    }

    @Test
    void DadaPersonaExistente_CuandoActualizarPersona_EntoncesPersonaActualizada() {
        PersonaDTO personaDTO = new PersonaDTO("A123456", "John", "Doe");
        PersonaORM personaORM = new PersonaORM();
        when(personaJPA.findById(personaDTO.pasaporteId())).thenReturn(Optional.of(personaORM));

        RespuestaDTO respuesta = personaService.updatePersona(personaDTO);

        assertTrue(respuesta.successful());
        verify(personaJPA, times(1)).save(any(PersonaORM.class));
    }

    @Test
    void DadaPersonaInexistente_CuandoActualizarPersona_EntoncesErrorPersonaNoExiste() {
        PersonaDTO personaDTO = new PersonaDTO("A123456", "John", "Doe");
        when(personaJPA.findById(personaDTO.pasaporteId())).thenReturn(Optional.empty());

        RespuestaDTO respuesta = personaService.updatePersona(personaDTO);

        assertFalse(respuesta.successful());
        assertEquals("Persona no existe", respuesta.mensaje());
        verify(personaJPA, times(0)).save(any(PersonaORM.class));
    }

    @Test
    void DadaPersonaExistente_CuandoBorrarPersona_EntoncesPersonaEliminada() {
        String passportId = "A123456";
        PersonaORM personaORM = new PersonaORM();
        when(personaJPA.findById(passportId)).thenReturn(Optional.of(personaORM));

        RespuestaDTO respuesta = personaService.deletePersona(passportId);

        assertTrue(respuesta.successful());
        verify(personaJPA, times(1)).delete(personaORM);
    }

    @Test
    void DadaPersonaInexistente_CuandoBorrarPersona_EntoncesErrorPersonaNoExiste() {
        String passportId = "A123456";
        when(personaJPA.findById(passportId)).thenReturn(Optional.empty());

        RespuestaDTO respuesta = personaService.deletePersona(passportId);

        assertFalse(respuesta.successful());
        assertEquals("Persona no existe", respuesta.mensaje());
        verify(personaJPA, times(0)).delete(any(PersonaORM.class));
    }
}
