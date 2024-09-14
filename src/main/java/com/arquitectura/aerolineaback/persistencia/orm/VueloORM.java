package com.arquitectura.aerolineaback.persistencia.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "VUELOS")
public class VueloORM {
    @Id
    @Column(name = "FLIGHT_ID", nullable = false, length = 50)
    private String flightId;

    @Column(name = "ORIGEN", length = 50)
    private String origen;

    @Column(name = "DESTINO", length = 50)
    private String destino;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Column(name = "ASIENTOS_LIBRES")
    private Integer asientosLibres;

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getAsientosLibres() {
        return asientosLibres;
    }

    public void setAsientosLibres(Integer asientosLibres) {
        this.asientosLibres = asientosLibres;
    }
}