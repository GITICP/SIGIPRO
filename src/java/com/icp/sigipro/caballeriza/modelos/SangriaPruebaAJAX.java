/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class SangriaPruebaAJAX {
    
    private int id_sangria_prueba;
    private List<CaballoAJAX> caballos;
    private String nombre_grupo;
    private Date fecha;
    private String identificador;
    
    public SangriaPruebaAJAX(SangriaPrueba sp) {
        id_sangria_prueba = sp.getId_sangria_prueba();
        caballos = new ArrayList<>();
        for (SangriaPruebaCaballo spc : sp.getLista_sangrias_prueba_caballo()) {
           CaballoAJAX c_ax = new CaballoAJAX(spc.getCaballo());
        }
    }
    
    public SangriaPruebaAJAX() {
        
    }

    public int getId_sangria_prueba() {
        return id_sangria_prueba;
    }

    public void setId_sangria_prueba(int id_sangria_prueba) {
        this.id_sangria_prueba = id_sangria_prueba;
    }

    public List<CaballoAJAX> getCaballos() {
        return caballos;
    }

    public void setCaballos(List<CaballoAJAX> caballos) {
        this.caballos = caballos;
    }
    
    public void agregarCaballo(Caballo c) {
        if (caballos == null) {
            caballos = new ArrayList<>();
        }
        CaballoAJAX c_ax = new CaballoAJAX(c);
        caballos.add(c_ax);
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getId_sangria_especial() {
        HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
        identificador = helper_fechas.formatearFecha(fecha).replaceAll("/", "-") + "-" + nombre_grupo + " (id: " + id_sangria_prueba + ")";
        return identificador;
    }
    
    public class CaballoAJAX {
        
        int id_caballo;
        int numero;
        
        public CaballoAJAX(Caballo c) {
            id_caballo = c.getId_caballo();
            numero = c.getNumero();
        }

        public int getId_caballo() {
            return id_caballo;
        }

        public void setId_caballo(int id_caballo) {
            this.id_caballo = id_caballo;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }
        
        
    }
    
}
