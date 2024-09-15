package com.arquitectura.aerolineaback.logica.dto;

import java.time.LocalDate;

public record VueloDTO(String vueloId,
                       String origen,
                       String destino,
                       LocalDate fecha,
                       int asientosDisponibles) { }
