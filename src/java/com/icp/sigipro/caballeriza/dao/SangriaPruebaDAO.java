/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
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
public class SangriaPruebaDAO
{

    private Connection conexion;

    public SangriaPruebaDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarSangriaPrueba(SangriaPrueba sp, String[] ids_caballos) throws SIGIPROException {
        boolean resultado_insert_sangriap = false;
        boolean resultado_asociacion_caballos = false;
        PreparedStatement consulta_caballos = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        try {
            getConexion().setAutoCommit(false);
            consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.sangrias_pruebas "
            +" ( muestra, num_solicitud, num_informe, fecha_recepcion_muestra, fecha_informe, responsable, id_inoculo) "
            +" VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_sangria_prueba;");
            consulta.setString(1,sp.getMuestra());
            consulta.setInt(2,sp.getNum_solicitud());
            consulta.setInt(3,sp.getNum_informe());
            consulta.setDate(4,sp.getFecha_recepcion_muestra());
            consulta.setDate(5,sp.getFecha_informe());
            consulta.setString(6,sp.getResponsable());
            consulta.setInt(7,sp.getInoculo().getId_inoculo());

            resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado_insert_sangriap = true;
                sp.setId_sangria_prueba(resultadoConsulta.getInt("id_sangria_prueba"));
            }
           if (ids_caballos.length > 0) {
                consulta_caballos = getConexion().prepareStatement(" INSERT INTO caballeriza.sangrias_pruebas_caballos (id_inoculo, id_caballo) VALUES (?,?);");

                for (String id_caballo : ids_caballos) {
                    consulta_caballos.setInt(1, sp.getId_sangria_prueba());
                    consulta_caballos.setInt(2, Integer.parseInt(id_caballo));
                    consulta_caballos.addBatch();
                }

                int[] asociacion_caballos = consulta_caballos.executeBatch();

                boolean iteracion_completa = true;

                for (int asociacion : asociacion_caballos) {
                    if (asociacion != 1) {
                        iteracion_completa = false;
                        break;
                    }
                }

                if (iteracion_completa) {
                    resultado_asociacion_caballos = true;
                }
                
                consulta_caballos.close();
            }
            else {
                resultado_asociacion_caballos = true;
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("No se pudo registrar el Evento Clinico.");
        }
        finally {
            try {
                if (resultado_insert_sangriap && resultado_asociacion_caballos) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (resultadoConsulta != null){
                    resultadoConsulta.close();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (consulta_caballos != null) {
                    consulta_caballos.close();
                }
                cerrarConexion();
            } catch(SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos.");
            }
        }
        return resultado_insert_sangriap && resultado_asociacion_caballos;
    }

    public List<SangriaPrueba> obtenerSangriasPruebas() throws SIGIPROException
    {
        List<SangriaPrueba> resultado = new ArrayList<SangriaPrueba>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.sangrias_pruebas");
            ResultSet rs = consulta.executeQuery();
            InoculoDAO dao = new InoculoDAO();
            while (rs.next()) {
                SangriaPrueba sangriap = new SangriaPrueba();
                sangriap.setId_sangria_prueba(rs.getInt("id_sangria_prueba"));
                sangriap.setMuestra(rs.getString("muestra"));
                sangriap.setNum_solicitud(rs.getInt("num_solicitud"));
                sangriap.setNum_informe(rs.getInt("num_informe"));
                sangriap.setFecha_recepcion_muestra(rs.getDate("fecha_recepcion_muestra"));
                sangriap.setFecha_informe(rs.getDate("fecha_informe"));
                sangriap.setResponsable(rs.getString("responsable"));
                sangriap.setInoculo(dao.obtenerInoculo(rs.getInt("id_inoculo")));
                resultado.add(sangriap);
            }
            consulta.close();
            conexion.close();
            rs.close();
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Los eventos clinicos no pueden ser accedidos.");
        }
        return resultado;
    }


    public List<Caballo> obtenerCaballosEvento(int id_evento) throws SIGIPROException
    {
        List<Caballo> resultado = new ArrayList<Caballo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" select nombre, numero_microchip from caballeriza.caballos c left outer join caballeriza.eventos_clinicos_caballos ecc on c.id_caballo = ecc.id_caballo where id_evento=?; ");
            consulta.setInt(1, id_evento);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                resultado.add(caballo);
            }
            rs.close();
            consulta.close();
            cerrarConexion();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
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
