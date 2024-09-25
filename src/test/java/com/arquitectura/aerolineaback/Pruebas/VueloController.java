package com.arquitectura.aerolineaback.Pruebas;

import com.arquitectura.aerolineaback.controller.VueloController;
import com.arquitectura.aerolineaback.logica.VueloService;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.logica.dto.VueloDTO;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VueloControllerTest {

    @Mock
    private VueloService vueloService;

    @InjectMocks
    private VueloController vueloController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void DadoListaDeVuelos_CuandoGetVuelos_EntoncesRetornarLista() {
        List<VueloORM> vuelos = Arrays.asList(new VueloORM(), new VueloORM());
        when(vueloService.getVuelos()).thenReturn(vuelos);

        List<VueloORM> result = vueloController.getVuelos();

        assertEquals(2, result.size());
        verify(vueloService, times(1)).getVuelos();
    }

    @Test
    void DadoVueloExistente_CuandoGetVueloPorId_EntoncesRetornarVuelo() {
        String flightId = "123";
        Optional<VueloORM> vuelo = Optional.of(new VueloORM());
        when(vueloService.getVuelo(flightId)).thenReturn(vuelo);

        Optional<VueloORM> result = vueloController.getVuelo(flightId);

        assertTrue(result.isPresent());
        verify(vueloService, times(1)).getVuelo(flightId);
    }

    @Test
    void DadoNuevoVuelo_CuandoGuardarVuelo_EntoncesVueloGuardado() {
        VueloDTO vueloDTO = new VueloDTO("123", "Madrid", "Londres", LocalDate.of(2023,10,10), 50);
        RespuestaDTO respuesta = new RespuestaDTO(true, "Vuelo creado");
        when(vueloService.saveVuelo(vueloDTO)).thenReturn(respuesta);

        RespuestaDTO result = vueloController.guardarVuelo(vueloDTO);

        assertTrue(result.successful());
        verify(vueloService, times(1)).saveVuelo(vueloDTO);
    }

    @Test
    void DadoVueloExistente_CuandoActualizarVuelo_EntoncesVueloActualizado() {
        VueloDTO vueloDTO = new VueloDTO("123", "Madrid", "Londres", LocalDate.of(2023,10,10), 50);
        RespuestaDTO respuesta = new RespuestaDTO(true, "Vuelo actualizado");
        when(vueloService.updateVuelo(vueloDTO)).thenReturn(respuesta);

        RespuestaDTO result = vueloController.actualizarVuelo(vueloDTO);

        assertTrue(result.successful());
        verify(vueloService, times(1)).updateVuelo(vueloDTO);
    }

    @Test
    void DadoVueloExistente_CuandoBorrarVuelo_EntoncesVueloBorrado() {
        String flightId = "123";
        RespuestaDTO respuesta = new RespuestaDTO(true, "Vuelo eliminado");
        when(vueloService.deleteVuelo(flightId)).thenReturn(respuesta);

        RespuestaDTO result = vueloController.borrarVuelo(flightId);

        assertTrue(result.successful());
        verify(vueloService, times(1)).deleteVuelo(flightId);
    }
}
