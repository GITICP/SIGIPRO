/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos;

import com.icp.sigipro.caballeriza.modelos.Sangria;
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
public class AsociacionLALSangria extends Asociacion {

    private List<ObjetoAsociacionMultiple> objetos = new ArrayList<ObjetoAsociacionMultiple>();
    private String campo;
    private Sangria sangria;

    public AsociacionLALSangria() {
        objeto = "sangrias_caballos";
        tabla = "caballeriza.sangrias_caballos";
    }

    @Override
    protected void asociar(Resultado resultado, HttpServletRequest request) {

        if (sangria == null) {
            int id_sangria = Integer.parseInt(request.getParameter("sangria"));
            sangria = new Sangria();
            sangria.setId_sangria(id_sangria);
            campo = "id_resultado_lal_dia" + request.getParameter("dia");
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
    protected List<PreparedStatement> asociarSQL(Connection conexion) throws SQLException {
        
        List<PreparedStatement> resultado = new ArrayList<PreparedStatement>();
        
        PreparedStatement consulta_sangria = conexion.prepareStatement(
                " UPDATE caballeriza.sangrias SET num_inf_cc = ? WHERE id_sangria = ? "
        );
        
        consulta_sangria.setInt(1, informe.getId_informe());
        consulta_sangria.setInt(2, sangria.getId_sangria());
        
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

}
