package com.arquitectura.aerolineaback;

import java.util.Date;

public record VueloDTO(String vueloId,
                       String origen,
                       String destino,
                       Date fecha,
                       int asientosDisponibles) { }
