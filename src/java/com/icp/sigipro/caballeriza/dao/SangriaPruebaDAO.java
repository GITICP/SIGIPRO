/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
import com.icp.sigipro.caballeriza.modelos.SangriaPruebaCaballo;
import com.icp.sigipro.core.SIGIPROException;
import java.math.BigDecimal;
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

    public boolean insertarSangriaPrueba(SangriaPrueba sp, List<SangriaPruebaCaballo> informacion_caballos) throws SIGIPROException
    {
        boolean resultado_insert_sangriap = false;
        boolean resultado_asociacion_caballos = false;
        PreparedStatement consulta_caballos = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        try {
            getConexion().setAutoCommit(false);
            consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.sangrias_pruebas "
                                                      + " ( muestra, num_solicitud, num_informe, fecha_recepcion_muestra, fecha_informe, responsable, id_inoculo) "
                                                      + " VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_sangria_prueba;");
            consulta.setString(1, sp.getMuestra());
            consulta.setInt(2, sp.getNum_solicitud());
            consulta.setInt(3, sp.getNum_informe());
            consulta.setDate(4, sp.getFecha_recepcion_muestra());
            consulta.setDate(5, sp.getFecha_informe());
            consulta.setString(6, sp.getResponsable());
            consulta.setInt(7, sp.getInoculo().getId_inoculo());

            resultadoConsulta = consulta.executeQuery();
            
            if (resultadoConsulta.next()) {
                resultado_insert_sangriap = true;
                sp.setId_sangria_prueba(resultadoConsulta.getInt("id_sangria_prueba"));
            }
            
            if (informacion_caballos.size() > 0) {
                consulta_caballos = getConexion().prepareStatement(" INSERT INTO caballeriza.sangrias_pruebas_caballos (id_sangria_prueba, id_caballo, hematrocito, hemoglobina) VALUES (?,?,?,?);");

                for (SangriaPruebaCaballo informacion_caballo : informacion_caballos) {
                    consulta_caballos.setInt(1, sp.getId_sangria_prueba());
                    consulta_caballos.setInt(2, informacion_caballo.getCaballo().getId_caballo());
                    consulta_caballos.setFloat(3, informacion_caballo.getHematrocito());
                    consulta_caballos.setFloat(4, informacion_caballo.getHemoglobina());
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
            throw new SIGIPROException("No se pudo registrar la sangría de prueba.");
        }
        finally {
            try {
                if (resultado_insert_sangriap && resultado_asociacion_caballos) {
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

    public List<Caballo> obtenerCaballosSangriaP(int id_sangria_prueba) throws SIGIPROException
    {
        List<Caballo> resultado = new ArrayList<Caballo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("select nombre, numero_microchip, numero, hematrocito, hemoglobina from caballeriza.caballos c left outer join caballeriza.sangrias_pruebas_caballos ecc on c.id_caballo = ecc.id_caballo where id_sangria_prueba=?; ");
            consulta.setInt(1, id_sangria_prueba);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getString("numero_microchip"));
                caballo.setNumero(rs.getInt("numero"));
                caballo.setHematrocito(rs.getBigDecimal("hematrocito"));
                caballo.setHemoglibina(rs.getBigDecimal("hemoglobina"));
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

    public SangriaPrueba obtenerSangriaPrueba(int id_sangria_prueba) throws SIGIPROException
    {
        InoculoDAO inoculodao = new InoculoDAO();
        SangriaPrueba sangriap = new SangriaPrueba();
        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM caballeriza.sangrias_pruebas"
                                                                        + " where id_sangria_prueba = ?");
            consulta.setInt(1, id_sangria_prueba);
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                sangriap.setId_sangria_prueba(rs.getInt("id_sangria_prueba"));
                sangriap.setMuestra(rs.getString("muestra"));
                sangriap.setNum_solicitud(rs.getInt("num_solicitud"));
                sangriap.setNum_informe(rs.getInt("num_informe"));
                sangriap.setFecha_recepcion_muestra(rs.getDate("fecha_recepcion_muestra"));
                sangriap.setFecha_informe(rs.getDate("fecha_informe"));
                sangriap.setResponsable(rs.getString("responsable"));
                sangriap.setInoculo(inoculodao.obtenerInoculo(rs.getInt("id_inoculo")));
            }
            consulta.close();
            conexion.close();
            rs.close();
        }
        catch (SQLException ex) {
            throw new SIGIPROException("La prueba de sangría no puede ser obtenida.");
        }
        return sangriap;
    }

    public int obtenerIdSangriaPrueba(String muestra)
    {
        int id_sangriap = 0;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    "SELECT id_sangria_prueba "
                    + " FROM caballeriza.sangrias_pruebas "
                    + " WHERE muestra = ?;");
            consulta.setString(1, muestra);
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                id_sangriap = rs.getInt("id_sangria_prueba");
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return id_sangriap;
    }

    public boolean editarSangriaPruebaCaballo(int id_sangria_prueba, int id_caballo, float hematrocitop, float hemoglobinap) throws SIGIPROException
    {
        boolean resultado = false;
        BigDecimal hematrocito = BigDecimal.valueOf(hematrocitop);
        BigDecimal hemoglobina = BigDecimal.valueOf(hemoglobinap);

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.sangrias_pruebas_caballos "
                    + " SET hematrocito=?,  hemoglobina=?"
                    + " WHERE id_caballo=? and id_sangria_prueba=?; "
            );
            consulta.setBigDecimal(1, hematrocito);
            consulta.setBigDecimal(2, hemoglobina);
            consulta.setInt(3, id_caballo);
            consulta.setInt(4, id_sangria_prueba);

            if (consulta.executeUpdate() >= 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Ocurrió un error al procesar su solicitud.");
        }
        return resultado;

    }
    
    public List<SangriaPrueba> obtenerSangriasPruebasLimitadoConCaballos() {
        List<SangriaPrueba> resultado = new ArrayList<SangriaPrueba>();
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT sp.id_sangria_prueba, c.id_caballo, c.nombre, c.numero_microchip, c.numero "
                  + " FROM ( "
                  + "   SELECT id_sangria_prueba "
                  + "   FROM caballeriza.sangrias_pruebas sp"  
                  + "   WHERE id_sangria_prueba NOT IN (SELECT id_sangria_prueba FROM caballeriza.sangrias)"
                  + "      ) AS sp "
                  + " INNER JOIN caballeriza.sangrias_pruebas_caballos spc "
                  + "   ON spc.id_sangria_prueba = sp.id_sangria_prueba "
                  + " INNER JOIN caballeriza.caballos c "
                  + "   ON spc.id_caballo = c.id_caballo"
            );
            
            ResultSet rs = consulta.executeQuery();
            
            SangriaPrueba sp = null;
            
            while(rs.next()) {
                
                int id_sangria_prueba = rs.getInt("id_sangria_prueba");
                
                if (sp == null || sp.getId_sangria_prueba() != id_sangria_prueba) {
                    sp = new SangriaPrueba();
                    sp.setId_sangria_prueba(id_sangria_prueba);
                    resultado.add(sp);
                }
                
                Caballo c = new Caballo();
                c.setId_caballo(rs.getInt("id_caballo"));
                c.setNombre(rs.getString("nombre"));
                c.setNumero_microchip(rs.getString("numero_microchip"));
                c.setNumero(rs.getInt("numero"));
                sp.agregarCaballo(c);
            }
            
            rs.close();
            consulta.close();
            cerrarConexion();
                    
        } catch(SQLException ex) {
            
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
