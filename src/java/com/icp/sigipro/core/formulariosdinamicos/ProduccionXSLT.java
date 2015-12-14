/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core.formulariosdinamicos;

import java.sql.SQLXML;

/**
 *
 * @author ld.conejo
 */
public class ProduccionXSLT {
    private int id;
    private String nombre;
    private SQLXML estructura;

    public ProduccionXSLT() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public SQLXML getEstructura() {
        return estructura;
    }

    public void setEstructura(SQLXML estructura) {
        this.estructura = estructura;
    }
    
    
}
