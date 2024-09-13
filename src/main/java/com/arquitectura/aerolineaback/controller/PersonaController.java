package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.logica.PersonaService;
import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @CrossOrigin(origins = {"*"})
    @GetMapping(path = "/get")
    public List<PersonaORM> getPersonas() {
        return personaService.getPersonas();
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping(path = "/guardar")
    public RespuestaDTO guardarPersona(@RequestBody PersonaDTO personaDTO) {
        return personaService.savePersona(personaDTO);
    }

    @CrossOrigin(origins = {"*"})
    @DeleteMapping(path = "/borrar")
    public String borrarPersona(@RequestBody PersonaDTO personaDTO) {

        return "persona eliminada";
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(path = "/actualizar")
    public String actualizarPersona(@RequestBody PersonaDTO personaDTO) {

        return "persona actualizada";
    }
}