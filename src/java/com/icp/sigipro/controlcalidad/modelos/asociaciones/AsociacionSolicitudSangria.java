/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import com.icp.sigipro.caballeriza.dao.SangriaDAO;
import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.controlcalidad.dao.ResultadoDAO;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public class AsociacionSolicitudSangria extends Asociacion {

    private Sangria sangria;
    private int dia;
    private final SolicitudCC solicitud;
    private final SangriaDAO sangria_dao = new SangriaDAO();
    private final ResultadoDAO resultado_dao = new ResultadoDAO();

    public AsociacionSolicitudSangria(SolicitudCC p_solicitud) {
        tipo = "sangria";
        tabla = "caballeriza.sangrias";
        solicitud = p_solicitud;
    }

    @Override
    public void asociar(HttpServletRequest request) {
        dia = Integer.parseInt(request.getParameter("dia"));
        int id_sangria = Integer.parseInt(request.getParameter("sangria"));
        sangria = new Sangria();
        sangria.setId_sangria(id_sangria);
    }

    @Override
    public void asociar(ResultSet rs) throws SQLException {
        dia = Integer.parseInt(rs.getString("informacion_referencia_adicional"));
        int id_sangria = rs.getInt("id_referenciado");
        sangria = new Sangria();
        sangria.setId_sangria(id_sangria);
    }

    @Override
    public void prepararEditarSolicitud(HttpServletRequest request) throws SIGIPROException {
        request.setAttribute("tipo", "sangria");
        request.setAttribute("id_sangria", sangria.getId_sangria());
        request.setAttribute("dia", dia); 
        request.setAttribute("sangrias", sangria_dao.obtenerSangriasLALPendiente());
    }

    @Override
    public void prepararGenerarInforme(HttpServletRequest request) throws SIGIPROException {
        request.setAttribute("tipo", "sangria");
        request.setAttribute("id_sangria", sangria.getId_sangria());
        request.setAttribute("dia", dia);
        request.setAttribute("sangrias", sangria_dao.obtenerSangriasLALPendiente());
        request.setAttribute("caballos_sangria", sangria_dao.obtenerCaballosSangriaDia(sangria.getId_sangria(), dia));
    }
    
    @Override 
    public void prepararEditarInforme(HttpServletRequest request) throws SIGIPROException {
        request.setAttribute("tipo", "sangria");
        request.setAttribute("id_sangria", sangria.getId_sangria());
        request.setAttribute("dia", dia);
        request.setAttribute("sangrias", sangria_dao.obtenerSangriasLALPendiente());
        request.setAttribute("caballos_sangria", sangria_dao.obtenerCaballosSangriaDia(sangria.getId_sangria(), dia));
        request.setAttribute("caballos_resultado", resultado_dao.obtenerCaballosResultado(sangria.getId_sangria(), dia));
    }

    @Override
    public List<PreparedStatement> insertarSQL(Connection conexion) throws SQLException {

        List<PreparedStatement> resultado = new ArrayList<>();

        PreparedStatement consulta = conexion.prepareStatement(
                " UPDATE control_calidad.solicitudes SET "
                + " tipo_referencia = ?, "
                + " tabla_referencia = ?, "
                + " id_referenciado = ?,"
                + " informacion_referencia_adicional = ?"
                + " where id_solicitud = ?; "
        );

        consulta.setString(1, tipo);
        consulta.setString(2, tabla);
        consulta.setInt(3, sangria.getId_sangria());
        consulta.setString(4, String.valueOf(dia));
        consulta.setInt(5, solicitud.getId_solicitud());
        consulta.addBatch();

        resultado.add(consulta);

        return resultado;

    }

    // <editor-fold defaultstate="collapsed" desc="Métodos Abstractos Inutilizados">
    @Override
    public void asociar(Resultado resultado, HttpServletRequest request) {

    }
    
    @Override
    public List<PreparedStatement> editarSQL(Connection conexion) throws SQLException {
        throw new SQLException("Operación no existe.");
    }

    // </editor-fold>
}
