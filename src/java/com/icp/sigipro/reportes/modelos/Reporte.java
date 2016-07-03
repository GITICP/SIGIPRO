/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import com.icp.sigipro.core.IModelo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Boga
 */
public class Reporte extends IModelo {

    private int id_reporte;
    private String nombre;
    private String descripcion;
    private String consulta;
    private List<Parametro> parametros;
    private String url_js;

    private int objetos_multiples = 0;

    public Reporte() {
    }

    public int getId_reporte() {
        return id_reporte;
    }

    public void setId_reporte(int id_reporte) {
        this.id_reporte = id_reporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }

    public void agregarParametro(Parametro parametro, boolean primera_vez) {
        if (parametros == null) {
            this.parametros = new ArrayList<>();
        }
        if (primera_vez) {
            modificarParametroPrimeraVez(parametro);
        } else {
            if (parametro.getTipo().equals("objeto_multiple")) {
                this.objetos_multiples++;
            }
        }
        this.parametros.add(parametro);
    }

    private void modificarParametroPrimeraVez(Parametro parametro) {
        if (parametro.getTipo().equals("objeto_multiple")) {
            this.objetos_multiples++;
            int nuevo_id_param = parametro.getNumero() + 100;
            modificarStringConsulta(nuevo_id_param, parametro.getNumero());
            parametro.setNumero(nuevo_id_param);
        } else {
            parametro.setNumero(parametro.getNumero() - this.objetos_multiples);
        }
    }

    public String getUrl_js() {
        return this.url_js;
    }

    public void setUrl_js(String url_js) {
        this.url_js = url_js;
    }
    
    public boolean tieneJS() {
        boolean resultado = false;
        if (this.url_js != null) {
            if (!this.url_js.isEmpty()) {
                resultado = true;
            }
        }
        return resultado;
    }

    public void modificarStringConsulta(int numero_reemplazo, int num_parametro) {
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("\\?");
        Matcher m = p.matcher(this.consulta);
        int contador = 1;
        while (m.find()) {
            if (contador == num_parametro) {
                m.appendReplacement(sb, "_" + numero_reemplazo + "_");
                break;
            }
            contador++;
        }
        m.appendTail(sb);
        this.consulta = sb.toString();
    }

    public String getStringConsulta() {
        String resultado = this.consulta;
        if (this.objetos_multiples > 0) {
            for (Parametro p : this.parametros) {
                if (p.getTipo().equals("objeto_multiple")) {
                    ObjetoMultiple ob_m = (ObjetoMultiple) p;
                    resultado = this.consulta.replace("_" + p.getNumero() + "_", ob_m.getIdsString());
                }
            }
        }
        return resultado;
    }

    public void prepararConsulta(PreparedStatement consulta) throws SQLException {
        for (Parametro p : this.parametros) {
            p.agregarAConsulta(consulta);
        }
    }

}
