package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.logica.PersonaService;
import com.arquitectura.aerolineaback.logica.dto.PersonaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personas")
@CrossOrigin(origins = { "*" })
public class PersonaController {
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping(path = "/get")
    public List<PersonaORM> getPersonas() {
        return personaService.getPersonas();
    }

    @GetMapping(path = "/find/{passportId}")
    public Optional<PersonaORM> getPersona(@PathVariable String passportId) {
        return personaService.getPersona(passportId);
    }

    @PostMapping(path = "/guardar")
    public RespuestaDTO guardarPersona(@RequestBody PersonaDTO personaDTO) {
        return personaService.savePersona(personaDTO);
    }

    @DeleteMapping(path = "/borrar")
    public String borrarPersona(@RequestBody PersonaDTO personaDTO) {

        return "persona eliminada";
    }

    @PutMapping(path = "/actualizar")
    public String actualizarPersona(@RequestBody PersonaDTO personaDTO) {

        return "persona actualizada";
    }
}