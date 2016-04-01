/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Producto_Intencion;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.dao.Intencion_ventaDAO;
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
public class Producto_IntencionDAO extends DAO {

    public Producto_IntencionDAO() {
    }
    
    private Producto_ventaDAO pDAO = new Producto_ventaDAO();
    private Intencion_ventaDAO iDAO = new Intencion_ventaDAO();
    
    public boolean esProductoIntencion (int id_producto, int id_intencion) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_intencion WHERE id_producto=? and id_intencion=?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_intencion);
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
    
    public List<Producto_Intencion> obtenerProductosIntencion(int id_intencion) throws SIGIPROException {

        List<Producto_Intencion> resultado = new ArrayList<Producto_Intencion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_intencion WHERE id_intencion=?; ");
            consulta.setInt(1, id_intencion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Producto_Intencion producto_intencion = new Producto_Intencion();
                
                producto_intencion.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                producto_intencion.setIntencion(iDAO.obtenerIntencion_venta(rs.getInt("id_intencion")));
                producto_intencion.setCantidad(rs.getInt("cantidad"));
                producto_intencion.setPosible_fecha_despacho(rs.getDate("posible_fecha_despacho"));
                
                resultado.add(producto_intencion);
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
    
    public int CantidadDeIntencionesConProducto(int id_producto) throws SIGIPROException {
        int resultado = 0;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_intencion WHERE id_producto=?; ");
            consulta.setInt(1, id_producto);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                
                resultado += 1;
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

    public Producto_Intencion obtenerProducto_Intencion(int id_producto, int id_intencion) throws SIGIPROException {

        Producto_Intencion resultado = new Producto_Intencion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_intencion where id_producto = ? and id_intencion = ?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_intencion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                resultado.setIntencion(iDAO.obtenerIntencion_venta(rs.getInt("id_intencion")));
                resultado.setCantidad(rs.getInt("cantidad"));
                resultado.setPosible_fecha_despacho(rs.getDate("posible_fecha_despacho"));
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
    
    public int insertarProducto_Intencion(Producto_Intencion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.producto_intencion (id_producto, id_intencion, cantidad, posible_fecha_despacho)"
                    + " VALUES (?,?,?,?) RETURNING id_producto");

            consulta.setInt(1,p.getProducto().getId_producto());
            consulta.setInt(2,p.getIntencion().getId_intencion());
            consulta.setInt(3,p.getCantidad());
            consulta.setDate(4,p.getPosible_fecha_despacho());
            
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

    public boolean editarProducto_Intencion(Producto_Intencion p) throws SIGIPROException {

        boolean resultado = false;
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.producto_intencion"
                    + " SET cantidad=?, posible_fecha_despacho=?"
                    + " WHERE id_producto=? and id_intencion=?; "
            );
            
            consulta.setInt(1,p.getCantidad());
            consulta.setDate(2,p.getPosible_fecha_despacho());
            consulta.setInt(3,p.getProducto().getId_producto());
            consulta.setInt(4,p.getIntencion().getId_intencion());
            
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

    public boolean eliminarProducto_Intencion(int id_producto, int id_intencion) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_intencion "
                    + " WHERE id_producto=? and id_intencion=?; "
            );

            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_intencion);

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

    public List<Producto_Intencion> parsearProductos(String productos_intencion, int id_intencion) {
        List<Producto_Intencion> resultado = null;
        try {
            resultado = new ArrayList<Producto_Intencion>();
            List<String> productosParcial = new LinkedList<String>(Arrays.asList(productos_intencion.split("#r#")));
            productosParcial.remove("");
            for (String i : productosParcial) {
                String[] rol = i.split("#c#");
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fPosibleDespacho = formatoFecha.parse(rol[2]);
                java.sql.Date fPosibleDespachoSQL = new java.sql.Date(fPosibleDespacho.getTime());
                
                int id_producto = Integer.parseInt(rol[0]);
                int cantidad = Integer.parseInt(rol[1]);
                
                Producto_Intencion p = new Producto_Intencion();
                p.setIntencion(iDAO.obtenerIntencion_venta(id_intencion));
                p.setProducto(pDAO.obtenerProducto_venta(id_producto));
                p.setCantidad(cantidad);
                p.setPosible_fecha_despacho(fPosibleDespachoSQL);
                
                resultado.add(p);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado = null;
        }
        return resultado;
    }

    public void asegurarProductos_Intencion(List<Producto_Intencion> p_i, int id_intencion) throws SIGIPROException {
        List<Producto_Intencion> productosDeLaIntencion = this.obtenerProductosIntencion(id_intencion);
        boolean esta = false;
        for (Producto_Intencion p : productosDeLaIntencion){
            for (Producto_Intencion i : p_i){
                if ((i.getProducto().getId_producto() == p.getProducto().getId_producto()) && (i.getIntencion().getId_intencion() == p.getIntencion().getId_intencion())){
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Producto_Intencion a eliminar: id_producto = "+p.getProducto().getId_producto()+", id_intencion = "+p.getIntencion().getId_intencion());
                this.eliminarProducto_Intencion(p.getProducto().getId_producto(), p.getIntencion().getId_intencion());
            }
            esta = false;
        }
    }

    public boolean eliminarProductos_Intencion(int id_intencion) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_intencion "
                    + " WHERE id_intencion=?; "
            );

            consulta.setInt(1, id_intencion);

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
