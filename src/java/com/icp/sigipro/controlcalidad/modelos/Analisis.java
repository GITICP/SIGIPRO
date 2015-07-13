/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.SQLXML;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class Analisis {

    private int id_analisis;
    private String nombre;
    private int cantidad_pendiente;
    //Debe ser de tipo XML pero se define luego
    private SQLXML estructura;
    private String estructuraString;
    //PATH de la ubicacion del archivo XSL
    private String machote;
    private Usuario encargado;

    private List<TipoEquipo> tipos_equipos_analisis;
    private List<TipoReactivo> tipos_reactivos_analisis;

    private boolean isTipoMuestra;

    public Analisis() {
    }

    public String getEstructuraString() {
        return estructuraString;
    }

    public boolean isIsTipoMuestra() {
        return isTipoMuestra;
    }

    public String getListaTiposReactivo() {
        String respuesta = "";
        if (tipos_reactivos_analisis != null) {
            for (TipoReactivo tr : tipos_reactivos_analisis) {
                respuesta += tr.getId_tipo_reactivo();
                respuesta += ",";
            }
            respuesta = respuesta.substring(0, respuesta.length() - 1);
        }
        return respuesta;
    }

    public String getListaTiposEquipo() {
        String respuesta = "";
        if (tipos_equipos_analisis != null) {
            for (TipoEquipo tr : tipos_equipos_analisis) {
                respuesta += tr.getId_tipo_equipo();
                respuesta += ",";
            }
            respuesta = respuesta.substring(0, respuesta.length() - 1);
        }
        return respuesta;
    }

    public void setIsTipoMuestra(boolean isTipoMuestra) {
        this.isTipoMuestra = isTipoMuestra;
    }

    public void setEstructuraString(String estructuraString) {
        this.estructuraString = estructuraString;
    }

    public int getCantidad_pendiente() {
        return cantidad_pendiente;
    }

    public void setCantidad_pendiente(int cantidad_pendiente) {
        this.cantidad_pendiente = cantidad_pendiente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_analisis() {
        return id_analisis;
    }

    public void setId_analisis(int id_analisis) {
        this.id_analisis = id_analisis;
    }

    public SQLXML getEstructura() {
        return estructura;
    }

    public void setEstructura(SQLXML estructura) {
        this.estructura = estructura;
    }

    public String getMachote() {
        return machote;
    }

    public void setMachote(String machote) {
        this.machote = machote;
    }

    public Usuario getEncargado() {
        return encargado;
    }

    public void setEncargado(Usuario encargado) {
        this.encargado = encargado;
    }

    public List<TipoEquipo> getTipos_equipos_analisis() {
        return tipos_equipos_analisis;
    }

    public void setTipos_equipos_analisis(List<TipoEquipo> tipos_equipos_analisis) {
        this.tipos_equipos_analisis = tipos_equipos_analisis;
    }

    public List<TipoReactivo> getTipos_reactivos_analisis() {
        return tipos_reactivos_analisis;
    }

    public void setTipos_reactivos_analisis(List<TipoReactivo> tipos_reactivos_analisis) {
        this.tipos_reactivos_analisis = tipos_reactivos_analisis;
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

    public int[] pasar_ids_tipos(String tipo) {
        int[] resultado = new int[0];

        switch (tipo) {
            case "reactivos": {
                int tamano = tipos_reactivos_analisis.size();
                resultado = new int[tamano];
                for (int i = 0; i < tamano; i++) {
                    resultado[i] = tipos_reactivos_analisis.get(i).getId_tipo_reactivo();
                }
                break;
            }
            case "equipos": {
                int tamano = tipos_equipos_analisis.size();
                resultado = new int[tamano];
                for (int i = 0; i < tamano; i++) {
                    resultado[i] = tipos_equipos_analisis.get(i).getId_tipo_equipo();
                }
                break;
            }
        }

        return resultado;
    }

    public boolean tiene_equipos() {
        if (tipos_equipos_analisis == null) {
            return false;
        } else {
            if (tipos_equipos_analisis.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean tiene_reactivos() {
        if (tipos_reactivos_analisis == null) {
            return false;
        } else {
            if (tipos_reactivos_analisis.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean tieneMachote() {
        boolean resultado = false;
        if (this.machote != null) {
            resultado = !this.machote.isEmpty();
        }
        return resultado;
    }
}
