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
 * @author Amed
 */
public class ProductoExterno {
  private int id_producto_ext;
  private String producto;
  private String codigo_externo;
  private String marca;
  private int id_proveedor;
  private String nombre_proveedor;
  
  public ProductoExterno(){}
  
  public ProductoExterno(int id_producto_ext_interno, String p_producto, String p_codigo_externo, int p_id_proveedor, String p_marca)
  {
    this.id_producto_ext = id_producto_ext_interno;
    this.producto = p_producto;
    this.codigo_externo = p_codigo_externo;
    this.marca = p_marca;
    this.id_proveedor = p_id_proveedor;

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

  
  public int getId_producto_ext()
  {
    return id_producto_ext;
  }

  public String getProducto()
  {
    return producto;
  }

  public String getCodigo_Externo()
  {
    return codigo_externo;
  }

  public int getId_Proveedor()
  {
    return id_proveedor;
  }

  public String getMarca()
  {
    return marca;
  }
  public String getNombreProveedor()
  {
    return nombre_proveedor;
  }
  
  public void setId_producto_ext(int id_producto_ext)
  {
    this.id_producto_ext = id_producto_ext;
  }

  public void setProducto(String producto)
  {
    this.producto = producto;
  }
  
  public void setCodigo_Externo(String codigo_externo)
  {
    this.codigo_externo = codigo_externo;
  }

  public void setId_Proveedor(int id_proveedor)
  {
    this.id_proveedor = id_proveedor;
  }

  public void setMarca(String marca)
  {
    this.marca = marca;
  }
  public void setNombreProveedor(String nombre)
  {
    this.nombre_proveedor = nombre;
  }
}
