/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.modelos;

import com.icp.sigipro.produccion.modelos.Inventario_PT;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author Josue
 */
public class Producto_Orden {
    private Producto_venta producto;
    private Orden_compra orden;
    private int cantidad;
    private Date fecha_entrega;
    private int contador; //Por facilidad para duplicar y modificar en la tabla de productos
    private Inventario_PT lote;

    public Inventario_PT getLote() {
        return lote;
    }

    public void setLote(Inventario_PT lote) {
        this.lote = lote;
    }
    
    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public Orden_compra getOrden() {
        return orden;
    }

    public void setOrden(Orden_compra orden) {
        this.orden = orden;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }
    
    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
    
    public String getFecha_S(){
        if (this.fecha_entrega != null)
            {return formatearFecha(this.fecha_entrega);}
        else
            {return "";}
    }
    
    public Producto_venta getProducto() {
        return producto;
    }

    public void setProducto(Producto_venta producto) {
        this.producto = producto;
    }

    public Orden_compra getOrden_compra() {
        return orden;
    }

    public void setOrden_compra(Orden_compra orden) {
        this.orden = orden;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
            JSON.put("id_orden",this.orden.getId_orden());
            JSON.put("lote",this.lote.getId_inventario_pt());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
