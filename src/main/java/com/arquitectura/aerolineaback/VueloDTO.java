package com.arquitectura.aerolineaback;

public record VueloDTO(String vueloId,
                       String origen,
                       String destino,
                       int asientosDisponibles) { }
