package com.arquitectura.aerolineaback.logica.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

public record VueloDTO(String vueloId,
                       String origen,
                       String destino,
                       LocalDate fecha,
                       @Schema(type = "string", format = "time", example = "07:20:00")
                       LocalTime hora,
                       int asientosDisponibles) { }
