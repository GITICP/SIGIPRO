/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;

/**
 *
 * @author Josue
 */
public class Participantes_reunion {
    private Reunion_produccion reunion;
    private Usuario usuario;

    public Reunion_produccion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion_produccion reunion) {
        this.reunion = reunion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
