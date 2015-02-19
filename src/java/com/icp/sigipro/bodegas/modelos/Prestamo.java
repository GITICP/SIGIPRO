/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

/**
 *
 * @author Amed
 */
public class Prestamo {
  private int id_solicitud;
  private int id_seccion_presta;
  private int id_usuario_aprobo;
  
  public Solicitud solicitud;
  
  public Prestamo(){}
  public void setId_solicitud(int id){ this.id_solicitud = id;}
  public void setId_seccion_presta(int id){ this.id_seccion_presta = id;}
  public void setId_usuario_aprobo(int id){ this.id_usuario_aprobo = id;}
  
   public void setSolicitud(Solicitud s){this.solicitud = s;}
   
  public int getId_solicitud () {return id_solicitud; }
  public int getId_seccion_presta () {return id_seccion_presta; }
  public int getId_usuario_aprobo(){ return id_usuario_aprobo;}
}
