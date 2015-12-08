/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

/**
 *
 * @author Boga
 */
public class PermisoSubBodegas
{
    private boolean ver;
    private boolean ingresar;
    private boolean consumir;
    private boolean encargado;
    
    public PermisoSubBodegas(){}

    public boolean isVer()
    {
        return ver;
    }

    public void setVer(boolean ver)
    {
        this.ver = ver;
    }

    public boolean isIngresar()
    {
        return ingresar;
    }

    public void setIngresar(boolean ingresar)
    {
        this.ingresar = ingresar;
    }

    public boolean isConsumir()
    {
        return consumir;
    }

    public void setConsumir(boolean consumir)
    {
        this.consumir = consumir;
    }

    public boolean isEncargado()
    {
        return encargado;
    }

    public void setEncargado(boolean encargado)
    {
        this.encargado = encargado;
    }
    
    public boolean tieneAlgunPermiso() {
        return ver || ingresar || consumir || encargado;
    }
    
    @SuppressWarnings("ConvertToStringSwitch")
    public void asignarPermiso(String permiso) {
        if (permiso.equals("ingresos")){
            this.ingresar = true;
        } else if (permiso.equals("ver")){
            this.ver = true;
        } else if (permiso.equals("egresos")){
            this.consumir = true;
        } else if (permiso.equals("encargado")){
            this.encargado = true;
        }
    }
}
