package com.arquitectura.aerolineaback.logica;

import com.arquitectura.aerolineaback.persistencia.jpa.VueloJPA;
import com.arquitectura.aerolineaback.persistencia.orm.VueloORM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsientosManager {
    private final VueloJPA vueloJPA;

    @Autowired
    public AsientosManager(VueloJPA vueloJPA) {
        this.vueloJPA = vueloJPA;
    }

    public boolean checkDisponibilidad(VueloORM vueloORM) {
        return vueloORM.getAsientosLibres() != 0;
    }

    public void reservarAsiento(VueloORM vueloORM) {
        int asientos = vueloORM.getAsientosLibres();
        vueloORM.setAsientosLibres(asientos - 1);
        vueloJPA.save(vueloORM);
    }

    public void liberarAsiento(VueloORM vueloORM) {
        int asientos = vueloORM.getAsientosLibres();
        vueloORM.setAsientosLibres(asientos + 1);
        vueloJPA.save(vueloORM);
    }
}
