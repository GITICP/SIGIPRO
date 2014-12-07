/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.clases;
import java.sql.Date;
/**
 *
 * @author Amed
 */
public class RolUsuario
{
    
    int idRol;
    int idUsuario;
    Date fechaActivacion;
    Date fechaDesactivacion;
    String nombreRol;


    public RolUsuario (int p_idrol,
    int p_idusuario,
    Date p_fechaActivacion, Date p_fechaDesactivacion, String p_nombreRol)
    {
        idRol = p_idrol;
        idUsuario = p_idusuario;
        fechaActivacion = p_fechaActivacion;
        fechaDesactivacion = p_fechaDesactivacion;
        nombreRol = p_nombreRol;
    }
    
    public int getIDRol()                 {return idRol;}
    public int getIDUsuario()                 {return idUsuario;}
    public Date getFechaActivacion()    {return fechaActivacion;}
    public Date getFechaDesactivacion() {return fechaDesactivacion;}
    public String getNombreRol() {return nombreRol;}

    
    
}
