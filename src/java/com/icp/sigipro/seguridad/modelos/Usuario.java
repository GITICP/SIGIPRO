/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    int id_seccion;
    String puesto;
    Date fechaActivacion;
    Date fechaDesactivacion;
    boolean activo;
    String nombreSeccion;
    
    public Usuario(){}
    
    public Usuario (int p_idUsuario, String p_nombreUsuario, String p_correo, String p_nombreCompleto,
            String p_cedula, int p_departamento, String p_puesto, Date p_fechaActivacion, Date p_fechaDesactivacion,
            boolean p_activo)
    {
        idUsuario = p_idUsuario;
        nombreUsuario = p_nombreUsuario;
        correo = p_correo;
        nombreCompleto = p_nombreCompleto;
        cedula = p_cedula;
        id_seccion = p_departamento;
        puesto = p_puesto;
        fechaActivacion = p_fechaActivacion;
        fechaDesactivacion = p_fechaDesactivacion;
        activo = p_activo;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setIdSeccion(int seccion) {
        this.id_seccion = seccion;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public void setFechaActivacion(Date fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public void setFechaDesactivacion(Date fechaDesactivacion) {
        this.fechaDesactivacion = fechaDesactivacion;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public void setNombreSeccion(String activo) {
        this.nombreSeccion = activo;
    }
    public int getID()                 {return idUsuario;}
    public String getNombreUsuario()   {return nombreUsuario;}
    public String getCorreo()          {return correo;}
    public String getNombreCompleto()  {return nombreCompleto;}
    public String getCedula()          {return cedula;}
    public Integer getIdSeccion()    {return id_seccion;}
    public String getPuesto()          {return puesto;}
    public String getFechaActivacion()   {return formatearFecha(fechaActivacion);}
    public String getFechaDesactivacion(){return formatearFecha(fechaDesactivacion);}
    public String getActivo()         {
        if(activo){
            return "Activo" ;
                    }
        else {
            return "Inactivo";
        }
    }
    public String getNombreSeccion() {return nombreSeccion;}
    

    
    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }
    
    
}
