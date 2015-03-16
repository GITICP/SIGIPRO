/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class LotesEntregasSolicitud {
    private Lote lote;
    private EntregasSolicitud entrega_solicitud;
    private int cantidad;

    public LotesEntregasSolicitud() {
    }

    public LotesEntregasSolicitud(Lote id_lote, EntregasSolicitud entrega_solicitud, int cantidad) {
        this.lote = id_lote;
        this.entrega_solicitud = entrega_solicitud;
        this.cantidad = cantidad;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public EntregasSolicitud getEntrega_solicitud() {
        return entrega_solicitud;
    }

    public void setEntrega_solicitud(EntregasSolicitud entrega_solicitud) {
        this.entrega_solicitud = entrega_solicitud;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
            JSON.put("id_entrega_solicitud",this.entrega_solicitud.getId_entrega());
            JSON.put("id_lote",this.lote.getId_lote());

        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
