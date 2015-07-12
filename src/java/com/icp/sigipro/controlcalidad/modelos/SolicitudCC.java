/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class SolicitudCC {

    private int id_solicitud;
    private String numero_solicitud;
    private Usuario usuario_solicitante;
    private Usuario usuario_recibido;
    private Date fecha_solicitud;
    private Date fecha_recibido;
    private String estado;

    private List<AnalisisGrupoSolicitud> analisis_solicitud;
    private ControlSolicitud control_solicitud;


    public SolicitudCC() {
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

    public String getNumero_solicitud() {
        return numero_solicitud;
    }

    public void setNumero_solicitud(String numero_solicitud) {
        this.numero_solicitud = numero_solicitud;
    }

    public Usuario getUsuario_solicitante() {
        return usuario_solicitante;
    }

    public void setUsuario_solicitante(Usuario usuario_solicitante) {
        this.usuario_solicitante = usuario_solicitante;
    }

    public Usuario getUsuario_recibido() {
        return usuario_recibido;
    }

    public void setUsuario_recibido(Usuario usuario_recibido) {
        this.usuario_recibido = usuario_recibido;
    }

    public Date getFecha_recibido() {
        return fecha_recibido;
    }

    public void setFecha_recibido(Date fecha_recibido) {
        this.fecha_recibido = fecha_recibido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_solicitudAsString() {
        return formatearFecha(fecha_solicitud);
    }

    public String getFecha_recibidoAsString() {
        return formatearFecha(fecha_recibido);
    }

    public List<AnalisisGrupoSolicitud> getAnalisis_solicitud() {
        return analisis_solicitud;
    }

    public void setAnalisis_solicitud(List<AnalisisGrupoSolicitud> analisis_solicitud) {
        this.analisis_solicitud = analisis_solicitud;
    }

    public ControlSolicitud getControl_solicitud() {
        return control_solicitud;
    }

    public void setControl_solicitud(ControlSolicitud control_solicitud) {
        this.control_solicitud = control_solicitud;
    }
    
    public List<Muestra> obtenerMuestras() {
        List<Muestra> lista_muestras = new ArrayList<Muestra>();
        List<Integer> ids_muestras = new ArrayList<Integer>();
        
        for (AnalisisGrupoSolicitud ags : this.analisis_solicitud) {
            for (Muestra m : ags.obtenerMuestras()) {
                if (!ids_muestras.contains(m.getId_muestra())) {
                    ids_muestras.add(m.getId_muestra());
                    lista_muestras.add(m);
                }
            }
        }
        
        return lista_muestras;
    }

    public String parseJSON() {
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try {
            Field properties[] = _class.getDeclaredFields();
            System.out.println(properties.length);
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0) {
                    if(!field.getName().equals("analisis_solicitud")){
                        JSON.put(field.getName(), field.get(this));
                    }
                } else {
                    JSON.put("id_objeto", field.get(this));
                }
            }
            JSON.put("id_usuario_solicitante", this.getUsuario_solicitante().getId_usuario());
            //JSON.put("id_usuario_recibido", this.getUsuario_recibido().getId_usuario());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toString();
    }

    private String formatearFecha(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }
}
