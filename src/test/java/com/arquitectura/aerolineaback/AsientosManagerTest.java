package com.arquitectura.aerolineaback;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.arquitectura.aerolineaback.logica.AsientosManager;
import com.arquitectura.aerolineaback.persistencia.jpa.VueloJPA;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AsientosManagerTest {

    @Mock
    private VueloJPA vueloJPA;

    @InjectMocks
    private AsientosManager asientosManager;

    @Mock
    private VueloORM vueloORM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkDisponibilidad_ShouldReturnTrue_WhenAsientosLibresAreNotZero() {
        when(vueloORM.getAsientosLibres()).thenReturn(5);

        boolean result = asientosManager.checkDisponibilidad(vueloORM);

        assertTrue(result);
        verify(vueloORM).getAsientosLibres();
    }

    @Test
    void checkDisponibilidad_ShouldReturnFalse_WhenAsientosLibresAreZero() {
        when(vueloORM.getAsientosLibres()).thenReturn(0);

        boolean result = asientosManager.checkDisponibilidad(vueloORM);

        assertFalse(result);
        verify(vueloORM).getAsientosLibres();
    }
    @Test
    void reservarAsiento_ShouldDecrementAsientosLibresAndSave() {
        when(vueloORM.getAsientosLibres()).thenReturn(10);

        asientosManager.reservarAsiento(vueloORM);

        verify(vueloORM).setAsientosLibres(9);  // Verificar que se decrementa el asiento
        verify(vueloJPA).save(vueloORM);        // Verificar que se guarda el vuelo
    }
    @Test
    void liberarAsiento_ShouldIncrementAsientosLibresAndSave() {
        when(vueloORM.getAsientosLibres()).thenReturn(5);

        asientosManager.liberarAsiento(vueloORM);

        verify(vueloORM).setAsientosLibres(6);  // Verificar que se incrementa el asiento
        verify(vueloJPA).save(vueloORM);        // Verificar que se guarda el vuelo
    }
}