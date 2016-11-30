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
public class Factura {
    private int id_factura;
    private Cliente cliente;
    private Orden_compra orden;
    private Date fecha;
    private int monto;
    private float monto_pendiente;
    private Date fecha_vencimiento;
    private String documento_1;
    private String documento_2;
    private String documento_3;
    private String documento_4;
    private String tipo;
    private String moneda;
    
    private int numero;
    private int proyecto;
    private int plazo;
    private String estado;
    private String detalle;
    private String nombre;
    private String correo_enviar;

    public String getCorreo_enviar() {
        return correo_enviar;
    }

    public void setCorreo_enviar(String correo_enviar) {
        this.correo_enviar = correo_enviar;
    }

    public float getMonto_pendiente() {
        return monto_pendiente;
    }

    public void setMonto_pendiente(float monto_pendiente) {
        this.monto_pendiente = monto_pendiente;
    }
    
    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getProyecto() {
        return proyecto;
    }

    public void setProyecto(int proyecto) {
        this.proyecto = proyecto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    
    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Orden_compra getOrden() {
        return orden;
    }

    public void setOrden(Orden_compra orden) {
        this.orden = orden;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
    
    public String getFecha_S(){
        if (this.fecha != null)
            {return formatearFecha(this.fecha);}
        else
            {return "";}
    }
    
    public String getFecha_vencimiento_S(){
        if (this.fecha_vencimiento != null)
            {return formatearFecha(this.fecha_vencimiento);}
        else
            {return "";}
    }
    
    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
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

    public String getDocumento_4() {
        return documento_4;
    }

    public void setDocumento_4(String documento_4) {
        this.documento_4 = documento_4;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
            JSON.put("id_orden",this.orden.getId_orden());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
