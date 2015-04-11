/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
/**
 *
 * @author Amed
 */
public class Cara {

  private int id_cara;
  private int numero_cara;
  private String macho_as;
  private String hembra_as;
  private Date fecha_apareamiento_i;
  private Date fecha_apareamiento_f;
  private Date fecha_eliminacionmacho_i;
  private Date fecha_eliminacionmacho_f;
  private Date fecha_eliminacionhembra_i;
  private Date fecha_eliminacionhembra_f;
  private Date fecha_seleccionnuevos_i;
  private Date fecha_seleccionnuevos_f;
  private Date fecha_reposicionciclo_i;
  private Date fecha_reposicionciclo_f;
  private Date fecha_vigencia;
  private Cepa cepa;
          
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
            JSON.put("id_cepa",this.cepa.getId_cepa());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
  public String getMacho_as() {
    return macho_as;
  }

  public void setMacho_as(String macho_as) {
    this.macho_as = macho_as;
  }

  public String getHembra_as() {
    return hembra_as;
  }

  public void setHembra_as(String hembra_as) {
    this.hembra_as = hembra_as;
  }

  public Date getFecha_apareamiento_i() {
    return fecha_apareamiento_i;
  }

  public void setFecha_apareamiento_i(Date fecha_apareamiento_i) {
    this.fecha_apareamiento_i = fecha_apareamiento_i;
  }

  public Date getFecha_apareamiento_f() {
    return fecha_apareamiento_f;
  }

  public void setFecha_apareamiento_f(Date fecha_apareamiento_f) {
    this.fecha_apareamiento_f = fecha_apareamiento_f;
  }

  public Date getFecha_eliminacionmacho_i() {
    return fecha_eliminacionmacho_i;
  }

  public void setFecha_eliminacionmacho_i(Date fecha_eliminacionmacho_i) {
    this.fecha_eliminacionmacho_i = fecha_eliminacionmacho_i;
  }

  public Date getFecha_eliminacionmacho_f() {
    return fecha_eliminacionmacho_f;
  }

  public void setFecha_eliminacionmacho_f(Date fecha_eliminacionmacho_f) {
    this.fecha_eliminacionmacho_f = fecha_eliminacionmacho_f;
  }

  public Date getFecha_eliminacionhembra_i() {
    return fecha_eliminacionhembra_i;
  }

  public void setFecha_eliminacionhembra_i(Date fecha_eliminacionhembra_i) {
    this.fecha_eliminacionhembra_i = fecha_eliminacionhembra_i;
  }

  public Date getFecha_eliminacionhembra_f() {
    return fecha_eliminacionhembra_f;
  }

  public void setFecha_eliminacionhembra_f(Date fecha_eliminacionhembra_f) {
    this.fecha_eliminacionhembra_f = fecha_eliminacionhembra_f;
  }

  public Date getFecha_seleccionnuevos_i() {
    return fecha_seleccionnuevos_i;
  }

  public void setFecha_seleccionnuevos_i(Date fecha_seleccionnuevos_i) {
    this.fecha_seleccionnuevos_i = fecha_seleccionnuevos_i;
  }

  public Date getFecha_seleccionnuevos_f() {
    return fecha_seleccionnuevos_f;
  }

  public void setFecha_seleccionnuevos_f(Date fecha_seleccionnuevos_f) {
    this.fecha_seleccionnuevos_f = fecha_seleccionnuevos_f;
  }

  public Date getFecha_reposicionciclo_i() {
    return fecha_reposicionciclo_i;
  }

  public void setFecha_reposicionciclo_i(Date fecha_reposicionciclo_i) {
    this.fecha_reposicionciclo_i = fecha_reposicionciclo_i;
  }

  public Date getFecha_reposicionciclo_f() {
    return fecha_reposicionciclo_f;
  }

  public void setFecha_reposicionciclo_f(Date fecha_reposicionciclo_f) {
    this.fecha_reposicionciclo_f = fecha_reposicionciclo_f;
  }

  public Date getFecha_vigencia() {
    return fecha_vigencia;
  }

  public void setFecha_vigencia(Date fecha_vigencia) {
    this.fecha_vigencia = fecha_vigencia;
  }
  
   public String getFecha_apareamiento_i_S(){
    if (this.fecha_apareamiento_i != null)
    {return formatearFecha(fecha_apareamiento_i);}
    else
    {return "";}}
   public String getFecha_apareamiento_f_S(){
    if (this.fecha_apareamiento_f != null)
    {return formatearFecha(fecha_apareamiento_f);}
    else
    {return "";}}
   public String getFecha_eliminacionmacho_i_S(){
    if (this.fecha_eliminacionmacho_i != null)
    {return formatearFecha(fecha_eliminacionmacho_i);}
    else
    {return "";}}
   public String getFecha_eliminacionmacho_f_S(){
    if (this.fecha_eliminacionmacho_f != null)
    {return formatearFecha(fecha_eliminacionmacho_f);}
    else
    {return "";}}
   public String getFecha_eliminacionhembra_i_S(){
    if (this.fecha_eliminacionhembra_i != null)
    {return formatearFecha(fecha_eliminacionhembra_i);}
    else
    {return "";}}
   public String getFecha_eliminacionhembra_f_S(){
    if (this.fecha_eliminacionhembra_f != null)
    {return formatearFecha(fecha_eliminacionhembra_f);}
    else
    {return "";}}
   public String getFecha_seleccionnuevos_i_S(){
    if (this.fecha_seleccionnuevos_i != null)
    {return formatearFecha(fecha_seleccionnuevos_i);}
    else
    {return "";}}
   public String getFecha_seleccionnuevos_f_S(){
    if (this.fecha_seleccionnuevos_f != null)
    {return formatearFecha(fecha_seleccionnuevos_f);}
    else
    {return "";}}
   public String getFecha_reposicionciclo_i_S(){
    if (this.fecha_reposicionciclo_i != null)
    {return formatearFecha(fecha_reposicionciclo_i);}
    else
    {return "";}}
    public String getFecha_reposicionciclo_f_S(){
    if (this.fecha_reposicionciclo_f != null)
    {return formatearFecha(fecha_reposicionciclo_f);}
    else
    {return "";}}
     public String getFecha_vigencia_S(){
    if (this.fecha_vigencia != null)
    {return formatearFecha(fecha_vigencia);}
    else
    {return "";}}

  public int getId_cara() {
    return id_cara;
  }

  public void setId_cara(int id_cara) {
    this.id_cara = id_cara;
  }

  public int getNumero_cara() {
    return numero_cara;
  }

  public void setNumero_cara(int numero_cara) {
    this.numero_cara = numero_cara;
  }

  public Cepa getCepa() {
    return cepa;
  }

  public void setCepa(Cepa cepa) {
    this.cepa = cepa;
  }
   
   
  private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
}
