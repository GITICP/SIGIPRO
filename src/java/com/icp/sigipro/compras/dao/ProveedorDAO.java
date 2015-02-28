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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class ProveedorDAO {
    
    private Connection conexion;

    public ProveedorDAO() {
        
            SingletonBD s = SingletonBD.getSingletonBD();
            conexion = s.conectar();
    }

    public boolean insertarProveedor(Proveedor proveedor) {
        boolean resultado = false;
        try {
            PreparedStatement consulta = conexion
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean editarProveedor(Proveedor proveedor) {
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = conexion
                    .prepareStatement("update compras.proveedores set nombre_proveedor=?, telefono1=?, telefono2=?, telefono3=?, correo=?"
                            + "where id_proveedor=?");
            // Parameters start with 1
            preparedStatement.setString(1, proveedor.getNombre_proveedor());
            preparedStatement.setString(2, proveedor.getTelefono1());
            preparedStatement.setString(3, proveedor.getTelefono2());
            preparedStatement.setString(4, proveedor.getTelefono3());
            preparedStatement.setString(5, proveedor.getCorreo());
            preparedStatement.setInt(6, proveedor.getId_proveedor());

        if (preparedStatement.executeUpdate() == 1){
               resultado = true;
           }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
        public boolean eliminarProveedor(int id_proveedor) {
        boolean resultado = false;            
        try {
            PreparedStatement preparedStatement = conexion
                    .prepareStatement("delete from compras.proveedores where id_proveedor=?");
            // Parameters start with 1
            preparedStatement.setInt(1, id_proveedor);
        if (preparedStatement.executeUpdate() == 1){
               resultado = true;
           }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
        
    public Proveedor obtenerProveedor(int id_proveedor) {
        Proveedor proveedor = new Proveedor();
        try {
            PreparedStatement preparedStatement = conexion.
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedor;
    }
    
    public List<Proveedor> obtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<Proveedor>();
        try {
            Statement statement = conexion.createStatement();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedores;
    }    

}
