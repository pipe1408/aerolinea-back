package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.logica.EstadoEnum;
import com.arquitectura.aerolineaback.logica.dto.EstadoDTO;
import com.arquitectura.aerolineaback.logica.service.VueloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/estados")
@CrossOrigin(origins = { "*" })
public class EstadoController {

    private final VueloService vueloService;

    public EstadoController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoEnum>> getPossibleStates() {
        return new ResponseEntity<>(Arrays.asList(EstadoEnum.values()), HttpStatus.OK);
    }

    @GetMapping("/flights")
    public ResponseEntity<List<EstadoDTO>> getFlightStates() {
        return new ResponseEntity<>(vueloService.getEstados(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateEstado(@RequestBody EstadoDTO estadoDTO) {
        return new ResponseEntity<>(vueloService.updateEstado(estadoDTO), HttpStatus.OK);
    }
}
