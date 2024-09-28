package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.jpa.ReservaJPA;
import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import com.arquitectura.aerolineaback.persistencia.orm.ReservaORM;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaServiceTest {

    @Mock
    private ReservaJPA reservaJPA;

    @Mock
    private VueloService vueloService;

    @Mock
    private PersonaService personaService;

    @Mock
    private AsientosManager asientosManager;

    @Mock
    private DateValidator dateValidator;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void DadaNuevaReserva_CuandoGuardarReserva_EntoncesReservaGuardada() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        VueloORM vueloORM = new VueloORM();
        PersonaORM personaORM = new PersonaORM();

        when(vueloService.getVuelo("V123456")).thenReturn(Optional.of(vueloORM));
        when(personaService.getPersona("A123456")).thenReturn(Optional.of(personaORM));
        when(reservaJPA.findByPassportAndFlight(personaORM, vueloORM)).thenReturn(Optional.empty());
        when(asientosManager.checkDisponibilidad(vueloORM)).thenReturn(true);
        when(dateValidator.fechaReservable(vueloORM)).thenReturn(true);

        RespuestaDTO respuesta = reservaService.saveReserva(reservaDTO);

        assertTrue(respuesta.successful());
        verify(reservaJPA, times(1)).save(any(ReservaORM.class));
    }

    @Test
    void DadaReservaDuplicada_CuandoGuardarReserva_EntoncesErrorReservaDuplicada() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        VueloORM vueloORM = new VueloORM();
        PersonaORM personaORM = new PersonaORM();
        ReservaORM reservaORM = new ReservaORM();

        when(vueloService.getVuelo("V123456")).thenReturn(Optional.of(vueloORM));
        when(personaService.getPersona("A123456")).thenReturn(Optional.of(personaORM));
        when(reservaJPA.findByPassportAndFlight(personaORM, vueloORM)).thenReturn(Optional.of(reservaORM));

        RespuestaDTO respuesta = reservaService.saveReserva(reservaDTO);

        assertFalse(respuesta.successful());
        assertEquals("La persona ya tiene reserva para ese vuelo", respuesta.mensaje());
        verify(reservaJPA, times(0)).save(any(ReservaORM.class));
    }

    @Test
    void DadaReservaExistente_CuandoActualizarReserva_EntoncesReservaActualizada() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        ReservaORM reservaORM = new ReservaORM();
        VueloORM vueloORM = new VueloORM();
        PersonaORM personaORM = new PersonaORM();

        when(vueloService.getVuelo("V123456")).thenReturn(Optional.of(vueloORM));
        when(personaService.getPersona("A123456")).thenReturn(Optional.of(personaORM));
        when(reservaService.getReserva("T123456")).thenReturn(Optional.of(reservaORM));

        RespuestaDTO respuesta = reservaService.updateReserva(reservaDTO);

        assertTrue(respuesta.successful());
        verify(reservaJPA, times(1)).save(reservaORM);
    }

    @Test
    void DadaReservaInexistente_CuandoActualizarReserva_EntoncesErrorReservaNoExiste() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        when(reservaService.getReserva("T123456")).thenReturn(Optional.empty());

        RespuestaDTO respuesta = reservaService.updateReserva(reservaDTO);

        assertFalse(respuesta.successful());
        assertEquals("Vuelo o persona no encontrados", respuesta.mensaje()); // Cambiado aquí
        verify(reservaJPA, times(0)).save(any(ReservaORM.class));
    }
}
