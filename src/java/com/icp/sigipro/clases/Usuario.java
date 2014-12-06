/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.clases;

import java.sql.Date;

/**
 *
 * @author Boga
 */
public class Usuario 
{
    
    int idUsuario;
    String nombreUsuario;
    String correo;
    String nombreCompleto;
    String cedula;
    String departamento;
    String puesto;
    Date fechaActivacion;
    Date fechaDesactivacion;
    boolean activo;
    
    public Usuario (int p_idUsuario, String p_nombreUsuario, String p_correo, String p_nombreCompleto,
            String p_cedula, String p_departamento, String p_puesto, Date p_fechaActivacion, Date p_fechaDesactivacion,
            boolean p_activo)
    {
        idUsuario = p_idUsuario;
        nombreUsuario = p_nombreUsuario;
        correo = p_correo;
        nombreCompleto = p_nombreCompleto;
        cedula = p_cedula;
        departamento = p_departamento;
        puesto = p_puesto;
        fechaActivacion = p_fechaActivacion;
        fechaDesactivacion = p_fechaDesactivacion;
        activo = p_activo;
    }
    
    public int getID()                 {return idUsuario;}
    public String getNombreUsuario()   {return nombreUsuario;}
    public String getCorreo()          {return correo;}
    public String getNombreCompleto()  {return nombreCompleto;}
    public String getCedula()          {return cedula;}
    public String getDepartamento()    {return departamento;}
    public String getPuesto()          {return puesto;}
    public Date getFechaActivacion()   {return fechaActivacion;}
    public Date getFechaDesactivacion(){return fechaDesactivacion;}
    public boolean getActivo()         {return activo;}
    
    
}
