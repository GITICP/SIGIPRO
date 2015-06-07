package com.icp.sigipro.activosfijos.modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class ActivoFijo {

  int id_activo_fijo;
  String placa;
  String equipo;
  String marca;
  Date   fecha_movimiento;
  int id_seccion;
  int id_ubicacion;
  Date    fecha_registro;
  String estado;
  String nombre_seccion;
  String nombre_ubicacion;
  String responsable;
  String serie;

  public ActivoFijo() {
  }

  public ActivoFijo(int id_activo_fijo, String placa, String equipo, String marca, Date fecha_movimiento, int id_seccion, int id_ubicacion, Date fecha_registro, String estado) {
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
  
    //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public String parseJSON(){
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try{
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0){
                    JSON.put(field.getName(), field.get(this));
                }else{
                    JSON.put("id_objeto", field.get(this));
                }
            }          
        }catch (Exception e){
            e.printStackTrace();
        }
        return JSON.toString();
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
    if( placa.isEmpty() || placa.equals("") ){
      this.placa = "Sin placa";
    } else {
      this.placa = placa;
    }
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
    if( marca.isEmpty() || marca.equals("") ){
      this.marca = "Sin marca";
    } else {
      this.marca = marca;
    }
  }

  public Date getFecha_movimientoAsDate() throws ParseException {
    return fecha_movimiento;
  }
  
  public String getFecha_movimiento()  {
    String resultado = "";
    if(fecha_movimiento != null){
      resultado = formatearFecha(fecha_movimiento);
    }
    return resultado;
    
  }

  public void setFecha_movimiento(Date fecha_movimiento) {
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

  public Date getFecha_registroAsDate() {
    return fecha_registro;
  }
  
  public String getFecha_registro()  {
    String resultado = "";
    if(fecha_movimiento != null){
      resultado = formatearFecha(fecha_registro);
    }
    return resultado;
  }

  public void setFecha_registro(Date fecha_registro) {
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
  
  private String formatearFecha(Date fecha)
  {
      DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      return df.format(fecha);
  }
  
  public String getNombreFormato() {
    return this.getEquipo() + " (" + this.getPlaca() + ")";
  }
  
  public void setResponsable(String responsable) {
    this.responsable = responsable;
  }

  public void setSerie(String serie) {
    this.serie = serie;
  }

  public String getResponsable() {
    return responsable;
  }

  public String getSerie() {
    return serie;
  }
}
