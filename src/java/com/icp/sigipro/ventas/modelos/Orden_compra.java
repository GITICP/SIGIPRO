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
public class Orden_compra {
    private int id_orden;
    private Cliente cliente;
    private Intencion_venta intencion;
    private Cotizacion cotizacion;
    private String rotulacion;
    private String estado;

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Intencion_venta getIntencion() {
        return intencion;
    }

    public void setIntencion(Intencion_venta intencion) {
        this.intencion = intencion;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public String getRotulacion() {
        return rotulacion;
    }

    public void setRotulacion(String rotulacion) {
        this.rotulacion = rotulacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
            JSON.put("id_cliente",this.cliente.getId_cliente());
            JSON.put("id_intencion",this.intencion.getId_intencion());
            JSON.put("id_cotizacion",this.cotizacion.getId_cotizacion());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
