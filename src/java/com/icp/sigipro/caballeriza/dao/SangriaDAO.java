/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.caballeriza.modelos.SangriaCaballo;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class SangriaDAO extends DAO
{

    public SangriaDAO() {
    }

    public Sangria insertarSangria(Sangria s) throws SIGIPROException {

        boolean resultado_sangria = false;
        boolean resultado_caballos = false;
        PreparedStatement consulta_sangria = null;
        PreparedStatement consulta_caballos = null;
        ResultSet rs_sangria = null;
        CallableStatement actualizar_estadisticas = null;

        try {
            getConexion().setAutoCommit(false);

            consulta_sangria = getConexion().prepareStatement(
                    " INSERT INTO caballeriza.sangrias(responsable, cantidad_de_caballos, num_inf_cc, potencia, volumen_plasma_total, id_grupo_caballos, fecha) "
                    + " VALUES (?,?,?,?,?,?, current_date) RETURNING id_sangria;"
            );

            consulta_sangria.setInt(1, s.getResponsable().getId_usuario());
            consulta_sangria.setInt(2, s.getSangrias_caballos().size());

            if (s.getNum_inf_cc() == 0) {
                consulta_sangria.setNull(3, java.sql.Types.INTEGER);
            } else {
                consulta_sangria.setInt(3, s.getNum_inf_cc());
            }
            if (s.getPotencia() == 0.0f) {
                consulta_sangria.setNull(4, java.sql.Types.FLOAT);
            } else {
                consulta_sangria.setFloat(4, s.getPotencia());
            }
            if (s.getVolumen_plasma_total() == 0.0f) {
                consulta_sangria.setNull(5, java.sql.Types.FLOAT);
            } else {
                consulta_sangria.setFloat(5, s.getVolumen_plasma_total());
            }
            consulta_sangria.setInt(6, s.getGrupo().getId_grupo_caballo());
            rs_sangria = consulta_sangria.executeQuery();

            if (rs_sangria.next()) {
                resultado_sangria = true;
                s.setId_sangria(rs_sangria.getInt("id_sangria"));
            } else {
                resultado_sangria = false;
            }

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

            actualizar_estadisticas = getConexion().prepareCall(" { call caballeriza.actualizar_estadisticas_sangria( ? ) } ");

            actualizar_estadisticas.setInt(1, s.getId_sangria());
            actualizar_estadisticas.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("No se pudo la sangría correctamente.");
        } finally {
            try {
                if (resultado_sangria && resultado_caballos) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos");
            }
            cerrarSilencioso(actualizar_estadisticas);
            cerrarSilencioso(rs_sangria);
            cerrarSilencioso(consulta_sangria);
            cerrarSilencioso(consulta_caballos);
            cerrarConexion();
        }

        return s;
    }

    public Sangria obtenerSangria(int id_sangria) throws SIGIPROException {
        Sangria sangria = new Sangria();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT s.*, sc.*, c.id_caballo, c.nombre, c.numero_microchip, c.numero, u.nombre_completo, u.id_usuario, fecha, gc.nombre as nombre_grupo "
                    + " FROM (SELECT * FROM caballeriza.sangrias WHERE id_sangria = ?) AS s "
                    + "   INNER JOIN caballeriza.sangrias_caballos sc ON sc.id_sangria = s.id_sangria "
                    + "   INNER JOIN caballeriza.caballos c ON c.id_caballo = sc.id_caballo "
                    + "   INNER JOIN seguridad.usuarios u ON s.responsable = u.id_usuario "
                    + "   INNER JOIN caballeriza.grupos_de_caballos gc ON gc.id_grupo_de_caballo = s.id_grupo_caballos;"
            );

            consulta.setInt(1, id_sangria);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {

                sangria.setId_sangria(id_sangria);
                sangria.setFecha(rs.getDate("fecha"));
                sangria.setCantidad_de_caballos(rs.getInt("cantidad_de_caballos"));
                sangria.setFecha_dia1(rs.getDate("fecha_dia1"));
                sangria.setFecha_dia2(rs.getDate("fecha_dia2"));
                sangria.setFecha_dia3(rs.getDate("fecha_dia3"));
                sangria.setNum_inf_cc(rs.getInt("num_inf_cc"));
                sangria.setPeso_plasma_total(rs.getFloat("peso_plasma_total"));
                sangria.setPlasma_por_caballo(rs.getFloat("plasma_por_caballo"));
                sangria.setPotencia(rs.getFloat("potencia"));
                sangria.setSangre_total(rs.getFloat("sangre_total"));
                sangria.setVolumen_plasma_total(rs.getFloat("volumen_plasma_total"));
                Usuario usuario = new Usuario();
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setId_usuario(rs.getInt("id_usuario"));
                sangria.setResponsable(usuario);
                GrupoDeCaballos g = new GrupoDeCaballos();
                g.setNombre(rs.getString("nombre_grupo"));
                sangria.setGrupo(g);

                do {

                    SangriaCaballo sangria_caballo = new SangriaCaballo();

                    Caballo caballo = new Caballo();
                    caballo.setId_caballo(rs.getInt("id_caballo"));
                    caballo.setNombre(rs.getString("nombre"));
                    caballo.setNumero_microchip(rs.getString("numero_microchip"));
                    caballo.setNumero(rs.getInt("numero"));
                    sangria_caballo.setCaballo(caballo);
                    sangria_caballo.setParticipo_dia1((Boolean)rs.getObject("participo_dia1"));
                    sangria_caballo.setParticipo_dia2((Boolean)rs.getObject("participo_dia2"));
                    sangria_caballo.setParticipo_dia3((Boolean)rs.getObject("participo_dia3"));
                    sangria_caballo.setObservaciones_dia1(rs.getString("observaciones_dia1")); // Esta línea se cambió
                    sangria_caballo.setObservaciones_dia2(rs.getString("observaciones_dia2")); // Esta línea se cambió
                    sangria_caballo.setObservaciones_dia3(rs.getString("observaciones_dia3")); // Esta línea se cambió
                    sangria_caballo.setPlasma_dia1(rs.getFloat("plasma_dia1"));
                    sangria_caballo.setPlasma_dia2(rs.getFloat("plasma_dia2"));
                    sangria_caballo.setPlasma_dia3(rs.getFloat("plasma_dia3"));
                    sangria_caballo.setSangre_dia1(rs.getFloat("sangre_dia1"));
                    sangria_caballo.setSangre_dia2(rs.getFloat("sangre_dia2"));
                    sangria_caballo.setSangre_dia3(rs.getFloat("sangre_dia3"));

                    sangria.agregarSangriaCaballo(sangria_caballo);

                } while (rs.next());
            } else {
                throw new SIGIPROException("La sangría que está intentando buscar no existe.");
            }

            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Ocurrió un error al procesar su solitud.");
        }
        return sangria;
    }

    public Sangria obtenerSangriaConCaballosDeGrupo(int id_sangria) throws SIGIPROException {
        Sangria sangria = new Sangria();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT s.id_sangria, s.fecha_dia1, s.num_inf_cc, s.volumen_plasma_total, g.nombre as nombre_grupo, g.id_grupo_de_caballo, u.nombre_completo, u.id_usuario,"
                    + "                             s.potencia, fecha,"
                    + "                             c.id_caballo, c.nombre, c.numero, c.numero_microchip, "
                    + "                             CASE "
                    + "                                  WHEN c.id_caballo in (SELECT id_caballo FROM caballeriza.sangrias_caballos WHERE id_sangria = ?) THEN true "
                    + "                                  ELSE false "
                    + "                              END AS incluido "
                    + "                      FROM (SELECT * FROM caballeriza.sangrias WHERE id_sangria = ?) AS s "
                    + "                      INNER JOIN seguridad.usuarios u ON u.id_usuario = s.responsable "
                    + "                      INNER JOIN caballeriza.grupos_de_caballos g ON g.id_grupo_de_caballo = s.id_grupo_caballos"
                    + "                      INNER JOIN caballeriza.caballos c ON c.id_grupo_de_caballo = g.id_grupo_de_caballo; "
            );

            consulta.setInt(1, id_sangria);
            consulta.setInt(2, id_sangria);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {

                sangria.setId_sangria(id_sangria);
                sangria.setFecha(rs.getDate("fecha"));
                sangria.setFecha_dia1(rs.getDate("fecha_dia1"));
                sangria.setNum_inf_cc(rs.getInt("num_inf_cc"));
                sangria.setPotencia(rs.getFloat("potencia"));
                Usuario u = new Usuario();
                u.setNombreCompleto(rs.getString("nombre_completo"));
                u.setId_usuario(rs.getInt("id_usuario"));
                sangria.setResponsable(u);
                sangria.setVolumen_plasma_total(rs.getFloat("volumen_plasma_total"));

                GrupoDeCaballos g = new GrupoDeCaballos();
                g.setId_grupo_caballo(rs.getInt("id_grupo_de_caballo"));
                g.setNombre(rs.getString("nombre_grupo"));

                do {
                    Caballo caballo = new Caballo();
                    caballo.setId_caballo(rs.getInt("id_caballo"));
                    caballo.setNombre(rs.getString("nombre"));
                    caballo.setNumero_microchip(rs.getString("numero_microchip"));
                    caballo.setNumero(rs.getInt("numero"));

                    g.agregarCaballo(caballo);
                    if (rs.getBoolean("incluido")) {
                        SangriaCaballo sc = new SangriaCaballo();
                        sc.setCaballo(caballo);
                        sangria.agregarSangriaCaballo(sc);
                    }
                } while (rs.next());

                sangria.setGrupo(g);
            } else {
                throw new SIGIPROException("La sangría que está intentando buscar no existe.");
            }

            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Ocurrió un error al procesar su solitud.");
        }

        return sangria;
    }

    public List<Sangria> obtenerSangrias() throws SIGIPROException {
        List<Sangria> resultado = new ArrayList<Sangria>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT s.*, u.nombre_completo, u.id_usuario, gc.nombre "
                    + " FROM caballeriza.sangrias s "
                    + " INNER JOIN seguridad.usuarios u ON s.responsable = u.id_usuario"
                    + " INNER JOIN caballeriza.grupos_de_caballos gc ON gc.id_grupo_de_caballo = s.id_grupo_caballos;"
            );
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                Sangria sangria = new Sangria();
                sangria.setId_sangria(rs.getInt("id_sangria"));
                sangria.setFecha(rs.getDate("fecha"));
                sangria.setFecha_dia1(rs.getDate("fecha_dia1"));
                sangria.setFecha_dia2(rs.getDate("fecha_dia2"));
                sangria.setFecha_dia3(rs.getDate("fecha_dia3"));
                sangria.setNum_inf_cc(rs.getInt("num_inf_cc"));
                sangria.setCantidad_de_caballos(rs.getInt("cantidad_de_caballos"));
                sangria.setSangre_total(rs.getFloat("sangre_total"));
                sangria.setPeso_plasma_total(rs.getFloat("peso_plasma_total"));
                sangria.setVolumen_plasma_total(rs.getFloat("volumen_plasma_total"));
                sangria.setPlasma_por_caballo(rs.getFloat("plasma_por_caballo"));
                sangria.setPotencia(rs.getFloat("potencia"));
                resultado.add(sangria);
                Usuario usuario = new Usuario();
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setId_usuario(rs.getInt("id_usuario"));
                sangria.setResponsable(usuario);
                GrupoDeCaballos g = new GrupoDeCaballos();
                g.setNombre(rs.getString("nombre"));
                sangria.setGrupo(g);
            }
            rs.close();
            consulta.close();
            cerrarConexion();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Las Sangrias no pueden ser accedidas.");
        }
        return resultado;
    }

    public Sangria registrarExtraccion(Sangria sangria, int dia) throws SIGIPROException {
        boolean resultado = false;
        boolean resultado_sangria = false;
        boolean resultado_sangrias_caballos = false;

        Method get_fecha;

        PreparedStatement consulta_preparacion = null;
        PreparedStatement consulta_sangrias_caballos = null;
        PreparedStatement update_sangria = null;
        CallableStatement actualizar_estadisticas = null;

        try {
            get_fecha = Sangria.class.getDeclaredMethod("getFecha_dia" + dia, (Class<?>[]) null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error inesperado. Contacte al administrador del sistema.");
        }

        try {

            getConexion().setAutoCommit(false);

            if (sangria.getSangrias_caballos() != null) {
                consulta_preparacion = getConexion().prepareStatement(
                        " UPDATE caballeriza.sangrias_caballos "
                        + " SET sangre_dia" + dia + " = 0,"
                        + "     plasma_dia" + dia + " = 0,"
                        + "     observaciones_dia" + dia + " = ''," // Esta línea cambió.
                        + "     participo_dia" + dia + " = false "
                        + " WHERE id_sangria = ? AND id_caballo NOT IN " + this.pasar_ids_a_parentesis(sangria.obtener_ids_caballos_sangria()) + ";"
                );
                
                consulta_preparacion.setInt(1, sangria.getId_sangria());

                consulta_preparacion.executeUpdate();

                consulta_sangrias_caballos = getConexion().prepareStatement(
                        " UPDATE caballeriza.sangrias_caballos "
                        + " SET sangre_dia" + dia + " = ?, "
                        + "     plasma_dia" + dia + " = ?, "
                        + "     observaciones_dia" + dia + " = ?, " // Esta línea se cambió
                        + "     participo_dia" + dia + " = true "
                        + " WHERE id_sangria = ? AND id_caballo = ?; "
                );

                for (SangriaCaballo sangria_caballo : sangria.getSangrias_caballos()) {
                    if (sangria_caballo.sumatoria(dia) > 0.0f) {
                        consulta_sangrias_caballos.setFloat(1, sangria_caballo.getSangre(dia));
                        consulta_sangrias_caballos.setFloat(2, sangria_caballo.getPlasma(dia));
                        consulta_sangrias_caballos.setString(3, sangria_caballo.getObservaciones(dia)); // Esta línea se cambió
                        consulta_sangrias_caballos.setInt(4, sangria.getId_sangria());
                        consulta_sangrias_caballos.setInt(5, sangria_caballo.getCaballo().getId_caballo());
                        consulta_sangrias_caballos.addBatch();
                    }
                }

                int[] resultados_caballos = consulta_sangrias_caballos.executeBatch();

                boolean iteracion_completa = true;

                for (int i : resultados_caballos) {
                    if (i != 1) {
                        iteracion_completa = false;
                    }
                }

                if (iteracion_completa) {
                    resultado_sangrias_caballos = true;
                }
            } else {
                resultado_sangrias_caballos = true;
            }

            update_sangria = getConexion().prepareStatement(
                    " UPDATE caballeriza.sangrias "
                    + " SET fecha_dia" + dia + " = ? "
                    + " WHERE id_sangria = ?"
            );

            update_sangria.setDate(1, (Date) get_fecha.invoke(sangria, (Object[]) null));
            update_sangria.setInt(2, sangria.getId_sangria());

            if (update_sangria.executeUpdate() == 1) {
                resultado_sangria = true;
            }

            actualizar_estadisticas = getConexion().prepareCall(" { call caballeriza.actualizar_estadisticas_sangria( ? ) } ");

            actualizar_estadisticas.setInt(1, sangria.getId_sangria());
            actualizar_estadisticas.execute();

            resultado = resultado_sangrias_caballos && resultado_sangria;
        } catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultado) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema.");
            }
            cerrarSilencioso(actualizar_estadisticas);
            cerrarSilencioso(consulta_preparacion);
            cerrarSilencioso(consulta_sangrias_caballos);
            cerrarSilencioso(update_sangria);
            cerrarConexion();
        }
        return sangria;
    }

    public Sangria editarSangria(Sangria s) throws SIGIPROException {

        boolean resultado_sangria = false;
        boolean resultado_caballos = false;
        PreparedStatement consulta_sangria = null;
        PreparedStatement consulta_caballos = null;
        PreparedStatement eliminar_caballos = null;
        ResultSet rs_sangria = null;

        try {
            getConexion().setAutoCommit(false);
            boolean edicion_caballos = s.getSangrias_caballos() != null;

            String consulta = " UPDATE caballeriza.sangrias"
                              + " SET responsable = ?, num_inf_cc = ?, potencia = ?, volumen_plasma_total = ? ";
            if (edicion_caballos) {
                consulta += ", cantidad_de_caballos = ? ";
            }
            consulta += " WHERE id_sangria = ? RETURNING fecha_dia1;";

            consulta_sangria = getConexion().prepareStatement(consulta);

            consulta_sangria.setInt(1, s.getResponsable().getId_usuario());

            if (s.getNum_inf_cc() == 0) {
                consulta_sangria.setNull(2, java.sql.Types.INTEGER);
            } else {
                consulta_sangria.setInt(2, s.getNum_inf_cc());
            }
            if (s.getPotencia() == 0.0f) {
                consulta_sangria.setNull(3, java.sql.Types.FLOAT);
            } else {
                consulta_sangria.setFloat(3, s.getPotencia());
            }
            if (s.getVolumen_plasma_total() == 0.0f) {
                consulta_sangria.setNull(4, java.sql.Types.FLOAT);
            } else {
                consulta_sangria.setFloat(4, s.getVolumen_plasma_total());
            }
            if (edicion_caballos) {
                consulta_sangria.setInt(5, s.getSangrias_caballos().size());
                consulta_sangria.setInt(6, s.getId_sangria());
            } else {
                consulta_sangria.setInt(5, s.getId_sangria());
            }

            rs_sangria = consulta_sangria.executeQuery();

            boolean realizar_insercion_caballos;

            if (rs_sangria.next()) {
                realizar_insercion_caballos = rs_sangria.getDate("fecha_dia1") == null;
                if (rs_sangria.next()) {
                    resultado_sangria = false;
                    realizar_insercion_caballos = false;
                } else {
                    resultado_sangria = true;
                }
            } else {
                resultado_sangria = false;
                resultado_caballos = false;
                realizar_insercion_caballos = false;
            }
            rs_sangria.close();

            if (realizar_insercion_caballos) {

                eliminar_caballos = getConexion().prepareStatement(
                        " DELETE FROM caballeriza.sangrias_caballos WHERE id_sangria = ?; "
                );

                eliminar_caballos.setInt(1, s.getId_sangria());
                eliminar_caballos.executeUpdate();

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
            } else {
                resultado_caballos = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("No se pudo registrar la sangría.");
        } finally {
            try {
                if (resultado_sangria && resultado_caballos) {
                    getConexion().commit();
                } else {
                    getConexion().rollback();
                }
            } catch (SQLException sql_ex) {
                sql_ex.printStackTrace();
                throw new SIGIPROException("Error de comunicación con la base de datos");
            }
            cerrarSilencioso(rs_sangria);
            cerrarSilencioso(consulta_sangria);
            cerrarSilencioso(consulta_caballos);
            cerrarSilencioso(eliminar_caballos);
            cerrarConexion();
        }
        return s;
    }
}
