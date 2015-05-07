/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Boga
 */
public class Modulo implements java.io.Serializable
{

    private int id_modulo;
    private int id_padre;
    private String nombre;
    private List<Modulo> sub_modulos = new ArrayList<Modulo>();
    private List<Funcionalidad> funcionalidades = new ArrayList<Funcionalidad>();

    public Modulo()
    {
    }

    public int getId_modulo()
    {
        return id_modulo;
    }

    public void setId_modulo(int id_modulo)
    {
        this.id_modulo = id_modulo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public List<Modulo> getSub_modulos()
    {
        return sub_modulos;
    }

    public void setSub_modulos(List<Modulo> sub_modulos)
    {
        this.sub_modulos = sub_modulos;
    }

    public List<Funcionalidad> getFuncionalidades()
    {
        return funcionalidades;
    }

    public void setFuncionalidades(List<Funcionalidad> funcionalidades)
    {
        this.funcionalidades = funcionalidades;
    }

    public int getId_padre()
    {
        return id_padre;
    }

    public void setId_padre(int id_padre)
    {
        this.id_padre = id_padre;
    }

    public boolean agregarFuncionalidad(Funcionalidad funcionalidad)
    {
        boolean resultado = false;
        if (id_modulo == funcionalidad.getId_padre()) {
            funcionalidades.add(funcionalidad);
            resultado = true;
        } else {
            if (sub_modulos != null) {
                for (Modulo modulo : sub_modulos) {
                    resultado = modulo.agregarFuncionalidad(funcionalidad);
                }
            }
        }
        return resultado;
    }
    
    public boolean agregarSubModulo(Modulo modulo) {
        boolean resultado = false;
        if (id_modulo == modulo.getId_padre()) {
            sub_modulos.add(modulo);
            resultado = true;
        } else {
            if (sub_modulos != null) {
                for (Modulo sub_modulo : sub_modulos) {
                    resultado = sub_modulo.agregarSubModulo(modulo);
                }
            }
        }
        return resultado;
    }
    
    public boolean tieneContenido() {
        boolean tiene_funcionalidades = funcionalidades.size() > 0;
        boolean tiene_sub_modulos = iterarSubModulos();
        return tiene_funcionalidades || tiene_sub_modulos;
    }
    
    public boolean iterarSubModulos() {
        List<Modulo> arreglo = new ArrayList<Modulo>();
        for (Modulo mod : sub_modulos) {
            if(mod.tieneContenido()) {
                arreglo.add(mod);
            }
        }
        sub_modulos = arreglo;
        return sub_modulos.size() > 0;
    }
    
    public void ordenar() {
        Collections.sort(funcionalidades, new ComparadorFuncionalidades());
        
    }
    
    private class ComparadorFuncionalidades implements Comparator<Funcionalidad>
    {
        @Override
        public int compare(Funcionalidad f1, Funcionalidad f2)
        {
            return f1.getOrden() - f2.getOrden();
        }
    }
}
