package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.logica.dto.VueloDTO;
import com.arquitectura.aerolineaback.logica.service.VueloService;
import com.arquitectura.aerolineaback.persistencia.jpa.VueloJPA;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VueloServiceTest{

    @Mock
    private VueloJPA vueloJPA;

    @InjectMocks
    private VueloService vueloService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void DadoNuevoVuelo_CuandoGuardarVuelo_EntoncesVueloGuardado() {
        // Adding LocalTime for the flight time (7:20 AM)
        VueloDTO vueloDTO = new VueloDTO("123", "Madrid", "Londres", LocalDate.of(2023, 10, 10), LocalTime.of(7, 20), 50);
        when(vueloJPA.findById(vueloDTO.vueloId())).thenReturn(Optional.empty());

        RespuestaDTO respuesta = vueloService.saveVuelo(vueloDTO);

        assertTrue(respuesta.successful());
        verify(vueloJPA, times(1)).save(any(VueloORM.class));
    }

    @Test
    void DadoVueloExistente_CuandoGuardarVuelo_EntoncesErrorVueloDuplicado() {
        // Adding LocalTime for the flight time (7:20 AM)
        VueloDTO vueloDTO = new VueloDTO("123", "Madrid", "Londres", LocalDate.of(2023, 10, 10), LocalTime.of(7, 20), 50);
        VueloORM vueloORM = new VueloORM();
        when(vueloJPA.findById(vueloDTO.vueloId())).thenReturn(Optional.of(vueloORM));

        RespuestaDTO respuesta = vueloService.saveVuelo(vueloDTO);

        assertFalse(respuesta.successful());
        assertEquals("Ya existe un vuelo con ese código", respuesta.mensaje());
        verify(vueloJPA, times(0)).save(any(VueloORM.class));
    }

    @Test
    void DadoVueloExistente_CuandoActualizarVuelo_EntoncesVueloActualizado() {
        // Adding LocalTime for the flight time (7:20 AM)
        VueloDTO vueloDTO = new VueloDTO("123", "Madrid", "Londres", LocalDate.of(2023, 10, 10), LocalTime.of(7, 20), 50);
        VueloORM vueloORM = new VueloORM();
        when(vueloJPA.findById(vueloDTO.vueloId())).thenReturn(Optional.of(vueloORM));

        RespuestaDTO respuesta = vueloService.updateVuelo(vueloDTO);

        assertTrue(respuesta.successful());
        verify(vueloJPA, times(1)).save(any(VueloORM.class));
    }

    @Test
    void DadoVueloInexistente_CuandoActualizarVuelo_EntoncesErrorVueloNoExiste() {
        // Adding LocalTime for the flight time (7:20 AM)
        VueloDTO vueloDTO = new VueloDTO("123", "Madrid", "Londres", LocalDate.of(2024, 9, 24), LocalTime.of(7, 20), 50);
        when(vueloJPA.findById(vueloDTO.vueloId())).thenReturn(Optional.empty());

        RespuestaDTO respuesta = vueloService.updateVuelo(vueloDTO);

        assertFalse(respuesta.successful());
        assertEquals("Vuelo no existe", respuesta.mensaje());
        verify(vueloJPA, times(0)).save(any(VueloORM.class));
    }

    @Test
    void DadoVueloExistente_CuandoBorrarVuelo_EntoncesVueloEliminado() {
        String flightId = "123";
        VueloORM vueloORM = new VueloORM();
        when(vueloJPA.findById(flightId)).thenReturn(Optional.of(vueloORM));

        RespuestaDTO respuesta = vueloService.deleteVuelo(flightId);

        assertTrue(respuesta.successful());
        verify(vueloJPA, times(1)).delete(vueloORM);
    }

    @Test
    void DadoVueloInexistente_CuandoBorrarVuelo_EntoncesErrorVueloNoExiste() {
        String flightId = "123";
        when(vueloJPA.findById(flightId)).thenReturn(Optional.empty());

        RespuestaDTO respuesta = vueloService.deleteVuelo(flightId);

        assertFalse(respuesta.successful());
        assertEquals("Vuelo no existe", respuesta.mensaje());
        verify(vueloJPA, times(0)).delete(any(VueloORM.class));
    }
}
