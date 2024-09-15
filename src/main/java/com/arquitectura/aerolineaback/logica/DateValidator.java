package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.persistencia.jpa.VueloJPA;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class DateValidator {
    static VueloJPA vueloJPA;

    public DateValidator(VueloJPA vueloJPA) {
        DateValidator.vueloJPA = vueloJPA;
    }

    public static boolean fechaReservable(VueloORM vueloORM) {
        LocalDate fecha = vueloORM.getFecha();
        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(today, fecha);
        return daysBetween >= 1;
    }
}
