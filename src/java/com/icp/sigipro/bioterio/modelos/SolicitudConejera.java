/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class SolicitudConejera {

    private int id_solicitud;
    private Date fecha_solicitud;
    private Date fecha_necesita;
    private int numero_animales;
    private String peso_requerido;
    private String sexo;
    private Usuario usuario_solicitante;
    private Usuario usuario_utiliza;
    private String observaciones;
    private String observaciones_rechazo;
    private String estado;
    private EntregaConejera entrega;

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
            JSON.put("usuario_solicitante", this.usuario_solicitante.getID());
            JSON.put("usuario_utiliza", this.usuario_utiliza.getID());
        } catch (Exception e) {

        }
        return JSON.toString();
    }

    private String formatearFecha(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }

    public Usuario getUsuario_utiliza() {
        return usuario_utiliza;
    }

    public void setUsuario_utiliza(Usuario usuario_utiliza) {
        this.usuario_utiliza = usuario_utiliza;
    }

    public String getFecha_necesita_S() {
        if (this.fecha_necesita != null) {
            return formatearFecha(fecha_necesita);
        } else {
            return "";
        }
    }

    public Date getFecha_necesita() {
        return fecha_necesita;
    }

    public void setFecha_necesita(Date fecha_necesita) {
        this.fecha_necesita = fecha_necesita;
    }

    public String getFecha_solicitud_S() {
        if (this.fecha_solicitud != null) {
            return formatearFecha(fecha_solicitud);
        } else {
            return "";
        }
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public Date getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Date fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public int getNumero_animales() {
        return numero_animales;
    }

    public void setNumero_animales(int numero_animales) {
        this.numero_animales = numero_animales;
    }

    public String getPeso_requerido() {
        return peso_requerido;
    }

    public void setPeso_requerido(String peso_requerido) {
        this.peso_requerido = peso_requerido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Usuario getUsuario_solicitante() {
        return usuario_solicitante;
    }

    public void setUsuario_solicitante(Usuario usuario_solicitante) {
        this.usuario_solicitante = usuario_solicitante;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones_rechazo() {
        return observaciones_rechazo;
    }

    public void setObservaciones_rechazo(String observaciones_rechazo) {
        this.observaciones_rechazo = observaciones_rechazo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EntregaConejera getEntrega() {
        return entrega;
    }

    public void setEntrega(EntregaConejera entrega) {
        this.entrega = entrega;
    }

}
