/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;
import com.icp.sigipro.serpentario.modelos.Veneno;
import java.lang.reflect.Field;
import org.json.JSONObject;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Veneno_Produccion {
  
  private int id_veneno;
  private String veneno;
  private int cantidad;
  private Date fecha_ingreso;
  private String observaciones;
  private Veneno veneno_serpentario;

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
            JSON.put("id_veneno_serpentario",this.veneno_serpentario.getId_veneno());
        }catch (Exception e){
            
        }
        return JSON.toString();
    }

  
    public int getId_veneno() {
        return id_veneno;
    }

    public void setId_veneno(int id_veneno) {
        this.id_veneno = id_veneno;
    }

    public String getVeneno() {
        return veneno;
    }

    public void setVeneno(String veneno) {
        this.veneno = veneno;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    private String formatearFecha(Date fecha)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }  
    
    public String getFecha_ingreso_S(){
        if (this.fecha_ingreso != null)
            {return formatearFecha(this.fecha_ingreso);}
        else
            {return "";}
    }
    
    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Veneno getVeneno_serpentario() {
        return veneno_serpentario;
    }

    public void setVeneno_serpentario(Veneno veneno_serpentario) {
        this.veneno_serpentario = veneno_serpentario;
    }

}
