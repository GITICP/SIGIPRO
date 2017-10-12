/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Producto_Factura;
import com.icp.sigipro.ventas.dao.Producto_ventaDAO;
import java.sql.Date;
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
public class Producto_FacturaDAO extends DAO {

    public Producto_FacturaDAO() {
    }
    
    private Producto_ventaDAO pDAO = new Producto_ventaDAO();
    private FacturaDAO iDAO = new FacturaDAO();
    
    public boolean esProductoFactura (int id_producto, int id_factura, int cantidad, Date fecha) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_factura WHERE id_producto=? and id_factura=? and cantidad=? and fecha_entrega=? ; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_factura);
            consulta.setInt(3, cantidad);
            consulta.setDate(4, fecha);
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
    
    public List<Producto_Factura> obtenerProductosFactura(int id_factura) throws SIGIPROException {

        List<Producto_Factura> resultado = new ArrayList<Producto_Factura>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_factura WHERE id_factura=?; ");
            consulta.setInt(1, id_factura);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Producto_Factura producto_factura = new Producto_Factura();
                
                producto_factura.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                producto_factura.setFactura(iDAO.obtenerFactura(rs.getInt("id_factura")));
                producto_factura.setCantidad(rs.getInt("cantidad"));
                producto_factura.setFecha_entrega(rs.getDate("fecha_entrega"));
                producto_factura.setLote(rs.getString("lotes"));
                
                resultado.add(producto_factura);
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

    public Producto_Factura obtenerProducto_Factura(int id_producto, int id_factura) throws SIGIPROException {

        Producto_Factura resultado = new Producto_Factura();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_factura where id_producto = ? and id_factura = ?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_factura);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                resultado.setFactura(iDAO.obtenerFactura(rs.getInt("id_factura")));
                resultado.setCantidad(rs.getInt("cantidad"));
                resultado.setFecha_entrega(rs.getDate("fecha_entrega"));
                resultado.setLote(rs.getString("lotes"));
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
    
    public int insertarProducto_Factura(Producto_Factura p) throws SIGIPROException {
        //System.out.println("insertarProducto_Factura. ID ="+p.getProducto().getId_producto()+", Idfactura = "+p.getFactura().getId_factura()+", Cantidad = "+p.getCantidad()+", Fecha = "+p.getFecha_S());
        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.producto_factura (id_producto, id_factura, cantidad, fecha_entrega, lotes)"
                    + " VALUES (?,?,?,?,?) RETURNING id_producto");

            consulta.setInt(1,p.getProducto().getId_producto());
            consulta.setInt(2,p.getFactura().getId_factura());
            consulta.setInt(3,p.getCantidad());
            consulta.setDate(4,p.getFecha_entrega());
            consulta.setString(5, p.getLote());
            
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

    public boolean editarProducto_Factura(Producto_Factura p) throws SIGIPROException {

        boolean resultado = false;
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.producto_factura"
                    + " SET cantidad=?,fecha_entrega=?, lotes=?"
                    + " WHERE id_producto=? and id_factura=?; "
            );
            
            consulta.setInt(1,p.getCantidad());
            consulta.setDate(2,p.getFecha_entrega());
            consulta.setInt(3,p.getProducto().getId_producto());
            consulta.setString(4, p.getLote());
            consulta.setInt(5,p.getFactura().getId_factura());
            
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

    public boolean eliminarProducto_Factura(int id_producto, int id_factura) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_factura "
                    + " WHERE id_producto=? and id_factura=?; "
            );

            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_factura);

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

    public List<Producto_Factura> parsearProductos(String productos_intencion, int id_factura) {
        List<Producto_Factura> resultado = null;
        try {
            resultado = new ArrayList<Producto_Factura>();
            List<String> productosParcial = new LinkedList<String>(Arrays.asList(productos_intencion.split("#r#")));
            productosParcial.remove("");
            for (String i : productosParcial) {
                String[] rol = i.split("#c#");
                
                int id_producto = Integer.parseInt(rol[0]);
                int cantidad = Integer.parseInt(rol[1]);
                java.sql.Date fecha_entregaSQL;
                String lotes = "";
                try {
                  SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                  java.util.Date fecha_entrega = formatoFecha.parse(rol[2]);
                  fecha_entregaSQL = new java.sql.Date(fecha_entrega.getTime());
                  lotes = rol[3];
                }
                
                catch (Exception ex) { fecha_entregaSQL = null;}  
                Producto_Factura p = new Producto_Factura();
                p.setFecha_entrega(fecha_entregaSQL);
                p.setFactura(iDAO.obtenerFactura(id_factura));
                p.setProducto(pDAO.obtenerProducto_venta(id_producto));
                p.setCantidad(cantidad);
                p.setLote(lotes);
                resultado.add(p);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            resultado = null;
        }
        return resultado;
    }

    public void asegurarProductos_Factura(List<Producto_Factura> p_i, int id_factura) throws SIGIPROException {
        List<Producto_Factura> productosDeLaFactura = this.obtenerProductosFactura(id_factura);
        boolean esta = false;
        for (Producto_Factura p : productosDeLaFactura){
            for (Producto_Factura i : p_i){
                if ((i.getProducto().getId_producto() == p.getProducto().getId_producto()) && (i.getFactura().getId_factura() == p.getFactura().getId_factura())){
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Producto_Factura a eliminar: id_producto = "+p.getProducto().getId_producto()+", id_factura = "+p.getFactura().getId_factura());
                this.eliminarProducto_Factura(p.getProducto().getId_producto(), p.getFactura().getId_factura());
            }
            esta = false;
        }
    }

    public boolean eliminarProductos_Factura(int id_factura) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_factura "
                    + " WHERE id_factura=?; "
            );

            consulta.setInt(1, id_factura);

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
