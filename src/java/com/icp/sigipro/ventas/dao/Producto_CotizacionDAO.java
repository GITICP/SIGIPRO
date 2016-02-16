/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Producto_Cotizacion;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import com.icp.sigipro.ventas.dao.CotizacionDAO;
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
public class Producto_CotizacionDAO extends DAO {

    public Producto_CotizacionDAO() {
    }
    
    private Producto_ventaDAO pDAO = new Producto_ventaDAO();
    private CotizacionDAO iDAO = new CotizacionDAO();
    
    public boolean esProductoCotizacion (int id_producto, int id_cotizacion) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_cotizacion WHERE id_producto=? and id_cotizacion=?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_cotizacion);
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
    
    public List<Producto_Cotizacion> obtenerProductosCotizacion(int id_cotizacion) throws SIGIPROException {

        List<Producto_Cotizacion> resultado = new ArrayList<Producto_Cotizacion>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_cotizacion WHERE id_cotizacion=?; ");
            consulta.setInt(1, id_cotizacion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Producto_Cotizacion producto_cotizacion = new Producto_Cotizacion();
                
                producto_cotizacion.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                producto_cotizacion.setCotizacion(iDAO.obtenerCotizacion(rs.getInt("id_cotizacion")));
                producto_cotizacion.setCantidad(rs.getInt("cantidad"));
                producto_cotizacion.setPosible_fecha_despacho(rs.getDate("posible_fecha_despacho"));
                
                resultado.add(producto_cotizacion);
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

    public Producto_Cotizacion obtenerProducto_Cotizacion(int id_producto, int id_cotizacion) throws SIGIPROException {

        Producto_Cotizacion resultado = new Producto_Cotizacion();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_cotizacion where id_producto = ? and id_cotizacion = ?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_cotizacion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                resultado.setCotizacion(iDAO.obtenerCotizacion(rs.getInt("id_cotizacion")));
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
    
    public int insertarProducto_Cotizacion(Producto_Cotizacion p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.producto_cotizacion (id_producto, id_cotizacion, cantidad, posible_fecha_despacho)"
                    + " VALUES (?,?,?,?) RETURNING id_producto");

            consulta.setInt(1,p.getProducto().getId_producto());
            consulta.setInt(2,p.getCotizacion().getId_cotizacion());
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

    public boolean editarProducto_Cotizacion(Producto_Cotizacion p) throws SIGIPROException {

        boolean resultado = false;
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.producto_cotizacion"
                    + " SET cantidad=?, posible_fecha_despacho=?"
                    + " WHERE id_producto=? and id_cotizacion=?; "
            );
            
            consulta.setInt(1,p.getCantidad());
            consulta.setDate(2,p.getPosible_fecha_despacho());
            consulta.setInt(3,p.getProducto().getId_producto());
            consulta.setInt(4,p.getCotizacion().getId_cotizacion());
            
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

    public boolean eliminarProducto_Cotizacion(int id_producto, int id_cotizacion) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_cotizacion "
                    + " WHERE id_producto=? and id_cotizacion=?; "
            );

            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_cotizacion);

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

    public List<Producto_Cotizacion> parsearProductos(String productos_cotizacion, int id_cotizacion) {
        List<Producto_Cotizacion> resultado = null;
        try {
            resultado = new ArrayList<Producto_Cotizacion>();
            List<String> productosParcial = new LinkedList<String>(Arrays.asList(productos_cotizacion.split("#r#")));
            productosParcial.remove("");
            for (String i : productosParcial) {
                String[] rol = i.split("#c#");
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date fPosibleDespacho = formatoFecha.parse(rol[2]);
                java.sql.Date fPosibleDespachoSQL = new java.sql.Date(fPosibleDespacho.getTime());
                
                int id_producto = Integer.parseInt(rol[0]);
                int cantidad = Integer.parseInt(rol[1]);
                
                Producto_Cotizacion p = new Producto_Cotizacion();
                p.setCotizacion(iDAO.obtenerCotizacion(id_cotizacion));
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

    public void asegurarProductos_Cotizacion(List<Producto_Cotizacion> p_i, int id_cotizacion) throws SIGIPROException {
        List<Producto_Cotizacion> productosDeLaCotizacion = this.obtenerProductosCotizacion(id_cotizacion);
        boolean esta = false;
        for (Producto_Cotizacion p : productosDeLaCotizacion){
            for (Producto_Cotizacion i : p_i){
                if ((i.getProducto().getId_producto() == p.getProducto().getId_producto()) && (i.getCotizacion().getId_cotizacion() == p.getCotizacion().getId_cotizacion())){
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Producto_Cotizacion a eliminar: id_producto = "+p.getProducto().getId_producto()+", id_cotizacion = "+p.getCotizacion().getId_cotizacion());
                this.eliminarProducto_Cotizacion(p.getProducto().getId_producto(), p.getCotizacion().getId_cotizacion());
            }
            esta = false;
        }
    }

    public boolean eliminarProductos_Cotizacion(int id_cotizacion) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_cotizacion "
                    + " WHERE id_cotizacion=?; "
            );

            consulta.setInt(1, id_cotizacion);

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
