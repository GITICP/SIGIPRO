/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Amed
 */
public class Solicitud {
  private int id_solicitud; 
  private int id_usuario;
  private int id_inventario;
  private int cantidad;
  private Date fecha_solicitud;
  private String estado;
  private Date fecha_entrega;
  private int id_usuario_recibo;
  
  private Usuario usuario;
  private Usuario usuario_receptor;
  private Inventario inventario;
  
  public Solicitud(){
    };
  public void setId_solicitud(int id){ this.id_solicitud = id;}
  public void setId_usuario(int id){ this.id_usuario = id;}
  public void setId_inventario(int id){ this.id_inventario = id;}
  public void setCantidad(int id){ this.cantidad = id;}
  public void setFecha_solicitud(Date id){ this.fecha_solicitud = id;}
  public void setEstado(String id){ this.estado = id;}
  public void setFecha_entrega(Date id){ this.fecha_entrega = id;}
  public void setId_usuario_recibo(int id){ this.id_usuario_recibo = id;}
  
  public void setUsuario(Usuario u){this.usuario =u;}
  public void setInventario(Inventario u){this.inventario =u;}
  public void setUsuarioReceptor(Usuario u){this.usuario_receptor =u;}
  
  public Usuario getUsuario(){return usuario ;}
  public Inventario getInventario(){return inventario;}
  public Usuario getUsuarioReceptor(){return usuario_receptor ;}
          
  
  public int getId_solicitud () {return id_solicitud; }
  public int getId_usuario () {return id_usuario; }
  public int getId_inventario () {return id_inventario; }
  public int getCantidad () {return cantidad; }
  public String getEstado () {return estado;}
  public String getFecha_solicitud()   {
    if (this.fecha_solicitud != null)
    {return formatearFecha(fecha_solicitud);}
    else
    {return "Sin fecha";}
  }
  public Date getFecha_solicitudAsDate()   {return fecha_solicitud;}
  public Date getFecha_entregaAsDate()   {return fecha_entrega;}
  public String getFecha_entrega(){
    if (this.fecha_entrega != null)
    {return formatearFecha(fecha_entrega);}
    else
    {return "Sin fecha";}}
  public int getId_usuario_recibo() {return id_usuario_recibo; }
  
  
  private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }
  
}
