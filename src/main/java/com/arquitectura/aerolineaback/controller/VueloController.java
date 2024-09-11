package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.bd.jpa.PersonaJPA;
import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import com.arquitectura.aerolineaback.logica.dto.VueloDTO;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin(origins = {"*"})
public class VueloController {

    private PersonaJPA personaJPA;

    @GetMapping(path = "/vuelos")
    public String todoslosvuelos() {
        return "trayendo los vuelos...";
    }

    @PostMapping(path = "/guardarvuelo")
    public String guardarvuelo(@RequestBody VueloDTO vueloDTO) {

        return "vuelo creado";
    }

    @DeleteMapping(path = "/borrarvuelo")
    public String borrarvuelo(@RequestBody VueloDTO vueloDTO) {

        return "vuelo eliminado";
    }

    @PutMapping(path = "/actualizarvuelo")
    public String actualizarvuelo(@RequestBody VueloDTO vueloDTO) {

        return "vuelo actualizado";
    }
}