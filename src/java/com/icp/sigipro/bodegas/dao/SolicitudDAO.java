/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.bodegas.modelos.Prestamo;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.bodegas.modelos.Solicitud;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Amed
 */
public class SolicitudDAO
{

    private Connection conexion;

    public SolicitudDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarSolicitud(Solicitud p)
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.solicitudes ( id_usuario, id_inventario, cantidad, fecha_solicitud, estado) "
                                                                        + " VALUES (?,?,?,?,?) RETURNING id_solicitud");

            consulta.setInt(1, p.getId_usuario());
            consulta.setInt(2, p.getId_inventario());
            consulta.setInt(3, p.getCantidad());
            consulta.setDate(4, p.getFecha_solicitudAsDate());
            consulta.setString(5, p.getEstado());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean insertarSolicitud_Prestamo(Solicitud p, Prestamo pr)
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.solicitudes ( id_usuario, id_inventario, cantidad, fecha_solicitud, estado) "
                                                                        + " VALUES (?,?,?,?,?) RETURNING id_solicitud");

            consulta.setInt(1, p.getId_usuario());
            consulta.setInt(2, p.getId_inventario());
            consulta.setInt(3, p.getCantidad());
            consulta.setDate(4, p.getFecha_solicitudAsDate());
            consulta.setString(5, p.getEstado());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                PrestamoDAO prDAO = new PrestamoDAO();
                int id_solicitud = resultadoConsulta.getInt("id_solicitud");
                pr.setId_solicitud(id_solicitud);
                boolean resultado_prestamo = prDAO.insertarPrestamo(pr);
                if (resultado_prestamo) {
                    resultado = true;
                }
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean editarSolicitud(Solicitud p)
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE bodega.solicitudes"
                    + " SET cantidad=?, estado=?, fecha_entrega=?, id_usuario_recibo=?, observaciones=?"
                    + " WHERE id_solicitud=? AND cantidad <= (Select cantidad from bodega.inventarios where id_inventario =? ) "
            );

            consulta.setInt(1, p.getCantidad());
            consulta.setString(2, p.getEstado());
            consulta.setDate(3, p.getFecha_entregaAsDate());
            if (p.getId_usuario_recibo() == 0) {
                consulta.setNull(4, java.sql.Types.INTEGER);
            }
            else {
                consulta.setInt(4, p.getId_usuario_recibo());
            }
            consulta.setString(5, p.getObservaciones());
            consulta.setInt(6, p.getId_solicitud());
            consulta.setInt(7, p.getId_inventario());

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

    public boolean eliminarSolicitud(int id_solicitud)
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bodega.solicitudes "
                    + " WHERE id_solicitud=?; "
            );

            consulta.setInt(1, id_solicitud);

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

    public Solicitud obtenerSolicitud(int id)
    {

        Solicitud solicitud = new Solicitud();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT solicitud.*, "
                  + "         u.nombre_completo AS nombre_solicitante, "
                  + "         u_rec.nombre_completo AS nombre_recibe, "
                  + "         s.id_seccion AS seccion_inventario, "
                  + "         s.nombre_seccion AS nombre_seccion, "
                  + "         u.id_seccion AS id_seccion, "
                  + "         s_usuario.nombre_seccion AS nombre_seccion_usuario, "
                  + "         ci.nombre AS nombre_producto, "
                  + "         ci.codigo_icp AS cod_icp ,"
                  + "         ci.id_producto, "
                  + "         i.stock_actual "
                  + " FROM ( "
                  + "         SELECT * FROM bodega.solicitudes where id_solicitud = ? "
                  + "         ) AS solicitud "
                  + "         INNER JOIN bodega.inventarios i ON i.id_inventario = solicitud.id_inventario "
                  + "         INNER JOIN bodega.catalogo_interno ci ON i.id_producto = ci.id_producto "
                  + "         INNER JOIN seguridad.secciones s ON i.id_seccion = s.id_seccion "
                  + "         INNER JOIN seguridad.usuarios u ON solicitud.id_usuario = u.id_usuario "
                  + "         INNER JOIN seguridad.secciones s_usuario ON u.id_seccion = s_usuario.id_seccion "
                  + "         LEFT JOIN seguridad.usuarios u_rec ON solicitud.id_usuario_recibo = u_rec.id_usuario "
            );

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setId_inventario(rs.getInt("id_inventario"));
                solicitud.setId_usuario(rs.getInt("id_usuario"));
                solicitud.setCantidad(rs.getInt("cantidad"));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud.setEstado(rs.getString("estado"));
                solicitud.setFecha_entrega(rs.getDate("fecha_entrega"));
                solicitud.setId_usuario_recibo(rs.getInt("id_usuario_recibo"));
                solicitud.setObservaciones(rs.getString("observaciones"));
                
                Usuario u = new Usuario();
                    u.setNombreCompleto(rs.getString("nombre_solicitante"));
                    u.setIdSeccion(rs.getInt("id_seccion"));
                    u.setNombreSeccion(rs.getString("nombre_seccion_usuario"));

                Usuario u_receptor = new Usuario();
                    u_receptor.setNombreCompleto(rs.getString("nombre_recibe"));

                Inventario i = new Inventario();
                    ProductoInterno p = new ProductoInterno();
                    p.setId_producto(rs.getInt("id_producto"));
                    p.setCodigo_icp(rs.getString("cod_icp"));
                    p.setNombre(rs.getString("nombre_producto"));
                    
                    i.setId_producto(rs.getInt("id_producto"));

                    Seccion s = new Seccion();
                    s.setNombre_seccion(rs.getString("nombre_seccion"));
                    s.setId_seccion(rs.getInt("seccion_inventario"));
                    
                    i.setId_seccion(rs.getInt("seccion_inventario"));
                    
                    i.setProducto(p);
                    i.setSeccion(s);
                    
                    i.setStock_actual(rs.getInt("stock_actual"));

                solicitud.setUsuario(u);
                solicitud.setInventario(i);
                solicitud.setUsuarioReceptor(u_receptor);
                rs.next();
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return solicitud;
    }

    public List<Solicitud> obtenerSolicitudes(int id_usuario)
    {

        List<Solicitud> resultado = new ArrayList<Solicitud>();
        String parte_1 = " SELECT solicitud.*, "
                  + "         u.nombre_completo AS nombre_solicitante, "
                  + "         u_rec.nombre_completo AS nombre_recibe, "
                  + "         s.id_seccion AS seccion_inventario, "
                  + "         s.nombre_seccion AS nombre_seccion, "
                  + "         u.id_seccion AS id_seccion, "
                  + "         s_usuario.nombre_seccion AS nombre_seccion_usuario, "
                  + "         ci.nombre AS nombre_producto, "
                  + "         ci.codigo_icp AS cod_icp ,"
                  + "         ci.perecedero, "
                  + "         ci.id_producto, "
                  + "         i.stock_actual "
                  + " FROM ( ";

        String codigo_consulta;
        
        String parte_2 = "         ) AS solicitud "
                  + "         INNER JOIN bodega.inventarios i ON i.id_inventario = solicitud.id_inventario "
                  + "         INNER JOIN bodega.catalogo_interno ci ON i.id_producto = ci.id_producto "
                  + "         INNER JOIN seguridad.secciones s ON i.id_seccion = s.id_seccion "
                  + "         INNER JOIN seguridad.usuarios u ON solicitud.id_usuario = u.id_usuario "
                  + "         INNER JOIN seguridad.secciones s_usuario ON u.id_seccion = s_usuario.id_seccion "
                  + "         LEFT JOIN seguridad.usuarios u_rec ON solicitud.id_usuario_recibo = u_rec.id_usuario ";

        try {
            PreparedStatement consulta;
            if (id_usuario == 0) {
                codigo_consulta = parte_1 + " SELECT * FROM bodega.solicitudes Where estado != 'Pendiente Prestamo' ORDER BY fecha_solicitud DESC " + parte_2;
                consulta = getConexion().prepareStatement(codigo_consulta);
            }
            else {
                codigo_consulta = parte_1 + " SELECT s.* FROM bodega.solicitudes s "
                        + " INNER JOIN seguridad.usuarios u on s.id_usuario = u.id_usuario "
                        + " WHERE u.id_seccion = ? AND s.estado != 'Pendiente Prestamo' ORDER BY s.id_solicitud DESC " + parte_2;
                consulta = getConexion().prepareStatement(codigo_consulta);
                consulta.setInt(1, id_usuario);
            }

            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId_solicitud(rs.getInt("id_solicitud"));
                solicitud.setId_inventario(rs.getInt("id_inventario"));
                solicitud.setId_usuario(rs.getInt("id_usuario"));
                solicitud.setCantidad(rs.getInt("cantidad"));
                solicitud.setFecha_solicitud(rs.getDate("fecha_solicitud"));
                solicitud.setEstado(rs.getString("estado"));
                solicitud.setFecha_entrega(rs.getDate("fecha_entrega"));
                solicitud.setId_usuario_recibo(rs.getInt("id_usuario_recibo"));
                solicitud.setObservaciones(rs.getString("observaciones"));
                
                Usuario u = new Usuario();
                    u.setNombreCompleto(rs.getString("nombre_solicitante"));
                    u.setIdSeccion(rs.getInt("id_seccion"));
                    u.setNombreSeccion(rs.getString("nombre_seccion_usuario"));

                Usuario u_receptor = new Usuario();
                    u_receptor.setNombreCompleto(rs.getString("nombre_recibe"));

                Inventario i = new Inventario();
                    ProductoInterno p = new ProductoInterno();
                    p.setId_producto(rs.getInt("id_producto"));
                    p.setCodigo_icp(rs.getString("cod_icp"));
                    p.setNombre(rs.getString("nombre_producto"));
                    p.setPerecedero(rs.getBoolean("perecedero"));
                    
                    i.setId_producto(rs.getInt("id_producto"));

                    Seccion s = new Seccion();
                    s.setNombre_seccion(rs.getString("nombre_seccion"));
                    s.setId_seccion(rs.getInt("seccion_inventario"));
                    
                    i.setId_seccion(rs.getInt("seccion_inventario"));
                    
                    i.setProducto(p);
                    i.setSeccion(s);
                    
                    i.setStock_actual(rs.getInt("stock_actual"));
                    
                solicitud.setUsuario(u);
                solicitud.setInventario(i);
                solicitud.setUsuarioReceptor(u_receptor);

                resultado.add(solicitud);
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

        public boolean entregarMasivo(String ids, int id_usuario_recibo) throws SIGIPROException
    {
        boolean resultado = false;
        
        try {
            getConexion().setAutoCommit(false);
            java.util.Date hoy = new java.util.Date();
            Date hoysql = new Date(hoy.getTime());
            String estado = "Entregada";
            
            String[] ids_parseados = parsearAsociacion("#af#", ids);
            
            PreparedStatement consulta_entregar = getConexion().prepareStatement(
                    " UPDATE bodega.solicitudes"
                  + " SET estado=?, fecha_entrega=?, id_usuario_recibo=?"
                  + " WHERE id_solicitud in " + this.pasar_ids_solicitudes(ids_parseados) + ";"
            );
            
            consulta_entregar.setInt(3, id_usuario_recibo);
            consulta_entregar.setDate(2, hoysql);
            consulta_entregar.setString(1, estado);
            
            int cant_resultados = consulta_entregar.executeUpdate();
            
            PreparedStatement consulta_inventarios = getConexion().prepareStatement(
                    " SELECT i.id_producto, s.id_solicitud, s.cantidad "
                  + " FROM (SELECT id_solicitud, cantidad, id_inventario FROM bodega.solicitudes WHERE id_solicitud in (1,2)) s "
                  + "     INNER JOIN bodega.inventarios i ON i.id_inventario = s.id_inventario; "
            );
            
            ResultSet rs_solicitudes = consulta_inventarios.executeQuery();
            
            List<Solicitud> solicitudes = new ArrayList<Solicitud>();
            
            while(rs_solicitudes.next()) {
                cant_resultados++;
                Solicitud s = new Solicitud();
                    s.setId_solicitud(rs_solicitudes.getInt("id_solicitud"));
                    s.setCantidad(rs_solicitudes.getInt("cantidad"));
                    Inventario i = new Inventario();
                    i.setId_producto(rs_solicitudes.getInt("id_producto"));
                    s.setInventario(i);
            }
            
            rs_solicitudes.close();
            
            consulta_inventarios.close();
            consulta_entregar.close();
            
            resultado = cant_resultados == ids_parseados.length;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            try {
                getConexion().rollback();
                throw new SIGIPROException("No se pudo realizar la entrega múltiple.");
            }
            catch (SQLException roll_ex) {
                roll_ex.printStackTrace();
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
                    throw new SIGIPROException("Ocurrió un error a la hora de realizar la entrega múltiple. Inténtelo nuevamente.");
                }
            }
            catch (SQLException roll_ex) {
                roll_ex.printStackTrace();
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
            ex.printStackTrace();
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
                ex.printStackTrace();
                conexion = null;
            }
        }
    }
    
    private String pasar_ids_solicitudes(String[] ids_solicitudes)
    {
        String solicitudes = "(";
        for (String s : ids_solicitudes) {
            solicitudes = solicitudes + s;
            solicitudes = solicitudes + ",";
        }
        solicitudes = solicitudes.substring(0, solicitudes.length() - 1);
        solicitudes = solicitudes + ")";
        return solicitudes;
    }
}
