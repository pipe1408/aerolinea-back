package com.arquitectura.aerolineaback.logica;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;

class DateValidatorTest {

    @InjectMocks
    private DateValidator dateValidator;

    @Mock
    private VueloORM vueloORM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void DadoFechaVueloManana_CuandoFechaReservable_EntoncesDevuelveTrue() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        when(vueloORM.getFecha()).thenReturn(tomorrow);

        boolean esReservable = dateValidator.fechaReservable(vueloORM);

        assertTrue(esReservable);
    }

    @Test
    void DadoFechaVueloHoy_CuandoFechaReservable_EntoncesDevuelveFalse() {
        LocalDate today = LocalDate.now();
        when(vueloORM.getFecha()).thenReturn(today);

        boolean esReservable = dateValidator.fechaReservable(vueloORM);

        assertFalse(esReservable);
    }

    @Test
    void DadoFechaVueloEnElPasado_CuandoFechaReservable_EntoncesDevuelveFalse() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        when(vueloORM.getFecha()).thenReturn(pastDate);

        boolean esReservable = dateValidator.fechaReservable(vueloORM);

        assertFalse(esReservable);
    }

    @Test
    void DadoFechaVueloDentroDeVariosDias_CuandoFechaReservable_EntoncesDevuelveTrue() {
        LocalDate futureDate = LocalDate.now().plusDays(5);
        when(vueloORM.getFecha()).thenReturn(futureDate);

        boolean esReservable = dateValidator.fechaReservable(vueloORM);

        assertTrue(esReservable);
    }
}
