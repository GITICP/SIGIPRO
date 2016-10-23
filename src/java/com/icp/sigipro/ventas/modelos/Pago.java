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
public class Pago {
    private int id_pago;
    private Factura factura;
    private int codigo;
    private float monto;
    private String nota;
    private String fecha;
    private String consecutive;
    private String moneda;
    private int codigo_remision;
    private String consecutive_remision;
    private String fecha_remision;

    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConsecutive() {
        return consecutive;
    }

    public void setConsecutive(String consecutive) {
        this.consecutive = consecutive;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getCodigo_remision() {
        return codigo_remision;
    }

    public void setCodigo_remision(int codigo_remision) {
        this.codigo_remision = codigo_remision;
    }

    public String getConsecutive_remision() {
        return consecutive_remision;
    }

    public void setConsecutive_remision(String consecutive_remision) {
        this.consecutive_remision = consecutive_remision;
    }

    public String getFecha_remision() {
        return fecha_remision;
    }

    public void setFecha_remision(String fecha_remision) {
        this.fecha_remision = fecha_remision;
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
            JSON.put("id_factura",this.factura.getId_factura());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
