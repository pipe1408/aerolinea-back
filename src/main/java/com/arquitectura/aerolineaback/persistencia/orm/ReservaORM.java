package com.arquitectura.aerolineaback.persistencia.orm;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "RESERVAS")
public class ReservaORM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID", nullable = false)
    private Integer ticketId;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "FLIGHT_ID")
    private VueloORM flight;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "PASSPORT_ID")
    private PersonaORM passport;

    public Integer getTicketId() {
        return ticketId;
    }

    public VueloORM getFlight() {
        return flight;
    }

    public void setFlight(VueloORM flight) {
        this.flight = flight;
    }

    public PersonaORM getPassport() {
        return passport;
    }

    public void setPassport(PersonaORM passport) {
        this.passport = passport;
    }
}