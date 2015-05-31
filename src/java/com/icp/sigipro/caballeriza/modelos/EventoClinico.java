/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.postgresql.util.Base64;

/**
 *
 * @author Walter
 */
public class EventoClinico {

    private int id_evento;
    private Date fecha;
    private String descripcion;
    private Usuario responsable;
    private String observaciones;
    private TipoEvento tipo_evento;
    private List<Caballo> caballos;
    private List<GrupoDeCaballos> grupos_involucrados;
    private long imagen_tamano;
    private byte[] imagen;
    private boolean accion;
    private List<String> id_caballos;

    public EventoClinico() {
    }

    public EventoClinico(int id_evento, Date fecha, String descripcion, Usuario responsable, TipoEvento tipo_evento) {
        this.id_evento = id_evento;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.responsable = responsable;
        this.tipo_evento = tipo_evento;
    }

    public List<String> getId_caballos() {
        return id_caballos;
    }

    public void setId_caballos(List<String> id_caballos) {
        this.id_caballos = id_caballos;
    }

    public boolean isAccion() {
        return accion;
    }

    public void setAccion(boolean accion) {
        this.accion = accion;
    }

    public long getImagen_tamano() {
        return imagen_tamano;
    }

    public void setImagen_tamano(long imagen_tamano) {
        this.imagen_tamano = imagen_tamano;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagen_ver() {
        try {
            return "data:image/jpeg;base64," + Base64.encodeBytes(this.getImagen());
        } catch (Exception ex) {
            return "";
        }
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getFechaAsString() {
        return formatearFecha(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public TipoEvento getTipo_evento() {
        return tipo_evento;
    }

    public void setTipo_evento(TipoEvento tipo_evento) {
        this.tipo_evento = tipo_evento;
    }

    public List<Caballo> getCaballos() {
        return caballos;
    }

    public void setCaballos(List<Caballo> caballos) {
        this.caballos = caballos;
    }

    public void agregarCaballo(Caballo c) {
        if (caballos == null) {
            caballos = new ArrayList<Caballo>();
        }
        caballos.add(c);
    }

    public List<GrupoDeCaballos> getGrupos_involucrados() {
        return grupos_involucrados;
    }

    public String getGrupos_involucradosAsString() {
        String resultado = "Sin grupos asociados.";
        if (grupos_involucrados != null) {
            resultado = "";
            for (GrupoDeCaballos g : grupos_involucrados) {
                resultado = resultado + g.getNombre() + ", ";
            }
            resultado = resultado.substring(0, resultado.length() - 2);
        }
        return resultado;
    }

    public void setGrupos_involucrados(List<GrupoDeCaballos> grupos_involucrados) {
        this.grupos_involucrados = grupos_involucrados;
    }

    public void agregarGrupo(GrupoDeCaballos g) {
        if (grupos_involucrados == null) {
            grupos_involucrados = new ArrayList<GrupoDeCaballos>();
        }
        grupos_involucrados.add(g);
    }

    public boolean valididarCaballoEnEvento(Caballo c) {
        boolean resultado = false;
        if (caballos != null) {
            for (Caballo caballo : caballos) {
                if (caballo.getId_caballo() == c.getId_caballo()) {
                    resultado = true;
                    break;
                }
            }
        }
        return resultado;
    }

//Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
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
            JSON.put("id_tipo_evento", this.tipo_evento.getId_tipo_evento());

        } catch (Exception e) {

        }
        return JSON.toString();
    }

    private String formatearFecha(Date fecha) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(fecha);
    }
}
