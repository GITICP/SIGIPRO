/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.clases;

/**
 *
 * @author Amed
 */
public class Rol
{
    
    int idRol;
    String nombreRol;
    String descripcionRol;

    
    public Rol (int p_idrol,
    String p_nombrerol,
    String p_descripcionrol)
    {
        idRol = p_idrol;
        nombreRol = p_nombrerol;
        descripcionRol = p_descripcionrol; 
    }
    
    public int getID()                 {return idRol;}
    public String getNombreRol()   {return nombreRol;}
    public String getDescripcion()          {return descripcionRol;}

    
    
}
