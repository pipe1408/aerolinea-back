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
    private Optional<ReservaORM> optionalReserva;

    public ReservaService(ReservaJPA reservaJPA, VueloService vueloService, PersonaService personaService) {
        this.reservaJPA = reservaJPA;
        this.vueloService = vueloService;
        this.personaService = personaService;
    }

    public List<ReservaORM> getReservas() {
        return reservaJPA.findAll();
    }

    public List<ReservaORM> getReservasByPersona(String passportId) {
        return reservaJPA.findAllByPassport(personaService.getPersona(passportId).orElse(null));
    }

    public List<ReservaORM> getReservasByFlight(String flightId) {
        return reservaJPA.findAllByFlight(vueloService.getVuelo(flightId).orElse(null));
    }

    public Optional<ReservaORM> getReserva(String ticketId) {
        return reservaJPA.findById(ticketId);
    }

    public RespuestaDTO saveReserva(ReservaDTO reservaDTO) {
        String vueloId = reservaDTO.vueloId();
        String pasajeroId = reservaDTO.pasajeroId();
        VueloORM vueloORM = vueloService.getVuelo(vueloId).orElse(null);
        PersonaORM personaORM = personaService.getPersona(pasajeroId).orElse(null);

        if (vueloORM == null || personaORM == null) {
            return new RespuestaDTO(false, "Vuelo o persona no encontrados");
        }
        if (reservaJPA.findByPassportAndFlight(personaORM, vueloORM).isPresent()) {
            return new RespuestaDTO(false, "La persona ya tiene reserva para ese vuelo");
        }
        reservaORM = new ReservaORM();
        reservaORM.setFlight(vueloORM);
        reservaORM.setPassport(personaORM);
        reservaJPA.save(reservaORM);

        return new RespuestaDTO(true, "Reserva creada");
    }

    public RespuestaDTO updateReserva(ReservaDTO reservaDTO) {
        String vueloId = reservaDTO.vueloId();
        String pasajeroId = reservaDTO.pasajeroId();
        PersonaORM personaORM = personaService.getPersona(pasajeroId).orElse(null);
        VueloORM vueloORM = vueloService.getVuelo(vueloId).orElse(null);
        optionalReserva = getReserva(reservaDTO.ticketId());

        if ((vueloORM == null || personaORM == null)) {
            return new RespuestaDTO(false, "Vuelo o persona no encontrados");
        }
        reservaORM = optionalReserva.get();
        reservaORM.setFlight(vueloORM);
        reservaORM.setPassport(personaORM);
        reservaJPA.save(reservaORM);

        return new RespuestaDTO(true, "Reserva actualizada");
    }

    public RespuestaDTO deleteReserva(String ticketId) {
        optionalReserva = getReserva(ticketId);

        if (optionalReserva.isEmpty()) {
            return new RespuestaDTO(false, "Reserva no existe");
        }
        reservaJPA.delete(optionalReserva.get());
        return new RespuestaDTO(true, "Reserva eliminada");
    }
}
