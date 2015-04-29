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
public class Funcionalidad
{
    private int id_funcionalidad;
    private int id_padre;
    private String tag;
    private String redirect;
    
    public Funcionalidad(){}

    public int getId_funcionalidad()
    {
        return id_funcionalidad;
    }

    public void setId_funcionalidad(int id_funcionalidad)
    {
        this.id_funcionalidad = id_funcionalidad;
    }

    public int getId_padre()
    {
        return id_padre;
    }

    public void setId_padre(int id_padre)
    {
        this.id_padre = id_padre;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public String getRedirect()
    {
        return redirect;
    }

    public void setRedirect(String redirect)
    {
        this.redirect = redirect;
    }
}
