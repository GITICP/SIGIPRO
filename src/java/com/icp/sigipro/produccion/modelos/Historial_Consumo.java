/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Josue
 */
public class Historial_Consumo {
    private int id_historial;
    private int id_veneno;
    private Date fecha;
    private int id_usuario;
    private int cantidad;
    private Veneno_Produccion veneno;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }
    
    public Veneno_Produccion getVeneno() {
        return veneno;
    }

    public void setVeneno(Veneno_Produccion veneno) {
        this.veneno = veneno;
    }

    public int getId_veneno() {
        return id_veneno;
    }

    public void setId_veneno(int id_veneno) {
        this.id_veneno = id_veneno;
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
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
