package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.bd.jpa.PersonaJPA;
import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import org.springframework.web.bind.annotation.*;

@RestController

public class PersonaController {

    private PersonaJPA personaJPA;

    @CrossOrigin(origins = {"*"})
    @GetMapping(path = "/personas")
    public String todaslaspersonas() {
        return "trayendo personas...";
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping(path = "/guardarpersona")
    public String guardarpersona(@RequestBody PersonaDTO personaDTO) {

        return "persona guardada";
    }

    @CrossOrigin(origins = {"*"})
    @DeleteMapping(path = "/borrarpersona")
    public String borrarpersona(@RequestBody PersonaDTO personaDTO) {

        return "persona eliminada";
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(path = "/actualizarpersona")
    public String actualizarpersona(@RequestBody PersonaDTO personaDTO) {

        return "persona actualizada";
    }
}