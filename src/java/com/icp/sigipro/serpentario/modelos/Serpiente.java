/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Date;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Serpiente {
    private int id_serpiente;
    private Date fecha_ingreso;
    private String localidad_origen;
    private String colectada;
    private String recibida;
    private String sexo;
    private int talla_cabeza;
    private int talla_cola;
    private int peso;
    private Blob imagen;
    private Especie especie;

    public Serpiente() {
    }

    public Serpiente(int id_serpiente, Date fecha_ingreso, String localidad_origen, String colectada, String recibida, String sexo, int talla_cabeza, int talla_cola, int peso, Blob imagen, Especie especie) {
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

    public int getTalla_total(){
        return this.talla_cabeza+this.talla_cola;
    }
    
    public int getId_serpiente() {
        return id_serpiente;
    }

    public void setId_serpiente(int id_serpiente) {
        this.id_serpiente = id_serpiente;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
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

    public String getRecibida() {
        return recibida;
    }

    public void setRecibida(String recibida) {
        this.recibida = recibida;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getTalla_cabeza() {
        return talla_cabeza;
    }

    public void setTalla_cabeza(int talla_cabeza) {
        this.talla_cabeza = talla_cabeza;
    }

    public int getTalla_cola() {
        return talla_cola;
    }

    public void setTalla_cola(int talla_cola) {
        this.talla_cola = talla_cola;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
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
    
}
