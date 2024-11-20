package com.arquitectura.aerolineaback.persistencia.orm;

import jakarta.persistence.*;
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

    @Column(name = "ASIENTOS_LIBRES")
    private Integer asientosLibres;

    @Size(max = 50)
    @Column(name = "DESTINO", length = 50)
    private String destino;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Size(max = 50)
    @Column(name = "ORIGEN", length = 50)
    private String origen;

    @NotNull
    @Column(name = "HORA", nullable = false)
    private LocalTime hora;

    @Lob
    @Column(name = "ESTADO")
    private String estado;

}