/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Resultado
{

    private int id_resultado;
    private String path;
    //Debe ser de tipo XML pero por ahora lo dejo asi
    private SQLXML datos;
    private String datos_string;
    private Date fecha;
    private Usuario usuario;
    private int repeticion;

    private List<Reactivo> reactivos_resultado;
    private List<Equipo> equipos_resultado;
    private AnalisisGrupoSolicitud ags;

    public Resultado() {
    }

    public int getId_resultado() {
        return id_resultado;
    }

    public void setId_resultado(int id_resultado) {
        this.id_resultado = id_resultado;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public SQLXML getDatos() {
        return datos;
    }

    public void setDatos(SQLXML datos) {
        this.datos = datos;
    }

    public String getDatos_string() {
        return datos_string;
    }

    public void setDatos_string(String datos_string) {
        this.datos_string = datos_string;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }

    public List<Reactivo> getReactivos_resultado() {
        return reactivos_resultado;
    }

    public void setReactivos_resultado(List<Reactivo> reactivos_resultado) {
        this.reactivos_resultado = reactivos_resultado;
    }

    public List<Equipo> getEquipos_resultado() {
        return equipos_resultado;
    }

    public void setEquipos_resultado(List<Equipo> equipos_resultado) {
        this.equipos_resultado = equipos_resultado;
    }

    public AnalisisGrupoSolicitud getAgs() {
        return ags;
    }

    public void setAgs(AnalisisGrupoSolicitud ags) {
        this.ags = ags;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public void setEquipos(String[] ids) {

        this.equipos_resultado = new ArrayList<Equipo>();

        for (String id : ids) {
            Equipo equipo = new Equipo();
            equipo.setId_equipo(Integer.parseInt(id));
            this.equipos_resultado.add(equipo);
        }
    }

    public void setReactivos(String[] ids) {

        this.reactivos_resultado = new ArrayList<Reactivo>();

        for (String id : ids) {
            Reactivo reactivo = new Reactivo();
            reactivo.setId_reactivo(Integer.parseInt(id));
            this.reactivos_resultado.add(reactivo);
        }
    }

    public boolean tieneEquipos() {
        boolean resultado = false;
        if (this.equipos_resultado != null) {
            resultado = !this.equipos_resultado.isEmpty();
        }
        return resultado;
    }

    public boolean tieneReactivos() {
        boolean resultado = false;
        if (this.reactivos_resultado != null) {
            resultado = !this.reactivos_resultado.isEmpty();
        }
        return resultado;
    }
}
