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
public class Inoculo {
  private int id_inoculo;
  private String identificador;
  private Date fecha_preparacion;
  private Usuario encargado_preparacion;

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
            JSON.put("id_encargado_preparacion",this.encargado_preparacion.getId_usuario());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
  
    public int getId_inoculo() {
        return id_inoculo;
    }

    public void setId_inoculo(int id_inoculo) {
        this.id_inoculo = id_inoculo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
    
    public Date getFecha_preparacion() {
        return fecha_preparacion;
    }
    
    public String getFecha_preparacion_S(){
        if (this.fecha_preparacion != null)
            {return formatearFecha(this.fecha_preparacion);}
        else
            {return "";}
    }
    
    public void setFecha_preparacion(Date fecha_preparacion) {
        this.fecha_preparacion = fecha_preparacion;
    }

    public Usuario getEncargado_preparacion() {
        return encargado_preparacion;
    }

    public void setEncargado_preparacion(Usuario encargado_preparacion) {
        this.encargado_preparacion = encargado_preparacion;
    }

  
  
 
}
