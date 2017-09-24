/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.google.gson.Gson;
import com.icp.sigipro.core.IModelo;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Date;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class Resultado extends IModelo
{

    private int id_resultado;
    private String path;
    private transient SQLXML datos;
    private String datos_string;
    private Date fecha;
    private Date fecha_reportado;
    private Usuario usuario;
    private int repeticion;
    private String resultado;

    private List<Reactivo> reactivos_resultado;
    private List<Equipo> equipos_resultado;
    private List<Patron> controles_resultado;
    private List<Patron> patrones_resultado;
    
    // Evitar error al parsear a JSON con la libraría Gson
    private transient AnalisisGrupoSolicitud ags;

    public Resultado() {
    }
    
    public String getTipo() {
        return "Normal";
    }

    public int getId_resultado() {
        return id_resultado;
    }
    
    public void setId_resultado(int id_resultado) {
        this.id_resultado = id_resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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
        Gson gson = new Gson();
        return gson.toJson(this);
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
        boolean resultado_func = false;
        if (this.equipos_resultado != null) {
            resultado_func = !this.equipos_resultado.isEmpty();
        }
        return resultado_func;
    }

    public boolean tieneReactivos() {
        boolean resultado_func = false;
        if (this.reactivos_resultado != null) {
            resultado_func = !this.reactivos_resultado.isEmpty();
        }
        return resultado_func;
    }
    
    public boolean tieneReactivo(Reactivo reactivo) {
        boolean resultado_func = false;
        if (this.reactivos_resultado != null) {
            for (Reactivo r : this.reactivos_resultado) {
                if (r.getId_reactivo() == reactivo.getId_reactivo()) {
                    resultado_func = true;
                    break;
                }
            }
        }
        return resultado_func;
    }
    
    public boolean tieneEquipo(Equipo equipo) {
        boolean resultado_func = false;
        if (this.equipos_resultado != null) {
            for (Equipo e : this.equipos_resultado) {
                if (e.getId_equipo() == equipo.getId_equipo()) {
                    resultado_func = true;
                    break;
                }
            }
        }
        return resultado_func;
    }

    public List<Patron> getControles_resultado() {
        return controles_resultado;
    }

    public void setControles_resultado(List<Patron> controles_resultado) {
        this.controles_resultado = controles_resultado;
    }

    public List<Patron> getPatrones_resultado() {
        return patrones_resultado;
    }

    public void setPatrones_resultado(List<Patron> patrones_resultado) {
        this.patrones_resultado = patrones_resultado;
    }
    
    public void setControles(String[] ids) {

        this.controles_resultado = new ArrayList<>();

        for (String id : ids) {
            Patron patron = new Patron();
            patron.setId_patron(Integer.parseInt(id));
            this.controles_resultado.add(patron);
        }
    }
    
    public void setPatrones(String[] ids) {

        this.patrones_resultado = new ArrayList<>();

        for (String id : ids) {
            Patron patron = new Patron();
            patron.setId_patron(Integer.parseInt(id));
            this.patrones_resultado.add(patron);
        }
    }
    
    public boolean tienePatronesOControles() {
        boolean resultado_func = false;
        if (this.patrones_resultado != null) {
            resultado_func = !this.patrones_resultado.isEmpty();
        }
        if (this.controles_resultado != null) {
            resultado_func = !this.controles_resultado.isEmpty() || resultado_func;
        }
        return resultado_func;
    }
    
    public boolean tienePatron(Patron p) {
        boolean resultado_func = false;
        if (this.patrones_resultado != null) {
            resultado_func = tienePatronEnLista(patrones_resultado, p.getId_patron());
        }
        return resultado_func;
    }
    
    public boolean tieneControl(Patron p) {
        boolean resultado_func = false;
        if (this.controles_resultado != null) {
            resultado_func = tienePatronEnLista(controles_resultado, p.getId_patron());
        }
        return resultado_func;
    }
    
    private boolean tienePatronEnLista(List<Patron> lista, int id_patron) {
        boolean resultado_func = false;
        for (Patron p : lista) {
            if (p.getId_patron() == id_patron) {
                resultado_func = true;
                break;
            }
        }
        return resultado_func;
    }
    
    public void agregarPatron(Patron p) {
        if (!p.getTipo().equalsIgnoreCase("Control Interno")) {
            if(patrones_resultado == null) {
                patrones_resultado = new ArrayList<>();
            }
            patrones_resultado.add(p);
        } else {
            if (controles_resultado == null) {
                controles_resultado = new ArrayList<>();
            }
            controles_resultado.add(p);
        }
    }

    public Date getFecha_reportado() {
        return fecha_reportado;
    }
    
    public String getFecha_reportado_formateada() {
        return helper_fechas.formatearFecha(fecha_reportado);
    }
    
    public void setFecha_reportado(Date fecha_reportado) {
        this.fecha_reportado = fecha_reportado;
    }
    
    
}
