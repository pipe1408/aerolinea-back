package com.arquitectura.aerolineaback.Pruebas;

import com.arquitectura.aerolineaback.controller.ReservaController;
import com.arquitectura.aerolineaback.logica.ReservaService;
import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.orm.ReservaORM;
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

class ReservaControllerTest {

    @Mock
    private ReservaService reservaService;

    @InjectMocks
    private ReservaController reservaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void DadoListaDeReservas_CuandoGetReservas_EntoncesRetornarLista() {
        List<ReservaORM> reservas = Arrays.asList(new ReservaORM(), new ReservaORM());
        when(reservaService.getReservas()).thenReturn(reservas);

        List<ReservaORM> result = reservaController.getReservas();

        assertEquals(2, result.size());
        verify(reservaService, times(1)).getReservas();
    }

    @Test
    void DadaReservaExistente_CuandoGetReservaPorId_EntoncesRetornarReserva() {
        String ticketId = "R123456";
        Optional<ReservaORM> reserva = Optional.of(new ReservaORM());
        when(reservaService.getReserva(ticketId)).thenReturn(reserva);

        Optional<ReservaORM> result = reservaController.getReserva(ticketId);

        assertTrue(result.isPresent());
        verify(reservaService, times(1)).getReserva(ticketId);
    }

    @Test
    void DadaPersonaExistente_CuandoGetReservasPorPersona_EntoncesRetornarListaDeReservas() {
        String passportId = "A123456";
        List<ReservaORM> reservas = Arrays.asList(new ReservaORM(), new ReservaORM());
        when(reservaService.getReservasByPersona(passportId)).thenReturn(reservas);

        List<ReservaORM> result = reservaController.getReservasPorPersona(passportId);

        assertEquals(2, result.size());
        verify(reservaService, times(1)).getReservasByPersona(passportId);
    }

    @Test
    void DadoVueloExistente_CuandoGetReservasPorVuelo_EntoncesRetornarListaDeReservas() {
        String vueloId = "V123456";
        List<ReservaORM> reservas = Arrays.asList(new ReservaORM(), new ReservaORM());
        when(reservaService.getReservasByFlight(vueloId)).thenReturn(reservas);

        List<ReservaORM> result = reservaController.getReservasPorVuelo(vueloId);

        assertEquals(2, result.size());
        verify(reservaService, times(1)).getReservasByFlight(vueloId);
    }

    @Test
    void DadaNuevaReserva_CuandoGuardarReserva_EntoncesReservaGuardada() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        RespuestaDTO respuesta = new RespuestaDTO(true, "Reserva creada");
        when(reservaService.saveReserva(reservaDTO)).thenReturn(respuesta);

        RespuestaDTO result = reservaController.guardarReserva(reservaDTO);

        assertTrue(result.successful());
        verify(reservaService, times(1)).saveReserva(reservaDTO);
    }

    @Test
    void DadaReservaExistente_CuandoActualizarReserva_EntoncesReservaActualizada() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        RespuestaDTO respuesta = new RespuestaDTO(true, "Reserva actualizada");
        when(reservaService.updateReserva(reservaDTO)).thenReturn(respuesta);

        RespuestaDTO result = reservaController.actualizarReserva(reservaDTO);

        assertTrue(result.successful());
        verify(reservaService, times(1)).updateReserva(reservaDTO);
    }

    @Test
    void DadaReservaExistente_CuandoBorrarReserva_EntoncesReservaBorrada() {
        String ticketId = "T123456";
        RespuestaDTO respuesta = new RespuestaDTO(true, "Reserva eliminada");
        when(reservaService.deleteReserva(ticketId)).thenReturn(respuesta);

        RespuestaDTO result = reservaController.borrarReserva(ticketId);

        assertTrue(result.successful());
        verify(reservaService, times(1)).deleteReserva(ticketId);
    }
}
