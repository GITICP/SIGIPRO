/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.Ingreso;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Boga
 */
public class IngresoDAO extends DAO<Ingreso>
{

    public IngresoDAO()
    {
        super(Ingreso.class, "bodega", "ingresos");
    }

    @Override
    public Ingreso buscar(int id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, SIGIPROException
    {
        String codigoConsulta = "SELECT * FROM " + nombreModulo + "." + nombreTabla + " i INNER JOIN bodega.catalogo_interno ci on ci.id_producto = i.id_producto INNER JOIN seguridad.secciones s on i.id_seccion = s.id_seccion WHERE id_ingreso = ?;";

        Ingreso t = tipo.newInstance();

        PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
        consulta.setInt(1, id);
        ResultSet resultado = ejecutarConsulta(consulta);
        if (resultado.next()) {
            Ingreso i = construirObjeto(t.getMetodos("set"), resultado);
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
    public List<Ingreso> buscarPor(String[] campos, Object valor)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Ingreso param)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(Ingreso param)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private PreparedStatement construirConsultaObtenerTodo(String condicion) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        String codigoConsulta = "SELECT * FROM " + nombreModulo + "." + nombreTabla + " i "
                                + " INNER JOIN bodega.catalogo_interno ci on ci.id_producto = " + "i.id_producto "
                                + " INNER JOIN seguridad.secciones s on s.id_seccion = i.id_seccion "
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
            insertIngreso = construirInsertar(param);

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
