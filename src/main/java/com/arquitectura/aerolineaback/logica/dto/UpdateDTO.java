package com.arquitectura.aerolineaback.logica.dto;

import com.arquitectura.aerolineaback.logica.EstadoEnum;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateDTO(
        String flightId,
        String origen,
        String destino,
        LocalDate fecha,
        LocalTime hora,
        EstadoEnum previousState,
        EstadoEnum newState
) {
}
