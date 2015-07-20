/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class Informe
{
    private int id_informe;
    private List<Resultado> resultados;
    private SolicitudCC solicitud;
    private Usuario usuario;
    private Date fecha;
    
    public Informe(){}

    public int getId_informe() {
        return id_informe;
    }

    public void setId_informe(int id_informe) {
        this.id_informe = id_informe;
    }
    
    public List<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(List<Resultado> resultados) {
        this.resultados = resultados;
    }
    
    public void agregarResultado(Resultado resultado) {
        if (resultados == null) {
            resultados = new ArrayList<Resultado>();
        }
        resultados.add(resultado);
    }

    public SolicitudCC getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudCC solicitud) {
        this.solicitud = solicitud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
