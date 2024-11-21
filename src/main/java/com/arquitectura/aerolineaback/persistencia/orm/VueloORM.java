package com.arquitectura.aerolineaback.persistencia.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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

    @Size(max = 50)
    @NotNull
    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;

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