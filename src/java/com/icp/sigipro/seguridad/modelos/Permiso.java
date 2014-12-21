/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

/**
 *
 * @author Amed
 */
public class Permiso {
    int idPermiso;
    String nombrePermiso;
    String descripcionPermiso;

    
    public Permiso (int p_idPermiso,
    String p_nombrePermiso,
    String p_descripcionPermiso)
    {
        idPermiso = p_idPermiso;
        nombrePermiso = p_nombrePermiso;
        descripcionPermiso = p_descripcionPermiso; 
    }
    
    public int getID()                 {return idPermiso;}
    public String getNombrePermiso()   {return nombrePermiso;}
    public String getDescripcion()          {return descripcionPermiso;}
}
