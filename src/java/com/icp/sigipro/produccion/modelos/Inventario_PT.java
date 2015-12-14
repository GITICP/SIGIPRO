/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;
import java.lang.reflect.Field;
import org.json.JSONObject;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Inventario_PT {
  private int id_inventario_pt;
  private String lote;
  private int cantidad;
  private Date fecha_vencimiento;
  private Protocolo protocolo;
  private int cantidad_disponible;
  private Catalogo_PT producto;

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
            JSON.put("id_protocolo",this.protocolo.getId_protocolo());
            JSON.put("id_producto",this.producto.getId_catalogo_pt());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }

  public Catalogo_PT getProducto() {
    return producto;
  }

  public void setProducto(Catalogo_PT producto) {
    this.producto = producto;
  }
    
  public int getId_inventario_pt() {
    return id_inventario_pt;
  }

  public void setId_inventario_pt(int id_inventario_pt) {
    this.id_inventario_pt = id_inventario_pt;
  }

  public String getLote() {
    return lote;
  }

  public void setLote(String lote) {
    this.lote = lote;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public Date getFecha_vencimiento() {
    return fecha_vencimiento;
  }

  public void setFecha_vencimiento(Date fecha_vencimiento) {
    this.fecha_vencimiento = fecha_vencimiento;
  }
  public String getFecha_vencimiento_S(){
    if (this.fecha_vencimiento != null)
    {return formatearFecha(fecha_vencimiento);}
    else
    {return "";}}

  public Protocolo getProtocolo() {
    return protocolo;
  }

  public void setProtocolo(Protocolo protocolo) {
    this.protocolo = protocolo;
  }
  private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  

  public int getCantidad_disponible() {
    return cantidad_disponible;
  }

  public void setCantidad_disponible(int cantidad_disponible) {
    this.cantidad_disponible = cantidad_disponible;
  }
  
  
}
