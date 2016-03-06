/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import com.icp.sigipro.controlcalidad.modelos.Informe;
import com.icp.sigipro.core.IModelo;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author Walter
 */
public class SangriaPrueba extends IModelo {

    private int id_sangria_prueba;
    private Usuario usuario;
    private Date fecha;
    private List<SangriaPruebaCaballo> lista_sangrias_prueba_caballo;
    private Informe informe;

    public SangriaPrueba() {
    }

    public int getId_sangria_prueba() {
        return id_sangria_prueba;
    }

    public void setId_sangria_prueba(int id_sangria_prueba) {
        this.id_sangria_prueba = id_sangria_prueba;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<SangriaPruebaCaballo> getLista_sangrias_prueba_caballo() {
        return lista_sangrias_prueba_caballo;
    }

    public void setLista_sangrias_prueba_caballo(List<SangriaPruebaCaballo> lista_sangrias_prueba_caballo) {
        this.lista_sangrias_prueba_caballo = lista_sangrias_prueba_caballo;
    }

    public Date getFecha() {
        return fecha;
    }
    
    public String getFechaAsString() {
        HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
        return helper_fechas.formatearFecha(fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Informe getInforme() {
        return informe;
    }

    public void setInforme(Informe informe) {
        this.informe = informe;
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
            JSON.put("id_resonsable", this.usuario.getId_usuario());

        } catch (Exception e) {

        }
        return JSON.toString();
    }

}
