package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.logica.dto.EstadoDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.logica.dto.UpdateDTO;
import com.arquitectura.aerolineaback.logica.dto.VueloDTO;
import com.arquitectura.aerolineaback.logica.service.VueloService;
import com.arquitectura.aerolineaback.persistencia.jpa.VueloJPA;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import com.arquitectura.aerolineaback.rabbit.RabbitProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VueloServiceTest{

    @Mock
    private VueloJPA vueloJPA;

    @Mock
    private RabbitProducer rabbitProducer;

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

    @Test
    void DadoVueloExistente_CuandoActualizarEstado_EntoncesEstadoActualizado() {
        String flightId = "123";
        EstadoEnum previousState = EstadoEnum.PROGRAMADO;
        EstadoEnum newState = EstadoEnum.EN_VUELO;

        VueloORM vueloORM = new VueloORM();
        vueloORM.setFlightId(flightId);
        vueloORM.setEstado(previousState);

        EstadoDTO estadoDTO = new EstadoDTO(flightId, newState);

        when(vueloJPA.findById(flightId)).thenReturn(Optional.of(vueloORM));

        String result = vueloService.updateEstado(estadoDTO);

        assertEquals(flightId + ":" + previousState + "->" + newState, result);
        assertEquals(newState, vueloORM.getEstado());
        verify(vueloJPA, times(1)).save(vueloORM);
        verify(rabbitProducer, times(1)).sendMessage(any(UpdateDTO.class));
    }

    @Test
    void DadoVueloInexistente_CuandoActualizarEstado_EntoncesErrorVueloNoExiste() {
        String flightId = "123";
        EstadoEnum newState = EstadoEnum.EN_VUELO;

        EstadoDTO estadoDTO = new EstadoDTO(flightId, newState);

        when(vueloJPA.findById(flightId)).thenReturn(Optional.empty());

        String result = vueloService.updateEstado(estadoDTO);

        assertNull(result);
        verify(vueloJPA, times(0)).save(any(VueloORM.class));
    }

    @Test
    void DadoVuelosExistentes_CuandoObtenerEstados_EntoncesEstadosDevueltos() {
        VueloORM vuelo1 = new VueloORM();
        vuelo1.setFlightId("123");
        vuelo1.setEstado(EstadoEnum.PROGRAMADO);

        VueloORM vuelo2 = new VueloORM();
        vuelo2.setFlightId("124");
        vuelo2.setEstado(EstadoEnum.EN_VUELO);

        when(vueloJPA.findAll()).thenReturn(List.of(vuelo1, vuelo2));

        List<EstadoDTO> estados = vueloService.getEstados();

        assertEquals(2, estados.size());
        assertEquals("123", estados.get(0).flightId());
        assertEquals(EstadoEnum.PROGRAMADO, estados.get(0).state());
        assertEquals("124", estados.get(1).flightId());
        assertEquals(EstadoEnum.EN_VUELO, estados.get(1).state());
    }

    @Test
    void DadoNingunVuelo_CuandoObtenerEstados_EntoncesListaVacia() {
        when(vueloJPA.findAll()).thenReturn(List.of());

        List<EstadoDTO> estados = vueloService.getEstados();

        assertTrue(estados.isEmpty());
    }
}
