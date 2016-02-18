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
    private String tipo;
    private String observaciones;
    private String documento_1;
    private String documento_2;
    private String documento_3;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getDocumento_1() {
        return documento_1;
    }

    public void setDocumento_1(String documento_1) {
        this.documento_1 = documento_1;
    }

    public String getDocumento_2() {
        return documento_2;
    }

    public void setDocumento_2(String documento_2) {
        this.documento_2 = documento_2;
    }

    public String getDocumento_3() {
        return documento_3;
    }

    public void setDocumento_3(String documento_3) {
        this.documento_3 = documento_3;
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
