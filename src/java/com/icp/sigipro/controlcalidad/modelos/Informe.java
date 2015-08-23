/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.icp.sigipro.controlcalidad.modelos.asociaciones.AsociacionLALSangria;
import com.icp.sigipro.controlcalidad.modelos.asociaciones.Asociacion;
import com.icp.sigipro.controlcalidad.modelos.asociaciones.Asociable;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public class Informe extends Asociable
{
    private int id_informe;
    private List<Resultado> resultados;
    private SolicitudCC solicitud;
    private Usuario usuario;
    private Date fecha;
    private Asociacion asociacion;
    
    public Informe(){}

    @Override
    public int getId() {
        return id_informe;
    }
    
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
    
    public void agregarResultado(Resultado resultado, HttpServletRequest request) {
        if (resultados == null) {
            resultados = new ArrayList<Resultado>();
        }
        resultados.add(resultado);
        if (asociacion != null) {
            asociacion.asociar(resultado, request);
        }
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
    
    public String getFechaAsString() {
        String resultado = "Sin fecha";
        if (fecha != null) {
            HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
            resultado = helper_fechas.formatearFecha(fecha);
        }
        return resultado;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public void setTipoAsociacion(String objeto) {
        
        switch (objeto) {
            case SANGRIA:
                asociacion = new AsociacionLALSangria();
                asociacion.setAsociable(this);
                break;
            default:
                break;
        }
    }
    
    @Override
    public boolean tieneTipoAsociacion() {
        return asociacion != null;
    }
    
    public boolean tieneResultado(int id_resultado) {
        boolean resultado = false;
        if (resultados != null) {
            for (Resultado r : resultados) {
                if (r.getId_resultado() == id_resultado) {
                    resultado = true;
                    break;
                }
            }
        }
        return resultado;
    }
    
    public List<PreparedStatement> obtenerConsultasInsertarAsociacion(Connection conexion) throws SQLException {
        List<PreparedStatement> resultado = new ArrayList<PreparedStatement>();
        if (asociacion != null) {
            resultado = asociacion.insertarSQL(conexion);
        }
        return resultado;
    }
    
    public List<PreparedStatement> obtenerConsultasEditarAsociacion(Connection conexion) throws SQLException {
        
        // Obtener asociación de solicitud
        // Actualizar asociación de solicitud a nulo
        
        List<PreparedStatement> resultado = new ArrayList<PreparedStatement>();
        if (asociacion != null) {
            resultado = asociacion.editarSQL(conexion);
        }
        return resultado;
    }
}
