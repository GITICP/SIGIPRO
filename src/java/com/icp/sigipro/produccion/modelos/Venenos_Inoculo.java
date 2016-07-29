/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import org.json.JSONObject;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Venenos_Inoculo {
  private Inoculo inoculo;
  private Veneno_Produccion veneno;
  private int cantidad;

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
            JSON.put("id_inoculo",this.inoculo.getId_inoculo());
            JSON.put("id_veneno",this.veneno.getId_veneno());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }

    public Inoculo getInoculo() {
        return inoculo;
    }

    public void setInoculo(Inoculo inoculo) {
        this.inoculo = inoculo;
    }

    public Veneno_Produccion getVeneno() {
        return veneno;
    }

    public void setVeneno(Veneno_Produccion veneno) {
        this.veneno = veneno;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
 
}
