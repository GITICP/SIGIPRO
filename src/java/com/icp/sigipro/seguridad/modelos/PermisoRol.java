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
public class PermisoRol {
     
    int idRol;
    int idPermiso;
    String nombrePermiso;


    public PermisoRol (int p_idrol, int p_idpermiso, String p_nombrepermiso)
    {
        idRol = p_idrol;
        idPermiso = p_idpermiso;
        nombrePermiso = p_nombrepermiso;
    }
    public PermisoRol (int p_idrol, int p_idpermiso)
    {
        idRol = p_idrol;
        idPermiso = p_idpermiso;
    }
    
    public int getIDRol()                 {return idRol;}
    public int getIDPermiso()             {return idPermiso;}
    public String getNombrePermiso() {return nombrePermiso;}

}
