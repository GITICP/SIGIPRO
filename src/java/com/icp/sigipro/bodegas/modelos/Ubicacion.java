package com.icp.sigipro.bodegas.modelos;

/**
 *
 * @author Walter
 */
public class Ubicacion 
{
    private int id_ubicacion;
    private String nombre;
    private String descripcion;

    public Ubicacion() {
    }

    public Ubicacion(int id_ubicacion, String nombre, String descripcion) {
        this.id_ubicacion = id_ubicacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
}
