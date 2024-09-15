package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.logica.dto.ReservaDTO;
import com.arquitectura.aerolineaback.logica.dto.RespuestaDTO;
import com.arquitectura.aerolineaback.persistencia.jpa.ReservaJPA;
import com.arquitectura.aerolineaback.persistencia.orm.PersonaORM;
import com.arquitectura.aerolineaback.persistencia.orm.ReservaORM;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {
    private final ReservaJPA reservaJPA;
    private final VueloService vueloService;
    private final PersonaService personaService;
    private ReservaORM reservaORM;

    public ReservaService(ReservaJPA reservaJPA, VueloService vueloService, PersonaService personaService) {
        this.reservaJPA = reservaJPA;
        this.vueloService = vueloService;
        this.personaService = personaService;
    }

    public List<ReservaORM> getReservas() {
        return reservaJPA.findAll();
    }

    public Optional<ReservaORM> getReserva(String ticketId) {
        return reservaJPA.findById(ticketId);
    }

    public RespuestaDTO saveReserva(ReservaDTO reservaDTO) {
        String vueloId = reservaDTO.vueloId();
        String pasajeroId = reservaDTO.pasajeroId();
        VueloORM vueloORM = vueloService.getVuelo(vueloId).orElse(null);
        PersonaORM personaORM = personaService.getPersona(pasajeroId).orElse(null);

        if ((vueloORM == null || personaORM == null)) {

        }

        return new RespuestaDTO(true, "Reserva creada");
    }

    public RespuestaDTO updateReserva(ReservaDTO reservaDTO) {


        return new RespuestaDTO(true, "Reserva actualizada");
    }

    public RespuestaDTO deleteReserva(String ticketId) {
        Optional<ReservaORM> reserva = getReserva(ticketId);

        if (reserva.isEmpty()) {
            return new RespuestaDTO(false, "Reserva no existe");
        }
        reservaJPA.delete(reserva.get());
        return new RespuestaDTO(true, "Reserva eliminada");
    }
}
