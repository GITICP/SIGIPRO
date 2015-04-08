/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.caballeriza.modelos.SangriaCaballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPrueba;
import com.icp.sigipro.core.SIGIPROException;
import java.lang.reflect.Method;
import java.sql.Connection;
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
            if (s.getNum_inf_cc() == 0) {
                consulta_sangria.setNull(4, java.sql.Types.INTEGER);
            }
            else {
                consulta_sangria.setInt(4, s.getNum_inf_cc());
            }
            if (s.getPotencia() == 0.0f) {
                consulta_sangria.setNull(5, java.sql.Types.FLOAT);
            }
            else {
                consulta_sangria.setFloat(5, s.getPotencia());
            }

            rs_sangria = consulta_sangria.executeQuery();

            if (rs_sangria.next()) {
                resultado_sangria = true;
                s.setId_sangria(rs_sangria.getInt("id_sangria"));
            }
            else {
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
                if (resultado_sangria && resultado_caballos) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (rs_sangria != null) {
                    rs_sangria.close();
                }
                if (consulta_sangria != null) {
                    consulta_sangria.close();
                }
                if (consulta_caballos != null) {
                    consulta_caballos.close();
                }
                cerrarConexion();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos");
            }
        }

        return s;
    }

    public Sangria obtenerSangria(int id_sangria) throws SIGIPROException
    {
        Sangria sangria = new Sangria();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT s.*, sc.*, c.id_caballo, c.nombre, c.numero_microchip "
                    + " FROM (SELECT * FROM caballeriza.sangrias WHERE id_sangria = ?) AS s "
                    + "   INNER JOIN caballeriza.sangrias_caballos sc ON sc.id_sangria = s.id_sangria "
                    + "   INNER JOIN caballeriza.caballos c ON c.id_caballo = sc.id_caballo; "
            );

            consulta.setInt(1, id_sangria);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {

                sangria.setId_sangria(id_sangria);
                sangria.setCantidad_de_caballos(rs.getInt("cantidad_de_caballos"));
                sangria.setFecha_dia1(rs.getDate("fecha_dia1"));
                sangria.setFecha_dia2(rs.getDate("fecha_dia2"));
                sangria.setFecha_dia3(rs.getDate("fecha_dia3"));
                sangria.setHematrocito_promedio(rs.getFloat("hematrocito_promedio"));
                sangria.setNum_inf_cc(rs.getInt("num_inf_cc"));
                sangria.setPeso_plasma_total(rs.getFloat("peso_plasma_total"));
                sangria.setPlasma_por_caballo(rs.getFloat("plasma_por_caballo"));
                sangria.setPotencia(rs.getFloat("potencia"));
                sangria.setResponsable(rs.getString("responsable"));
                sangria.setSangre_total(rs.getFloat("sangre_total"));
                SangriaPrueba sangria_prueba = new SangriaPrueba();
                sangria_prueba.setId_sangria_prueba(rs.getInt("id_sangria_prueba"));
                sangria.setSangria_prueba(sangria_prueba);
                sangria.setVolumen_plasma_total(rs.getFloat("volumen_plasma_total"));

                do {

                    SangriaCaballo sangria_caballo = new SangriaCaballo();

                    Caballo caballo = new Caballo();
                    caballo.setId_caballo(rs.getInt("id_caballo"));
                    caballo.setNombre(rs.getString("nombre"));
                    caballo.setNumero_microchip(rs.getInt("numero_microchip"));
                    sangria_caballo.setCaballo(caballo);
                    sangria_caballo.setLal_dia1(rs.getFloat("lal_dia1"));
                    sangria_caballo.setLal_dia2(rs.getFloat("lal_dia2"));
                    sangria_caballo.setLal_dia3(rs.getFloat("lal_dia3"));
                    sangria_caballo.setPlasma_dia1(rs.getFloat("plasma_dia1"));
                    sangria_caballo.setPlasma_dia2(rs.getFloat("plasma_dia2"));
                    sangria_caballo.setPlasma_dia3(rs.getFloat("plasma_dia3"));
                    sangria_caballo.setSangre_dia1(rs.getFloat("sangre_dia1"));
                    sangria_caballo.setSangre_dia2(rs.getFloat("sangre_dia2"));
                    sangria_caballo.setSangre_dia3(rs.getFloat("sangre_dia3"));

                    sangria.agregarSangriaCaballo(sangria_caballo);

                }
                while (rs.next());
            }
            else {
                throw new SIGIPROException("La sangría que está intentando buscar no existe.");
            }

            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Ocurrió un error al procesar su solitud.");
        }
        return sangria;
    }

    public Sangria obtenerSangriaConCaballosDePrueba(int id_sangria) throws SIGIPROException
    {
        Sangria sangria = new Sangria();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT s.id_sangria, s.fecha_dia1, s.num_inf_cc, "
                    + "        s.responsable, s.potencia, sp.id_sangria_prueba, "
                    + "        c.id_caballo, c.nombre, c.numero_microchip, "
                    + "        CASE "
                    + "             WHEN c.id_caballo in (SELECT id_caballo FROM caballeriza.sangrias_caballos WHERE id_sangria = ?) THEN true "
                    + "             ELSE false "
                    + "         END AS incluido "
                    + " FROM (SELECT * FROM caballeriza.sangrias WHERE id_sangria = ?) AS s "
                    + " INNER JOIN caballeriza.sangrias_pruebas sp ON sp.id_sangria_prueba = s.id_sangria_prueba "
                    + " INNER JOIN caballeriza.sangrias_pruebas_caballos spc ON sp.id_sangria_prueba = spc.id_sangria_prueba "
                    + " INNER JOIN caballeriza.caballos c ON c.id_caballo = spc.id_caballo; "
            );

            consulta.setInt(1, id_sangria);
            consulta.setInt(2, id_sangria);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {

                sangria.setId_sangria(id_sangria);
                sangria.setFecha_dia1(rs.getDate("fecha_dia1"));
                sangria.setNum_inf_cc(rs.getInt("num_inf_cc"));
                sangria.setPotencia(rs.getFloat("potencia"));
                sangria.setResponsable(rs.getString("responsable"));
                SangriaPrueba sangria_prueba = new SangriaPrueba();
                sangria_prueba.setId_sangria_prueba(rs.getInt("id_sangria_prueba"));

                do {
                    Caballo caballo = new Caballo();
                    caballo.setId_caballo(rs.getInt("id_caballo"));
                    caballo.setNombre(rs.getString("nombre"));
                    caballo.setNumero_microchip(rs.getInt("numero_microchip"));

                    sangria_prueba.agregarCaballo(caballo);
                    if (rs.getBoolean("incluido")) {
                        SangriaCaballo sc = new SangriaCaballo();
                        sc.setCaballo(caballo);
                        sangria.agregarSangriaCaballo(sc);
                    }
                }
                while (rs.next());

                sangria.setSangria_prueba(sangria_prueba);
            }
            else {
                throw new SIGIPROException("La sangría que está intentando buscar no existe.");
            }

            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Ocurrió un error al procesar su solitud.");
        }

        return sangria;
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

    public Sangria registrarExtraccion(Sangria sangria, int dia) throws SIGIPROException
    {
        boolean resultado = false;
        boolean resultado_sangria = false;
        boolean resultado_preparacion = false;
        boolean resultado_sangrias_caballos = false;

        Method get_fecha;

        PreparedStatement consulta_preparacion = null;
        PreparedStatement consulta_sangrias_caballos = null;
        PreparedStatement update_sangria = null;

        try {
            get_fecha = Sangria.class.getDeclaredMethod("getFecha_dia" + dia, (Class<?>[]) null);
        }
        catch (Exception ex) {
            throw new SIGIPROException("Error inesperado. Contacte al administrador del sistema.");
        }

        try {

            getConexion().setAutoCommit(false);

            consulta_preparacion = getConexion().prepareStatement(
                    " UPDATE caballeriza.sangrias_caballos "
                    + " SET sangre_dia" + dia + " = 0,"
                    + "     plasma_dia" + dia + " = 0,"
                    + "     lal_dia" + dia + " = 0"
                    + " WHERE id_sangria = ?; "
            );

            consulta_preparacion.setInt(1, sangria.getId_sangria());

            int filas_actualizadas_preparacion = consulta_preparacion.executeUpdate();
            if (filas_actualizadas_preparacion >= 1) {
                resultado_preparacion = true;
            }

            consulta_sangrias_caballos = getConexion().prepareStatement(
                    " UPDATE caballeriza.sangrias_caballos "
                    + " SET sangre_dia" + dia + " = ?, "
                    + "     plasma_dia" + dia + " = ?, "
                    + "     lal_dia" + dia + " = ? "
                    + " WHERE id_sangria = ? AND id_caballo = ?; "
            );

            for (SangriaCaballo sangria_caballo : sangria.getSangrias_caballos()) {
                consulta_sangrias_caballos.setFloat(1, sangria_caballo.getSangre(dia));
                consulta_sangrias_caballos.setFloat(2, sangria_caballo.getPlasma(dia));
                consulta_sangrias_caballos.setFloat(3, sangria_caballo.getLal(dia));
                consulta_sangrias_caballos.setInt(4, sangria.getId_sangria());
                consulta_sangrias_caballos.setInt(5, sangria_caballo.getCaballo().getId_caballo());
                consulta_sangrias_caballos.addBatch();
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

            resultado = resultado_sangrias_caballos && resultado_preparacion && resultado_sangria;
        }
        catch (SQLException sql_ex) {
            sql_ex.printStackTrace();
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
                if( consulta_preparacion != null ) {
                    consulta_preparacion.close();
                }
                if( consulta_sangrias_caballos != null ) {
                    consulta_sangrias_caballos.close();
                }
                if( update_sangria != null ) {
                    update_sangria.close();
                }
                cerrarConexion();
            }
            catch (SQLException ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema.");
            }
        }
        return sangria;
    }

    public Sangria editarSangria(Sangria s) throws SIGIPROException
    {

        boolean resultado_sangria = false;
        boolean resultado_caballos = false;
        PreparedStatement consulta_sangria = null;
        PreparedStatement consulta_caballos = null;
        PreparedStatement eliminar_caballos = null;
        ResultSet rs_sangria = null;

        try {
            getConexion().setAutoCommit(false);

            consulta_sangria = getConexion().prepareStatement(
                    " UPDATE caballeriza.sangrias"
                    + " SET responsable = ?, cantidad_de_caballos = ?, num_inf_cc = ?, potencia = ? "
                    + " WHERE id_sangria = ? RETURNING fecha_dia1;"
            );

            consulta_sangria.setString(1, s.getResponsable());
            consulta_sangria.setInt(2, s.getSangrias_caballos().size());

            if (s.getNum_inf_cc() == 0) {
                consulta_sangria.setNull(3, java.sql.Types.INTEGER);
            }
            else {
                consulta_sangria.setInt(3, s.getNum_inf_cc());
            }
            if (s.getPotencia() == 0.0f) {
                consulta_sangria.setNull(4, java.sql.Types.FLOAT);
            }
            else {
                consulta_sangria.setFloat(4, s.getPotencia());
            }
            
            consulta_sangria.setInt(5, s.getId_sangria());
            rs_sangria = consulta_sangria.executeQuery();

            boolean realizar_insercion_caballos;

            if (rs_sangria.next()) {
                realizar_insercion_caballos = rs_sangria.getDate("fecha_dia1") == null;
                if (rs_sangria.next()) {
                    resultado_sangria = false;
                    realizar_insercion_caballos = false;
                }
                else {
                    resultado_sangria = true;
                }
            }
            else {
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
        }
        catch (SQLException ex) {
            throw new SIGIPROException("No se pudo registrar el Evento Clinico.");
        }
        finally {
            try {
                if (resultado_sangria && resultado_caballos) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (rs_sangria != null) {
                    rs_sangria.close();
                }
                if (consulta_sangria != null) {
                    consulta_sangria.close();
                }
                if (consulta_caballos != null) {
                    consulta_caballos.close();
                }
                if (eliminar_caballos != null) {
                    eliminar_caballos.close();
                }
                cerrarConexion();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos");
            }
        }
        return s;
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
