/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Producto_venta;;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Producto_ventaDAO extends DAO {

    public Producto_ventaDAO() {
    }

    public List<Producto_venta> obtenerProductos_venta() throws SIGIPROException {

        List<Producto_venta> resultado = new ArrayList<Producto_venta>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_venta; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Producto_venta producto = new Producto_venta();
                
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setTipo(rs.getString("tipo"));
                resultado.add(producto);

            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }

    public Producto_venta obtenerProducto_venta(int id_producto) throws SIGIPROException {

        Producto_venta resultado = new Producto_venta();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_venta where id_producto = ?; ");
            consulta.setInt(1, id_producto);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_producto(rs.getInt("id_producto"));
                resultado.setNombre(rs.getString("nombre"));
                resultado.setDescripcion(rs.getString("descripcion"));
                resultado.setTipo(rs.getString("tipo"));
            }
            rs.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
    
    public int insertarProducto_venta(Producto_venta p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.producto_venta (nombre, descripcion, tipo)"
                    + " VALUES (?,?,?) RETURNING id_producto");

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getDescripcion());
            consulta.setString(3, p.getTipo());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_producto");
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getMessage().contains("llave"))
              { 
                throw new SIGIPROException("El nombre del producto debe ser único");}
            else {
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");}
        }
        return resultado;
    }

    public boolean editarProducto_venta(Producto_venta p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.producto_venta"
                    + " SET nombre=?, descripcion=?, tipo=?"
                    + " WHERE id_producto=?; "
            );

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getDescripcion());
            consulta.setString(3, p.getTipo());
            consulta.setInt(4, p.getId_producto());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getMessage().contains("llave"))
              { 
                throw new SIGIPROException("El nombre del producto debe ser único");}
            else {
            throw new SIGIPROException("Se produjo un error al procesar la edición");}
        }
        return resultado;
    }

    public boolean eliminarProducto_venta(int id_producto) throws SIGIPROException {

        boolean resultado = false;
        Producto_IntencionDAO piDAO = new Producto_IntencionDAO();
        
        if (piDAO.CantidadDeIntencionesConProducto(id_producto) > 0){
            throw new SIGIPROException("El producto a eliminar tiene Solicitudes o Intenciones de Venta relacionadas.");
        }
        else{
            try {
                PreparedStatement consulta = getConexion().prepareStatement(
                        " DELETE FROM ventas.producto_venta "
                        + " WHERE id_producto=?; "
                );

                consulta.setInt(1, id_producto);

                if (consulta.executeUpdate() == 1) {
                    resultado = true;
                }
                consulta.close();
                cerrarConexion();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new SIGIPROException("Se produjo un error al procesar la eliminación");
            }
            return resultado;
        }
    }

}
