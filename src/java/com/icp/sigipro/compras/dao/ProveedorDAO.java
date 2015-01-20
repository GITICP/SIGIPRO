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
import org.apache.tomcat.jni.User;
import com.icp.sigipro.compras.modelos.Proveedor;

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
            PreparedStatement preparedStatement = conexion
                    .prepareStatement("insert into compras.proveedores(nombre_proveedor,telefono1,telefono2,telefono3,correo) values (?, ?, ?, ?, ?)");
            // Parameters start with 1
            preparedStatement.setString(1, proveedor.getNombre_proveedor());
            preparedStatement.setString(2, proveedor.getTelefono1());
            preparedStatement.setString(3, proveedor.getTelefono2());
            preparedStatement.setString(4, proveedor.getTelefono3());
            preparedStatement.setString(5, proveedor.getCorreo());

           
           if (preparedStatement.executeUpdate() == 1){
               resultado = true;
           }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    
}
