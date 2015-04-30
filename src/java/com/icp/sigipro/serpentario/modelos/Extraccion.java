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
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Extraccion {
    private int id_extraccion;
    private String numero_extraccion;
    private Especie especie;
    private boolean ingreso_cv;
    private Date fecha_extraccion;
    private float volumen_extraido;
    private Usuario usuario_registro;
    private Date fecha_registro;
    private Lote lote;
    
    private Centrifugado centrifugado;
    private Liofilizacion liofilizacion;
    
    private boolean isSerpiente;
    private boolean isRegistro;
    private boolean isCentrifugado;
    private boolean isLiofilizacionInicio;
    private boolean isLiofilizacionFin;

    public Extraccion() {
        isSerpiente=false;
        isRegistro=false;
        isCentrifugado=false;
        isLiofilizacionInicio=false;
        isLiofilizacionFin=false;
    }

    public Extraccion(int id_extraccion, String numero_extraccion, Especie especie, boolean ingreso_cv, Date fecha_extraccion, float volumen_extraido, Usuario usuario_registro, Date fecha_registro, Lote lote) {
        this.id_extraccion = id_extraccion;
        this.numero_extraccion = numero_extraccion;
        this.especie = especie;
        this.ingreso_cv = ingreso_cv;
        this.fecha_extraccion = fecha_extraccion;
        this.volumen_extraido = volumen_extraido;
        this.usuario_registro = usuario_registro;
        this.fecha_registro = fecha_registro;
        this.lote = lote;
    }

    public Centrifugado getCentrifugado() {
        return centrifugado;
    }

    public void setCentrifugado(Centrifugado centrifugado) {
        this.centrifugado = centrifugado;
    }

    public Liofilizacion getLiofilizacion() {
        return liofilizacion;
    }

    public void setLiofilizacion(Liofilizacion liofilizacion) {
        this.liofilizacion = liofilizacion;
    }

    
    
    
    public boolean isIsSerpiente() {
        return isSerpiente;
    }

    public void setIsSerpiente(boolean isSerpiente) {
        this.isSerpiente = isSerpiente;
    }

    public boolean isIsRegistro() {
        return isRegistro;
    }

    public void setIsRegistro(boolean isRegistro) {
        this.isRegistro = isRegistro;
    }

    public boolean isIsCentrifugado() {
        return isCentrifugado;
    }

    public void setIsCentrifugado(boolean isCentrifugado) {
        this.isCentrifugado = isCentrifugado;
    }

    public boolean isIsLiofilizacionInicio() {
        return isLiofilizacionInicio;
    }

    public void setIsLiofilizacionInicio(boolean isLiofilizacionInicio) {
        this.isLiofilizacionInicio = isLiofilizacionInicio;
    }

    public boolean isIsLiofilizacionFin() {
        return isLiofilizacionFin;
    }

    public void setIsLiofilizacionFin(boolean isLiofilizacionFinal) {
        this.isLiofilizacionFin = isLiofilizacionFinal;
    }
    
    public int getId_extraccion() {
        return id_extraccion;
    }

    public void setId_extraccion(int id_extraccion) {
        this.id_extraccion = id_extraccion;
    }

    public String getNumero_extraccion() {
        return numero_extraccion;
    }

    public void setNumero_extraccion(String numero_extraccion) {
        this.numero_extraccion = numero_extraccion;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public boolean isIngreso_cv() {
        return ingreso_cv;
    }

    public void setIngreso_cv(boolean ingreso_cv) {
        this.ingreso_cv = ingreso_cv;
    }

    public Date getFecha_extraccion() {
        return fecha_extraccion;
    }

    public String getFecha_extraccionAsString(){
        return formatearFecha(fecha_extraccion);
    }
    
    public String getFecha_registroAsString(){
        return formatearFecha(fecha_registro);
    }
    
    public void setFecha_extraccion(Date fecha_extraccion) {
        this.fecha_extraccion = fecha_extraccion;
    }

    public float getVolumen_extraido() {
        return volumen_extraido;
    }

    public void setVolumen_extraido(float volumen_extraido) {
        this.volumen_extraido = volumen_extraido;
    }

    public Usuario getUsuario_registro() {
        return usuario_registro;
    }

    public void setUsuario_registro(Usuario usuario_registro) {
        this.usuario_registro = usuario_registro;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
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
            JSON.put("id_usuario",this.usuario_registro.getId_usuario());
            JSON.put("id_especie",this.especie.getId_especie());
            JSON.put("id_lote",this.lote.getId_lote());
            
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
