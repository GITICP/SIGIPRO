/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Serpiente {
    private int id_serpiente;
    private int numero_serpiente;
    private Date fecha_ingreso;
    private String localidad_origen;
    private String colectada;
    private Usuario recibida;
    private String sexo;
    private float talla_cabeza;
    private float talla_cola;
    private float peso;
    private byte[] imagen;
    private long imagenTamano;
    private Especie especie;
    private boolean estado;
    private boolean coleccionviva;

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isColeccionviva() {
        return coleccionviva;
    }

    public void setColeccionviva(boolean coleccionviva) {
        this.coleccionviva = coleccionviva;
    }

    

    public long getImagenTamano() {
        return imagenTamano;
    }

    public void setImagenTamano(long imagenTamano) {
        this.imagenTamano = imagenTamano;
    }

    public Serpiente() {
    }

    public Serpiente(int id_serpiente, Date fecha_ingreso, String localidad_origen, String colectada, Usuario recibida, String sexo, float talla_cabeza, float talla_cola, float peso, byte[] imagen, Especie especie) {
        this.id_serpiente = id_serpiente;
        this.fecha_ingreso = fecha_ingreso;
        this.localidad_origen = localidad_origen;
        this.colectada = colectada;
        this.recibida = recibida;
        this.sexo = sexo;
        this.talla_cabeza = talla_cabeza;
        this.talla_cola = talla_cola;
        this.peso = peso;
        this.imagen = imagen;
        this.especie = especie;
    }

    public int getDias_cautiverio(){
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        java.util.Date fecha_hoy = helper.getFecha_hoy_asDate();
        Date date_ingreso = this.fecha_ingreso;
        long diferencia = 0;
        long dias = 0;
        
        try{
            SingletonBD parser = SingletonBD.getSingletonBD();
            
            Date date_hoy = new Date(fecha_hoy.getTime());
                        
            diferencia = date_hoy.getTime() - date_ingreso.getTime();
            dias = diferencia / (1000 * 60 * 60 * 24);
        }catch (Exception e){
            System.out.println("Error "+ e);
        }
        return (int)dias;
    }
    
    public int getDias_cautiverio(Date fecha_deceso){
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        Date date_ingreso = this.fecha_ingreso;
        long diferencia = 0;
        long dias = 0;
        
        try{
            SingletonBD parser = SingletonBD.getSingletonBD();
                                    
            diferencia = fecha_deceso.getTime() - date_ingreso.getTime();
            dias = diferencia / (1000 * 60 * 60 * 24);
        }catch (Exception e){
            System.out.println("Error "+ e);
        }
        return (int)dias;
    }
    
    public float getTalla_total(){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        float talla_total = this.talla_cabeza+this.talla_cola;
        
        return talla_total;
    }
    
    public int getId_serpiente() {
        return id_serpiente;
    }

    public void setId_serpiente(int id_serpiente) {
        this.id_serpiente = id_serpiente;
    }

    public int getNumero_serpiente() {
        return numero_serpiente;
    }

    public void setNumero_serpiente(int numero_serpiente) {
        this.numero_serpiente = numero_serpiente;
    }
    
    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }
    
    public String getFecha_ingresoAsString() {
        return formatearFecha(fecha_ingreso);
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getLocalidad_origen() {
        return localidad_origen;
    }

    public void setLocalidad_origen(String localidad_origen) {
        this.localidad_origen = localidad_origen;
    }

    public String getColectada() {
        return colectada;
    }

    public void setColectada(String colectada) {
        this.colectada = colectada;
    }

    public Usuario getRecibida() {
        return recibida;
    }

    public void setRecibida(Usuario recibida) {
        this.recibida = recibida;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public float getTalla_cabeza() {
        return talla_cabeza;
    }

    public void setTalla_cabeza(float talla_cabeza) {
        this.talla_cabeza = talla_cabeza;
    }

    public float getTalla_cola() {
        return talla_cola;
    }

    public void setTalla_cola(float talla_cola) {
        this.talla_cola = talla_cola;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
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
            JSON.put("id_especie",this.especie.getId_especie());
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
