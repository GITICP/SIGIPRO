/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Walter
 */
public class GrupoDeCaballosDAO
{

    private Connection conexion;

    public GrupoDeCaballosDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarGrupoDeCaballos(GrupoDeCaballos g, String[] ids_caballos) throws SIGIPROException
    {
        boolean resultado = false;
        boolean resultado_insert_grupo = false;
        String caballos = pasar_id_caballos(ids_caballos);
        int cant_caballos = ids_caballos.length;
        PreparedStatement consulta_caballos = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        try {
            getConexion().setAutoCommit(false);

            consulta = getConexion().prepareStatement("INSERT INTO caballeriza.grupos_de_caballos (nombre, descripcion) "
                                                      + " VALUES (?,?) RETURNING id_grupo_de_caballo");
            consulta.setString(1, g.getNombre());
            consulta.setString(2, g.getDescripcion());

            resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado_insert_grupo = true;
                g.setId_grupo_caballo(resultadoConsulta.getInt("id_grupo_de_caballo"));
            }

            if (!caballos.equals(")")) {
                consulta_caballos = getConexion().prepareStatement("UPDATE caballeriza.caballos "
                                                                   + " SET id_grupo_de_caballo = (CASE "
                                                                   + " WHEN id_caballo in " + caballos + " THEN ? "
                                                                   + " ELSE Null END) "
                                                                   + " WHERE id_grupo_de_caballo = ? or id_caballo in " + caballos + ";");

                consulta_caballos.setInt(1, g.getId_grupo_caballo());
                consulta_caballos.setInt(2, g.getId_grupo_caballo());

                int filas_caballos = consulta_caballos.executeUpdate();

                if (resultado_insert_grupo == true && filas_caballos >= cant_caballos) {
                    resultado = true;
                }
                else {
                    resultado = false;
                }
            } else {
                resultado = resultado_insert_grupo;
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (resultado) {
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
                getConexion().close();
            }
            catch (SQLException ex_operaciones) {
                throw new SIGIPROException("El Grupo de caballos no pudo ser guardado.");
            }
        }
        return resultado;
    }

    public boolean eliminarGrupoDeCaballos(int id_grupo_de_caballo) throws SIGIPROException
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM caballeriza.grupos_de_caballos "
                    + " WHERE id_grupo_de_caballo=?; "
            );

            consulta.setInt(1, id_grupo_de_caballo);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Grupo de caballos no pudo ser eliminado debido a que uno o más caballos se encuentran asociadas a este.");
        }
        return resultado;
    }

    public boolean editarGrupoDeCaballos(GrupoDeCaballos g, String[] ids_caballos) throws SIGIPROException
    {
        boolean resultado = false;
        int cant_caballos = ids_caballos.length;
        String caballos = pasar_id_caballos(ids_caballos);
        if (")".equals(caballos)) {
            caballos = "(0)";
        }
        PreparedStatement consulta = null;
        PreparedStatement consulta_caballos = null;
        try {
            getConexion().setAutoCommit(false);

            consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.grupos_de_caballos "
                    + " SET nombre=?, descripcion=? "
                    + " WHERE id_grupo_de_caballo=?; "
            );
            consulta_caballos = getConexion().prepareStatement(
                    "UPDATE caballeriza.caballos "
                    + " SET id_grupo_de_caballo = (CASE "
                    + " WHEN id_caballo in " + caballos + " THEN ? "
                    + " ELSE Null END) "
                    + " WHERE id_grupo_de_caballo = ? or id_caballo in " + caballos + ";");

            consulta.setString(1, g.getNombre());
            consulta.setString(2, g.getDescripcion());
            consulta.setInt(3, g.getId_grupo_caballo());
            consulta_caballos.setInt(1, g.getId_grupo_caballo());
            consulta_caballos.setInt(2, g.getId_grupo_caballo());

            int filas_grupo = consulta.executeUpdate();
            int filas_caballos = consulta_caballos.executeUpdate();

            if (filas_grupo == 1 && filas_caballos >= cant_caballos) {
                resultado = true;
            }
            else {
                resultado = false;
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (resultado) {
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
                getConexion().close();
            }
            catch (SQLException ex_operaciones) {
                throw new SIGIPROException("El Grupo de caballos no pude ser editado.");
            }
        }
        return resultado;

    }

    public GrupoDeCaballos obtenerGrupoDeCaballos(int id_grupo_caballo)
    {
        GrupoDeCaballos grupodecaballos = new GrupoDeCaballos();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.grupos_de_caballos where id_grupo_de_caballo = ?");
            consulta.setInt(1, id_grupo_caballo);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                grupodecaballos.setId_grupo_caballo(rs.getInt("id_grupo_de_caballo"));
                grupodecaballos.setNombre(rs.getString("nombre"));
                grupodecaballos.setDescripcion(rs.getString("descripcion"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return grupodecaballos;
    }

    public List<GrupoDeCaballos> obtenerGruposDeCaballos()
    {
        List<GrupoDeCaballos> resultado = new ArrayList<GrupoDeCaballos>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("select * from caballeriza.grupos_de_caballos where id_grupo_de_caballo != 0");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                GrupoDeCaballos grupo = new GrupoDeCaballos();
                grupo.setId_grupo_caballo(rs.getInt("id_grupo_de_caballo"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setDescripcion(rs.getString("descripcion"));
                resultado.add(grupo);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<GrupoDeCaballos> obtenerGruposDeCaballosConCaballos()
    {
        List<GrupoDeCaballos> resultado = new ArrayList<GrupoDeCaballos>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT gc.id_grupo_de_caballo, gc.nombre as nombre_grupo, c.id_caballo, c.nombre as nombre_caballo, numero_microchip, c.numero "
                    + " FROM caballeriza.grupos_de_caballos gc "
                    + "     INNER JOIN caballeriza.caballos c "
                    + "         ON gc.id_grupo_de_caballo = c.id_grupo_de_caballo AND c.estado = ?;");

            consulta.setString(1, Caballo.VIVO);

            GrupoDeCaballos g = null;

            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                int id_grupo = rs.getInt("id_grupo_de_caballo");

                if (g == null || g.getId_grupo_caballo() != id_grupo) {
                    g = new GrupoDeCaballos();
                    g.setId_grupo_caballo(id_grupo);
                    g.setNombre(rs.getString("nombre_grupo"));
                    resultado.add(g);
                }

                Caballo c = new Caballo();
                c.setId_caballo(rs.getInt("id_caballo"));
                c.setNombre(rs.getString("nombre_caballo"));
                c.setNumero_microchip(rs.getString("numero_microchip"));
                c.setNumero(rs.getInt("numero"));

                g.agregarCaballo(c);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public String[] parsearAsociacion(String pivote, String asociacionesCodificadas)
    {
        String[] idsTemp = asociacionesCodificadas.split(pivote);
        return Arrays.copyOfRange(idsTemp, 1, idsTemp.length);
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

    private String pasar_id_caballos(String[] ids_caballos)
    {
        String caballos = "(";
        for (String s : ids_caballos) {
            caballos = caballos + s;
            caballos = caballos + ",";
        }
        caballos = caballos.substring(0, caballos.length() - 1);
        caballos = caballos + ")";
        return caballos;
    }
}
