/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.Ingreso;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.DAOEspecial;
import com.icp.sigipro.core.SIGIPROException;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class IngresoDAO extends DAOEspecial<Ingreso>
{

    public IngresoDAO()
    {
        super(Ingreso.class, "bodega", "ingresos");
    }

    public Ingreso buscar(int id) throws SIGIPROException, SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        ;

        PreparedStatement consulta = getConexion().prepareStatement(
                " SELECT i.*, s.nombre_seccion, s.id_seccion, ci.id_producto, ci.nombre as nombre_producto, sb.id_sub_bodega, sb.nombre as nombre_sub_bodega "
              + " FROM bodega.ingresos i "
              + " INNER JOIN bodega.catalogo_interno ci on ci.id_producto = i.id_producto "
              + " INNER JOIN seguridad.secciones s on i.id_seccion = s.id_seccion "
              + " LEFT JOIN bodega.sub_bodegas sb on i.id_sub_bodega = sb.id_sub_bodega "
              + " WHERE id_ingreso = ?;"
        );
        consulta.setInt(1, id);
        ResultSet resultado = ejecutarConsulta(consulta);
        if (resultado.next()) {
            Ingreso i = new Ingreso();
            
            i.setCantidad(resultado.getInt("cantidad"));
            i.setEstado(resultado.getString("estado"));
            i.setFecha_ingreso(resultado.getDate("fecha_ingreso"));
            i.setFecha_registro(resultado.getDate("fecha_registro"));
            i.setFecha_vencimiento(resultado.getDate("fecha_vencimiento"));
            i.setId_ingreso(resultado.getInt("id_ingreso"));
            i.setPrecio(resultado.getInt("precio"));
            
            ProductoInterno pi = new ProductoInterno();
            pi.setNombre(resultado.getString("nombre_producto"));
            pi.setId_producto(resultado.getInt("id_producto"));
            i.setProducto(pi);
            
            Seccion s = new Seccion();
            s.setNombre_seccion(resultado.getString("nombre_seccion"));
            s.setId_seccion(resultado.getInt("id_seccion"));
            i.setSeccion(s);
            
            int id_sub_bodega = resultado.getInt("id_sub_bodega");
            if(id_sub_bodega != 0) {
                SubBodega sb = new SubBodega();
                sb.setId_sub_bodega(id_sub_bodega);
                sb.setNombre(resultado.getString("nombre_sub_bodega"));
                i.setSub_bodega(sb);
            }
            resultado.close();
            consulta.close();
            return i;
        }
        else {
            resultado.close();
            consulta.close();
            cerrarConexion();
            SIGIPROException ex = new SIGIPROException("El ingreso que está intentando buscar no existe.");
            throw ex;
        }
    }

    @Override
    public List<Ingreso> obtenerTodo() throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        return obtenerPorEstado(Ingreso.DISPONIBLE);
    }

    public List<Ingreso> obtenerPorEstado(String condicion) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        PreparedStatement consulta = construirConsultaObtenerTodo(condicion);
        List<Ingreso> resultado = construirLista(ejecutarConsulta(consulta));
        consulta.close();
        return resultado;
    }
    
    @Override
    protected List<Ingreso> construirLista(ResultSet resultadoConsulta) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        
        List<Ingreso> resultado = new ArrayList<Ingreso>();
        
        while (resultadoConsulta.next()) {
            Ingreso i = new Ingreso();
            
            i.setCantidad(resultadoConsulta.getInt("cantidad"));
            i.setEstado(resultadoConsulta.getString("estado"));
            i.setFecha_ingreso(resultadoConsulta.getDate("fecha_ingreso"));
            i.setFecha_registro(resultadoConsulta.getDate("fecha_registro"));
            i.setFecha_vencimiento(resultadoConsulta.getDate("fecha_vencimiento"));
            i.setId_ingreso(resultadoConsulta.getInt("id_ingreso"));
            i.setPrecio(resultadoConsulta.getInt("precio"));
            
            ProductoInterno pi = new ProductoInterno();
            pi.setNombre(resultadoConsulta.getString("nombre_producto"));
            pi.setId_producto(resultadoConsulta.getInt("id_producto"));
            i.setProducto(pi);
            
            Seccion s = new Seccion();
            s.setNombre_seccion(resultadoConsulta.getString("nombre_seccion"));
            s.setId_seccion(resultadoConsulta.getInt("id_seccion"));
            i.setSeccion(s);
            
            int id_sub_bodega = resultadoConsulta.getInt("id_sub_bodega");
            if(id_sub_bodega != 0) {
                SubBodega sb = new SubBodega();
                sb.setId_sub_bodega(id_sub_bodega);
                sb.setNombre(resultadoConsulta.getString("nombre_sub_bodega"));
                i.setSub_bodega(sb);
            }
            resultado.add(i);
        }

        resultadoConsulta.close();
        return resultado;
    }

    private PreparedStatement construirConsultaObtenerTodo(String condicion) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        String codigoConsulta = "SELECT i.*, s.nombre_seccion, s.id_seccion, ci.id_producto, ci.nombre as nombre_producto, sb.id_sub_bodega, sb.nombre as nombre_sub_bodega "
                                + " FROM " + nombreModulo + "." + nombreTabla + " i "
                                + " INNER JOIN bodega.catalogo_interno ci on ci.id_producto = " + "i.id_producto "
                                + " INNER JOIN seguridad.secciones s on s.id_seccion = i.id_seccion "
                                + " LEFT JOIN bodega.sub_bodegas sb on sb.id_sub_bodega = i.id_sub_bodega "
                                + " WHERE i.estado = ?";
        PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
        consulta.setString(1, condicion);
        return consulta;
    }

    public Ingreso registrarIngreso(Ingreso param) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException
    {
        boolean resultado = false;

        PreparedStatement insertIngreso = null;
        PreparedStatement upsertInventario = null;
        ResultSet resultadoInsert = null;

        try {
            getConexion().setAutoCommit(false);
            insertIngreso = getConexion().prepareStatement(
                    " INSERT INTO bodega.ingresos (id_producto, id_seccion, "
                  + " fecha_ingreso, fecha_registro, cantidad, fecha_vencimiento,"
                  + " estado, precio) VALUES (?,?,?,?,?,?,?,?) RETURNING id_ingreso;"
            );
            
            insertIngreso.setInt(1, param.getProducto().getId_producto());
            insertIngreso.setInt(2, param.getSeccion().getId_seccion());
            insertIngreso.setDate(3, param.getFecha_ingreso());
            insertIngreso.setDate(4, param.getFecha_registro());
            insertIngreso.setInt(5, param.getCantidad());
            insertIngreso.setDate(6, param.getFecha_vencimiento());
            insertIngreso.setString(7, param.getEstado());
            insertIngreso.setInt(8, param.getPrecio());
            

            if (!(param.getEstado().equalsIgnoreCase(Ingreso.CUARENTENA) || param.getEstado().equalsIgnoreCase(Ingreso.NO_DISPONIBLE))) {
                upsertInventario = construirUpsertInventario(param);
                upsertInventario.executeUpdate();
            }
            resultadoInsert = insertIngreso.executeQuery();
            if (resultadoInsert.next()) {
                param.setId_ingreso(resultadoInsert.getInt("id_ingreso"));
            }
            
            getConexion().commit();
            resultado = true;

        }
        catch (SQLException e) {
            resultado = false;
            e.printStackTrace();
            try {
                getConexion().rollback();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (insertIngreso != null) {
                insertIngreso.close();
            }
            if (upsertInventario != null) {
                upsertInventario.close();
            }
            if (resultadoInsert != null) {
                resultadoInsert.close();
            }
            getConexion().setAutoCommit(true);
            getConexion().close();
        }
        return param;
    }

    public boolean actualizar(Ingreso ingreso, int cantidadPrevia, String estadoOriginal) throws SQLException
    {
        boolean resultado = false;

        PreparedStatement actualizarIngreso = null;
        PreparedStatement actualizarInventario = null;

        boolean resultadoUpdate = false;
        boolean resultadoUpsert = false;

        try {

            getConexion().setAutoCommit(false);

            actualizarIngreso = getConexion().prepareStatement(" UPDATE bodega.ingresos "
                                                               + " SET id_producto = ?, "
                                                               + "     id_seccion = ?,"
                                                               + "     fecha_ingreso = ?,"
                                                               + "     fecha_registro = ?,"
                                                               + "     fecha_vencimiento = ?,"
                                                               + "     cantidad = ?,"
                                                               + "     estado = ?,"
                                                               + "     precio = ?"
                                                               + " WHERE id_ingreso = ?");

            actualizarIngreso.setInt(1, ingreso.getProducto().getId_producto());
            actualizarIngreso.setInt(2, ingreso.getSeccion().getId_seccion());
            actualizarIngreso.setDate(3, ingreso.getFecha_ingreso());
            actualizarIngreso.setDate(4, ingreso.getFecha_registro());
            actualizarIngreso.setDate(5, ingreso.getFecha_vencimiento());
            actualizarIngreso.setInt(6, ingreso.getCantidad());
            actualizarIngreso.setString(7, ingreso.getEstado());
            actualizarIngreso.setInt(8, ingreso.getPrecio());
            actualizarIngreso.setInt(9, ingreso.getId_ingreso());

            if (actualizarIngreso.executeUpdate() == 1) {
                resultadoUpdate = true;
            }
            else {
                resultadoUpdate = false;
            }

            if (ingreso.getEstado().equalsIgnoreCase(Ingreso.DISPONIBLE) || estadoOriginal.equalsIgnoreCase(Ingreso.DISPONIBLE)) {
                if (ingreso.getEstado().equalsIgnoreCase(estadoOriginal)) {
                    int diferencia = ingreso.getCantidad() - cantidadPrevia;
                    if (diferencia != 0) {
                        ingreso.setCantidad(diferencia);
                        actualizarInventario = construirUpsertInventario(ingreso);
                    }
                    else {
                        resultadoUpsert = true;
                    }
                }
                else if (ingreso.getEstado().equals(Ingreso.DISPONIBLE)) {
                    actualizarInventario = construirUpsertInventario(ingreso);
                }
                else {
                    int nuevaCantidad = cantidadPrevia * -1;
                    ingreso.setCantidad(nuevaCantidad);
                    actualizarInventario = construirUpsertInventario(ingreso);
                }
                if (!resultadoUpsert) {
                    int filasActualizarInventario = actualizarInventario.executeUpdate();
                    if (filasActualizarInventario == 0 || filasActualizarInventario == 1) {
                        resultadoUpsert = true;
                    }
                    else {
                        resultadoUpsert = false;
                    }
                }
            }

            resultado = resultadoUpsert && resultadoUpdate;
        }
        catch (SQLException e) {
            resultado = false;
            e.printStackTrace();
            try {
                getConexion().rollback();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        finally {
            if (resultado) {
                getConexion().commit();
            }
            if (actualizarIngreso != null) {
                actualizarIngreso.close();
            }
            if (actualizarInventario != null) {
                actualizarInventario.close();
            }
            cerrarConexion();
        }
        return resultado;
    }

    private PreparedStatement construirUpsertInventario(Ingreso param) throws SQLException
    {
        PreparedStatement upsertInventario = getConexion().prepareStatement(
                " WITH upsert AS "
                + " (UPDATE bodega.inventarios SET stock_actual = stock_actual + ? "
                + "                            WHERE id_producto = ? and id_seccion = ? RETURNING *) "
                + "   INSERT INTO bodega.inventarios(id_producto, "
                + "                                  id_seccion, "
                + "                                  stock_actual "
                + "                                 ) "
                + "                               SELECT ?, "
                + "                                      ?, "
                + "                                      ?  "
                + "                               WHERE NOT EXISTS (SELECT * FROM upsert); ");

        upsertInventario.setInt(1, param.getCantidad());
        upsertInventario.setInt(6, param.getCantidad());

        upsertInventario.setInt(2, param.getProducto().getId_producto());
        upsertInventario.setInt(4, param.getProducto().getId_producto());

        upsertInventario.setInt(3, param.getSeccion().getId_seccion());
        upsertInventario.setInt(5, param.getSeccion().getId_seccion());

        return upsertInventario;
    }

    public boolean decisionesCuarentena(Ingreso ingreso, String estado) throws SQLException
    {
        boolean resultado = false;
        boolean resultadoIngreso = false;
        boolean resultadoInventario = false;

        PreparedStatement updateIngreso = null;
        PreparedStatement upsertInventario = null;
        PreparedStatement[] consultas = null;

        try {

            consultas = prepararConsultaDecision(ingreso, estado);
            updateIngreso = consultas[0];
            upsertInventario = consultas[1];

            getConexion().setAutoCommit(false);

            int registrosIngresos = updateIngreso.executeUpdate();
            if (registrosIngresos == 1) {
                resultadoIngreso = true;
            }

            if (upsertInventario != null) {
                int registrosInventarios = upsertInventario.executeUpdate();
                if (registrosInventarios == 1 || registrosInventarios == 0) {
                    resultadoInventario = true;
                }
            }
            else {
                resultadoInventario = true;
            }

            if (resultadoIngreso && resultadoInventario) {
                resultado = true;
            }
            else {
                getConexion().rollback();
            }
        }
        catch (SQLException e) {
            resultado = false;
            e.printStackTrace();
            try {
                getConexion().rollback();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        finally {
            if (resultado) {
                getConexion().commit();
            }
            if (consultas != null) {
                for(PreparedStatement consulta : consultas) {
                    if (consulta != null){
                        consulta.close();
                    }
                }
            }
            if (updateIngreso != null) {
                updateIngreso.close();
            }
            if (upsertInventario != null) {
                upsertInventario.close();
            }
            cerrarConexion();
        }
        return resultado;
    }

    private PreparedStatement[] prepararConsultaDecision(Ingreso ingreso, String estado) throws SQLException
    {
        PreparedStatement[] listaConsultas = new PreparedStatement[2];
        String stringConsulta = " UPDATE " + this.nombreModulo + "." + this.nombreTabla
                                + " SET estado = ? "
                                + " WHERE id_ingreso = ?";

        PreparedStatement consulta = getConexion().prepareStatement(stringConsulta);

        consulta.setString(1, estado);
        consulta.setInt(2, ingreso.getId_ingreso());

        listaConsultas[0] = consulta;
        if (estado.equals(Ingreso.DISPONIBLE)) {
            listaConsultas[1] = this.construirUpsertInventario(ingreso);
        }

        return listaConsultas;
    }
}
