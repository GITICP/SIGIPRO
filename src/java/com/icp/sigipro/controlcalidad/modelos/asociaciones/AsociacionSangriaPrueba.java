/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import com.icp.sigipro.caballeriza.dao.SangriaPruebaDAO;
import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
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
public class AsociacionSangriaPrueba extends AsociacionSolicitud {
    
    private SangriaPrueba sangria_prueba;
    private final SangriaPruebaDAO sangria_prueba_dao = new SangriaPruebaDAO();
    private final AsociacionHemaHemoSangriaPrueba asociacion_informe = new AsociacionHemaHemoSangriaPrueba(this);
    
    public AsociacionSangriaPrueba(SolicitudCC p_solicitud) {
        tipo = "sangria_prueba";
        tabla = "caballeriza.sangrias_prueba";
        solicitud = p_solicitud;
    }

    @Override
    public void asociar(HttpServletRequest request) {
        String id_sangria_prueba_str = request.getParameter("sangria_prueba");
        int id_sangria_prueba = Integer.parseInt(id_sangria_prueba_str);
        sangria_prueba = new SangriaPrueba();
        sangria_prueba.setId_sangria_prueba(id_sangria_prueba);
    }

    @Override
    public void asociar(ResultSet rs) throws SQLException {
        int id_sangria_prueba = rs.getInt("id_referenciado");
        sangria_prueba = new SangriaPrueba();
        sangria_prueba.setId_sangria_prueba(id_sangria_prueba);
    }

    @Override
    public void asociarResultado(Resultado resultado, HttpServletRequest request) {
        asociacion_informe.asociar(resultado, request);
    }

    @Override
    public void prepararEditarSolicitud(HttpServletRequest request) throws SIGIPROException {
        request.setAttribute("tipo", "sangria_prueba");
        request.setAttribute("id_sangria", sangria_prueba.getId_sangria_prueba());
        request.setAttribute("sangrias_prueba", sangria_prueba_dao.obtenerSangriasPruebaPendiente());
    }

    @Override
    public void prepararGenerarInforme(HttpServletRequest request) throws SIGIPROException {
        request.setAttribute("tipo", "sangria_prueba");
        request.setAttribute("id_sangria_prueba", sangria_prueba.getId_sangria_prueba());
        request.setAttribute("sangria_prueba", sangria_prueba);
        request.setAttribute("caballos_sangria", sangria_prueba_dao.obtenerCaballosSangriaP(sangria_prueba.getId_sangria_prueba()));
    }

    @Override
    public void prepararEditarInforme(HttpServletRequest request) throws SIGIPROException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PreparedStatement> insertarSQLSolicitud(Connection conexion) throws SQLException {
        List<PreparedStatement> resultado = new ArrayList<>();

        PreparedStatement consulta = conexion.prepareStatement(
                " UPDATE control_calidad.solicitudes SET "
                + " tipo_referencia = ?, "
                + " tabla_referencia = ?, "
                + " id_referenciado = ? "
                + " where id_solicitud = ?; "
        );

        consulta.setString(1, tipo);
        consulta.setString(2, tabla);
        consulta.setInt(3, sangria_prueba.getId_sangria_prueba());
        consulta.setInt(4, solicitud.getId_solicitud());
        consulta.addBatch();

        resultado.add(consulta);

        return resultado;
    }

    @Override
    public List<PreparedStatement> insertarSQLInforme(Connection conexion) throws SQLException {
        return asociacion_informe.insertarSQL(conexion);
    }

    @Override
    public List<PreparedStatement> editarSQLInforme(Connection conexion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PreparedStatement> resetear(Connection conexion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
