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
public class Funcionalidad extends ItemMenu implements java.io.Serializable
{
    private String redirect;
    
    public Funcionalidad(){}

    public String getRedirect()
    {
        return redirect;
    }

    public void setRedirect(String redirect)
    {
        this.redirect = redirect;
    }
}
