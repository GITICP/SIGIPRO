/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.icp.sigipro.controlcalidad.modelos.asociaciones.Asociable;
import com.icp.sigipro.controlcalidad.modelos.asociaciones.AsociacionSangria;
import com.icp.sigipro.controlcalidad.modelos.asociaciones.AsociacionSangriaPrueba;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
public class SolicitudCC extends Asociable {

    private int id_solicitud;
    private String numero_solicitud;
    private Usuario usuario_solicitante;
    private Usuario usuario_recibido;
    private Timestamp fecha_solicitud;
    private Timestamp fecha_recibido;
    private String estado;
    private String observaciones;
    private Informe informe;
    private Timestamp fecha_cierre;

    private List<AnalisisGrupoSolicitud> analisis_solicitud;
    private transient ControlSolicitud control_solicitud;

    public SolicitudCC() {
    }

    @Override
    public int getId() {
        return id_solicitud;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public Timestamp getFecha_solicitud() {
        return fecha_solicitud;
    }

    public void setFecha_solicitud(Timestamp fecha_solicitud) {
        this.fecha_solicitud = fecha_solicitud;
    }

    public Timestamp getFecha_recibido() {
        return fecha_recibido;
    }

    public void setFecha_recibido(Timestamp fecha_recibido) {
        this.fecha_recibido = fecha_recibido;
    }

    public Timestamp getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(Timestamp fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = "Sin observaciones.";
        if (observaciones != null) {
            if (!observaciones.isEmpty()) {
                this.observaciones = observaciones;
            }
        }
    }

    public Informe getInforme() {
        return informe;
    }

    public void setInforme(Informe informe) {
        this.informe = informe;
    }

    public String getNumero_solicitud() {
        return numero_solicitud;
    }

    public void setNumero_solicitud(String numero_solicitud) {
        this.numero_solicitud = numero_solicitud;
    }

    public Usuario getUsuario_solicitante() {
        return usuario_solicitante;
    }

    public void setUsuario_solicitante(Usuario usuario_solicitante) {
        this.usuario_solicitante = usuario_solicitante;
    }

    public Usuario getUsuario_recibido() {
        return usuario_recibido;
    }

    public void setUsuario_recibido(Usuario usuario_recibido) {
        this.usuario_recibido = usuario_recibido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_solicitudAsString() {
        return formatearFecha(fecha_solicitud);
    }

    public String getFecha_recibidoAsString() {
        return formatearFecha(fecha_recibido);
    }
    
    public String getFecha_cierreAsString() {
        return formatearFecha(fecha_cierre);
    }

    public List<AnalisisGrupoSolicitud> getAnalisis_solicitud() {
        return analisis_solicitud;
    }

    public List<Resultado> getResultados() {

        List<AnalisisGrupoSolicitud> ags_solicitud = new ArrayList<>();
        for (AnalisisGrupoSolicitud ags : analisis_solicitud) {
            if (ags.getResultados() != null) {
                ags_solicitud.add(ags);
            }
        }

        List<Resultado> resultados = new ArrayList<>();
        if (!ags_solicitud.isEmpty()) {
            for (AnalisisGrupoSolicitud ags : ags_solicitud) {
                resultados.addAll(ags.getResultados());
            }
        }
        return resultados;
    }

    public void setAnalisis_solicitud(List<AnalisisGrupoSolicitud> analisis_solicitud) {
        this.analisis_solicitud = analisis_solicitud;
    }

    public ControlSolicitud getControl_solicitud() {
        return control_solicitud;
    }

    public void setControl_solicitud(ControlSolicitud control_solicitud) {
        this.control_solicitud = control_solicitud;
    }

    public List<Muestra> obtenerMuestras() {
        List<Muestra> lista_muestras = new ArrayList<>();
        List<Integer> ids_muestras = new ArrayList<>();

        for (AnalisisGrupoSolicitud ags : this.analisis_solicitud) {
            for (Muestra m : ags.obtenerMuestras()) {
                if (!ids_muestras.contains(m.getId_muestra())) {
                    ids_muestras.add(m.getId_muestra());
                    lista_muestras.add(m);
                }
            }
        }

        return lista_muestras;
    }

    public void agregarResultadoAnalisisGrupoSolicitud(Resultado r) {
        for (AnalisisGrupoSolicitud ags : this.analisis_solicitud) {
            if (r.getAgs().getId_analisis_grupo_solicitud() == ags.getId_analisis_grupo_solicitud()) {
                r.setAgs(ags);
                ags.agregarResultado(r);
                break;
            }
        }
    }

    public void asociar(HttpServletRequest request) {
        if (asociacion != null) {
            asociacion.asociar(request);
        }
    }

    public void asociar(ResultSet rs) throws SQLException {
        if (asociacion != null) {
            asociacion.asociar(rs);
        }
    }
    
    public void prepararEditarSolicitud(HttpServletRequest request) throws SIGIPROException {
        if (asociacion != null) {
            asociacion.prepararEditarSolicitud(request);
        }
    }
    
    public void prepararGenerarInforme(HttpServletRequest request) throws SIGIPROException {
        if (asociacion != null) {
            asociacion.prepararGenerarInforme(request);
        }
    }
    
    public void prepararEditarInforme(HttpServletRequest request) throws SIGIPROException {
        if (asociacion != null) {
            asociacion.prepararEditarInforme(request);
        }
    }
    
    public List<PreparedStatement> obtenerConsultasEditarInforme(Connection conexion) throws SQLException {
        List<PreparedStatement> resultado = new ArrayList<>();
        if (asociacion != null) {
            resultado = asociacion.editarSQLInforme(conexion);
        }
        return resultado;
    }

    @Override
    public void setTipoAsociacion(String objeto) {

        switch (objeto) {
            case SANGRIA:
                asociacion = new AsociacionSangria(this);
                asociacion.setSolicitud(this);
                break;
            case SANGRIA_PRUEBA:
                asociacion = new AsociacionSangriaPrueba(this);
                asociacion.setSolicitud(this);
            default:
                break;
        }

    }
    
    @Override
    public boolean tieneTipoAsociacion() {
        return asociacion != null;
    }

    public List<PreparedStatement> obtenerConsultasInsertarAsociacion(Connection conexion) throws SQLException {
        List<PreparedStatement> resultado = new ArrayList<>();
        if (asociacion != null) {
            resultado = asociacion.insertarSQLSolicitud(conexion);
        }
        return resultado;
    }
    
    public List<PreparedStatement> resetear(Connection conexion) throws SQLException {
        List<PreparedStatement> resultado = new ArrayList<>();
        if (asociacion != null) {
            resultado = asociacion.resetear(conexion);
        }
        return resultado;
    }

    public String parseJSON() {
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try {
            Field properties[] = _class.getDeclaredFields();
            System.out.println(properties.length);
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0) {
                    if (!field.getName().equals("analisis_solicitud")) {
                        JSON.put(field.getName(), field.get(this));
                    }
                } else {
                    JSON.put("id_objeto", field.get(this));
                }
            }
            //JSON.put("id_usuario_solicitante", this.getUsuario_solicitante().getId_usuario());
            //JSON.put("id_usuario_recibido", this.getUsuario_recibido().getId_usuario());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toString();
    }

    private String formatearFecha(Timestamp fecha) {
        String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha);
        return date;
    }

    public List<PreparedStatement> obtenerConsultasInsertarAsociacionInforme(Connection conexion) throws SQLException {
        List<PreparedStatement> resultado = new ArrayList<>();
        if (asociacion != null) {
            resultado = asociacion.insertarSQLInforme(conexion);
        }
        return resultado;
    }
}
