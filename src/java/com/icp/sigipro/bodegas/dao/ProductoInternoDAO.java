/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.bodegas.modelos.Reactivo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Boga
 */
public class ProductoInternoDAO
{

    private Connection conexion;

    public ProductoInternoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarProductoInterno(ProductoInterno p, String ubicaciones, String productosExternos)
    {

        boolean resultado = false;

        try {
            getConexion().setAutoCommit(false);
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.catalogo_interno (nombre, codigo_icp, stock_minimo, stock_maximo, presentacion, descripcion, cuarentena, perecedero) "
                                                                        + " VALUES (?,?,?,?,?,?,?,?) RETURNING id_producto");

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getCodigo_icp());
            consulta.setInt(3, p.getStock_minimo());
            consulta.setInt(4, p.getStock_maximo());
            consulta.setString(5, p.getPresentacion());
            consulta.setString(6, p.getDescripcion());
            consulta.setBoolean(7, p.isCuarentena());
            consulta.setBoolean(8, p.isPerecedero());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                p.setId_producto(resultadoConsulta.getInt("id_producto"));
            }

            Reactivo reactivo = p.getReactivo();

            if (reactivo != null) {
                PreparedStatement consultaReactivo = getConexion().prepareStatement(
                        " INSERT INTO bodega.reactivos (id_producto, numero_cas, formula_quimica, familia, cantidad_botella_bodega, cantidad_botella_lab, volumen_bodega, volumen_lab) "
                        + " VALUES (?,?,?,?,?,?,?,?) RETURNING id_reactivo");

                consultaReactivo.setInt(1, p.getId_producto());
                consultaReactivo.setString(2, reactivo.getNumero_cas());
                consultaReactivo.setString(3, reactivo.getFormula_quimica());
                consultaReactivo.setString(4, reactivo.getFamilia());
                consultaReactivo.setInt(5, reactivo.getCantidad_botella_bodega());
                consultaReactivo.setInt(6, reactivo.getCantidad_botella_lab());
                consultaReactivo.setString(7, reactivo.getVolumen_bodega());
                consultaReactivo.setString(8, reactivo.getVolumen_lab());

                ResultSet resultadoConsultaR = consultaReactivo.executeQuery();
                if (resultadoConsultaR.next()) {
                    resultado = true;
                    reactivo.setId_reactivo(resultadoConsultaR.getInt("id_reactivo"));
                    resultadoConsultaR.close();
                }
            }

            if (ubicaciones != null) {
                String[] ids = parsearAsociacion("#u#", ubicaciones);

                PreparedStatement consultaUbicaciones = getConexion().prepareStatement(
                        " INSERT INTO bodega.ubicaciones_catalogo_interno(id_ubicacion, id_producto) "
                        + " VALUES (?, ?)");

                consultaUbicaciones.setInt(2, p.getId_producto());

                for (String id : ids) {
                    consultaUbicaciones.setInt(1, Integer.parseInt(id));
                    consultaUbicaciones.executeUpdate();
                }
                
                consultaUbicaciones.close();
            }

            if (productosExternos != null) {
                String[] ids = parsearAsociacion("#p#", productosExternos);

                PreparedStatement consultaUbicaciones = getConexion().prepareStatement(
                        " INSERT INTO bodega.catalogos_internos_externos(id_producto_ext, id_producto) "
                        + " VALUES (?, ?)");

                for (String id : ids) {
                    consultaUbicaciones.setInt(1, Integer.parseInt(id));
                    consultaUbicaciones.setInt(2, p.getId_producto());
                    consultaUbicaciones.addBatch();
                }
                consultaUbicaciones.executeBatch();
            }

            getConexion().commit();
            resultado = true;
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean editarProductoInterno(ProductoInterno p, String ubicaciones, String productosExternos)
    {

        boolean resultado = false;
        try {
            getConexion().setAutoCommit(false);
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE bodega.catalogo_interno "
                    + " SET nombre=?, codigo_icp=?, stock_minimo=?, stock_maximo=?, "
                    + " presentacion=?, descripcion=?, cuarentena=?, perecedero=? "
                    + " WHERE id_producto=?; "
            );

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getCodigo_icp());
            consulta.setInt(3, p.getStock_minimo());
            consulta.setInt(4, p.getStock_maximo());
            consulta.setString(5, p.getPresentacion());
            consulta.setString(6, p.getDescripcion());
            consulta.setBoolean(7, p.isCuarentena());
            consulta.setBoolean(8, p.isPerecedero());
            consulta.setInt(9, p.getId_producto());

            consulta.executeUpdate();

            if (p.getReactivo() == null) {
                PreparedStatement eliminarReactivo = getConexion().prepareStatement(
                        " DELETE FROM bodega.reactivos WHERE id_producto = ? ");
                eliminarReactivo.setInt(1, p.getId_producto());
                eliminarReactivo.execute();
                eliminarReactivo.close();
            }
            else {
                PreparedStatement upsertReactivo = getConexion().prepareStatement(
                        " WITH upsert AS "
                        + " (UPDATE bodega.reactivos SET id_producto = ?, "
                        + "                               numero_cas = ?, "
                        + "                               formula_quimica = ?, "
                        + "                               familia = ?, "
                        + "                               cantidad_botella_bodega = ?, "
                        + "                               cantidad_botella_lab = ?, "
                        + "                               volumen_bodega = ?, "
                        + "                               volumen_lab = ? "
                        + "                               WHERE id_producto = ? RETURNING *) "
                        + "   INSERT INTO bodega.reactivos(id_producto, "
                        + "                                numero_cas, "
                        + "                                formula_quimica, "
                        + "                                familia, "
                        + "                                cantidad_botella_bodega, "
                        + "                                cantidad_botella_lab, "
                        + "                                volumen_bodega, "
                        + "                                volumen_lab "
                        + "                                ) "
                        + "           SELECT ?, "
                        + "                  ?, "
                        + "                  ?, "
                        + "                  ?, "
                        + "                  ?, "
                        + "                  ?, "
                        + "                  ?, "
                        + "                  ?  "
                        + "                  WHERE NOT EXISTS (SELECT * FROM upsert); ");

                Reactivo r = p.getReactivo();

                upsertReactivo.setInt(1, p.getId_producto());
                upsertReactivo.setInt(9, p.getId_producto());
                upsertReactivo.setInt(10, p.getId_producto());
                upsertReactivo.setString(2, r.getNumero_cas());
                upsertReactivo.setString(11, r.getNumero_cas());
                upsertReactivo.setString(3, r.getFormula_quimica());
                upsertReactivo.setString(12, r.getFormula_quimica());
                upsertReactivo.setString(4, r.getFamilia());
                upsertReactivo.setString(13, r.getFamilia());
                upsertReactivo.setInt(5, r.getCantidad_botella_bodega());
                upsertReactivo.setInt(14, r.getCantidad_botella_bodega());
                upsertReactivo.setInt(6, r.getCantidad_botella_lab());
                upsertReactivo.setInt(15, r.getCantidad_botella_lab());
                upsertReactivo.setString(7, r.getVolumen_bodega());
                upsertReactivo.setString(16, r.getVolumen_bodega());
                upsertReactivo.setString(8, r.getVolumen_lab());
                upsertReactivo.setString(17, r.getVolumen_lab());

                upsertReactivo.executeUpdate();
                
                upsertReactivo.close();
            }

            PreparedStatement eliminarUbicaciones = getConexion().prepareStatement(
                    " DELETE FROM bodega.ubicaciones_catalogo_interno WHERE id_producto = ? ");
            eliminarUbicaciones.setInt(1, p.getId_producto());

            PreparedStatement eliminarProductos = getConexion().prepareStatement(
                    " DELETE FROM bodega.catalogos_internos_externos WHERE id_producto = ? ");
            eliminarProductos.setInt(1, p.getId_producto());

            eliminarUbicaciones.execute();
            eliminarProductos.execute();
            eliminarUbicaciones.close();
            eliminarProductos.close();

            if (ubicaciones != null) {
                String[] ids = parsearAsociacion("#u#", ubicaciones);

                PreparedStatement consultaUbicaciones = getConexion().prepareStatement(
                        " INSERT INTO bodega.ubicaciones_catalogo_interno(id_ubicacion, id_producto) "
                        + " VALUES (?, ?)");

                

                for (String id : ids) {
                    consultaUbicaciones.setInt(1, Integer.parseInt(id));
                    consultaUbicaciones.setInt(2, p.getId_producto());
                    consultaUbicaciones.addBatch();
                }
                consultaUbicaciones.executeBatch();
                consultaUbicaciones.close();
            }

            if (productosExternos != null) {
                String[] ids = parsearAsociacion("#p#", productosExternos);

                PreparedStatement consultaProductos = getConexion().prepareStatement(
                        " INSERT INTO bodega.catalogos_internos_externos(id_producto_ext, id_producto) "
                        + " VALUES (?, ?)");

                for (String id : ids) {
                    consultaProductos.setInt(1, Integer.parseInt(id));
                    consultaProductos.setInt(2, p.getId_producto());
                    consultaProductos.addBatch();
                }
                consultaProductos.executeBatch();
                consultaProductos.close();
            }

            consulta.close();
            getConexion().commit();
            cerrarConexion();
            resultado = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            try {
                getConexion().rollback();
                cerrarConexion();
            }
            catch (Exception ex_bd) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public boolean eliminarProductoInterno(int id_producto)
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bodega.catalogo_interno "
                    + " WHERE id_producto=?; "
            );

            consulta.setInt(1, id_producto);

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

    public int obtenerIdReactivo(int id_producto)
    {
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    "SELECT id_reactivo FROM bodega.reactivos WHERE id_producto = ?");

            consulta.setInt(1, id_producto);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_producto");
            }
            rs.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public ProductoInterno obtenerProductoInterno(int id_producto)
    {

        ProductoInterno producto = new ProductoInterno();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT producto.*, reactivo.*"
                    + " From ("
                    + " 	(Select * FROM bodega.catalogo_interno where id_producto = ?) producto"
                    + "		left join "
                    + "	(Select * FROM bodega.reactivos where id_producto = ?) reactivo"
                    + "	on producto.id_producto = reactivo.id_producto"
                    + " ) ");

            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_producto);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCodigo_icp(rs.getString("codigo_icp"));
                producto.setStock_minimo(rs.getInt("stock_minimo"));
                producto.setStock_maximo(rs.getInt("stock_maximo"));
                producto.setPresentacion(rs.getString("presentacion"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCuarentena(rs.getBoolean("cuarentena"));
                producto.setPerecedero(rs.getBoolean("perecedero"));

                if (rs.getInt("id_reactivo") != 0) {
                    Reactivo r = new Reactivo();
                    r.setId_reactivo(rs.getInt("id_reactivo"));
                    r.setNumero_cas(rs.getString("numero_cas"));
                    r.setFormula_quimica(rs.getString("formula_quimica"));
                    r.setFamilia(rs.getString("familia"));
                    r.setCantidad_botella_bodega(rs.getInt("cantidad_botella_bodega"));
                    r.setCantidad_botella_lab(rs.getInt("cantidad_botella_lab"));
                    r.setVolumen_bodega(rs.getString("volumen_bodega"));
                    r.setVolumen_lab(rs.getString("volumen_lab"));
                    producto.setReactivo(r);
                }
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return producto;
    }

    public List<ProductoInterno> obtenerProductos()
    {

        List<ProductoInterno> resultado = new ArrayList<ProductoInterno>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT ci.*, r.id_reactivo "
                                                                        + " FROM bodega.catalogo_interno ci "
                                                                        + "   LEFT OUTER JOIN bodega.reactivos r "
                                                                        + "     on ci.id_producto = r.id_producto ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                ProductoInterno producto = new ProductoInterno();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCodigo_icp(rs.getString("codigo_icp"));
                producto.setStock_minimo(rs.getInt("stock_minimo"));
                producto.setStock_maximo(rs.getInt("stock_maximo"));
                producto.setPresentacion(rs.getString("presentacion"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCuarentena(rs.getBoolean("cuarentena"));
                producto.setPerecedero(rs.getBoolean("perecedero"));

                int id_reactivo = rs.getInt("id_reactivo");
                if (id_reactivo > 0) {
                    producto.setReactivo(new Reactivo());
                }

                System.out.println(id_reactivo);

                resultado.add(producto);
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

    public List<ProductoInterno> obtenerProductosYCuarentena()
    {
        List<ProductoInterno> resultado = new ArrayList<ProductoInterno>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_producto, nombre, codigo_icp, cuarentena, perecedero FROM bodega.catalogo_interno ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                ProductoInterno producto = new ProductoInterno();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCodigo_icp(rs.getString("codigo_icp"));
                producto.setCuarentena(rs.getBoolean("cuarentena"));
                producto.setPerecedero(rs.getBoolean("perecedero"));

                resultado.add(producto);
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

    public List<ProductoInterno> obtenerProductosInternosRestantes(int p_IdExterno)
    {
        List<ProductoInterno> resultado = new ArrayList<ProductoInterno>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement("SELECT * "
                                                      + "FROM bodega.catalogo_interno c "
                                                      + "WHERE c.id_producto NOT IN (SELECT ru.id_producto FROM bodega.catalogos_internos_externos ru WHERE ru.id_producto_ext = ?)");
            consulta.setInt(1, p_IdExterno);
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                ProductoInterno producto = new ProductoInterno();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCodigo_icp(rs.getString("codigo_icp"));
                producto.setStock_minimo(rs.getInt("stock_minimo"));
                producto.setStock_maximo(rs.getInt("stock_maximo"));
                producto.setPresentacion(rs.getString("presentacion"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPerecedero(rs.getBoolean("perecedero"));
                producto.setCuarentena(rs.getBoolean("cuarentena"));

                resultado.add(producto);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            resultado = null;
        }

        return resultado;
    }

    public List<ProductoInterno> obtenerProductosInternos_Externo(int p_IdExterno)
    {
        List<ProductoInterno> resultado = new ArrayList<ProductoInterno>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement("SELECT * "
                                                      + "FROM bodega.catalogo_interno c "
                                                      + "WHERE c.id_producto IN (SELECT ru.id_producto FROM bodega.catalogos_internos_externos ru WHERE ru.id_producto_ext = ?)");
            consulta.setInt(1, p_IdExterno);
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                ProductoInterno producto = new ProductoInterno();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setCodigo_icp(rs.getString("codigo_icp"));
                producto.setStock_minimo(rs.getInt("stock_minimo"));
                producto.setStock_maximo(rs.getInt("stock_maximo"));
                producto.setPresentacion(rs.getString("presentacion"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCuarentena(rs.getBoolean("cuarentena"));
                producto.setPerecedero(rs.getBoolean("perecedero"));

                resultado.add(producto);
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            resultado = null;
        }

        return resultado;
    }

    public boolean validarCodigoICP(String codigo, int id_producto)
    {
        boolean resultado = false;

        if (conexion != null) {
            try {
                PreparedStatement consulta;
                consulta = getConexion().prepareStatement("SELECT codigo_icp FROM bodega.catalogo_interno WHERE codigo_icp = ? and id_producto <> ?");
                consulta.setString(1, codigo);
                consulta.setInt(2, id_producto);

                ResultSet resultadoConsulta = consulta.executeQuery();
                if (!resultadoConsulta.next()) {
                    resultado = true;
                }
                resultadoConsulta.close();
                consulta.close();
                cerrarConexion();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
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
