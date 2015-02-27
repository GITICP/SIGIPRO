/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import com.icp.sigipro.core.IModelo;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Boga
 */
public class Usuario  extends IModelo
{
    
    int id_usuario;
    String nombre_usuario;
    String correo;
    String nombre_completo;
    String cedula;
    int ref_seccion;
    int ref_puesto;
    Date fecha_activacion;
    Date fecha_desactivacion;
    boolean activo;
    //Opcionales para cosas de interfaz
    String nombre_seccion;
    String nombre_puesto;
    
    public Usuario(){}
    
    public Usuario (int p_idUsuario, String p_nombreUsuario, String p_correo, String p_nombreCompleto,
            String p_cedula, int p_departamento, int p_puesto, Date p_fechaActivacion, Date p_fechaDesactivacion,
            boolean p_activo)
    {
        id_usuario = p_idUsuario;
        nombre_usuario = p_nombreUsuario;
        correo = p_correo;
        nombre_completo = p_nombreCompleto;
        cedula = p_cedula;
        ref_seccion = p_departamento;
        ref_puesto = p_puesto;
        fecha_activacion = p_fechaActivacion;
        fecha_desactivacion = p_fechaDesactivacion;
        activo = p_activo;
    }

    public void setIdUsuario(int idUsuario) {
        this.id_usuario = idUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombre_usuario = nombreUsuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombre_completo = nombreCompleto;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setIdSeccion(int seccion) {
        this.ref_seccion = seccion;
    }

    public void setIdPuesto(int puesto) {
        this.ref_puesto = puesto;
    }

    public void setFechaActivacion(Date fechaActivacion) {
        this.fecha_activacion = fechaActivacion;
    }

    public void setFechaDesactivacion(Date fechaDesactivacion) {
        this.fecha_desactivacion = fechaDesactivacion;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public void setNombreSeccion(String activo) {
        this.nombre_seccion = activo;
    }
     public void setNombrePuesto(String activo) {
        this.nombre_puesto = activo;
    }
    public int getID()                 {return id_usuario;}
    public String getNombreUsuario()   {return nombre_usuario;}
    public String getCorreo()          {return correo;}
    public String getNombreCompleto()  {return nombre_completo;}
    public String getCedula()          {return cedula;}
    public Integer getIdSeccion()    {return ref_seccion;}
    public Integer getIdPuesto()          {return ref_puesto;}
    public String getFechaActivacion()   {return formatearFecha(fecha_activacion);}
    public Date getFechaDesactivacionAsDate()   {return fecha_desactivacion;}
    public Date getFechaActivacionAsDate()   {return fecha_activacion;}
    public String getFechaDesactivacion(){return formatearFecha(fecha_desactivacion);}
    public boolean getEstado() {return activo;}
    public String getActivo()         {
        if(activo){
            return "Activo" ;
                    }
        else {
            return "Inactivo";
        }
    }
    public String getNombreSeccion() {return nombre_seccion;}
    public String getNombrePuesto() {return nombre_puesto;}
    

    
    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }

  public int getId_usuario()
  {
    return id_usuario;
  }

  public void setId_usuario(int id_usuario)
  {
    this.id_usuario = id_usuario;
  }
    
  public String getNombre_usuario()
  {
    return nombre_usuario;
  }

  public void setNombre_usuario(String nombre_usuario)
  {
    this.nombre_usuario = nombre_usuario;
  }

  public String getNombre_completo()
  {
    return nombre_completo;
  }

  public void setNombre_completo(String nombre_completo)
  {
    this.nombre_completo = nombre_completo;
  }

  public int getId_seccion()
  {
    return ref_seccion;
  }

  public void setId_seccion(int id_seccion)
  {
    this.ref_seccion = id_seccion;
  }

  public int getId_puesto()
  {
    return ref_puesto;
  }

  public void setId_puesto(int id_puesto)
  {
    this.ref_puesto = id_puesto;
  }

  public Date getFecha_activacion()
  {
    return fecha_activacion;
  }

  public void setFecha_activacion(Date fecha_activacion)
  {
    this.fecha_activacion = fecha_activacion;
  }

  public Date getFecha_desactivacion()
  {
    return fecha_desactivacion;
  }

  public void setFecha_desactivacion(Date fecha_desactivacion)
  {
    this.fecha_desactivacion = fecha_desactivacion;
  }

  public String getNombre_seccion()
  {
    return nombre_seccion;
  }

  public void setNombre_seccion(String nombre_seccion)
  {
    this.nombre_seccion = nombre_seccion;
  }

  public String getNombre_puesto()
  {
    return nombre_puesto;
  }

  public void setNombre_puesto(String nombre_puesto)
  {
    this.nombre_puesto = nombre_puesto;
  }

  public boolean isActivo()
  {
    return activo;
  }
    
  
}
