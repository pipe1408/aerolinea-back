package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.logica.service.ReservaService;
import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.orm.ReservaORM;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/reservas")
@CrossOrigin(origins = {"*"})
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping(path = "/get")
    public List<ReservaORM> getReservas() {
        return reservaService.getReservas();
    }

    @GetMapping(path = "/find/{ticketId}")
    public Optional<ReservaORM> getReserva(@PathVariable String ticketId) {
        return reservaService.getReserva(ticketId);
    }

    @GetMapping(path = "/find-persona/{passportId}")
    public List<ReservaORM> getReservasPorPersona(@PathVariable String passportId) {
        return reservaService.getReservasByPersona(passportId);
    }

    @GetMapping(path = "/find-vuelo/{flightId}")
    public List<ReservaORM> getReservasPorVuelo(@PathVariable String flightId) {
        return reservaService.getReservasByFlight(flightId);
    }

    @PostMapping(path = "/guardar")
    public RespuestaDTO guardarReserva(@RequestBody ReservaDTO reservaDTO) {
        return reservaService.saveReserva(reservaDTO);
    }

    @PutMapping(path = "/actualizar")
    public RespuestaDTO actualizarReserva(@RequestBody ReservaDTO reservaDTO) {
        return reservaService.updateReserva(reservaDTO);
    }

    @DeleteMapping(path = "/borrar/{ticketId}")
    public RespuestaDTO borrarReserva(@PathVariable String ticketId) {
        return reservaService.deleteReserva(ticketId);
    }
}