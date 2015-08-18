/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;
/**
/**
 *
 * @author Amed
 */
public class EntregaRatonera {
  private int id_entrega;
  private SolicitudRatonera solicitud;
  private Date fecha_entrega;
  private int numero_animales;
  private String peso;
  private int numero_cajas;
  private String sexo;
  private Cepa cepa;
  private Usuario usuario_recipiente;

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
            JSON.put("usuario_solicitante",this.usuario_recipiente.getId_usuario());
            JSON.put("id_solicitud",this.solicitud.getId_solicitud());
        }catch (Exception e){
            
        }
        return JSON.toString();
    } 
  private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
  public String getFecha_entrega_S(){
    if (this.fecha_entrega != null)
    {return formatearFecha(fecha_entrega);}
    else
    {return "";}}

  public int getId_entrega() {
    return id_entrega;
  }

  public void setId_entrega(int id_entrega) {
    this.id_entrega = id_entrega;
  }

  public SolicitudRatonera getSolicitud() {
    return solicitud;
  }

  public void setSolicitud(SolicitudRatonera solicitud) {
    this.solicitud = solicitud;
  }

  public Date getFecha_entrega() {
    return fecha_entrega;
  }
  
  public String getFecha_entregaAsString() {
      HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
      String resultado = "Sin fecha";
      if (fecha_entrega != null) {
          resultado = helper_fechas.formatearFecha(fecha_entrega);
      }
      return resultado;
  }

  public void setFecha_entrega(Date fecha_entrega) {
    this.fecha_entrega = fecha_entrega;
  }

  public int getNumero_animales() {
    return numero_animales;
  }

  public void setNumero_animales(int numero_animales) {
    this.numero_animales = numero_animales;
  }

  public String getPeso() {
    return peso;
  }

  public void setPeso(String peso) {
    this.peso = peso;
  }

  public int getNumero_cajas() {
    return numero_cajas;
  }

  public void setNumero_cajas(int numero_cajas) {
    this.numero_cajas = numero_cajas;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public Cepa getCepa() {
    return cepa;
  }

  public void setCepa(Cepa cepa) {
    this.cepa = cepa;
  }

  public Usuario getUsuario_recipiente() {
    return usuario_recipiente;
  }

  public void setUsuario_recipiente(Usuario usuario_recipiente) {
    this.usuario_recipiente = usuario_recipiente;
  }

}
