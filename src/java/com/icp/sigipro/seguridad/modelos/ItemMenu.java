/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

/**
 *
 * @author Boga
 */
public class ItemMenu
{
    protected int id;
    protected int id_padre;
    protected String nombre;
    protected int orden;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId_padre()
    {
        return id_padre;
    }

    public void setId_padre(int id_padre)
    {
        this.id_padre = id_padre;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public int getOrden()
    {
        return orden;
    }

    public void setOrden(int orden)
    {
        this.orden = orden;
    }
    
    
}
