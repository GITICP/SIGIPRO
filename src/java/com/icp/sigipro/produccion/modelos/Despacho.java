/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import org.json.JSONObject;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Despacho {
 private int id_despacho;
 private Date fecha;
 private String destino;
 private Usuario coordinador;
 private Date fecha_coordinador;
 private boolean estado_coordinador;
 private Usuario regente;
 private Date fecha_regente;
 private boolean estado_regente;
 private int total;
 private String tipo;

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
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
            JSON.put("id_cooridnador",this.coordinador.getId_usuario());
            JSON.put("id_regente",this.regente.getId_usuario());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
 
 private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
 
  public int getId_despacho() {
    return id_despacho;
  }

  public void setId_despacho(int id_despacho) {
    this.id_despacho = id_despacho;
  }

  public Date getFecha() {
    return fecha;
  }
  public String getFecha_S(){
    if (this.fecha != null)
    {return formatearFecha(fecha);}
    else
    {return "";}}

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getDestino() {
    return destino;
  }

  public void setDestino(String destino) {
    this.destino = destino;
  }

  public Usuario getCoordinador() {
    return coordinador;
  }

  public void setCoordinador(Usuario coordinador) {
    this.coordinador = coordinador;
  }

  public Date getFecha_coordinador() {
    return fecha_coordinador;
  }
  public String getFecha_coordinador_S(){
    if (this.fecha_coordinador != null)
    {return formatearFecha(fecha_coordinador);}
    else
    {return "";}}

  public void setFecha_coordinador(Date fecha_coordinador) {
    this.fecha_coordinador = fecha_coordinador;
  }

  public boolean isEstado_coordinador() {
    return estado_coordinador;
  }

  public void setEstado_coordinador(boolean estado_coordinador) {
    this.estado_coordinador = estado_coordinador;
  }

  public Usuario getRegente() {
    return regente;
  }

  public void setRegente(Usuario regente) {
    this.regente = regente;
  }

  public Date getFecha_regente() {
    return fecha_regente;
  }
  public String getFecha_regente_S(){
    if (this.fecha_regente != null)
    {return formatearFecha(fecha_regente);}
    else
    {return "";}}
  public void setFecha_regente(Date fecha_regente) {
    this.fecha_regente = fecha_regente;
  }

  public boolean isEstado_regente() {
    return estado_regente;
  }

  public void setEstado_regente(boolean estado_regente) {
    this.estado_regente = estado_regente;
  }
 
 
}
