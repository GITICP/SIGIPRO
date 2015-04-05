/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.caballeriza.modelos.SangriaCaballo;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class SangriaDAO
{

    private Connection conexion;

    public SangriaDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public Sangria insertarSangria(Sangria s) throws SIGIPROException
    {

        boolean resultado_sangria = false;
        boolean resultado_caballos = false;
        PreparedStatement consulta_sangria = null;
        PreparedStatement consulta_caballos = null;
        ResultSet rs_sangria = null;

        try {
            getConexion().setAutoCommit(false);

            consulta_sangria = getConexion().prepareStatement(
                    " INSERT INTO caballeriza.sangrias(id_sangria_prueba, responsable, cantidad_de_caballos, num_inf_cc, potencia) "
                    + " VALUES (?,?,?,?,?) RETURNING id_sangria;"
            );
            
            consulta_sangria.setInt(1, s.getSangria_prueba().getId_sangria_prueba());
            consulta_sangria.setString(2, s.getResponsable());            
            
            consulta_sangria.setInt(3, s.getSangrias_caballos().size());
            if(s.getNum_inf_cc() == 0) {
                consulta_sangria.setNull(4, java.sql.Types.INTEGER);
            } else {
                consulta_sangria.setInt(4, s.getNum_inf_cc());
            }
            if(s.getPotencia() == 0.0f) {
                consulta_sangria.setNull(5, java.sql.Types.FLOAT);
            } else {
                consulta_sangria.setFloat(5, s.getPotencia());
            }

            rs_sangria = consulta_sangria.executeQuery();

            if (rs_sangria.next()) {
                resultado_sangria = true;
                s.setId_sangria(rs_sangria.getInt("id_sangria"));
            } else {
                resultado_sangria = false;
            }
            rs_sangria.close();

            consulta_caballos = getConexion().prepareStatement(
                    " INSERT INTO caballeriza.sangrias_caballos (id_sangria, id_caballo) "
                    + " VALUES (?,?); "
            );

            for (SangriaCaballo sangria_caballo : s.getSangrias_caballos()) {
                consulta_caballos.setInt(1, s.getId_sangria());
                consulta_caballos.setInt(2, sangria_caballo.getCaballo().getId_caballo());
                consulta_caballos.addBatch();
            }
            
            int[] resultados_caballos = consulta_caballos.executeBatch();
            
            boolean iteracion_completa = true;

            for (int asociacion : resultados_caballos) {
                if (asociacion != 1) {
                    iteracion_completa = false;
                    break;
                }
            }

            if (iteracion_completa) {
                resultado_caballos = true;
            }

        }
        catch (SQLException ex) {
            throw new SIGIPROException("No se pudo registrar el Evento Clinico.");
        }
        finally {
            try {
                if(resultado_sangria && resultado_caballos) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
                if(rs_sangria != null) {
                    rs_sangria.close();
                }
                if(consulta_sangria != null) {
                    consulta_sangria.close();
                }
                if(consulta_caballos != null) {
                    consulta_caballos.close();
                }
                cerrarConexion();
            } catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos");
            }
        }

        return s;
    }

    public List<Sangria> obtenerSangrias() throws SIGIPROException
    {
        List<Sangria> resultado = new ArrayList<Sangria>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.sangrias");
            ResultSet rs = consulta.executeQuery();
            SangriaPruebaDAO dao = new SangriaPruebaDAO();
            while (rs.next()) {
                Sangria sangria = new Sangria();
                sangria.setId_sangria(rs.getInt("id_sangria"));
                sangria.setSangria_prueba(dao.obtenerSangriaPrueba(rs.getInt("id_sangria_prueba")));
                sangria.setFecha_dia1(rs.getDate("fecha_dia1"));
                sangria.setFecha_dia2(rs.getDate("fecha_dia2"));
                sangria.setFecha_dia3(rs.getDate("fecha_dia3"));
                sangria.setHematrocito_promedio(rs.getFloat("hematrocito_promedio"));
                sangria.setNum_inf_cc(rs.getInt("num_inf_cc"));
                sangria.setResponsable(rs.getString("responsable"));
                sangria.setCantidad_de_caballos(rs.getInt("cantidad_de_caballos"));
                sangria.setSangre_total(rs.getFloat("sangre_total"));
                sangria.setPeso_plasma_total(rs.getFloat("peso_plasma_total"));
                sangria.setVolumen_plasma_total(rs.getFloat("volumen_plasma_total"));
                sangria.setPlasma_por_caballo(rs.getFloat("plasma_por_caballo"));
                sangria.setPotencia(rs.getFloat("potencia"));
                resultado.add(sangria);
            }
            consulta.close();
            conexion.close();
            rs.close();
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Las Sangrias no pueden ser accedidas.");
        }
        return resultado;
    }

    private Connection getConexion()
    {
        try {
            if (conexion.isClosed()) {
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        }
        catch (Exception ex) {
            conexion = null;
        }
        return conexion;
    }

    private void cerrarConexion()
    {
        if (conexion != null) {
            try {
                if (conexion.isClosed()) {
                    conexion.close();
                }
            }
            catch (Exception ex) {
                conexion = null;
            }
        }
    }

}
