package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.logica.ReservaService;
import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
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

    @PostMapping(path = "/guardar")
    public String guardarReserva(@RequestBody ReservaDTO reservaDTO) {

        return "reserva creada";
    }

    @PutMapping(path = "/actualizar")
    public String actualizarReserva(@RequestBody ReservaDTO reservaDTO) {

        return "reserva actualizada";
    }

    @DeleteMapping(path = "/borrar/{ticketId}")
    public String borrarReserva(@PathVariable String ticketId) {

        return "reserva eliminada";
    }
}