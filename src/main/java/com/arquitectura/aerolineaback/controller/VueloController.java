package com.arquitectura.aerolineaback.controller;

import com.arquitectura.aerolineaback.logica.VueloService;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.logica.dto.VueloDTO;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vuelos")
@CrossOrigin(origins = { "*" })
public class VueloController {
    private final VueloService vueloService;

    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping(path = "/get")
    public List<VueloORM> getVuelos() {
        return vueloService.getVuelos();
    }

    @GetMapping(path = "/find/{flightId}")
    public Optional<VueloORM> getVuelo(@PathVariable String flightId) {
        return vueloService.getVuelo(flightId);
    }

    @PostMapping(path = "/guardar")
    public RespuestaDTO guardarVuelo(@RequestBody VueloDTO vueloDTO) {
        return vueloService.saveVuelo(vueloDTO);
    }

    @PutMapping(path = "/actualizar")
    public RespuestaDTO actualizarVuelo(@RequestBody VueloDTO vueloDTO) {
        return vueloService.updateVuelo(vueloDTO);
    }

    @DeleteMapping(path = "/borrar/{flightId}")
    public RespuestaDTO borrarVuelo(@PathVariable String flightId) {
        return vueloService.deleteVuelo(flightId);
    }
}