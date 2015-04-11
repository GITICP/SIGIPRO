/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class CatalogoTejido {
    private int id_catalogo_tejido; 
    private int numero_catalogo_tejido;
    private Serpiente serpiente;
    private String numero_caja;
    private String posicion;
    private String observaciones;
    private String estado;
    private Usuario usuario;

    public CatalogoTejido() {
    }

    public CatalogoTejido(int id_catalogo_tejido, Serpiente serpiente, String numero_caja, String posicion, String observaciones, String estado, Usuario usuario) {
        this.id_catalogo_tejido = id_catalogo_tejido;
        this.serpiente = serpiente;
        this.numero_caja = numero_caja;
        this.posicion = posicion;
        this.observaciones = observaciones;
        this.estado = estado;
        this.usuario = usuario;
    }

    public int getId_catalogo_tejido() {
        return id_catalogo_tejido;
    }

    public void setId_catalogo_tejido(int id_catalogo_tejido) {
        this.id_catalogo_tejido = id_catalogo_tejido;
    }

    public int getNumero_catalogo_tejido() {
        return numero_catalogo_tejido;
    }

    public void setNumero_catalogo_tejido(int numero_catalogo_tejido) {
        this.numero_catalogo_tejido = numero_catalogo_tejido;
    }

    public Serpiente getSerpiente() {
        return serpiente;
    }

    public void setSerpiente(Serpiente serpiente) {
        this.serpiente = serpiente;
    }

    public String getNumero_caja() {
        return numero_caja;
    }

    public void setNumero_caja(String numero_caja) {
        this.numero_caja = numero_caja;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
            JSON.put("id_serpiente",this.serpiente.getId_serpiente());
            JSON.put("id_usuario",this.usuario.getId_usuario());
                    
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
}
