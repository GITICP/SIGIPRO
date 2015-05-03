/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.BitacoraSubBodega;
import com.icp.sigipro.bodegas.modelos.InventarioSubBodega;
import com.icp.sigipro.bodegas.modelos.PermisoSubBodegas;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.security.sasl.AuthenticationException;

/**
 *
 * @author Boga
 */
public class SubBodegaDAO extends DAO<SubBodega>
{

    public static final String INGRESAR = "bodega.usuarios_sub_bodegas_ingresos";
    public static final String EGRESAR = "bodega.usuarios_sub_bodegas_egresos";
    public static final String VER = "bodega.usuarios_sub_bodegas_ver";
    public static final String ENCARGADO = "bodega.sub_bodegas";

    public SubBodegaDAO()
    {
        super(SubBodega.class, "bodega", "sub_bodegas");
    }

    public boolean validarAcceso(String tabla, int id_usuario, int id_sub_bodega) throws AuthenticationException, SIGIPROException
    {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT 1 "
                    + " FROM bodega.sub_bodegas "
                    + " WHERE (id_usuario = ? and id_sub_bodega = ?) "
                    + "	or  exists "
                    + "      (select 1 from " + tabla + " WHERE id_usuario = ? and id_sub_bodega = ?); ");

            consulta.setInt(1, id_usuario);
            consulta.setInt(3, id_usuario);
            consulta.setInt(2, id_sub_bodega);
            consulta.setInt(4, id_sub_bodega);

            ResultSet resultado_consulta = consulta.executeQuery();

            if (resultado_consulta.next()) {
                resultado = true;
            }
            else {
                throw new AuthenticationException();
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al comunicarse con la base de datos. Notifique al administrador del sistema.");
        }
        return resultado;
    }

    public PermisoSubBodegas obtenerPermisos(int id_usuario, int id_sub_bodega) throws AuthenticationException, SIGIPROException
    {
        PermisoSubBodegas resultado = null;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    "	  SELECT 'ingresos' as permiso "
                    + "	  FROM bodega.usuarios_sub_bodegas_ingresos "
                    + "	  where id_usuario = ? and id_sub_bodega = ? "
                    + "	  UNION "
                    + "	  SELECT 'egresos' as permiso "
                    + "	  FROM bodega.usuarios_sub_bodegas_egresos "
                    + "	  where id_usuario = ? and id_sub_bodega = ? "
                    + "	  UNION "
                    + "	  SELECT 'ver' as permiso "
                    + "	  FROM bodega.usuarios_sub_bodegas_ver "
                    + "	  where id_usuario = ? and id_sub_bodega = ? "
                    + "	  UNION "
                    + "	  SELECT 'encargado' as permiso "
                    + "	  FROM bodega.sub_bodegas "
                    + "	  WHERE id_usuario = ? and id_sub_bodega = ?; "
            );

            consulta.setInt(1, id_usuario);
            consulta.setInt(2, id_sub_bodega);
            consulta.setInt(3, id_usuario);
            consulta.setInt(4, id_sub_bodega);
            consulta.setInt(5, id_usuario);
            consulta.setInt(6, id_sub_bodega);
            consulta.setInt(7, id_usuario);
            consulta.setInt(8, id_sub_bodega);

            ResultSet resultado_consulta = consulta.executeQuery();
            resultado = new PermisoSubBodegas();

            if (resultado_consulta.next()) {
                do {
                    resultado.asignarPermiso(resultado_consulta.getString("permiso"));
                }
                while (resultado_consulta.next());
            }
            else {
                throw new AuthenticationException();
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al comunicarse con la base de datos. Notifique al administrador del sistema.");
        }
        return resultado;
    }

    public boolean validarAcceso(int id_usuario) throws AuthenticationException, SIGIPROException
    {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT 1 FROM "
                    + "	( "
                    + "	  SELECT id_usuario "
                    + "	  FROM bodega.usuarios_sub_bodegas_ingresos "
                    + "	  where id_usuario = ? "
                    + "	  UNION "
                    + "	  SELECT id_usuario "
                    + "	  FROM bodega.usuarios_sub_bodegas_egresos "
                    + "	  where id_usuario = ?"
                    + "	  UNION "
                    + "	  SELECT id_usuario "
                    + "	  FROM bodega.usuarios_sub_bodegas_ver "
                    + "	  where id_usuario = ?"
                    + "	  UNION "
                    + "	  SELECT id_usuario "
                    + "	  FROM bodega.sub_bodegas "
                    + "	  WHERE id_usuario = ?"
                    + "	 ) as permisos;"
            );

            consulta.setInt(1, id_usuario);
            consulta.setInt(2, id_usuario);
            consulta.setInt(3, id_usuario);
            consulta.setInt(4, id_usuario);

            ResultSet resultado_consulta = consulta.executeQuery();

            if (resultado_consulta.next()) {
                resultado = true;
            }
            else {
                throw new AuthenticationException();
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al comunicarse con la base de datos. Notifique al administrador del sistema.");
        }
        return resultado;
    }

    public List<SubBodega> obtenerSubBodegas() throws SIGIPROException
    {
        List<SubBodega> listaResultado = new ArrayList<SubBodega>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT sb.id_sub_bodega, sb.nombre, u.nombre_completo, s.nombre_seccion"
                    + " FROM bodega.sub_bodegas sb INNER JOIN seguridad.secciones s on sb.id_seccion = s.id_seccion "
                    + "                            INNER JOIN seguridad.usuarios u on u.id_usuario = sb.id_usuario;");

            ResultSet resultados = consulta.executeQuery();

            while (resultados.next()) {
                listaResultado.add(construirSubBodega(resultados));
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al obtener subbodegas.");
        }
        return listaResultado;
    }

    public List<SubBodega> obtenerSubBodegas(int id_usuario) throws SIGIPROException
    {

        List<SubBodega> listaResultado = new ArrayList<SubBodega>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT sb.id_sub_bodega, sb.nombre, u.nombre_completo, s.nombre_seccion "
                    + " FROM bodega.sub_bodegas sb INNER JOIN seguridad.secciones s on sb.id_seccion = s.id_seccion "
                    + "                              INNER JOIN seguridad.usuarios u on u.id_usuario = sb.id_usuario "
                    + " WHERE sb.id_usuario = ? "
                    + "       OR ? in (SELECT id_usuario FROM bodega.usuarios_sub_bodegas_ingresos WHERE id_sub_bodega = sb.id_sub_bodega) "
                    + "       OR ? in (SELECT id_usuario FROM bodega.usuarios_sub_bodegas_egresos WHERE id_sub_bodega = sb.id_sub_bodega) "
                    + "       OR ? in (SELECT id_usuario FROM bodega.usuarios_sub_bodegas_ver WHERE id_sub_bodega = sb.id_sub_bodega);");

            consulta.setInt(1, id_usuario);
            consulta.setInt(2, id_usuario);
            consulta.setInt(3, id_usuario);
            consulta.setInt(4, id_usuario);

            ResultSet resultados = consulta.executeQuery();

            while (resultados.next()) {
                listaResultado.add(construirSubBodega(resultados));
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al obtener subbodegas.");
        }
        return listaResultado;
    }

    private SubBodega construirSubBodega(ResultSet resultados) throws SQLException
    {
        SubBodega sb = new SubBodega();
        sb.setId_sub_bodega(resultados.getInt("id_sub_bodega"));
        sb.setNombre(resultados.getString("nombre"));

        Usuario u = new Usuario();
        u.setNombreCompleto(resultados.getString("nombre_completo"));

        Seccion s = new Seccion();
        s.setNombre_seccion(resultados.getString("nombre_seccion"));

        sb.setUsuario(u);
        sb.setSeccion(s);

        return sb;
    }

    @Override
    public int insertar(SubBodega param) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException
    {
        PreparedStatement objetoConsulta = getConexion().prepareStatement(" INSERT INTO " + this.nombreModulo + "." + this.nombreTabla
                                                                          + " (id_seccion, id_usuario, nombre) VALUES (?,?,?);");

        objetoConsulta.setInt(1, param.getSeccion().getId_seccion());
        objetoConsulta.setInt(2, param.getUsuario().getId_usuario());
        objetoConsulta.setString(3, param.getNombre());

        return ejecutarConsultaSinResultado(objetoConsulta);
    }

    public SubBodega buscarSubBodega(int id) throws SIGIPROException
    {
        SubBodega s = new SubBodega();
        try {
            String codigoConsulta = "SELECT sb.nombre, u.id_usuario, u.nombre_usuario, u.nombre_completo, s.id_seccion, s.nombre_seccion FROM " + nombreModulo + "." + nombreTabla + " sb INNER JOIN seguridad.secciones s on s.id_seccion = sb.id_seccion INNER JOIN seguridad.usuarios u on u.id_usuario = sb.id_usuario WHERE id_sub_bodega = ?";

            PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
            consulta.setInt(1, id);
            ResultSet resultado = ejecutarConsulta(consulta);

            if (resultado.next()) {
                s.setId_sub_bodega(id);
                s.setNombre(resultado.getString("nombre"));

                Seccion seccion = new Seccion();
                seccion.setId_seccion(resultado.getInt("id_seccion"));
                seccion.setNombre_seccion(resultado.getString("nombre_seccion"));

                Usuario usuario = new Usuario();
                usuario.setId_usuario(resultado.getInt("id_usuario"));
                usuario.setNombreCompleto(resultado.getString("nombre_completo"));
                usuario.setNombreUsuario(resultado.getString("nombre_usuario"));

                s.setSeccion(seccion);
                s.setUsuario(usuario);
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al obtener sub bodega");
        }
        return s;
    }

    public SubBodega buscarSubBodegaEInventarios(int id) throws SIGIPROException
    {
        SubBodega sub_bodega = null;
        try {
            String codigoConsulta = " SELECT sb.id_sub_bodega, sb.nombre, u.nombre_completo, s.nombre_seccion, isb.id_inventario_sub_bodega, ci.id_producto, ci.nombre as nombre_producto, ci.codigo_icp, isb.cantidad, isb.fecha_vencimiento "
                                    + " FROM bodega.sub_bodegas sb "
                                    + "  INNER JOIN seguridad.usuarios u on sb.id_usuario = u.id_usuario "
                                    + "   INNER JOIN seguridad.secciones s on sb.id_seccion = s.id_seccion "
                                    + "   LEFT JOIN bodega.inventarios_sub_bodegas isb on isb.id_sub_bodega = sb.id_sub_bodega and isb.cantidad > 0 "
                                    + "   LEFT JOIN bodega.catalogo_interno ci on ci.id_producto = isb.id_producto "
                                    + " WHERE sb.id_sub_bodega = ?;";

            PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
            consulta.setInt(1, id);
            ResultSet resultado = ejecutarConsulta(consulta);

            if (resultado.next()) {
                sub_bodega = new SubBodega();

                sub_bodega.setId_sub_bodega(id);
                sub_bodega.setNombre(resultado.getString("nombre"));

                Usuario u = new Usuario();
                u.setNombreCompleto(resultado.getString("nombre_completo"));
                Seccion s = new Seccion();
                s.setNombre_seccion(resultado.getString("nombre_seccion"));

                sub_bodega.setUsuario(u);
                sub_bodega.setSeccion(s);

                List<InventarioSubBodega> inventarios = new ArrayList<InventarioSubBodega>();
                do {
                    InventarioSubBodega inventario_sb = new InventarioSubBodega();
                    int id_inventario_sub_bodega = resultado.getInt("id_inventario_sub_bodega");

                    if (id_inventario_sub_bodega != 0) {
                        inventario_sb.setId_inventario_sub_bodega(id_inventario_sub_bodega);
                        inventario_sb.setCantidad(resultado.getInt("cantidad"));

                        inventario_sb.setFecha_vencimiento(resultado.getDate("fecha_vencimiento"));

                        ProductoInterno p = new ProductoInterno();

                        p.setId_producto(resultado.getInt("id_producto"));
                        p.setNombre(resultado.getString("nombre_producto"));
                        p.setCodigo_icp(resultado.getString("codigo_icp"));

                        inventario_sb.setProducto(p);
                        inventario_sb.setSub_bodega(sub_bodega);

                        inventarios.add(inventario_sb);
                    }

                }
                while (resultado.next());

                sub_bodega.setInventarios(inventarios);
            }
            else {
                throw new SIGIPROException("No se encontraron registros de inventario para esta sub bodega");
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al obtener sub bodega");
        }
        return sub_bodega;
    }

    public boolean insertar(SubBodega param, String[] idsIngresos, String[] idsEgresos, String[] idsVer) throws SIGIPROException
    {

        boolean resultado = false;
        PreparedStatement consultaInsertar = null;
        PreparedStatement consultaEliminarIngresos = null;
        PreparedStatement consultaEliminarEgresos = null;
        PreparedStatement consultaEliminarVer = null;
        PreparedStatement insertarIngresos = null;
        PreparedStatement insertarEgresos = null;
        PreparedStatement insertarVer = null;

        try {
            getConexion().setAutoCommit(false);

            consultaInsertar = getConexion().prepareStatement(" INSERT INTO " + this.nombreModulo + "." + this.nombreTabla
                                                              + " (id_seccion, id_usuario, nombre) VALUES (?,?,?) RETURNING id_sub_bodega");

            consultaInsertar.setInt(1, param.getSeccion().getId_seccion());
            consultaInsertar.setInt(2, param.getUsuario().getId_usuario());
            consultaInsertar.setString(3, param.getNombre());

            ResultSet idSubBodega = consultaInsertar.executeQuery();

            if (idSubBodega.next()) {
                param.setId_sub_bodega(idSubBodega.getInt("id_sub_bodega"));
                idSubBodega.close();
                consultaInsertar.close();
            }
            else {
                consultaInsertar.close();
                idSubBodega.close();
                throw new SIGIPROException("Error al insertar sub bodega");
            }

            consultaEliminarIngresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ingresos WHERE id_sub_bodega = ?");
            consultaEliminarEgresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_egresos WHERE id_sub_bodega = ?");
            consultaEliminarVer = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ver WHERE id_sub_bodega = ?");

            consultaEliminarIngresos.setInt(1, param.getId_sub_bodega());
            consultaEliminarEgresos.setInt(1, param.getId_sub_bodega());
            consultaEliminarVer.setInt(1, param.getId_sub_bodega());

            consultaEliminarIngresos.executeUpdate();
            consultaEliminarEgresos.executeUpdate();
            consultaEliminarVer.executeUpdate();

            insertarIngresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ingresos (id_sub_bodega, id_usuario) VALUES (?,?)");
            insertarEgresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_egresos (id_sub_bodega, id_usuario) VALUES (?,?)");
            insertarVer = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ver (id_sub_bodega, id_usuario) VALUES (?,?)");

            for (String idIngreso : idsIngresos) {
                insertarIngresos.setInt(1, param.getId_sub_bodega());
                insertarIngresos.setInt(2, Integer.parseInt(idIngreso));
                insertarIngresos.addBatch();
            }

            for (String idEgreso : idsEgresos) {
                insertarEgresos.setInt(1, param.getId_sub_bodega());
                insertarEgresos.setInt(2, Integer.parseInt(idEgreso));
                insertarEgresos.addBatch();
            }

            for (String idVer : idsVer) {
                insertarVer.setInt(1, param.getId_sub_bodega());
                insertarVer.setInt(2, Integer.parseInt(idVer));
                insertarVer.addBatch();
            }

            insertarIngresos.executeBatch();
            insertarEgresos.executeBatch();
            insertarVer.executeBatch();

            resultado = true;
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema.");
        }
        finally {
            try {
                if (resultado) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (consultaInsertar != null) {
                    consultaInsertar.close();
                }
                if (consultaEliminarIngresos != null) {
                    consultaEliminarIngresos.close();
                }
                if (consultaEliminarEgresos != null) {
                    consultaEliminarEgresos.close();
                }
                if (consultaEliminarVer != null) {
                    consultaEliminarVer.close();
                }
                if (insertarIngresos != null) {
                    insertarIngresos.close();
                }
                if (insertarEgresos != null) {
                    insertarEgresos.close();
                }
                if (insertarVer != null) {
                    insertarVer.close();
                }
                getConexion().close();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema");
            }
        }

        return resultado;
    }

    public boolean editarSubBodega(SubBodega param, String[] idsIngresos, String[] idsEgresos, String[] idsVer) throws SIGIPROException
    {

        boolean resultado = false;
        try {
            getConexion().setAutoCommit(false);

            PreparedStatement consultaInsertar = getConexion().prepareStatement(" UPDATE " + this.nombreModulo + "." + this.nombreTabla
                                                                                + " SET id_seccion = ?, id_usuario = ?, nombre = ? WHERE id_sub_bodega = ?");

            consultaInsertar.setInt(1, param.getSeccion().getId_seccion());
            consultaInsertar.setInt(2, param.getUsuario().getId_usuario());
            consultaInsertar.setString(3, param.getNombre());
            consultaInsertar.setInt(4, param.getId_sub_bodega());

            if (consultaInsertar.executeUpdate() != 1) {
                consultaInsertar.close();
                throw new SIGIPROException("Error al editar sub bodega");
            }

            PreparedStatement consultaEliminarIngresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ingresos WHERE id_sub_bodega = ?");
            PreparedStatement consultaEliminarEgresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_egresos WHERE id_sub_bodega = ?");
            PreparedStatement consultaEliminarVer = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ver WHERE id_sub_bodega = ?");

            consultaEliminarIngresos.setInt(1, param.getId_sub_bodega());
            consultaEliminarEgresos.setInt(1, param.getId_sub_bodega());
            consultaEliminarVer.setInt(1, param.getId_sub_bodega());

            consultaEliminarIngresos.executeUpdate();
            consultaEliminarEgresos.executeUpdate();
            consultaEliminarVer.executeUpdate();

            consultaEliminarIngresos.close();
            consultaEliminarEgresos.close();
            consultaEliminarVer.close();

            PreparedStatement insertarIngresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ingresos (id_sub_bodega, id_usuario) VALUES (?,?)");
            PreparedStatement insertarEgresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_egresos (id_sub_bodega, id_usuario) VALUES (?,?)");
            PreparedStatement insertarVer = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ver (id_sub_bodega, id_usuario) VALUES (?,?)");

            for (String idIngreso : idsIngresos) {
                insertarIngresos.setInt(1, param.getId_sub_bodega());
                insertarIngresos.setInt(2, Integer.parseInt(idIngreso));
                insertarIngresos.addBatch();
            }

            for (String idEgreso : idsEgresos) {
                insertarEgresos.setInt(1, param.getId_sub_bodega());
                insertarEgresos.setInt(2, Integer.parseInt(idEgreso));
                insertarEgresos.addBatch();
            }

            for (String idVer : idsVer) {
                insertarVer.setInt(1, param.getId_sub_bodega());
                insertarVer.setInt(2, Integer.parseInt(idVer));
                insertarVer.addBatch();
            }

            insertarIngresos.executeBatch();
            insertarEgresos.executeBatch();
            insertarVer.executeBatch();

            insertarIngresos.close();
            insertarEgresos.close();
            insertarVer.close();

            resultado = true;
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema.");
        }
        finally {
            try {
                if (resultado) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                getConexion().close();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema.");
            }
        }

        return resultado;
    }

    public boolean registrarIngreso(InventarioSubBodega inventario_sub_bodega, BitacoraSubBodega bitacora) throws SIGIPROException
    {

        boolean resultado = false;
        PreparedStatement upsert_inventario = null;
        PreparedStatement insert_bitacora = null;

        try {
            getConexion().setAutoCommit(false);

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

            insert_bitacora = prepararInsertBitacora(bitacora);

            if (insert_bitacora.executeUpdate() == 1) {
                resultado = true;
            }
            else {
                resultado = false;
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al registrar ingreso. Inténtelo nuevamente.");
        }
        finally {
            try {
                if (resultado) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (upsert_inventario != null) {
                    upsert_inventario.close();
                }
                if (insert_bitacora != null) {
                    insert_bitacora.close();
                }
                getConexion().close();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema");
            }
        }

        return resultado;
    }

    public List<Usuario> usuariosPermisos(String tabla_por_buscar, int id_sub_bodega) throws SIGIPROException
    {
        List<Usuario> usuarios = new ArrayList<Usuario>();

        try {
            PreparedStatement consulta_permisos = getConexion().prepareStatement(" SELECT u.id_usuario, u.nombre_completo, u.nombre_usuario "
                                                                                 + " FROM " + tabla_por_buscar + " p_sb "
                                                                                 + " INNER JOIN seguridad.usuarios u ON u.id_usuario = p_sb.id_usuario "
                                                                                 + " WHERE id_sub_bodega = ?;");

            consulta_permisos.setInt(1, id_sub_bodega);

            ResultSet resultados = consulta_permisos.executeQuery();

            while (resultados.next()) {
                Usuario u = new Usuario();

                u.setId_usuario(resultados.getInt("id_usuario"));
                u.setNombre_completo(resultados.getString("nombre_completo"));
                u.setNombre_usuario(resultados.getString("nombre_usuario"));

                usuarios.add(u);
            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener los usuarios con permisos.");
        }

        return usuarios;
    }

    public boolean consumirArticulo(int id_inventario, int cantidad, BitacoraSubBodega bitacora) throws SIGIPROException
    {
        boolean resultado = false;
        ResultSet resultado_update = null;
        PreparedStatement actualizar_inventario = null;
        PreparedStatement insert_bitacora = null;

        try {
            getConexion().setAutoCommit(false);

            actualizar_inventario = getConexion().prepareStatement(" UPDATE bodega.inventarios_sub_bodegas SET cantidad = cantidad - ? WHERE id_inventario_sub_bodega = ? RETURNING id_producto; ");

            actualizar_inventario.setInt(1, cantidad);
            actualizar_inventario.setInt(2, id_inventario);

            resultado_update = actualizar_inventario.executeQuery();

            if (resultado_update.next()) {
                ProductoInterno producto = new ProductoInterno();
                producto.setId_producto(resultado_update.getInt("id_producto"));
                bitacora.setProducto(producto);

                insert_bitacora = prepararInsertBitacora(bitacora);

                if (insert_bitacora.executeUpdate() == 1) {
                    resultado = true;
                }
                else {
                    resultado = false;
                }
            }
            else {
                throw new SIGIPROException("Error al consumir de la sub bodega. Inténtelo nuevamente.");
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de conexión con la base de datos. Contacte al administrador del sistema.");
        }
        finally {
            try {
                if (resultado) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (resultado_update != null) {
                    resultado_update.close();
                }
                if (actualizar_inventario != null) {
                    actualizar_inventario.close();
                }
                if (insert_bitacora != null) {
                    insert_bitacora.close();
                }
                getConexion().close();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema");
            }
        }

        return resultado;
    }

    public boolean moverArticulo(int id_inventario, int cantidad, int id_sub_bodega_destino, BitacoraSubBodega bitacora) throws SIGIPROException
    {
        boolean resultado = false;
        PreparedStatement restar_inventario = null;
        ResultSet inventario_sub_bodega_origen = null;
        PreparedStatement upsert_inventario = null;
        PreparedStatement insert_bitacora = null;

        try {
            getConexion().setAutoCommit(false);

            restar_inventario = getConexion().prepareStatement(" UPDATE bodega.inventarios_sub_bodegas SET cantidad = cantidad - ? WHERE id_inventario_sub_bodega = ? RETURNING *; ");

            restar_inventario.setInt(1, cantidad);
            restar_inventario.setInt(2, id_inventario);

            inventario_sub_bodega_origen = restar_inventario.executeQuery();

            if (inventario_sub_bodega_origen.next()) {

                InventarioSubBodega inventario_sub_bodega = new InventarioSubBodega();
                ProductoInterno producto = new ProductoInterno();
                producto.setId_producto(inventario_sub_bodega_origen.getInt("id_producto"));
                bitacora.setProducto(producto);

                inventario_sub_bodega.setFecha_vencimiento(inventario_sub_bodega_origen.getDate("fecha_vencimiento"));
                inventario_sub_bodega.setProducto(producto);

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
                    upsert_inventario.setInt(1, cantidad);
                    upsert_inventario.setInt(7, cantidad);

                    upsert_inventario.setInt(2, inventario_sub_bodega.getProducto().getId_producto());
                    upsert_inventario.setInt(4, inventario_sub_bodega.getProducto().getId_producto());

                    upsert_inventario.setInt(3, id_sub_bodega_destino);
                    upsert_inventario.setInt(5, id_sub_bodega_destino);

                    upsert_inventario.setNull(6, java.sql.Types.DATE);
                }
                else {
                    upsert_inventario.setInt(1, cantidad);
                    upsert_inventario.setInt(8, cantidad);

                    upsert_inventario.setInt(2, inventario_sub_bodega.getProducto().getId_producto());
                    upsert_inventario.setInt(5, inventario_sub_bodega.getProducto().getId_producto());

                    upsert_inventario.setInt(3, id_sub_bodega_destino);
                    upsert_inventario.setInt(6, id_sub_bodega_destino);

                    upsert_inventario.setDate(4, inventario_sub_bodega.getFecha_vencimiento());
                    upsert_inventario.setDate(7, inventario_sub_bodega.getFecha_vencimiento());
                }

                upsert_inventario.executeUpdate();

                insert_bitacora = this.prepararInsertBitacora(bitacora);

                if (insert_bitacora.executeUpdate() == 1) {
                    resultado = true;
                }
                else {
                    resultado = false;
                }

            }
            else {
                throw new SIGIPROException("Error al consumir de la sub bodega. Inténtelo nuevamente.");
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema.");
        }
        finally {
            try {
                if (resultado) {
                    getConexion().commit();
                }
                else {
                    getConexion().rollback();
                }
                if (restar_inventario != null) {
                    restar_inventario.close();
                }
                if (inventario_sub_bodega_origen != null) {
                    inventario_sub_bodega_origen.close();
                }
                if (upsert_inventario != null) {
                    upsert_inventario.close();
                }
                if (insert_bitacora != null) {
                    upsert_inventario.close();
                }
                getConexion().close();
            }
            catch (SQLException sql_ex) {
                throw new SIGIPROException("Error de comunicación con la base de datos. Contacte al administrador del sistema");
            }
        }

        return resultado;
    }

    public String[] parsearAsociacion(String pivote, String asociacionesCodificadas)
    {
        String[] idsTemp = asociacionesCodificadas.split(pivote);
        return Arrays.copyOfRange(idsTemp, 1, idsTemp.length);
    }

    private PreparedStatement prepararInsertBitacora(BitacoraSubBodega bitacora) throws SQLException
    {
        String columnas_por_insertar = "INSERT INTO bodega.bitacora_sub_bodegas (id_sub_bodega, accion, id_producto, cantidad, id_usuario";
        String valores_por_insertar = "VALUES (?,?,?,?,?";

        boolean accion_mover = bitacora.getAccion().equals(BitacoraSubBodega.MOVER);
        if (accion_mover) {
            columnas_por_insertar += ",id_sub_bodega_destino";
            valores_por_insertar += ",?";
        }

        String consulta_final = columnas_por_insertar + ")" + valores_por_insertar + ");";

        PreparedStatement insert_bitacora = getConexion().prepareStatement(consulta_final);

        insert_bitacora.setInt(1, bitacora.getSub_bodega().getId_sub_bodega());
        insert_bitacora.setString(2, bitacora.getAccion());
        insert_bitacora.setInt(3, bitacora.getProducto().getId_producto());
        insert_bitacora.setInt(4, bitacora.getCantidad());
        insert_bitacora.setInt(5, bitacora.getUsuario().getId_usuario());

        if (accion_mover) {
            insert_bitacora.setInt(6, bitacora.getSub_bodega_destino().getId_sub_bodega());
        }

        return insert_bitacora;
    }
}
