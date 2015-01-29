package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.ActivoFijo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Walter
 */
public class ActivoFijoDAO {

    private Connection conexion;

    public ActivoFijoDAO() {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarActivoFijo(ActivoFijo a) {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.activos_fijos (placa, equipo, marca, fecha_movimiento, id_seccion, id_ubicacion, fecha_registro, estado) "
                    + " VALUES (?,?,?,?,?,?,?,?) RETURNING id_activo_fijo");

            consulta.setInt(1, a.getPlaca());
            consulta.setInt(2, a.getEquipo());
            consulta.setString(3, a.getMarca());
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            String strFecha = a.getFecha_movimiento();
            Date fecha = null;
            try {

                fecha = formatoFecha.parse(strFecha);

            } catch (Exception ex) {

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
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                a.setId_activo_fijo(resultadoConsulta.getInt("id_activo_fijo"));
            }
            consulta.close();
            conexion.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarActivoFijo(int id_activo_fijo) {

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
            consulta.close();
            conexion.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public ActivoFijo obtenerActivoFijo(int id_activo_fijo) {

        ActivoFijo activo = new ActivoFijo();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.activos_fijos where id_activo_fijo = ?");

            consulta.setInt(1, id_activo_fijo);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                activo.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                activo.setPlaca(rs.getInt("placa"));
                activo.setEquipo(rs.getInt("equipo"));
                activo.setMarca(rs.getString("marca"));
                activo.setFecha_movimiento(rs.getString("fecha_movimiento"));
                activo.setId_seccion(rs.getInt("id_seccion"));
                activo.setId_ubicacion(rs.getInt("id_ubicacion"));
                activo.setFecha_registro(rs.getString("fecha_registro"));
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return activo;
    }

    public List<ActivoFijo> obtenerActivosFijos() {

        List<ActivoFijo> resultado = new ArrayList<ActivoFijo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.activos_fijos ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                ActivoFijo activo = new ActivoFijo();
                activo.setId_activo_fijo(rs.getInt("id_activo_fijo"));
                activo.setPlaca(rs.getInt("placa"));
                activo.setEquipo(rs.getInt("equipo"));
                activo.setMarca(rs.getString("marca"));
                activo.setFecha_movimiento(rs.getString("fecha_movimiento"));
                activo.setId_seccion(rs.getInt("id_seccion"));
                PreparedStatement consultaSeccion = conexion.prepareStatement(" Select nombre_seccion "
                        + " From seguridad.secciones"
                        + " Where id_seccion = ? ");
                consultaSeccion.setInt(1, rs.getInt("id_seccion"));
                ResultSet resultadoConsultaSeccion = consultaSeccion.executeQuery();
                resultadoConsultaSeccion.next();
                String seccion = resultadoConsultaSeccion.getString("nombre_seccion");
                activo.setNombre_seccion(seccion);
                activo.setId_ubicacion(rs.getInt("id_ubicacion"));
                PreparedStatement consultaUbicacion = conexion.prepareStatement(" Select nombre "
                        + " From bodega.ubicaciones"
                        + " Where id_ubicacion = ? ");
                consultaUbicacion.setInt(1, rs.getInt("id_ubicacion"));
                ResultSet resultadoConsultaUbicacion = consultaUbicacion.executeQuery();
                resultadoConsultaUbicacion.next();
                String ubicacion = resultadoConsultaUbicacion.getString("nombre");
                activo.setNombre_ubicacion(ubicacion);
                activo.setFecha_registro(rs.getString("fecha_registro"));
                activo.setEstado(rs.getString("estado"));

                resultado.add(activo);
            }

            consulta.close();
            conexion.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    private Connection getConexion() {
        try {

            if (conexion.isClosed()) {
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        } catch (Exception ex) {
            conexion = null;
        }

        return conexion;
    }

}
