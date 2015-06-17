/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.Inoculo;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class InoculoDAO extends DAO
{

    public InoculoDAO()
    {
    }

    public boolean insertarInoculo(Inoculo i, String[] ids_caballos) throws SIGIPROException
    {
        boolean resultado_insert_inoculo = false;
        boolean resultado_asociacion_caballos = false;
        PreparedStatement consulta_caballos = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        try {
            getConexion().setAutoCommit(false);
            consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.inoculos (mnn, baa, bap, cdd, lms, tetox, otro, encargado_preparacion, encargado_inyeccion, fecha, grupo_de_caballos) "
                                                      + " VALUES (?,?,?,?,?,?,?,?,?,?, ?) RETURNING id_inoculo");
            consulta.setString(1, i.getMnn());
            consulta.setString(2, i.getBaa());
            consulta.setString(3, i.getBap());
            consulta.setString(4, i.getCdd());
            consulta.setString(5, i.getLms());
            consulta.setString(6, i.getTetox());
            consulta.setString(7, i.getOtro());
            consulta.setString(8, i.getEncargado_preparacion());
            consulta.setString(9, i.getEncargado_inyeccion());
            consulta.setDate(10, i.getFecha());
            consulta.setInt(11, i.getGrupo_de_caballos().getId_grupo_caballo());

            resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado_insert_inoculo = true;
                i.setId_inoculo(resultadoConsulta.getInt("id_inoculo"));
            }
            if (ids_caballos.length > 0) {
                consulta_caballos = getConexion().prepareStatement(" INSERT INTO caballeriza.inoculos_caballos (id_inoculo, id_caballo) VALUES (?,?);");

                for (String id_caballo : ids_caballos) {
                    consulta_caballos.setInt(1, i.getId_inoculo());
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
            ex.printStackTrace();
            throw new SIGIPROException("No se pudo registrar el Evento Clinico.");
        }
        finally {
            try {
                if (resultado_insert_inoculo && resultado_asociacion_caballos) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (resultadoConsulta != null) {
                    resultadoConsulta.close();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (consulta_caballos != null) {
                    consulta_caballos.close();
                }
                cerrarConexion();
            }
            catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos.");
            }
        }
        return resultado_insert_inoculo && resultado_asociacion_caballos;
    }

    public boolean actualizarInoculoCaballo(int id_inoculo, String[] ids_caballos) throws SIGIPROException
    {
        boolean resultado_eliminar = false;
        boolean resultado_asociacion_caballos = false;
        PreparedStatement consulta_caballos = null;
        PreparedStatement consulta = null;
        int resultadoConsulta;
        //ResultSet resultadoConsulta = null;
        try {
            getConexion().setAutoCommit(false);
            consulta = getConexion().prepareStatement("DELETE FROM caballeriza.inoculos_caballos ic "
                                                      + "WHERE  ic.id_inoculo = ? "
            );
            consulta.setInt(1, id_inoculo);
            //resultadoConsulta = consulta.executeQuery();
            resultadoConsulta = consulta.executeUpdate();
            if (resultadoConsulta >= 1) {
                resultado_eliminar = true;
            }
            if (ids_caballos.length > 0) {
                consulta_caballos = getConexion().prepareStatement(" INSERT INTO caballeriza.inoculos_caballos (id_inoculo, id_caballo) VALUES (?,?);");

                for (String id_caballo : ids_caballos) {
                    consulta_caballos.setInt(1, id_inoculo);
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
            ex.printStackTrace();
            throw new SIGIPROException("No se pudo registrar el Evento Clínico.");
        }
        finally {
            try {
                if (resultado_eliminar && resultado_asociacion_caballos) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (consulta != null) {
                    consulta.close();
                }
                if (consulta_caballos != null) {
                    consulta_caballos.close();
                }
                cerrarConexion();
            }
            catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos.");
            }
        }
        return resultado_eliminar && resultado_asociacion_caballos;
    }

    public boolean editarInoculo(Inoculo i) throws SIGIPROException
    {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.inoculos "
                    + " SET mnn=?, baa=?, bap=?, cdd=?, lms=?, tetox=?, otro=?, encargado_preparacion=?, encargado_inyeccion=?, fecha=? "
                    + " WHERE id_inoculo=?; "
            );
            consulta.setString(1, i.getMnn());
            consulta.setString(2, i.getBaa());
            consulta.setString(3, i.getBap());
            consulta.setString(4, i.getCdd());
            consulta.setString(5, i.getLms());
            consulta.setString(6, i.getTetox());
            consulta.setString(7, i.getOtro());
            consulta.setString(8, i.getEncargado_preparacion());
            consulta.setString(9, i.getEncargado_inyeccion());
            consulta.setDate(10, i.getFecha());
            consulta.setInt(11, i.getId_inoculo());
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("El Inóculo no puede ser editado.");
        }
        return resultado;

    }

    public Inoculo obtenerInoculo(int id_inoculo) throws SIGIPROException
    {
        Inoculo inoculo = new Inoculo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.inoculos"
                                                                        + " where id_inoculo = ?");
            consulta.setInt(1, id_inoculo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                inoculo.setId_inoculo(rs.getInt("id_inoculo"));
                inoculo.setMnn(rs.getString("mnn"));
                inoculo.setBaa(rs.getString("baa"));
                inoculo.setBap(rs.getString("bap"));
                inoculo.setCdd(rs.getString("cdd"));
                inoculo.setLms(rs.getString("lms"));
                inoculo.setTetox(rs.getString("tetox"));
                inoculo.setOtro(rs.getString("otro"));
                inoculo.setEncargado_preparacion(rs.getString("encargado_preparacion"));
                inoculo.setEncargado_inyeccion(rs.getString("encargado_inyeccion"));
                inoculo.setFecha(rs.getDate("fecha"));
            }
            consulta.close();
            cerrarConexion();
            rs.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("El Inóculo no puede ser obtenido.");
        }
        return inoculo;
    }

    public List<Inoculo> obtenerInoculos() throws SIGIPROException
    {
        List<Inoculo> resultado = new ArrayList<Inoculo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.inoculos");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                Inoculo inoculo = new Inoculo();
                inoculo.setId_inoculo(rs.getInt("id_inoculo"));
                inoculo.setMnn(rs.getString("mnn"));
                inoculo.setBaa(rs.getString("baa"));
                inoculo.setBap(rs.getString("bap"));
                inoculo.setCdd(rs.getString("cdd"));
                inoculo.setLms(rs.getString("lms"));
                inoculo.setTetox(rs.getString("tetox"));
                inoculo.setOtro(rs.getString("otro"));
                inoculo.setEncargado_preparacion(rs.getString("encargado_preparacion"));
                inoculo.setEncargado_inyeccion(rs.getString("encargado_inyeccion"));
                inoculo.setFecha(rs.getDate("fecha"));
                resultado.add(inoculo);
            }
            consulta.close();
            cerrarConexion();
            rs.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Los Inóculos no pueden ser accedidos.");
        }
        return resultado;
    }

    public List<Caballo> obtenerCaballosInoculo(int id_inoculo) throws SIGIPROException
    {
        List<Caballo> resultado = new ArrayList<Caballo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" select c.id_caballo, nombre, numero_microchip, numero from caballeriza.caballos c left outer join caballeriza.inoculos_caballos ecc on c.id_caballo = ecc.id_caballo where id_inoculo=?; ");
            consulta.setInt(1, id_inoculo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getString("numero_microchip"));
                caballo.setNumero(rs.getInt("numero"));
                resultado.add(caballo);
            }
            rs.close();
            consulta.close();
            cerrarConexion();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    public GrupoDeCaballos obtenerGrupoInoculo(int id_inoculo) throws SIGIPROException
    {
        GrupoDeCaballos grupo = new GrupoDeCaballos();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    "Select Distinct(gc.nombre) nombre "
                    + " from caballeriza.inoculos_caballos ic "
                    + " Join caballeriza.caballos c "
                    + " On ic.id_caballo=c.id_caballo "
                    + " Join caballeriza.grupos_de_caballos  gc "
                    + " On c.id_grupo_de_caballo= gc.id_grupo_de_caballo "
                    + " where ic.id_inoculo=?;");
            consulta.setInt(1, id_inoculo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                grupo.setNombre(rs.getString("nombre"));
            }
            consulta.close();
            cerrarConexion();
            rs.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("El Inóculo no puede ser obtenido.");
        }
        return grupo;
    }

    public List<Inoculo> obtenerInoculosConCaballos() throws SIGIPROException
    {

        List<Inoculo> resultado = new ArrayList<Inoculo>();

        try {

            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT i.id_inoculo, c.id_caballo, c.nombre, c.numero_microchip, c.numero "
                    + " FROM caballeriza.inoculos i "
                    + "     INNER JOIN caballeriza.inoculos_caballos ic ON ic.id_inoculo = i.id_inoculo "
                    + "     INNER JOIN caballeriza.caballos c ON c.id_caballo = ic.id_caballo "
                    + " WHERE i.id_inoculo NOT IN (SELECT id_inoculo FROM caballeriza.sangrias_pruebas);");

            ResultSet rs = consulta.executeQuery();

            Inoculo inoculo = null;

            while (rs.next()) {

                int id_inoculo = rs.getInt("id_inoculo");

                if (inoculo == null || inoculo.getId_inoculo() != id_inoculo) {
                    inoculo = new Inoculo();
                    inoculo.setId_inoculo(id_inoculo);
                    resultado.add(inoculo);
                }

                Caballo c = new Caballo();
                c.setId_caballo(rs.getInt("id_caballo"));
                c.setNombre(rs.getString("nombre"));
                c.setNumero_microchip(rs.getString("numero_microchip"));
                c.setNumero(rs.getInt("numero"));

                inoculo.agregarCaballo(c);
            }

            rs.close();
            consulta.close();
            cerrarConexion();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Ourrió un error al realizar la solicitud.");
        }

        return resultado;
    }
}
