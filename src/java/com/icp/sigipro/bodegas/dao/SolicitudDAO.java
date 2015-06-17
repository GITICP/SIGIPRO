/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.BitacoraSubBodega;
import com.icp.sigipro.bodegas.modelos.Ingreso;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.bodegas.modelos.InventarioSubBodega;
import com.icp.sigipro.bodegas.modelos.Prestamo;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.bodegas.modelos.Solicitud;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Amed
 */
public class SolicitudDAO extends DAO
{

    public SolicitudDAO() { }

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

        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            
            if (id_usuario == 0) {
                codigo_consulta = parte_1
                                  + " SELECT * "
                                  + " FROM bodega.solicitudes "
                                  + " Where ( estado in ('Pendiente','Aprobada') ) OR ( (estado in ('Cerrada', 'Entregada', 'Rechazada') AND current_date - 7 < fecha_entrega) ) "
                                  + " ORDER BY fecha_solicitud DESC " 
                                  + parte_2;
                consulta = getConexion().prepareStatement(codigo_consulta);
            }
            else {
                codigo_consulta = parte_1 
                                  + " SELECT s.* FROM bodega.solicitudes s "
                                  + " INNER JOIN seguridad.usuarios u on s.id_usuario = u.id_usuario "
                                  + " WHERE u.id_seccion = ? AND (( estado in ('Pendiente','Aprobada') ) OR ( (estado in ('Cerrada', 'Entregada', 'Rechazada') AND current_date - 7 < fecha_entrega) )) "
                                  + " ORDER BY s.id_solicitud DESC " 
                                  + parte_2;
                consulta = getConexion().prepareStatement(codigo_consulta);
                consulta.setInt(1, id_usuario);
            }

            rs = consulta.executeQuery();

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
    
    public List<Solicitud> obtenerSolicitudesEntregadas(int seccion_usuario)
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
            if (seccion_usuario == 0) {
                codigo_consulta = parte_1 + " SELECT * FROM bodega.solicitudes Where estado = 'Entregada' OR estado = 'Cerrada' OR estado = 'Rechazada' ORDER BY fecha_solicitud DESC " + parte_2;
                consulta = getConexion().prepareStatement(codigo_consulta);
            }
            else {
                codigo_consulta = parte_1 + " SELECT s.* FROM bodega.solicitudes s "
                                  + " INNER JOIN seguridad.usuarios u on s.id_usuario = u.id_usuario "
                                  + " WHERE u.id_seccion = ? AND (s.estado = 'Entregada' OR s.estado = 'Cerrada' OR estado = 'Rechazada') ORDER BY s.id_solicitud DESC " + parte_2;
                consulta = getConexion().prepareStatement(codigo_consulta);
                consulta.setInt(1, seccion_usuario);
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
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean entregarMasivo(String ids, int id_usuario_recibo, HashMap fechas_vencimiento, int id_sub_bodega) throws SIGIPROException
    {
        boolean resultado = false;
        
        boolean resultado_entregar;
        boolean resultado_sb = true;
        
        PreparedStatement consulta_entregar = null;
        PreparedStatement upsert_inventario = null;
        PreparedStatement insert_bitacora = null;
        PreparedStatement consulta_inventarios = null;
        ResultSet rs_solicitudes = null;

        try {
            getConexion().setAutoCommit(false);
            java.util.Date hoy = new java.util.Date();
            Date hoysql = new Date(hoy.getTime());
            String estado = "Entregada";

            String[] ids_parseados = parsearAsociacion("#af#", ids);
            String ids_solicitudes = this.pasar_ids_solicitudes(ids_parseados);

            consulta_entregar = getConexion().prepareStatement(
                    " UPDATE bodega.solicitudes"
                    + " SET estado=?, fecha_entrega=?, id_usuario_recibo=?"
                    + " WHERE id_solicitud in " + ids_solicitudes + ";"
            );

            consulta_entregar.setInt(3, id_usuario_recibo);
            consulta_entregar.setDate(2, hoysql);
            consulta_entregar.setString(1, estado);

            resultado_entregar = consulta_entregar.executeUpdate() == ids_parseados.length;

            if (id_sub_bodega != 0 && fechas_vencimiento != null) {

                consulta_inventarios = getConexion().prepareStatement(
                        " SELECT i.id_producto, s.id_solicitud, s.cantidad "
                        + " FROM (SELECT id_solicitud, cantidad, id_inventario FROM bodega.solicitudes WHERE id_solicitud in " + ids_solicitudes + ") s "
                        + "     INNER JOIN bodega.inventarios i ON i.id_inventario = s.id_inventario; "
                );

                rs_solicitudes = consulta_inventarios.executeQuery();

                List<InventarioSubBodega> inventarios_sub_bodegas = new ArrayList<InventarioSubBodega>();
                HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();

                while (rs_solicitudes.next()) {
                    InventarioSubBodega isb = new InventarioSubBodega();

                    isb.setCantidad(rs_solicitudes.getInt("cantidad"));
                    String fecha = String.valueOf(fechas_vencimiento.get(String.valueOf(rs_solicitudes.getInt("id_solicitud"))));

                    if (!fecha.equals("null")) {
                        isb.setFecha_vencimiento(helper_fechas.formatearFecha(fecha));
                    }
                    else {
                        isb.setFecha_vencimiento(null);
                    }

                    ProductoInterno p = new ProductoInterno();
                    p.setId_producto(rs_solicitudes.getInt("id_producto"));
                    isb.setProducto(p);

                    SubBodega s = new SubBodega();
                    s.setId_sub_bodega(id_sub_bodega);
                    isb.setSub_bodega(s);

                    inventarios_sub_bodegas.add(isb);
                }

                insert_bitacora = getConexion().prepareStatement(
                        "INSERT INTO bodega.bitacora_sub_bodegas (id_sub_bodega, accion, id_producto, cantidad, id_usuario) VALUES (?,?,?,?,?);"
                );

                for (InventarioSubBodega inventario_sub_bodega : inventarios_sub_bodegas) {

                    String primera_parte_consulta = " WITH upsert AS "
                                                    + " (UPDATE bodega.inventarios_sub_bodegas "
                                                    + "  SET cantidad = cantidad + ? "
                                                    + "  WHERE id_producto = ? and id_sub_bodega = ? and fecha_vencimiento";
                    String segunda_parte_consulta = "     INSERT INTO bodega.inventarios_sub_bodegas(id_producto, "
                                                    + "                                                id_sub_bodega, "
                                                    + "                                                fecha_vencimiento, "
                                                    + "                                                cantidad"
                                                    + "                                               ) "
                                                    + "                                               SELECT ?, "
                                                    + "                                                      ?, "
                                                    + "                                                      ?, "
                                                    + "                                                      ?  "
                                                    + "                                                      WHERE NOT EXISTS (SELECT * FROM upsert); ";

                    String consulta_final;
                    boolean fechas_null = false;
                    if (inventario_sub_bodega.getFecha_vencimiento() != null) {
                        consulta_final = primera_parte_consulta + " = ? RETURNING *) " + segunda_parte_consulta;
                    }
                    else {
                        fechas_null = true;
                        consulta_final = primera_parte_consulta + " is null RETURNING *) " + segunda_parte_consulta;
                    }

                    upsert_inventario = getConexion().prepareStatement(consulta_final);

                    if (fechas_null) {
                        upsert_inventario.setInt(1, inventario_sub_bodega.getCantidad());
                        upsert_inventario.setInt(7, inventario_sub_bodega.getCantidad());

                        upsert_inventario.setInt(2, inventario_sub_bodega.getProducto().getId_producto());
                        upsert_inventario.setInt(4, inventario_sub_bodega.getProducto().getId_producto());

                        upsert_inventario.setInt(3, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());
                        upsert_inventario.setInt(5, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());

                        upsert_inventario.setNull(6, java.sql.Types.DATE);

                    }
                    else {
                        upsert_inventario.setInt(1, inventario_sub_bodega.getCantidad());
                        upsert_inventario.setInt(8, inventario_sub_bodega.getCantidad());

                        upsert_inventario.setInt(2, inventario_sub_bodega.getProducto().getId_producto());
                        upsert_inventario.setInt(5, inventario_sub_bodega.getProducto().getId_producto());

                        upsert_inventario.setInt(3, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());
                        upsert_inventario.setInt(6, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());

                        upsert_inventario.setDate(4, inventario_sub_bodega.getFecha_vencimiento());
                        upsert_inventario.setDate(7, inventario_sub_bodega.getFecha_vencimiento());
                    }
                    
                    upsert_inventario.executeUpdate();

                    insert_bitacora.setInt(1, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());
                    insert_bitacora.setString(2, BitacoraSubBodega.ENTREGAR);
                    insert_bitacora.setInt(3, inventario_sub_bodega.getProducto().getId_producto());
                    insert_bitacora.setInt(4, inventario_sub_bodega.getCantidad());
                    insert_bitacora.setInt(5, id_usuario_recibo);

                    insert_bitacora.addBatch();
                }
                for (int i : insert_bitacora.executeBatch()) {
                    if (i != 1) {
                        resultado_sb = false;
                    }
                }
            }

            resultado = resultado_entregar && resultado_sb;
        }
        catch (ParseException ex) {
            resultado = false;
            throw new SIGIPROException("Las fechas no son válidas.");
        }
        catch (SQLException ex) {
            ex.getNextException().printStackTrace();
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
                }
                else {
                    getConexion().rollback();
                    throw new SIGIPROException("No se pudo realizar la entrega múltiple.");
                }
                if (consulta_entregar != null) {
                    consulta_entregar.close();
                }
                if (upsert_inventario != null) {
                    upsert_inventario.close();
                }
                if (insert_bitacora != null) {
                    insert_bitacora.close();
                }
                if (consulta_inventarios != null) {
                    consulta_inventarios.close();
                }
                if (rs_solicitudes != null) {
                    rs_solicitudes.close();
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
