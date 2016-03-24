/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.SQLXML;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Respuesta_AA {

    private int id_respuesta;
    private int id_historial;
    private String nombre;
    private Actividad_Apoyo actividad;
    private int version;
    private Timestamp fecha;
    private Usuario usuario_realizar;
    private String respuestaString;
    private SQLXML respuesta;
    //1. Pendiente, no revision
    //2. Pendiente, requiere revision
    //3. Revisado, requiere aprobacion
    //4. Finalizado
    private int estado;
    
    private List<Respuesta_AA> historial;

    public Respuesta_AA() {
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public List<Respuesta_AA> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Respuesta_AA> historial) {
        this.historial = historial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getId_respuesta() {
        return id_respuesta;
    }

    public void setId_respuesta(int id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public Actividad_Apoyo getActividad() {
        return actividad;
    }

    public void setActividad(Actividad_Apoyo actividad) {
        this.actividad = actividad;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Usuario getUsuario_realizar() {
        return usuario_realizar;
    }

    public void setUsuario_realizar(Usuario usuario_realizar) {
        this.usuario_realizar = usuario_realizar;
    }

    public String getRespuestaString() {
        return respuestaString;
    }

    public void setRespuestaString(String respuestaString) {
        this.respuestaString = respuestaString;
    }

    public SQLXML getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(SQLXML respuesta) {
        this.respuesta = respuesta;
    }
    
    public String getFechaAsString() {
        return formatearFecha(fecha);
    }
    
    private String formatearFecha(Timestamp fecha) {
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha);
        return date;
    }

    public String parseJSON() {
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try {
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0) {
                    JSON.put(field.getName(), field.get(this));
                } else {
                    JSON.put("id_objeto", field.get(this));
                }
            }

        } catch (Exception e) {

        }
        return JSON.toString();
    }
    
}
