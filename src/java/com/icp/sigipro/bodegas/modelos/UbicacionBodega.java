package com.icp.sigipro.bodegas.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class UbicacionBodega 
{
    private int id_ubicacion;
    private String nombre;
    private String descripcion;

    public UbicacionBodega() {
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
        }catch (Exception e){
            
        }
        return JSON.toString();
    }

    
    public UbicacionBodega(int id_ubicacion, String nombre, String descripcion) {
        this.id_ubicacion = id_ubicacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
}
