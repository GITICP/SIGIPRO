/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class AnalisisGrupoSolicitud {

    private int id_analisis_grupo_solicitud;
    private Analisis analisis;
    private Grupo grupo;

    private String[] lista_analisis;

    public AnalisisGrupoSolicitud() {
    }

    public int getId_analisis_grupo_solicitud() {
        return id_analisis_grupo_solicitud;
    }

    public void setId_analisis_grupo_solicitud(int id_analisis_grupo_solicitud) {
        this.id_analisis_grupo_solicitud = id_analisis_grupo_solicitud;
    }

    public String[] getLista_analisis() {
        return lista_analisis;
    }

    public void setLista_analisis(String[] analisis) {
        this.lista_analisis = analisis;
    }

    public Analisis getAnalisis() {
        return analisis;
    }

    public void setAnalisis(Analisis analisis) {
        this.analisis = analisis;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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
            JSON.put("id_analisis", this.getAnalisis().getId_analisis());
            JSON.put("id_grupo", this.getGrupo().getId_grupo());

        } catch (Exception e) {

        }
        return JSON.toString();
    }

}
