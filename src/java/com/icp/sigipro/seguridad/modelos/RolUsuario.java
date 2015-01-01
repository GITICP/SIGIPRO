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

  public RolUsuario(int p_idRol, int p_idUsuario, Date p_fechaActivacion, Date p_fechaDesactivacion)
  {
    idRol = p_idRol;
    idUsuario = p_idUsuario;
    fechaActivacion = p_fechaActivacion;
    fechaDesactivacion = p_fechaDesactivacion;
  }

  public int getIDRol() {return idRol;}
  public int getIDUsuario() {return idUsuario;}
  public String getFechaActivacion() {return formatearFecha(fechaActivacion);}
  public String getFechaDesactivacion() {return formatearFecha(fechaDesactivacion);}
  public Date getFechaActivacionSQL() {return fechaActivacion;}
  public Date getFechaDesactivacionSQL() {return fechaDesactivacion;}
  public String getNombreRol() {return nombreRol;}

  private String formatearFecha(Date fecha)
  {
      DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      return df.format(fecha);
  }    
}
