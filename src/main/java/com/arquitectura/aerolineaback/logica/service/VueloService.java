package com.arquitectura.aerolineaback.logica.service;

import com.arquitectura.aerolineaback.logica.EstadoEnum;
import com.arquitectura.aerolineaback.logica.dto.EstadoDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.logica.dto.UpdateDTO;
import com.arquitectura.aerolineaback.logica.dto.VueloDTO;
import com.arquitectura.aerolineaback.persistencia.jpa.VueloJPA;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import com.arquitectura.aerolineaback.rabbit.RabbitProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VueloService {
    private final VueloJPA vueloJPA;
    private final RabbitProducer rabbitProducer;
    private VueloORM vueloORM;

    public VueloService(VueloJPA vueloJPA, RabbitProducer rabbitProducer) {
        this.vueloJPA = vueloJPA;
        this.rabbitProducer = rabbitProducer;
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
        vueloORM.setHora(vueloDTO.hora());
        vueloORM.setAsientosLibres(vueloDTO.asientosDisponibles());
        vueloORM.setEstado(EstadoEnum.PROGRAMADO);
        vueloJPA.save(vueloORM);

        return new RespuestaDTO(true, "Vuelo creado");
    }

    public RespuestaDTO updateVuelo(VueloDTO vueloDTO) {
        Optional<VueloORM> optionalVuelo = getVuelo(vueloDTO.vueloId());

        if (optionalVuelo.isEmpty()) {
            return new RespuestaDTO(false, "Vuelo no existe");
        }
        vueloORM = optionalVuelo.get();
        vueloORM.setFlightId(vueloDTO.vueloId());
        vueloORM.setOrigen(vueloDTO.origen());
        vueloORM.setDestino(vueloDTO.destino());
        vueloORM.setFecha(vueloDTO.fecha());
        vueloORM.setHora(vueloDTO.hora());
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

    public String updateEstado(EstadoDTO estadoDTO) {
        vueloORM = getVuelo(estadoDTO.flightId()).orElse(null);

        if (vueloORM == null) {
            return null;
        }

        EstadoEnum previousState = vueloORM.getEstado();

        vueloORM.setEstado(estadoDTO.state());
        vueloJPA.save(vueloORM);

        UpdateDTO update = new UpdateDTO(
                vueloORM.getFlightId(),
                vueloORM.getOrigen(),
                vueloORM.getDestino(),
                vueloORM.getFecha(),
                vueloORM.getHora(),
                previousState,
                estadoDTO.state()
        );

        rabbitProducer.sendMessage(update);

        return vueloORM.getFlightId() + ":" + previousState + "->" + estadoDTO.state();
    }


    public List<EstadoDTO> getEstados() {
        List<VueloORM> vuelos = getVuelos();

        return vuelos.stream()
                .map(vuelo -> new EstadoDTO(vuelo.getFlightId(), vuelo.getEstado()))
                .toList();
    }
}
