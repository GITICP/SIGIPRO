/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

/**
 *
 * @author Walter
 */
public class Puesto {
  int id_puesto;
  String nombre_puesto;
  String descripcion;

    public Puesto() {
    }

    public Puesto(int id_puesto, String nombre_puesto, String descripcion) {
        this.id_puesto = id_puesto;
        this.nombre_puesto = nombre_puesto;
        this.descripcion = descripcion;
    }

    public int getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(int id_puesto) {
        this.id_puesto = id_puesto;
    }

    public String getNombre_puesto() {
        return nombre_puesto;
    }

    public void setNombre_puesto(String nombre_puesto) {
        this.nombre_puesto = nombre_puesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
