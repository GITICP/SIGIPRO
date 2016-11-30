/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Josue
 */
public class Seguimiento_venta {
    private int id_seguimiento;
    private Cliente cliente;
    private Factura factura;
    private String documento_1;
    private String listaTipos;

    public String getListaTipos() {
        return listaTipos;
    }

    public void setListaTipos(String listaTipos) {
        this.listaTipos = listaTipos;
    }

    public int getId_seguimiento() {
        return id_seguimiento;
    }

    public void setId_seguimiento(int id_seguimiento) {
        this.id_seguimiento = id_seguimiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getDocumento_1() {
        return documento_1;
    }

    public void setDocumento_1(String documento_1) {
        this.documento_1 = documento_1;
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
        JSON.put("id_factura",this.factura.getId_factura());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
