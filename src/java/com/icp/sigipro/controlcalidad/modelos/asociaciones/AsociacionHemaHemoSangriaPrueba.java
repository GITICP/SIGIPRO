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
    
    private final List<ObjetoAsociacionMultiple> objetos_hematocrito = new ArrayList<>();
    private final List<ObjetoAsociacionMultiple> objetos_hemoglobina = new ArrayList<>();
    private final String tabla;
    private final String campo_hematocrito;
    private final String campo_hemoglobina;
    private SangriaPrueba sangria_prueba;
    private final AsociacionSangriaPrueba asociacion_sangria_prueba;
    
    public AsociacionHemaHemoSangriaPrueba(AsociacionSangriaPrueba p_asociacion_sangria_prueba) {
        tabla = "caballeriza.sangrias_pruebas_caballos";
        asociacion_sangria_prueba = p_asociacion_sangria_prueba;
        campo_hematocrito = "id_resultado_hematocrito";
        campo_hemoglobina = "id_resultado_hemoglobina";
    }
    
    public void asociar(Resultado resultado, HttpServletRequest request) {
        
        if (sangria_prueba == null) {
            int id_sangria_prueba = Integer.parseInt(request.getParameter("sangria_prueba"));
            sangria_prueba = new SangriaPrueba();
            sangria_prueba.setId_sangria_prueba(id_sangria_prueba);
        }
        
        String nombre_param = "caballos_res_";
        
        String ids_string_hematocrito = request.getParameter(nombre_param + "hematocrito_" + resultado.getId_resultado());
        String ids_string_hemoglobina = request.getParameter(nombre_param + "hemoglobina_" + resultado.getId_resultado());
        
        asignarValores(ids_string_hematocrito, ids_string_hemoglobina, resultado);
        
        
    }
    
    private void asignarValores(String ids_hematocrito, String ids_hemoglobina, Resultado resultado) {
        ObjetoAsociacionMultiple osm = new ObjetoAsociacionMultiple();
        osm.setResultado(resultado);
        if (ids_hematocrito != null) {
            if (!ids_hematocrito.isEmpty()) {
                asignarResultadosHematocrito(ids_hematocrito, osm);
            }
        } else {
            if (ids_hemoglobina != null) {
                if (!ids_hemoglobina.isEmpty()) {
                    asignarResultadosHemoglobina(ids_hemoglobina, osm);
                }
            }
        }
    }
    
    private void asignarResultadosHematocrito(String ids, ObjetoAsociacionMultiple osm) {        
        String[] ids_separados = ids.split(",");
        for (String id : ids_separados) {
            osm.agregarId(Integer.parseInt(id));
        }
        objetos_hematocrito.add(osm);
    }
    
    private void asignarResultadosHemoglobina(String ids, ObjetoAsociacionMultiple osm) {
        String[] ids_separados = ids.split(",");
        for (String id : ids_separados) {
            osm.agregarId(Integer.parseInt(id));
        
        }
        objetos_hemoglobina.add(osm);
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
        
        PreparedStatement consulta_caballos_hematocrito = conexion.prepareStatement(
                "UPDATE " + tabla + " SET " + campo_hematocrito + " = ? WHERE id_caballo = ? AND id_sangria_prueba = ?; " 
        );
        
        for (ObjetoAsociacionMultiple osm : objetos_hematocrito) {
            for (int id : osm.getIds()) {
                consulta_caballos_hematocrito.setInt(1, osm.getResultado().getId_resultado());
                consulta_caballos_hematocrito.setInt(2, id);
                consulta_caballos_hematocrito.setInt(3, sangria_prueba.getId_sangria_prueba());
                consulta_caballos_hematocrito.addBatch();
            }
        }
        
        PreparedStatement consulta_caballos_hemoglobina = conexion.prepareStatement(
                "UPDATE " + tabla + " SET " + campo_hemoglobina + " = ? WHERE id_caballo = ? AND id_sangria_prueba = ?; " 
        );
        
        for (ObjetoAsociacionMultiple osm : objetos_hemoglobina) {
            for (int id : osm.getIds()) {
                consulta_caballos_hemoglobina.setInt(1, osm.getResultado().getId_resultado());
                consulta_caballos_hemoglobina.setInt(2, id);
                consulta_caballos_hemoglobina.setInt(3, sangria_prueba.getId_sangria_prueba());
                consulta_caballos_hemoglobina.addBatch();
            }
        }
        
        resultado.add(consulta_sangria);
        resultado.add(consulta_caballos_hematocrito);
        resultado.add(consulta_caballos_hemoglobina);
        
        return resultado;
    }

    @Override
    public List<PreparedStatement> editarSQL(Connection conexion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Informe getInforme() {
        return asociacion_sangria_prueba.getSolicitud().getInforme();
    }
}
