package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.bd.jpa.PersonaJPA;
import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin(origins = {"*"})
public class ReservaController {

    private PersonaJPA personaJPA;

    @GetMapping(path = "/reservas")
    public String todaslasreservas() {
        return "trayendo las reservas...";
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