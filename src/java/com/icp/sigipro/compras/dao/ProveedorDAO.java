/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.compras.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.icp.sigipro.compras.modelos.Proveedor;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class ProveedorDAO
{

    private Connection conexion;

    public ProveedorDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
    }

    public boolean insertarProveedor(Proveedor proveedor)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion()
                    .prepareStatement("insert into compras.proveedores(nombre_proveedor,telefono1,telefono2,telefono3,correo) values (?, ?, ?, ?, ?) RETURNING id_proveedor");
            // Parameters start with 1
            consulta.setString(1, proveedor.getNombre_proveedor());
            consulta.setString(2, proveedor.getTelefono1());
            consulta.setString(3, proveedor.getTelefono2());
            consulta.setString(4, proveedor.getTelefono3());
            consulta.setString(5, proveedor.getCorreo());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                proveedor.setId_proveedor(resultadoConsulta.getInt("id_proveedor"));

            }

            resultadoConsulta.close();
            consulta.close();
            getConexion().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean editarProveedor(Proveedor proveedor)
    {
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = getConexion()
                    .prepareStatement("update compras.proveedores set nombre_proveedor=?, telefono1=?, telefono2=?, telefono3=?, correo=?"
                                      + "where id_proveedor=?");
            // Parameters start with 1
            preparedStatement.setString(1, proveedor.getNombre_proveedor());
            preparedStatement.setString(2, proveedor.getTelefono1());
            preparedStatement.setString(3, proveedor.getTelefono2());
            preparedStatement.setString(4, proveedor.getTelefono3());
            preparedStatement.setString(5, proveedor.getCorreo());
            preparedStatement.setInt(6, proveedor.getId_proveedor());

            if (preparedStatement.executeUpdate() == 1) {
                resultado = true;
            }
            preparedStatement.close();
            getConexion().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarProveedor(int id_proveedor) throws SIGIPROException
    {
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = getConexion()
                    .prepareStatement("delete from compras.proveedores where id_proveedor=?");
            preparedStatement.setInt(1, id_proveedor);
            if (preparedStatement.executeUpdate() == 1) {
                resultado = true;
            }
            preparedStatement.close();
            getConexion().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SIGIPROException("El proveedor no se pudo eliminar debido a que está referenciado en uno o más productos externos.");
        }
        return resultado;
    }

    public Proveedor obtenerProveedor(int id_proveedor)
    {
        Proveedor proveedor = new Proveedor();
        try {
            PreparedStatement preparedStatement = getConexion().
                    prepareStatement("select * from compras.proveedores where id_proveedor=?");
            preparedStatement.setInt(1, id_proveedor);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                proveedor.setId_proveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre_proveedor(rs.getString("nombre_proveedor"));
                proveedor.setTelefono1(rs.getString("telefono1"));
                proveedor.setTelefono2(rs.getString("telefono2"));
                proveedor.setTelefono3(rs.getString("telefono3"));
                proveedor.setCorreo(rs.getString("correo"));
            }
            rs.close();
            preparedStatement.close();
            getConexion().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedor;
    }

    public List<Proveedor> obtenerProveedores()
    {
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        try {
            Statement statement = getConexion().createStatement();
            ResultSet rs = statement.executeQuery("select * from compras.proveedores");
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId_proveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre_proveedor(rs.getString("nombre_proveedor"));
                proveedor.setTelefono1(rs.getString("telefono1"));
                proveedor.setTelefono2(rs.getString("telefono2"));
                proveedor.setTelefono3(rs.getString("telefono3"));
                proveedor.setCorreo(rs.getString("correo"));
                proveedores.add(proveedor);
            }
            
            rs.close();
            statement.close();
            getConexion().close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedores;
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
                ex.printStackTrace();
                conexion = null;
            }
        }
        return conexion;
    }

}
