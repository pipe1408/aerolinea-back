package com.arquitectura.aerolineaback.logica.dto;

import com.arquitectura.aerolineaback.logica.EstadoEnum;

public record EstadoDTO(String flightId,
                        EstadoEnum state) {
}
