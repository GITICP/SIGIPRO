/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
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
    public void prepararEditar(HttpServletRequest request) {
        String tipo_editar = "data-tipo=\"" + tipo + "\"";
        String tipo_id_sangria = "data-id-sangria = \"" + sangria.getId_sangria() + "\"";
        String tipo_dia = "data-dia=\"" + dia + "\"";
        request.setAttribute("valores_editar", tipo_editar + " " + tipo_id_sangria + " " + tipo_dia);
    }
    
    @Override
    public List<PreparedStatement> insertarSQL(Connection conexion) throws SQLException {
        
        List<PreparedStatement> resultado = new ArrayList<PreparedStatement>();
        
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

    // </editor-fold>

}
