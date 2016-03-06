/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
import com.icp.sigipro.caballeriza.modelos.SangriaPruebaAJAX;
import com.icp.sigipro.caballeriza.modelos.SangriaPruebaCaballo;
import com.icp.sigipro.controlcalidad.modelos.Informe;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class SangriaPruebaDAO extends DAO {

    public SangriaPruebaDAO() {
    }

    public boolean insertarSangriaPrueba(SangriaPrueba sp) throws SIGIPROException {
        boolean resultado_insert_sangriap = false;
        boolean resultado_asociacion_caballos = false;
        PreparedStatement consulta_caballos = null;
        PreparedStatement consulta = null;
        ResultSet resultadoConsulta = null;
        try {
            getConexion().setAutoCommit(false);
            consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.sangrias_pruebas "
                    + " (fecha, id_usuario) "
                    + " VALUES (?,?) RETURNING id_sangria_prueba;");

            consulta.setDate(1, sp.getFecha());
            consulta.setInt(2, sp.getUsuario().getID());

            resultadoConsulta = consulta.executeQuery();

            if (resultadoConsulta.next()) {
                resultado_insert_sangriap = true;
                sp.setId_sangria_prueba(resultadoConsulta.getInt("id_sangria_prueba"));
            }

            if (sp.getLista_sangrias_prueba_caballo().size() > 0) {
                consulta_caballos = getConexion().prepareStatement(" INSERT INTO caballeriza.sangrias_pruebas_caballos (id_sangria_prueba, id_caballo) VALUES (?,?);");

                for (SangriaPruebaCaballo informacion_caballo : sp.getLista_sangrias_prueba_caballo()) {
                    consulta_caballos.setInt(1, sp.getId_sangria_prueba());
                    consulta_caballos.setInt(2, informacion_caballo.getCaballo().getId_caballo());
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
            } else {
                resultado_asociacion_caballos = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("No se pudo registrar la sangría de prueba.");
        } finally {
            try {
                if (resultado_insert_sangriap && resultado_asociacion_caballos) {
                    getConexion().commit();
                } else {
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
            } catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos.");
            }
        }
        return resultado_insert_sangriap && resultado_asociacion_caballos;
    }

    public List<SangriaPrueba> obtenerSangriasPruebas() throws SIGIPROException {
        List<SangriaPrueba> resultado = new ArrayList<SangriaPrueba>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT sp.*, u.nombre_completo "
                    + " FROM caballeriza.sangrias_pruebas sp "
                    + "     INNER JOIN seguridad.usuarios u ON sp.id_usuario = u.id_usuario ");
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                SangriaPrueba sangriap = new SangriaPrueba();
                sangriap.setId_sangria_prueba(rs.getInt("id_sangria_prueba"));
                sangriap.setFecha(rs.getDate("fecha"));
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                sangriap.setUsuario(u);
                resultado.add(sangriap);
            }
            consulta.close();
            cerrarConexion();
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Los eventos clínicos no pueden ser accedidos.");
        }
        return resultado;
    }

    public List<Caballo> obtenerCaballosSangriaP(int id_sangria_prueba) throws SIGIPROException {
        List<Caballo> resultado = new ArrayList<Caballo>();

        PreparedStatement consulta = null;
        ResultSet rs = null;
        
        try {
            consulta = getConexion().prepareStatement(
                  " SELECT c.nombre, c.numero, c.id_caballo "
                + " FROM caballeriza.caballos c "
                + "     LEFT OUTER JOIN caballeriza.sangrias_pruebas_caballos ecc on c.id_caballo = ecc.id_caballo "
                + " WHERE id_sangria_prueba = ?; "
            );
            consulta.setInt(1, id_sangria_prueba);
            rs = consulta.executeQuery();

            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero(rs.getInt("numero"));
                resultado.add(caballo);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }        
        return resultado;
    }

    public SangriaPrueba obtenerSangriaPrueba(int id_sangria_prueba) throws SIGIPROException {
        SangriaPrueba sp = new SangriaPrueba();
        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT sp.*, csp.*, c.numero, c.nombre, u.nombre_completo, rasp.hematocrito, rasp.hemoglobina, i.id_informe, s.id_solicitud, s.numero_solicitud "
                    + " FROM caballeriza.sangrias_pruebas sp "
                    + "     INNER JOIN seguridad.usuarios u ON sp.id_usuario = u.id_usuario "
                    + "     INNER JOIN caballeriza.sangrias_pruebas_caballos csp ON sp.id_sangria_prueba = csp.id_sangria_prueba "
                    + "     INNER JOIN caballeriza.caballos c ON csp.id_caballo = c.id_caballo "
                    + "     LEFT JOIN control_calidad.resultados_analisis_sangrias_prueba rasp ON csp.id_resultado = rasp.id_resultado_analisis_sp "
                    + "     LEFT JOIN control_calidad.informes i ON i.id_informe = sp.id_informe "
                    + "     LEFT JOIN control_calidad.solicitudes s ON s.id_solicitud = i.id_solicitud "
                    + " WHERE sp.id_sangria_prueba = ?; "
            );

            consulta.setInt(1, id_sangria_prueba);
            rs = consulta.executeQuery();

            if (rs.next()) {
                sp.setId_sangria_prueba(id_sangria_prueba);
                sp.setFecha(rs.getDate("fecha"));
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombreCompleto(rs.getString("nombre_completo"));
                sp.setUsuario(u);
                
                if (rs.getInt("id_informe") != 0) {
                    Informe i = new Informe();
                    i.setId_informe(rs.getInt("id_informe"));
                    SolicitudCC s = new SolicitudCC();
                    s.setId_solicitud(rs.getInt("id_solicitud"));
                    s.setNumero_solicitud(rs.getString("numero_solicitud"));
                    i.setSolicitud(s);
                    sp.setInforme(i);
                }

                List<SangriaPruebaCaballo> caballos = new ArrayList<>();

                do {
                    SangriaPruebaCaballo informacion_caballo = new SangriaPruebaCaballo();
                    Caballo c = new Caballo();
                    c.setId_caballo(rs.getInt("id_caballo"));
                    c.setNombre(rs.getString("nombre"));
                    c.setNumero(rs.getInt("numero"));
                    informacion_caballo.setCaballo(c);
                    informacion_caballo.setSangria_prueba(sp);
                    informacion_caballo.setHematocrito(rs.getFloat("hematocrito"));
                    informacion_caballo.setHemoglobina(rs.getFloat("hemoglobina"));
                    caballos.add(informacion_caballo);
                } while (rs.next());

                sp.setLista_sangrias_prueba_caballo(caballos);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("La sangría de prueba no puede ser obtenida.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return sp;
    }

    public int obtenerIdSangriaPrueba(String muestra) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id_sangriap;
    }

    public boolean editarSangriaPruebaCaballo(int id_sangria_prueba, int id_caballo, float hematrocitop, float hemoglobinap) throws SIGIPROException {
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
            cerrarConexion();
        } catch (Exception ex) {
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

            while (rs.next()) {

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
                //sp.agregarCaballo(c);
            }

            rs.close();
            consulta.close();
            cerrarConexion();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultado;
    }

    public List<SangriaPruebaAJAX> obtenerSangriasPruebaPendiente() throws SIGIPROException {
        List<SangriaPruebaAJAX> resultado = new ArrayList<SangriaPruebaAJAX>();

        PreparedStatement consulta = null;
        ResultSet rs = null;

        try {
            consulta = getConexion().prepareStatement(
                    " SELECT sp.id_sangria_prueba, c.id_caballo, c.numero "
                    + " FROM caballeriza.sangrias_pruebas sp "
                    + "       INNER JOIN caballeriza.sangrias_pruebas_caballos spc ON sp.id_sangria_prueba = spc.id_sangria_prueba "
                    + "       INNER JOIN caballeriza.caballos c ON c.id_caballo = spc.id_caballo "
                    + " WHERE sp.id_informe is null; "
            );

            rs = consulta.executeQuery();

            SangriaPruebaAJAX s_ax = new SangriaPruebaAJAX();

            while (rs.next()) {

                if (s_ax.getId_sangria_prueba() != rs.getInt("id_sangria_prueba")) {
                    s_ax = new SangriaPruebaAJAX();
                    s_ax.setId_sangria_prueba(rs.getInt("id_sangria_prueba"));
                    resultado.add(s_ax);
                }

                Caballo c = new Caballo();
                c.setId_caballo(rs.getInt("id_caballo"));
                c.setNumero(rs.getInt("numero"));
                s_ax.agregarCaballo(c);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener las sangrías.");
        } finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }
}
