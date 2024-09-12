package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.persistencia.jpa.PersonaJPA;
import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin(origins = {"*"})
public class ReservaController {

    private PersonaJPA personaJPA;

    @CrossOrigin(origins = {"*"})
    @GetMapping(path = "/reservas")
    public String todaslasreservas() {
        return "trayendo las reservas...";
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping(path = "/guardarreserva")
    public String guardarreserva(@RequestBody ReservaDTO reservaDTO) {

        return "reserva creada";
    }

    @CrossOrigin(origins = {"*"})
    @DeleteMapping(path = "/borrarreserva")
    public String borrarreserva(@RequestBody ReservaDTO reservaDTO) {

        return "reserva eliminada";
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(path = "/actualizarreserva")
    public String actualizarreserva(@RequestBody ReservaDTO reservaDTO) {

        return "reserva actualizada";
    }
}