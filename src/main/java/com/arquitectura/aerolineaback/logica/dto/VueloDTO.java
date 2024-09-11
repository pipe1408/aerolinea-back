package com.arquitectura.aerolineaback.logica.dto;

import java.util.Date;

public record VueloDTO(String vueloId,
                       String origen,
                       String destino,
                       Date fecha,
                       int asientosDisponibles) { }
