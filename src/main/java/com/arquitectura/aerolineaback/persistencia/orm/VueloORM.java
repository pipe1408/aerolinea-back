package com.arquitectura.aerolineaback.persistencia.orm;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.arquitectura.aerolineaback.logica.EstadoEnum;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "VUELOS")
public class VueloORM {
    @Id
    @Size(max = 50)
    @Column(name = "FLIGHT_ID", nullable = false, length = 50)
    private String flightId;

    @NotNull
    @Column(name = "ASIENTOS_LIBRES", nullable = false)
    private Integer asientosLibres;

    @Size(max = 50)
    @NotNull
    @Column(name = "DESTINO", nullable = false, length = 50)
    private String destino;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false, length = 50)
    private EstadoEnum estado;

    @NotNull
    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "HORA", nullable = false)
    private LocalTime hora;

    @Size(max = 50)
    @NotNull
    @Column(name = "ORIGEN", nullable = false, length = 50)
    private String origen;
}
