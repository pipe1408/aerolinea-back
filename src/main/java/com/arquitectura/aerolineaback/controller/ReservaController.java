package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.logica.ReservaService;
import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
import com.arquitectura.aerolineaback.persistencia.jpa.ReservaJPA;
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

    @PostMapping(path = "/guardarreserva")
    public String guardarreserva(@RequestBody ReservaDTO reservaDTO) {

        return "reserva creada";
    }

    @DeleteMapping(path = "/borrarreserva")
    public String borrarreserva(@RequestBody ReservaDTO reservaDTO) {

        return "reserva eliminada";
    }

    @PutMapping(path = "/actualizarreserva")
    public String actualizarreserva(@RequestBody ReservaDTO reservaDTO) {

        return "reserva actualizada";
    }
}