/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class AnalisisGrupoSolicitud {

    private int id_analisis_grupo_solicitud;
    private Analisis analisis;
    private Grupo grupo;
    private List<Resultado> resultados;

    private String[] lista_analisis;

    public AnalisisGrupoSolicitud() {
    }
    
    public String getListadoIdentificadores(boolean con_tipo) {
        String resultado = "";
        if (grupo != null) {
            List<Muestra> grupos = grupo.getGrupos_muestras();
            if (grupos != null) {
                String tipo = grupo.getGrupos_muestras().get(0).getTipo_muestra().getNombre();
                
                int i = 0;
                
                while(i < grupos.size() && i < 2) {
                    resultado += grupos.get(i).getIdentificador() + ", ";
                    i++;
                }
                    
                resultado = resultado.substring(0, resultado.length() - 2);
                if (grupo.getGrupos_muestras().size() >= 3) {
                    resultado += ",..., ";
                }
                if (con_tipo) {
                    resultado += " del tipo " + tipo;
                }
            }
        }
        return resultado;
    }
    
    public String getListadoIdentificadoresCompleto(boolean con_tipo) {
        String resultado = "";
        if (grupo != null) {
            List<Muestra> grupos = grupo.getGrupos_muestras();
            if (grupos != null) {
                String tipo = grupo.getGrupos_muestras().get(0).getTipo_muestra().getNombre();
                
                int i = 0;
                
                while(i < grupos.size()) {
                    resultado += grupos.get(i).getIdentificador() + ", ";
                    i++;
                }
                    
                resultado = resultado.substring(0, resultado.length() - 2);
                if (con_tipo) {
                    resultado += " del tipo " + tipo;
                }
            }
        }
        return resultado;
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

    public List<Resultado> getResultados() {
        return resultados;
    }
    
    public Resultado getPrimerResultado() {
        return resultados.get(0);
    }
    
    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }
    
    public void agregarResultado(Resultado r) {
        if (resultados == null) {
            resultados = new ArrayList<>();
        }
        resultados.add(r);
    }
    
    public List<Muestra> obtenerMuestras() {
        return this.grupo.getGrupos_muestras();
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
