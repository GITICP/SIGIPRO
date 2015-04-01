/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.bodegas.modelos.ProductoExterno;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class ProductoExternoDAO
{

    private Connection conexion;

    public ProductoExternoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarProductoExterno(ProductoExterno p)
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO bodega.catalogo_externo (producto, codigo_externo, marca, id_proveedor) "
                                                                        + " VALUES (?,?,?,?) RETURNING id_producto_ext");

            consulta.setString(1, p.getProducto());
            consulta.setString(2, p.getCodigo_Externo());
            if (p.getId_Proveedor() == 0) {
                consulta.setNull(4, Types.NULL);
            }
            else {
                consulta.setInt(4, p.getId_Proveedor());
            }
            consulta.setString(3, p.getMarca());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                p.setId_producto_ext(resultadoConsulta.getInt("id_producto_ext"));
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

    public boolean editarProductoExterno(ProductoExterno p)
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE bodega.catalogo_externo "
                    + " SET producto=?, codigo_externo=?, marca=?, "
                    + " id_proveedor=? "
                    + " WHERE id_producto_ext=?; "
            );

            consulta.setString(1, p.getProducto());
            consulta.setString(2, p.getCodigo_Externo());
            if (p.getId_Proveedor() == 0) {
                consulta.setNull(4, Types.NULL);
            }
            else {
                consulta.setInt(4, p.getId_Proveedor());
            }
            consulta.setString(3, p.getMarca());
            consulta.setInt(5, p.getId_producto_ext());

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

    public boolean eliminarProductoExterno(int id_producto_ext) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM bodega.catalogo_externo "
                    + " WHERE id_producto_ext=?; "
            );

            consulta.setInt(1, id_producto_ext);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            throw new SIGIPROException("No se puede borrar este producto externo porque está ligado a uno o más productos internos.");
        }
        return resultado;
    }

    public ProductoExterno obtenerProductoExterno(int id_producto_ext)
    {

        ProductoExterno producto = new ProductoExterno();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM bodega.catalogo_externo where id_producto_ext = ?");

            consulta.setInt(1, id_producto_ext);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                producto.setId_producto_ext(rs.getInt("id_producto_ext"));
                producto.setProducto(rs.getString("producto"));
                producto.setCodigo_Externo(rs.getString("codigo_externo"));
                producto.setId_Proveedor(rs.getInt("id_proveedor"));
                producto.setMarca(rs.getString("marca"));
                try {
                    PreparedStatement consulta2 = getConexion().prepareStatement("SELECT nombre_proveedor FROM compras.proveedores where id_proveedor = ?");
                    consulta2.setInt(1, producto.getId_Proveedor());
                    ResultSet rs2 = consulta2.executeQuery();
                    if (rs2.next()) {
                        producto.setNombreProveedor(rs2.getString("nombre_proveedor"));
                    }
                    rs2.close();
                    consulta2.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
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

    public List<ProductoExterno> obtenerProductos()
    {

        List<ProductoExterno> resultado = new ArrayList<ProductoExterno>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM bodega.catalogo_externo ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                ProductoExterno producto = new ProductoExterno();
                producto.setId_producto_ext(rs.getInt("id_producto_ext"));
                producto.setProducto(rs.getString("producto"));
                producto.setCodigo_Externo(rs.getString("codigo_externo"));
                producto.setId_Proveedor(rs.getInt("id_proveedor"));
                producto.setMarca(rs.getString("marca"));
                try {
                    PreparedStatement consulta2 = getConexion().prepareStatement("SELECT nombre_proveedor FROM compras.proveedores where id_proveedor = ?");
                    consulta2.setInt(1, producto.getId_Proveedor());
                    ResultSet rs2 = consulta2.executeQuery();
                    if (rs2.next()) {
                        producto.setNombreProveedor(rs2.getString("nombre_proveedor"));
                    }
                    rs2.close();
                    consulta2.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
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

    public List<ProductoExterno> obtenerProductosLimitado()
    {

        List<ProductoExterno> resultado = new ArrayList<ProductoExterno>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_producto_ext, producto, codigo_externo FROM bodega.catalogo_externo ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                ProductoExterno producto = new ProductoExterno();

                producto.setId_producto_ext(rs.getInt("id_producto_ext"));
                producto.setProducto(rs.getString("producto"));
                producto.setCodigo_Externo(rs.getString("codigo_externo"));

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

    public List<ProductoExterno> obtenerProductosRestantes(int p_IdInterno)
    {
        List<ProductoExterno> resultado = new ArrayList<ProductoExterno>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement("SELECT id_producto_ext, producto, codigo_externo "
                                                      + "FROM bodega.catalogo_externo ce "
                                                      + "WHERE ce.id_producto_ext NOT IN (SELECT cei.id_producto_ext FROM bodega.catalogos_internos_externos cei WHERE cei.id_producto = ?)");
            consulta.setInt(1, p_IdInterno);
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                ProductoExterno producto = new ProductoExterno();
                producto.setId_producto_ext(rs.getInt("id_producto_ext"));
                producto.setProducto(rs.getString("producto"));
                producto.setCodigo_Externo(rs.getString("codigo_externo"));

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

    public List<ProductoExterno> obtenerProductos(int p_IdInterno)
    {
        List<ProductoExterno> resultado = new ArrayList<ProductoExterno>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement("SELECT id_producto_ext, producto, codigo_externo "
                                                      + "FROM bodega.catalogo_externo ce "
                                                      + "WHERE ce.id_producto_ext IN (SELECT cei.id_producto_ext FROM bodega.catalogos_internos_externos cei WHERE cei.id_producto = ?)");
            consulta.setInt(1, p_IdInterno);
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                ProductoExterno producto = new ProductoExterno();
                producto.setId_producto_ext(rs.getInt("id_producto_ext"));
                producto.setProducto(rs.getString("producto"));
                producto.setCodigo_Externo(rs.getString("codigo_externo"));

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

    private Connection getConexion()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        if (conexion == null) {
            conexion = s.conectar();
        }
        else {
            try {
                if (conexion.isClosed()) {
                    conexion = s.conectar();
                }
            }
            catch (Exception ex) {
                conexion = null;
            }
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
