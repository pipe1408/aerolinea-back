package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.logica.service.PersonaService;
import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonaControllerTest{

    @Mock
    private PersonaService personaService;

    @InjectMocks
    private PersonaController personaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void DadoListaDePersonas_CuandoGetPersonas_EntoncesRetornarLista() {
        List<PersonaORM> personas = Arrays.asList(new PersonaORM(), new PersonaORM());
        when(personaService.getPersonas()).thenReturn(personas);

        List<PersonaORM> result = personaController.getPersonas();

        assertEquals(2, result.size());
        verify(personaService, times(1)).getPersonas();
    }

    @Test
    void DadaPersonaExistente_CuandoGetPersonaPorId_EntoncesRetornarPersona() {
        String passportId = "A123456";
        Optional<PersonaORM> persona = Optional.of(new PersonaORM());
        when(personaService.getPersona(passportId)).thenReturn(persona);

        Optional<PersonaORM> result = personaController.getPersona(passportId);

        assertTrue(result.isPresent());
        verify(personaService, times(1)).getPersona(passportId);
    }

    @Test
    void DadaNuevaPersona_CuandoGuardarPersona_EntoncesPersonaGuardada() {
        PersonaDTO personaDTO = new PersonaDTO("A123456", "John", "Doe");
        RespuestaDTO respuesta = new RespuestaDTO(true, "Persona creada");
        when(personaService.savePersona(personaDTO)).thenReturn(respuesta);

        RespuestaDTO result = personaController.guardarPersona(personaDTO);

        assertTrue(result.successful());
        verify(personaService, times(1)).savePersona(personaDTO);
    }

    @Test
    void DadaPersonaExistente_CuandoActualizarPersona_EntoncesPersonaActualizada() {
        PersonaDTO personaDTO = new PersonaDTO("A123456", "John", "Doe");
        RespuestaDTO respuesta = new RespuestaDTO(true, "Persona actualizada");
        when(personaService.updatePersona(personaDTO)).thenReturn(respuesta);

        RespuestaDTO result = personaController.actualizarPersona(personaDTO);

        assertTrue(result.successful());
        verify(personaService, times(1)).updatePersona(personaDTO);
    }

    @Test
    void DadaPersonaExistente_CuandoBorrarPersona_EntoncesPersonaBorrada() {
        String passportId = "A123456";
        RespuestaDTO respuesta = new RespuestaDTO(true, "Persona eliminada");
        when(personaService.deletePersona(passportId)).thenReturn(respuesta);

        RespuestaDTO result = personaController.borrarPersona(passportId);

        assertTrue(result.successful());
        verify(personaService, times(1)).deletePersona(passportId);
    }
}
