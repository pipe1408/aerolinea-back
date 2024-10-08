package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.logica.dto.VueloDTO;
import com.arquitectura.aerolineaback.persistencia.jpa.VueloJPA;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VueloService {
    private final VueloJPA vueloJPA;
    private VueloORM vueloORM;

    public VueloService(VueloJPA vueloJPA) {
        this.vueloJPA = vueloJPA;
    }

    public List<VueloORM> getVuelos() {
        return vueloJPA.findAll();
    }

    public Optional<VueloORM> getVuelo(String flightId) {
        return vueloJPA.findById(flightId);
    }

    public RespuestaDTO saveVuelo(VueloDTO vueloDTO) {

        if (getVuelo(vueloDTO.vueloId()).isPresent()) {
            return new RespuestaDTO(false, "Ya existe un vuelo con ese código");
        }

        vueloORM = new VueloORM();
        vueloORM.setFlightId(vueloDTO.vueloId());
        vueloORM.setOrigen(vueloDTO.origen());
        vueloORM.setDestino(vueloDTO.destino());
        vueloORM.setFecha(vueloDTO.fecha());
        vueloORM.setAsientosLibres(vueloDTO.asientosDisponibles());
        vueloJPA.save(vueloORM);

        return new RespuestaDTO(true, "Vuelo creado");
    }

    public RespuestaDTO updateVuelo(VueloDTO vueloDTO) {
        Optional<VueloORM> optionalVuelo = getVuelo(vueloDTO.vueloId());

        if (optionalVuelo.isEmpty()) {
            return new RespuestaDTO(false, "Vuelo no existe");
        }
        vueloORM = optionalVuelo.get();
        vueloORM.setOrigen(vueloDTO.origen());
        vueloORM.setDestino(vueloDTO.destino());
        vueloORM.setFecha(vueloDTO.fecha());
        vueloORM.setAsientosLibres(vueloDTO.asientosDisponibles());
        vueloJPA.save(vueloORM);

        return new RespuestaDTO(true, "Vuelo actualizado");
    }

    public RespuestaDTO deleteVuelo(String flightId) {
        Optional<VueloORM> optionalVuelo = getVuelo(flightId);

        if (optionalVuelo.isEmpty()) {
            return new RespuestaDTO(false, "Vuelo no existe");
        }
        vueloJPA.delete(optionalVuelo.get());
        return new RespuestaDTO(true, "Vuelo eliminado");
    }
}
