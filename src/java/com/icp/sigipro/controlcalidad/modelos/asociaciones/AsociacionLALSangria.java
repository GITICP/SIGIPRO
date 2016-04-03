/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.controlcalidad.modelos.Informe;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public class AsociacionLALSangria extends AsociacionInforme {

    private final List<ObjetoAsociacionMultiple> objetos = new ArrayList<>();
    private final String tabla;
    private String campo;
    private Sangria sangria;
    private int dia;
    private final AsociacionSangria asociacion_sangria;

    public AsociacionLALSangria(AsociacionSangria p_asociacion_sangria) {
        tabla = "caballeriza.sangrias_caballos";
        asociacion_sangria = p_asociacion_sangria;
    }

    public void asociar(Resultado resultado, HttpServletRequest request) {

        if (sangria == null) {
            int id_sangria = Integer.parseInt(request.getParameter("sangria"));
            sangria = new Sangria();
            sangria.setId_sangria(id_sangria);
            dia = Integer.parseInt(request.getParameter("dia"));
            campo = "id_resultado_lal_dia" + dia;
        }

        ObjetoAsociacionMultiple osm = new ObjetoAsociacionMultiple();
        osm.setResultado(resultado);

        String ids_string = request.getParameter("caballos_res_" + resultado.getId_resultado());
        String[] ids_string_arreglo = ids_string.split(",");

        for (String id : ids_string_arreglo) {
            osm.agregarId(Integer.parseInt(id));
        }

        objetos.add(osm);
    }

    @Override
    public List<PreparedStatement> insertarSQL(Connection conexion) throws SQLException {

        List<PreparedStatement> resultado = new ArrayList<>();

        PreparedStatement consulta_sangria = conexion.prepareStatement(
                " UPDATE caballeriza.sangrias SET id_informe_dia" + dia + " = ? WHERE id_sangria = ? "
        );

        consulta_sangria.setInt(1, getInforme().getId_informe());
        consulta_sangria.setInt(2, sangria.getId_sangria());
        consulta_sangria.addBatch();

        PreparedStatement consulta_caballos = conexion.prepareStatement(
                " UPDATE  " + tabla + " SET " + campo + " = ? WHERE id_sangria = ? AND id_caballo = ?; "
        );

        for (ObjetoAsociacionMultiple osm : objetos) {
            for (int id : osm.getIds()) {
                consulta_caballos.setInt(1, osm.getResultado().getId_resultado());
                consulta_caballos.setInt(2, sangria.getId_sangria());
                consulta_caballos.setInt(3, id);
                consulta_caballos.addBatch();
            }
        }

        resultado.add(consulta_sangria);
        resultado.add(consulta_caballos);

        return resultado;

    }
    
    @Override
    public List<PreparedStatement> editarSQL(Connection conexion) throws SQLException {

        List<PreparedStatement> resultado = new ArrayList<>();

        PreparedStatement consulta_sangria = conexion.prepareStatement(
                " UPDATE caballeriza.sangrias SET id_informe_dia" + dia + " = ? WHERE id_sangria = ? "
        );

        consulta_sangria.setInt(1, getInforme().getId_informe());
        consulta_sangria.setInt(2, sangria.getId_sangria());
        consulta_sangria.addBatch();

        PreparedStatement consulta_caballos = conexion.prepareStatement(
                " UPDATE  " + tabla + " SET " + campo + " = ? WHERE id_sangria = ? AND id_caballo = ?; "
        );

        for (ObjetoAsociacionMultiple osm : objetos) {
            for (int id : osm.getIds()) {
                consulta_caballos.setInt(1, osm.getResultado().getId_resultado());
                consulta_caballos.setInt(2, sangria.getId_sangria());
                consulta_caballos.setInt(3, id);
                consulta_caballos.addBatch();
            }
        }
        
        resultado.add(consulta_caballos);

        return resultado;

    }
    
    private Informe getInforme() {
        return asociacion_sangria.getSolicitud().getInforme();
    }
    
}
