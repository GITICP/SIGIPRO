/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Producto_Orden;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Producto_OrdenDAO extends DAO {

    public Producto_OrdenDAO() {
    }
    
    private Producto_ventaDAO pDAO = new Producto_ventaDAO();
    private Orden_compraDAO iDAO = new Orden_compraDAO();
    
    public boolean esProductoOrden (int id_producto, int id_orden) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_orden WHERE id_producto=? and id_orden=?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_orden);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado = true;
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
    
    public List<Producto_Orden> obtenerProductosOrden(int id_orden) throws SIGIPROException {

        List<Producto_Orden> resultado = new ArrayList<Producto_Orden>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_orden WHERE id_orden=?; ");
            consulta.setInt(1, id_orden);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Producto_Orden producto_orden = new Producto_Orden();
                
                producto_orden.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                producto_orden.setOrden_compra(iDAO.obtenerOrden_compra(rs.getInt("id_orden")));
                producto_orden.setCantidad(rs.getInt("cantidad"));
                
                resultado.add(producto_orden);
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

    public Producto_Orden obtenerProducto_Orden(int id_producto, int id_orden) throws SIGIPROException {

        Producto_Orden resultado = new Producto_Orden();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_orden where id_producto = ? and id_orden = ?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_orden);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                resultado.setOrden_compra(iDAO.obtenerOrden_compra(rs.getInt("id_orden")));
                resultado.setCantidad(rs.getInt("cantidad"));
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
    
    public int insertarProducto_Orden(Producto_Orden p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.producto_orden (id_producto, id_orden, cantidad)"
                    + " VALUES (?,?,?) RETURNING id_producto");

            consulta.setInt(1,p.getProducto().getId_producto());
            consulta.setInt(2,p.getOrden_compra().getId_orden());
            consulta.setInt(3,p.getCantidad());
            
            //System.out.println("CONSULTA A EJECUTAR: "+consulta.toString());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_producto");
            }
            resultadoConsulta.close();
            
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean editarProducto_Orden(Producto_Orden p) throws SIGIPROException {

        boolean resultado = false;
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.producto_orden"
                    + " SET cantidad=?"
                    + " WHERE id_producto=? and id_orden=?; "
            );
            
            consulta.setInt(1,p.getCantidad());
            consulta.setInt(2,p.getProducto().getId_producto());
            consulta.setInt(3,p.getOrden_compra().getId_orden());
            
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }

    public boolean eliminarProducto_Orden(int id_producto, int id_orden) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_orden "
                    + " WHERE id_producto=? and id_orden=?; "
            );

            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_orden);

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

    public List<Producto_Orden> parsearProductos(String productos_intencion, int id_orden) {
        List<Producto_Orden> resultado = null;
        try {
            resultado = new ArrayList<Producto_Orden>();
            List<String> productosParcial = new LinkedList<String>(Arrays.asList(productos_intencion.split("#r#")));
            productosParcial.remove("");
            for (String i : productosParcial) {
                String[] rol = i.split("#c#");
                
                int id_producto = Integer.parseInt(rol[0]);
                int cantidad = Integer.parseInt(rol[2]);
                
                Producto_Orden p = new Producto_Orden();
                p.setOrden_compra(iDAO.obtenerOrden_compra(id_orden));
                p.setProducto(pDAO.obtenerProducto_venta(id_producto));
                p.setCantidad(cantidad);
                
                resultado.add(p);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado = null;
        }
        return resultado;
    }

    public void asegurarProductos_Orden(List<Producto_Orden> p_i, int id_orden) throws SIGIPROException {
        List<Producto_Orden> productosDeLaOrden = this.obtenerProductosOrden(id_orden);
        boolean esta = false;
        for (Producto_Orden p : productosDeLaOrden){
            for (Producto_Orden i : p_i){
                if ((i.getProducto().getId_producto() == p.getProducto().getId_producto()) && (i.getOrden_compra().getId_orden() == p.getOrden_compra().getId_orden())){
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Producto_Orden a eliminar: id_producto = "+p.getProducto().getId_producto()+", id_orden = "+p.getOrden_compra().getId_orden());
                this.eliminarProducto_Orden(p.getProducto().getId_producto(), p.getOrden_compra().getId_orden());
            }
            esta = false;
        }
    }

    public boolean eliminarProductos_Orden(int id_orden) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_orden "
                    + " WHERE id_orden=?; "
            );

            consulta.setInt(1, id_orden);

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
