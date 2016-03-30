/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.modelos;

import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author Josue
 */
public class Producto_Intencion {
    private Producto_venta producto;
    private Intencion_venta intencion;
    private int cantidad;
    private Date posible_fecha_despacho;

    public Producto_venta getProducto() {
        return producto;
    }

    public void setProducto(Producto_venta producto) {
        this.producto = producto;
    }

    public Intencion_venta getIntencion() {
        return intencion;
    }

    public void setIntencion(Intencion_venta intencion) {
        this.intencion = intencion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getPosible_fecha_despacho() {
        return posible_fecha_despacho;
    }

    public void setPosible_fecha_despacho(Date posible_fecha_despacho) {
        this.posible_fecha_despacho = posible_fecha_despacho;
    }
    
    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
    
    public String getFecha_S(){
        if (this.posible_fecha_despacho != null)
            {return formatearFecha(this.posible_fecha_despacho);}
        else
            {return "";}
    }
    
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
            JSON.put("id_producto",this.producto.getId_producto());
            JSON.put("id_intencion",this.intencion.getId_intencion());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
