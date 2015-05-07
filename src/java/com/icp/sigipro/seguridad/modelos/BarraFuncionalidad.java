/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Boga
 */
public class BarraFuncionalidad implements java.io.Serializable
{

    List<Modulo> modulos = new ArrayList<Modulo>();

    public BarraFuncionalidad()
    {
    }

    public List<Modulo> getModulos()
    {
        return modulos;
    }

    public void agregarModulo(Modulo modulo)
    {
        modulos.add(modulo);
    }

    public void eliminarModulosVacios()
    {
        List<Modulo> arreglo = new ArrayList<Modulo>();
        for (Modulo m : modulos) {
            if (m.tieneContenido()) {
                arreglo.add(m);
            }
        }
        modulos = arreglo;
    }

    public void agregarSubModulo(Modulo nuevo_modulo)
    {
        for (Modulo modulo : modulos) {
            if (modulo.agregarSubModulo(nuevo_modulo)) {
                break;
            }
        }
    }

    public void agregarFuncionalidad(Funcionalidad funcionalidad)
    {
        for (Modulo modulo : modulos) {
            if (modulo.agregarFuncionalidad(funcionalidad)) {
                break;
            }
        }
    }
    
    public void ordenarFuncionalidades()
    {
        for (Modulo modulo : modulos) {
            modulo.ordenar();
        }
    }
}
