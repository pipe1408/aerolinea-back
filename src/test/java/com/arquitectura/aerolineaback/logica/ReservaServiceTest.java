package com.arquitectura.aerolineaback.logica;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.jpa.ReservaJPA;
import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import com.arquitectura.aerolineaback.persistencia.orm.ReservaORM;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;

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
        assertEquals("Reserva actualizada", respuesta.mensaje());
        verify(reservaJPA, times(1)).save(reservaORM);
    }


    @Test
    void DadaReservaInexistente_CuandoActualizarReserva_EntoncesErrorReservaNoExiste() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        when(reservaService.getReserva("T123456")).thenReturn(Optional.empty());

        RespuestaDTO respuesta = reservaService.updateReserva(reservaDTO);

        assertFalse(respuesta.successful());
        assertEquals("Vuelo o persona no encontrados", respuesta.mensaje()); 
        verify(reservaJPA, times(0)).save(any(ReservaORM.class));
    }
    @Test
    void DadoVueloONoEncontrado_CuandoGuardarReserva_EntoncesErrorVueloPersonaNoEncontrados() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");

        
        when(vueloService.getVuelo("V123456")).thenReturn(Optional.empty());
        when(personaService.getPersona("A123456")).thenReturn(Optional.empty());

        RespuestaDTO respuesta = reservaService.saveReserva(reservaDTO);

        assertFalse(respuesta.successful());
        assertEquals("Vuelo o persona no encontrados", respuesta.mensaje());
        verify(reservaJPA, times(0)).save(any(ReservaORM.class));  
    }
    @Test
    void DadoVueloNoReservable_CuandoGuardarReserva_EntoncesErrorReservasCerradas() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        VueloORM vueloORM = new VueloORM();
        PersonaORM personaORM = new PersonaORM();

        when(vueloService.getVuelo("V123456")).thenReturn(Optional.of(vueloORM));
        when(personaService.getPersona("A123456")).thenReturn(Optional.of(personaORM));
        when(reservaJPA.findByPassportAndFlight(personaORM, vueloORM)).thenReturn(Optional.empty());
        when(dateValidator.fechaReservable(vueloORM)).thenReturn(false);

        RespuestaDTO respuesta = reservaService.saveReserva(reservaDTO);

        assertFalse(respuesta.successful());
        assertEquals("Las reservas están cerradas para este vuelo", respuesta.mensaje());
        verify(reservaJPA, times(0)).save(any(ReservaORM.class));
    }
    @Test
    void DadoVueloSinAsientosDisponibles_CuandoGuardarReserva_EntoncesErrorSinAsientos() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        VueloORM vueloORM = new VueloORM();
        PersonaORM personaORM = new PersonaORM();

        when(vueloService.getVuelo("V123456")).thenReturn(Optional.of(vueloORM));
        when(personaService.getPersona("A123456")).thenReturn(Optional.of(personaORM));
        when(reservaJPA.findByPassportAndFlight(personaORM, vueloORM)).thenReturn(Optional.empty());
        when(dateValidator.fechaReservable(vueloORM)).thenReturn(true);
        when(asientosManager.checkDisponibilidad(vueloORM)).thenReturn(false);

        RespuestaDTO respuesta = reservaService.saveReserva(reservaDTO);

        assertFalse(respuesta.successful());
        assertEquals("Este vuelo no cuenta con asientos disponibles.", respuesta.mensaje());
        verify(reservaJPA, times(0)).save(any(ReservaORM.class));
    }
    @Test
    void DadoDatosCorrectos_CuandoGuardarReserva_EntoncesReservaGuardada() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        VueloORM vueloORM = new VueloORM();
        PersonaORM personaORM = new PersonaORM();

        when(vueloService.getVuelo("V123456")).thenReturn(Optional.of(vueloORM));
        when(personaService.getPersona("A123456")).thenReturn(Optional.of(personaORM));
        when(reservaJPA.findByPassportAndFlight(personaORM, vueloORM)).thenReturn(Optional.empty());
        when(dateValidator.fechaReservable(vueloORM)).thenReturn(true);
        when(asientosManager.checkDisponibilidad(vueloORM)).thenReturn(true);

        RespuestaDTO respuesta = reservaService.saveReserva(reservaDTO);

        assertTrue(respuesta.successful());
        assertEquals("Reserva creada", respuesta.mensaje());
        verify(reservaJPA, times(1)).save(any(ReservaORM.class));
        verify(asientosManager, times(1)).reservarAsiento(vueloORM);  
    }
    
    @Test
    void DadoReservasExistentes_CuandoObtenerReservas_EntoncesRetornaListaReservas() {
        
        List<ReservaORM> reservas = List.of(new ReservaORM(), new ReservaORM());

        when(reservaJPA.findAll()).thenReturn(reservas);

        List<ReservaORM> resultado = reservaService.getReservas();

        assertEquals(2, resultado.size());
        verify(reservaJPA, times(1)).findAll();
    }
    @Test
    void DadaPersonaExistente_CuandoObtenerReservasPorPersona_EntoncesRetornaListaReservas() {
        String passportId = "A123456";
        PersonaORM personaORM = new PersonaORM();
        List<ReservaORM> reservas = List.of(new ReservaORM(), new ReservaORM());

        when(personaService.getPersona(passportId)).thenReturn(Optional.of(personaORM));
        when(reservaJPA.findAllByPassport(personaORM)).thenReturn(reservas);

        List<ReservaORM> resultado = reservaService.getReservasByPersona(passportId);

        assertEquals(2, resultado.size());
        verify(reservaJPA, times(1)).findAllByPassport(personaORM);
        verify(personaService, times(1)).getPersona(passportId);
    }
    @Test
    void DadoVueloExistente_CuandoObtenerReservasPorVuelo_EntoncesRetornaListaReservas() {
        String flightId = "V123456";
        VueloORM vueloORM = new VueloORM();
        List<ReservaORM> reservas = List.of(new ReservaORM(), new ReservaORM());
    
        when(vueloService.getVuelo(flightId)).thenReturn(Optional.of(vueloORM));
        when(reservaJPA.findAllByFlight(vueloORM)).thenReturn(reservas);
    
        List<ReservaORM> resultado = reservaService.getReservasByFlight(flightId);
    
        assertEquals(2, resultado.size());
        verify(reservaJPA, times(1)).findAllByFlight(vueloORM);
        verify(vueloService, times(1)).getVuelo(flightId);
    }

    @Test
    void DadaReservaInexistente_CuandoActualizarReserva_EntoncesErrorReservaNoEncontrada() {
        ReservaDTO reservaDTO = new ReservaDTO("T123456", "V123456", "A123456");
        VueloORM vueloORM = new VueloORM();
        PersonaORM personaORM = new PersonaORM();
    
        when(vueloService.getVuelo("V123456")).thenReturn(Optional.of(vueloORM));
        when(personaService.getPersona("A123456")).thenReturn(Optional.of(personaORM));
        when(reservaService.getReserva("T123456")).thenReturn(Optional.empty());
    
        RespuestaDTO respuesta = reservaService.updateReserva(reservaDTO);
    
        assertFalse(respuesta.successful());
        assertEquals("Reserva no encontrada", respuesta.mensaje());
        verify(reservaJPA, times(0)).save(any(ReservaORM.class));
    }
    @Test
    void DadaReservaExistente_CuandoEliminarReserva_EntoncesReservaEliminada() {
        
        String ticketId = "T123456";
        ReservaORM reservaORM = new ReservaORM();
        VueloORM vueloORM = new VueloORM();
        reservaORM.setFlight(vueloORM);

        
        when(reservaService.getReserva(ticketId)).thenReturn(Optional.of(reservaORM));

        
        RespuestaDTO respuesta = reservaService.deleteReserva(ticketId);

        
        assertTrue(respuesta.successful());
        assertEquals("Reserva eliminada", respuesta.mensaje());
        verify(asientosManager, times(1)).liberarAsiento(vueloORM);
        verify(reservaJPA, times(1)).delete(reservaORM);
    }
    @Test
    void DadaReservaInexistente_CuandoEliminarReserva_EntoncesErrorReservaNoExiste() {
        String ticketId = "T123456";
    
       
        when(reservaService.getReserva(ticketId)).thenReturn(Optional.empty());
    
        
        RespuestaDTO respuesta = reservaService.deleteReserva(ticketId);
    

        assertFalse(respuesta.successful());
        assertEquals("Reserva no existe", respuesta.mensaje());
        verify(asientosManager, times(0)).liberarAsiento(any(VueloORM.class));
        verify(reservaJPA, times(0)).delete(any(ReservaORM.class));
    }
    


}
