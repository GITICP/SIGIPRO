/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class EntregasSolicitud {
    private int id_entrega;
    private Solicitud solicitud;
    private Usuario usuario_entrega;
    private Date fecha_entrega;
    private float cantidad_entregada;
    private Usuario usuario_recibo;

    public EntregasSolicitud() {
    }

    public EntregasSolicitud(int id_entrega, Solicitud solicitud, Usuario usuario_entrega, Date fecha_entrega, float cantidad_entregada, Usuario usuario_recibo) {
        this.id_entrega = id_entrega;
        this.solicitud = solicitud;
        this.usuario_entrega = usuario_entrega;
        this.fecha_entrega = fecha_entrega;
        this.cantidad_entregada = cantidad_entregada;
        this.usuario_recibo = usuario_recibo;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Usuario getUsuario_entrega() {
        return usuario_entrega;
    }

    public void setUsuario_entrega(Usuario usuario_entrega) {
        this.usuario_entrega = usuario_entrega;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }
    
    public String getFecha_entregaAsString() {
        return formatearFecha(fecha_entrega);
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public float getCantidad_entregada() {
        return cantidad_entregada;
    }

    public void setCantidad_entregada(float cantidad_entregada) {
        this.cantidad_entregada = cantidad_entregada;
    }

    public Usuario getUsuario_recibo() {
        return usuario_recibo;
    }

    public void setUsuario_recibo(Usuario usuario_recibo) {
        this.usuario_recibo = usuario_recibo;
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
            JSON.put("id_usuario_entrega",this.usuario_entrega.getId_usuario());
            JSON.put("id_usuario_recibo",this.usuario_recibo.getId_usuario());
            JSON.put("id_solicitud",this.solicitud.getId_solicitud());

            }          
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
     private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }
}
