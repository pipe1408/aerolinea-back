package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.bd.jpa.PersonaJPA;
import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import com.arquitectura.aerolineaback.logica.dto.VueloDTO;
import org.springframework.web.bind.annotation.*;

@RestController

public class VueloController {

    private PersonaJPA personaJPA;

    @GetMapping(path = "/vuelos")
    public String todoslosvuelos() {
        return "trayendo los vuelos...";
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping(path = "/guardarvuelo")
    public String guardarvuelo(@RequestBody VueloDTO vueloDTO) {

        return "vuelo creado";
    }

    @CrossOrigin(origins = {"*"})
    @DeleteMapping(path = "/borrarvuelo")
    public String borrarvuelo(@RequestBody VueloDTO vueloDTO) {

        return "vuelo eliminado";
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(path = "/actualizarvuelo")
    public String actualizarvuelo(@RequestBody VueloDTO vueloDTO) {

        return "vuelo actualizado";
    }
}