/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.google.gson.Gson;
import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.Date;

/**
 *
 * @author Boga
 */
public class Patron {
    
    private final transient HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
    
    private int id_patron;
    private String numero_lote;
    private TipoPatronControl tipo;
    private Date fecha_ingreso;
    private Date fecha_vencimiento;
    private Date fecha_inicio_uso;
    private String certificado;
    private String lugar_almacenamiento;
    private String condicion_almacenamiento;
    private String observaciones;
    
    public Patron(){}

    public int getId_patron() {
        return id_patron;
    }

    public void setId_patron(int id_patron) {
        this.id_patron = id_patron;
    }

    public String getNumero_lote() {
        return numero_lote;
    }

    public void setNumero_lote(String numero_lote) {
        this.numero_lote = numero_lote;
    }

    public TipoPatronControl getTipo() {
        return tipo;
    }

    public void setTipo(TipoPatronControl tipo) {
        this.tipo = tipo;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }
    
    public String getFecha_ingresoAsString() {
        return formatearFecha(fecha_ingreso, "Sin fecha de vencimiento");
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }
    
    public String getFecha_vencimientoAsString() {
        return formatearFecha(fecha_vencimiento, "Sin fecha de vencimiento");
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
    }

    public Date getFecha_inicio_uso() {
        return fecha_inicio_uso;
    }
    
    public String getFecha_inicio_usoAsString() {
        return formatearFecha(fecha_inicio_uso, "Sin fecha de vencimiento");
    }

    public void setFecha_inicio_uso(Date fecha_inicio_uso) {
        this.fecha_inicio_uso = fecha_inicio_uso;
    }

    public String getCertificado() {
        return certificado;
    }
    
    public Boolean tieneCertificado() {
        boolean resultado = false;
        if (certificado != null) {
            if (!certificado.isEmpty()) {
                resultado = true;
            }
        }
        return resultado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getLugar_almacenamiento() {
        return lugar_almacenamiento;
    }

    public void setLugar_almacenamiento(String lugar_almacenamiento) {
        this.lugar_almacenamiento = lugar_almacenamiento;
    }

    public String getCondicion_almacenamiento() {
        return condicion_almacenamiento;
    }

    public void setCondicion_almacenamiento(String condicion_almacenamiento) {
        this.condicion_almacenamiento = condicion_almacenamiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = "Sin observaciones.";
        if (observaciones != null) {
            if (!observaciones.isEmpty()) {
                this.observaciones = observaciones;
            }
        }
    }
    
    private String formatearFecha(Date fecha, String mensaje_si_es_nula) {
        String resultado = mensaje_si_es_nula;
        if (fecha != null) {
            resultado = helper_fechas.formatearFecha(fecha);
        }
        return resultado;
    }
    
    public String parseJSON() {
        return new Gson().toJson(this);
    }
}
