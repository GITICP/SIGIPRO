package com.icp.sigipro.bodegas.modelos;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Walter
 */
public class ActivoFijo {

  int id_activo_fijo;
  String placa;
  String equipo;
  String marca;
  String fecha_movimiento;
  int id_seccion;
  int id_ubicacion;
  String fecha_registro;
  String estado;
  String nombre_seccion;
  String nombre_ubicacion;

  public ActivoFijo() {
  }

  public ActivoFijo(int id_activo_fijo, String placa, String equipo, String marca, String fecha_movimiento, int id_seccion, int id_ubicacion, String fecha_registro, String estado) {
    this.id_activo_fijo = id_activo_fijo;
    this.placa = placa;
    this.equipo = equipo;
    this.marca = marca;
    this.fecha_movimiento = fecha_movimiento;
    this.id_seccion = id_seccion;
    this.id_ubicacion = id_ubicacion;
    this.fecha_registro = fecha_registro;
    this.estado = estado;
  }

  public int getId_activo_fijo() {
    return id_activo_fijo;
  }

  public void setId_activo_fijo(int id_activo_fijo) {
    this.id_activo_fijo = id_activo_fijo;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public String getEquipo() {
    return equipo;
  }

  public void setEquipo(String equipo) {
    this.equipo = equipo;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public Date getFecha_movimientoAsDate() throws ParseException {
    return formatearFecha(fecha_movimiento);
  }
  public String getFecha_movimiento()  {
    return fecha_movimiento;
  }

  public void setFecha_movimiento(String fecha_movimiento) {
    this.fecha_movimiento = fecha_movimiento;
  }

  public int getId_seccion() {
    return id_seccion;
  }

  public void setId_seccion(int id_seccion) {
    this.id_seccion = id_seccion;
  }

  public int getId_ubicacion() {
    return id_ubicacion;
  }

  public void setId_ubicacion(int id_ubicacion) {
    this.id_ubicacion = id_ubicacion;
  }

  public Date getFecha_registroAsDate() throws ParseException {
    return formatearFecha(fecha_registro);
  }
    public String getFecha_registro()  {
    return fecha_registro;
  }

  public void setFecha_registro(String fecha_registro) {
    this.fecha_registro = fecha_registro;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getNombre_seccion() {
    return nombre_seccion;
  }

  public void setNombre_seccion(String nombre_seccion) {
    this.nombre_seccion = nombre_seccion;
  }

  public String getNombre_ubicacion() {
    return nombre_ubicacion;
  }

  public void setNombre_ubicacion(String nombre_ubicacion) {
    this.nombre_ubicacion = nombre_ubicacion;
  }
  
  private Date formatearFecha(String fecha) throws ParseException
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fechautil;
        fechautil = df.parse(fecha);
        java.sql.Date fechaout = new java.sql.Date(fechautil.getTime());
        return fechaout;
    }
}
