/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
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
public class AsociacionHemaHemoSangriaPrueba extends AsociacionInforme {
    
    private final List<ObjetoAsociacionMultiple> objetos = new ArrayList<>();
    private final String tabla;
    private final String campo;
    private SangriaPrueba sangria_prueba;
    private final AsociacionSangriaPrueba asociacion_sangria_prueba;
    
    public AsociacionHemaHemoSangriaPrueba(AsociacionSangriaPrueba p_asociacion_sangria_prueba) {
        tabla = "caballeriza.sangrias_pruebas_caballos";
        asociacion_sangria_prueba = p_asociacion_sangria_prueba;
        campo = "id_resultado";
    }
    
    public void asociar(Resultado resultado, HttpServletRequest request) {
        
        if (sangria_prueba == null) {
            int id_sangria_prueba = Integer.parseInt(request.getParameter("sangria_prueba"));
            sangria_prueba = new SangriaPrueba();
            sangria_prueba.setId_sangria_prueba(id_sangria_prueba);
        }
                
        String ids_caballos = request.getParameter("caballos_res_" + resultado.getId_resultado());
        
        asignarValores(ids_caballos, resultado);
        
        
    }
    
    private void asignarValores(String ids_caballos, Resultado resultado) {
        ObjetoAsociacionMultiple osm = new ObjetoAsociacionMultiple();
        osm.setResultado(resultado);
        String[] ids_separados = ids_caballos.split(",");
        for (String id : ids_separados) {
            osm.agregarId(Integer.parseInt(id));
        }
        objetos.add(osm);
    }

    @Override
    public List<PreparedStatement> insertarSQL(Connection conexion) throws SQLException {
        
        List<PreparedStatement> resultado = new ArrayList<>();
        
        PreparedStatement consulta_sangria = conexion.prepareStatement(
                "UPDATE caballeriza.sangrias_pruebas SET id_informe = ? WHERE id_sangria_prueba = ?"
        );
        
        consulta_sangria.setInt(1, getInforme().getId_informe());
        consulta_sangria.setInt(2, sangria_prueba.getId_sangria_prueba());
        consulta_sangria.addBatch();
        
        PreparedStatement consulta_caballos = conexion.prepareStatement(
                "UPDATE " + tabla + " SET " + campo + " = ? WHERE id_caballo = ? AND id_sangria_prueba = ?; " 
        );
        
        for (ObjetoAsociacionMultiple osm : objetos) {
            for (int id : osm.getIds()) {
                consulta_caballos.setInt(1, osm.getResultado().getId_resultado());
                consulta_caballos.setInt(2, id);
                consulta_caballos.setInt(3, sangria_prueba.getId_sangria_prueba());
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
                " UPDATE caballeriza.sangrias_pruebas SET id_informe = ? WHERE id_sangria_prueba = ? "
        );

        consulta_sangria.setInt(1, getInforme().getId_informe());
        consulta_sangria.setInt(2, sangria_prueba.getId_sangria_prueba());
        consulta_sangria.addBatch();

        PreparedStatement consulta_caballos = conexion.prepareStatement(
                " UPDATE  " + tabla + " SET " + campo + " = ? WHERE id_sangria_prueba = ? AND id_caballo = ?; "
        );

        for (ObjetoAsociacionMultiple osm : objetos) {
            for (int id : osm.getIds()) {
                consulta_caballos.setInt(1, osm.getResultado().getId_resultado());
                consulta_caballos.setInt(2, sangria_prueba.getId_sangria_prueba());
                consulta_caballos.setInt(3, id);
                consulta_caballos.addBatch();
            }
        }
        
        resultado.add(consulta_sangria);
        resultado.add(consulta_caballos);

        return resultado;
        
    }
    
    private Informe getInforme() {
        return asociacion_sangria_prueba.getSolicitud().getInforme();
    }
}
