/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Boga
 */
public class Reactivo
{
  private int id_reactivo;
  private ProductoInterno producto_interno;
  private String numero_cas;
  private String formula_quimica;
  private String familia;
  private int cantidad_botella_bodega;
  private int cantidad_botella_lab;
  private String volumen_bodega;
  private String volumen_lab;
  
  public Reactivo(){}

  public int getId_reactivo()
  {
    return id_reactivo;
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
            
        }
        return JSON.toString();
    }

  
  public void setId_reactivo(int id_reactivo)
  {
    this.id_reactivo = id_reactivo;
  }

  public ProductoInterno getProducto_interno()
  {
    return producto_interno;
  }

  public void setProducto_interno(ProductoInterno producto_interno)
  {
    this.producto_interno = producto_interno;
  }

  public String getNumero_cas()
  {
    return numero_cas;
  }

  public void setNumero_cas(String numero_cas)
  {
    this.numero_cas = numero_cas;
  }

  public String getFormula_quimica()
  {
    return formula_quimica;
  }

  public void setFormula_quimica(String formula_quimica)
  {
    this.formula_quimica = formula_quimica;
  }

  public String getFamilia()
  {
    return familia;
  }

  public void setFamilia(String familia)
  {
    this.familia = familia;
  }

  public int getCantidad_botella_bodega()
  {
    return cantidad_botella_bodega;
  }

  public void setCantidad_botella_bodega(int cantidad_botella_bodega)
  {
    this.cantidad_botella_bodega = cantidad_botella_bodega;
  }

  public int getCantidad_botella_lab()
  {
    return cantidad_botella_lab;
  }

  public void setCantidad_botella_lab(int cantidad_botella_lab)
  {
    this.cantidad_botella_lab = cantidad_botella_lab;
  }

  public String getVolumen_bodega()
  {
    return volumen_bodega;
  }

  public void setVolumen_bodega(String volumen_bodega)
  {
    this.volumen_bodega = volumen_bodega;
  }

  public String getVolumen_lab()
  {
    return volumen_lab;
  }

  public void setVolumen_lab(String volumen_lab)
  {
    this.volumen_lab = volumen_lab;
  }
  
  
}
