/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author Boga
 */
public class DireccionArchivos {

    private int id_direccion;
    private String direccion;

    public DireccionArchivos() {
    }

    public int getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(int id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion != null) {
            if (direccion.isEmpty()) {
                this.direccion = getDireccionPorDefecto();
            } else {
                this.direccion = direccion;
            }
        } else {
            this.direccion = getDireccionPorDefecto();
        }
    }

    public String getDireccionPorDefecto() {
        return File.separatorChar + "SIGIPRO_DEFAULT";
    }
}
