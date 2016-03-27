/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.SQLXML;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Respuesta_pxp {

    private int id_historial;
    private int id_respuesta;
    private Paso paso;
    private Lote lote;
    private SQLXML respuesta;
    private Usuario usuario_verificar;
    private Usuario usuario_revisar;
    private Usuario usuario_realizar;
    private String respuestaString;
    private int version;
    //1-Deshabilitado,2-Terminado requiere aprobacion,3-Deshabilitado requiere aprobacion, 4-Habilitado, 5-Incompleto, 6-Revisado, 7-Verificado, 
    //8-Ultimo deshabilitado, 9-Ultimo habilitado, 10-Ultimo con aprobacion, habilitado, 11-Habilitado requiere aprobacion
    private int estado;

    private List<Respuesta_pxp> historial;

    public Respuesta_pxp() {
    }

    public int getVersion() {
        return version;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public String getRespuestaString() {
        return respuestaString;
    }

    public void setRespuestaString(String respuestaString) {
        this.respuestaString = respuestaString;
    }

    public Usuario getUsuario_verificar() {
        return usuario_verificar;
    }

    public void setUsuario_verificar(Usuario usuario) {
        this.usuario_verificar = usuario;
    }

    public Usuario getUsuario_revisar() {
        return usuario_revisar;
    }

    public void setUsuario_revisar(Usuario usuario_revisar) {
        this.usuario_revisar = usuario_revisar;
    }

    public Usuario getUsuario_realizar() {
        return usuario_realizar;
    }

    public void setUsuario_realizar(Usuario usuario_realizar) {
        this.usuario_realizar = usuario_realizar;
    }

    public int getId_respuesta() {
        return id_respuesta;
    }

    public void setId_respuesta(int id_respuesta) {
        this.id_respuesta = id_respuesta;
    }

    public Paso getPaso() {
        return paso;
    }

    public void setPaso(Paso paso) {
        this.paso = paso;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public SQLXML getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(SQLXML respuesta) {
        this.respuesta = respuesta;
    }

    public List<Respuesta_pxp> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Respuesta_pxp> historial) {
        this.historial = historial;
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
            JSON.put("id_usuario_verificar", this.getUsuario_verificar().getId_usuario());
            JSON.put("id_usuario_revisar", this.getUsuario_revisar().getId_usuario());
            JSON.put("id_usuario_realizar", this.getUsuario_realizar().getId_usuario());
            JSON.put("id_paso", this.getPaso().getId_paso());
            JSON.put("id_lote", this.getLote().getId_lote());

        } catch (Exception e) {

        }
        return JSON.toString();
    }
}
