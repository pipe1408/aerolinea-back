package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.persistencia.jpa.VueloJPA;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.springframework.stereotype.Service;

@Service
public class AsientosManager {
    static int asientos;
    static VueloJPA vueloJPA;

    public AsientosManager(VueloJPA vueloJPA) {
        AsientosManager.vueloJPA = vueloJPA;
    }

    public static boolean checkDisponibilidad(VueloORM vueloORM) {
        return vueloORM.getAsientosLibres() != 0;
    }

    public static void reservarAsiento(VueloORM vueloORM) {
        asientos = vueloORM.getAsientosLibres();
        vueloORM.setAsientosLibres(asientos - 1);
        vueloJPA.save(vueloORM);
    }

    public static void liberarAsiento(VueloORM vueloORM) {
        asientos = vueloORM.getAsientosLibres();
        vueloORM.setAsientosLibres(asientos + 1);
        vueloJPA.save(vueloORM);
    }
}
