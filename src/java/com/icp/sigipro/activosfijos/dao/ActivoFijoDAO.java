package com.icp.sigipro.activosfijos.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.activosfijos.modelos.ActivoFijo;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.util.PSQLException;

/**
 *
 * @author Walter
 */
//Cada DAO tiene su propia conexion a la Bitacora
public class ActivoFijoDAO
{

    private Connection conexion;

    public ActivoFijoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarActivoFijo(ActivoFijo a) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.activos_fijos (placa, equipo, marca, fecha_movimiento, id_seccion, id_ubicacion, fecha_registro, estado, responsable, numero_serie) "
                                                                        + " VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id_activo_fijo");

            consulta.setString(1, a.getPlaca());
            consulta.setString(2, a.getEquipo());
            consulta.setString(3, a.getMarca());
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String strFecha = a.getFecha_movimiento();
            Date fecha = null;
            try {
                fecha = formatoFecha.parse(strFecha);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            java.sql.Date fMovimientoSQL = new java.sql.Date(fecha.getTime());
            consulta.setDate(4, fMovimientoSQL);
            consulta.setInt(5, a.getId_seccion());
            consulta.setInt(6, a.getId_ubicacion());
            java.util.Date fRegistro = formatoFecha.parse(a.getFecha_registro());
            java.sql.Date fRegistroSQL = new java.sql.Date(fRegistro.getTime());
            consulta.setDate(7, fRegistroSQL);
            consulta.setString(8, a.getEstado());
            consulta.setString(9, a.getResponsable());
            consulta.setString(10, a.getSerie());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                a.setId_activo_fijo(resultadoConsulta.getInt("id_activo_fijo"));
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (PSQLException ex) {
            throw new SIGIPROException("Esta placa ya existe en el sistema");
        }
        catch (Exception ex) {
            Logger.getLogger(ActivoFijoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public boolean editarActivoFijo(ActivoFijo a)
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE bodega.activos_fijos SET placa=?, equipo=?, marca=?, fecha_movimiento=?, "
                    + " id_seccion=?, id_ubicacion=?, fecha_registro=?, estado=?, responsable=?, numero_serie=? "
                    + " WHERE id_activo_fijo=?; "
            );
            consulta.setString(1, a.getPlaca());
            consulta.setString(2, a.getEquipo());
            consulta.setString(3, a.getMarca());
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String strFecha = a.getFecha_movimiento();
            Date fecha = null;
            try {
                fecha = formatoFecha.parse(strFecha);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            java.sql.Date fMovimientoSQL = new java.sql.Date(fecha.getTime());
            consulta.setDate(4, fMovimientoSQL);
            consulta.setInt(5, a.getId_seccion());
            consulta.setInt(6, a.getId_ubicacion());
            java.util.Date fRegistro = formatoFecha.parse(a.getFecha_registro());
            java.sql.Date fRegistroSQL = new java.sql.Date(fRegistro.getTime());
            consulta.setDate(7, fRegistroSQL);
            consulta.setString(8, a.getEstado());
            consulta.setString(9, a.getResponsable());
            consulta.setString(10, a.getSerie());
            consulta.setInt(11, a.getId_activo_fijo());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarActivoFijo(int id_activo_fijo) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bodega.activos_fijos "
                    + " WHERE id_activo_fijo=?; "
            );

            consulta.setInt(1, id_activo_fijo);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            else {
                throw new SIGIPROException("El activo que está intentando eliminar no existe.");
            }
            consulta.close();
            cerrarConexion();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public ActivoFijo obtenerActivoFijo(int id_activo_fijo)
    {

        ActivoFijo activo = new ActivoFijo();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.activos_fijos where id_activo_fijo = ?");

            consulta.setInt(1, id_activo_fijo);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                activo.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                activo.setPlaca(rs.getString("placa"));
                activo.setEquipo(rs.getString("equipo"));
                activo.setMarca(rs.getString("marca"));
                activo.setFecha_movimiento(rs.getDate("fecha_movimiento"));
                activo.setId_seccion(rs.getInt("id_seccion"));
                activo.setId_ubicacion(rs.getInt("id_ubicacion"));
                activo.setFecha_registro(rs.getDate("fecha_registro"));
                activo.setEstado(rs.getString("estado"));
                PreparedStatement consultaSeccion = conexion.prepareStatement(" Select nombre_seccion "
                                                                              + " From seguridad.secciones"
                                                                              + " Where id_seccion = ? ");
                consultaSeccion.setInt(1, rs.getInt("id_seccion"));
                ResultSet resultadoConsultaSeccion = consultaSeccion.executeQuery();
                resultadoConsultaSeccion.next();
                String seccion = resultadoConsultaSeccion.getString("nombre_seccion");
                activo.setNombre_seccion(seccion);
                PreparedStatement consultaUbicacion = conexion.prepareStatement(" Select nombre "
                                                                                + " From bodega.ubicaciones"
                                                                                + " Where id_ubicacion = ? ");
                consultaUbicacion.setInt(1, rs.getInt("id_ubicacion"));
                ResultSet resultadoConsultaUbicacion = consultaUbicacion.executeQuery();
                resultadoConsultaUbicacion.next();
                String ubicacion = resultadoConsultaUbicacion.getString("nombre");
                activo.setNombre_ubicacion(ubicacion);
                activo.setResponsable(rs.getString("responsable"));
                activo.setSerie(rs.getString("numero_serie"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return activo;
    }

    public List<ActivoFijo> obtenerActivosFijos()
    {

        List<ActivoFijo> resultado = new ArrayList<ActivoFijo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("select * from bodega.activos_fijos af left outer join seguridad.secciones s on s.id_seccion = af.id_seccion left outer join bodega.ubicaciones u on u.id_ubicacion = af.id_ubicacion order by af.id_activo_fijo asc ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                ActivoFijo activo = new ActivoFijo();
                activo.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                activo.setPlaca(rs.getString("placa"));
                activo.setEquipo(rs.getString("equipo"));
                activo.setMarca(rs.getString("marca"));
                activo.setFecha_movimiento(rs.getDate("fecha_movimiento"));
                activo.setNombre_seccion(rs.getString("nombre_seccion"));
                activo.setNombre_ubicacion(rs.getString("nombre"));
                activo.setFecha_registro(rs.getDate("fecha_registro"));
                activo.setEstado(rs.getString("estado"));
                activo.setResponsable(rs.getString("responsable"));
                activo.setSerie(rs.getString("numero_serie"));

                resultado.add(activo);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarMasivo(String ids) throws SIGIPROException
    {
        boolean resultado = false;
        try {
            getConexion().setAutoCommit(false);

            PreparedStatement consulta_eliminar = getConexion().prepareStatement("DELETE FROM bodega.activos_fijos WHERE id_activo_fijo = ?;");

            String[] ids_parseados = parsearAsociacion("#af#", ids);

            for (int i = 0; i < ids_parseados.length; i++) {
                consulta_eliminar.setInt(1, Integer.parseInt(ids_parseados[i]));
                consulta_eliminar.addBatch();
            }

            int[] resultados = consulta_eliminar.executeBatch();
            consulta_eliminar.close();

            boolean ciclo_break = false;

            for (int i = 0; i < resultados.length; i++) {
                if (resultados[i] != 1) {
                    ciclo_break = true;
                    break;
                }
            }
            

            if (ciclo_break) {
                resultado = false;
            }
            else {
                resultado = true;
            }

        }
        catch (SQLException ex) {
            try {
                getConexion().rollback();

                // Mapear mensaje.
                throw new SIGIPROException("No se pudo eliminar debido a que el activo fijo se referencia con otros registros.");
            }
            catch (SQLException roll_ex) {
                throw new SIGIPROException("Error de conexión con la base de datos.");
            }
        }
        finally {
            try {
                if (resultado) {
                    getConexion().commit();
                    getConexion().close();
                }
                else {
                    getConexion().rollback();
                    getConexion().close();
                    throw new SIGIPROException("Ocurrió un error a la hora de eliminar los activos fijos. Inténtelo nuevamente.");
                }
            }
            catch (SQLException roll_ex) {
                throw new SIGIPROException("Error de conexión con la base de datos.");
            }
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
