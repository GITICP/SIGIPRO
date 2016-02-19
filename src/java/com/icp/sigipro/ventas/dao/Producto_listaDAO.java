/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Producto_lista;
import com.icp.sigipro.ventas.dao.Producto_listaDAO;
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
public class Producto_listaDAO extends DAO {

    public Producto_listaDAO() {
    }
    
    private Producto_ventaDAO pDAO = new Producto_ventaDAO();
    private ListaDAO lDAO = new ListaDAO();
    
    
    public boolean esProductoLista (int id_producto, int id_lista) throws SIGIPROException{
        boolean resultado = false;
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_lista WHERE id_producto=? and id_lista=?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_lista);
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
    
    public List<Producto_lista> obtenerProductosLista(int id_lista) throws SIGIPROException {

        List<Producto_lista> resultado = new ArrayList<Producto_lista>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_lista WHERE id_lista=?; ");
            consulta.setInt(1, id_lista);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Producto_lista producto_lista = new Producto_lista();
                
                producto_lista.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                producto_lista.setLista(lDAO.obtenerLista(rs.getInt("id_lista")));
                producto_lista.setCantidad(rs.getInt("cantidad"));
                
                resultado.add(producto_lista);
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

    public Producto_lista obtenerProducto_lista(int id_producto, int id_lista) throws SIGIPROException {

        Producto_lista resultado = new Producto_lista();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.producto_lista where id_producto = ? and id_lista = ?; ");
            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_lista);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setProducto(pDAO.obtenerProducto_venta(rs.getInt("id_producto")));
                resultado.setLista(lDAO.obtenerLista(rs.getInt("id_lista")));
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
    
    public int insertarProducto_lista(Producto_lista p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.producto_lista (id_producto, id_lista, cantidad)"
                    + " VALUES (?,?,?) RETURNING id_producto");

            consulta.setInt(1,p.getProducto().getId_producto());
            consulta.setInt(2,p.getLista().getId_lista());
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

    public boolean editarProducto_lista(Producto_lista p) throws SIGIPROException {

        boolean resultado = false;
        
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.producto_lista"
                    + " SET cantidad=?"
                    + " WHERE id_producto=? and id_lista=?; "
            );
            
            consulta.setInt(1,p.getCantidad());
            consulta.setInt(2,p.getProducto().getId_producto());
            consulta.setInt(3,p.getLista().getId_lista());
            
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

    public boolean eliminarProducto_lista(int id_producto, int id_lista) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_lista "
                    + " WHERE id_producto=? and id_lista=?; "
            );

            consulta.setInt(1, id_producto);
            consulta.setInt(2, id_lista);

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

    public List<Producto_lista> parsearProductos(String productos_lista, int id_lista) {
        List<Producto_lista> resultado = null;
        try {
            resultado = new ArrayList<Producto_lista>();
            List<String> productosParcial = new LinkedList<String>(Arrays.asList(productos_lista.split("#r#")));
            productosParcial.remove("");
            for (String i : productosParcial) {
                String[] rol = i.split("#c#");
                
                int id_producto = Integer.parseInt(rol[0]);
                int cantidad = Integer.parseInt(rol[1]);
                
                Producto_lista p = new Producto_lista();
                p.setLista(lDAO.obtenerLista(id_lista));
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

    public void asegurarProductos_Lista(List<Producto_lista> p_i, int id_lista) throws SIGIPROException {
        List<Producto_lista> productosDeLaLista = this.obtenerProductosLista(id_lista);
        boolean esta = false;
        for (Producto_lista p : productosDeLaLista){
            for (Producto_lista i : p_i){
                if ((i.getProducto().getId_producto() == p.getProducto().getId_producto()) && (i.getLista().getId_lista() == p.getLista().getId_lista())){
                    esta = true;
                }
            }
            if (esta == false){
                //System.out.println("Producto_lista a eliminar: id_producto = "+p.getProducto().getId_producto()+", id_lista = "+p.getLista().getId_lista());
                this.eliminarProducto_lista(p.getProducto().getId_producto(), p.getLista().getId_lista());
            }
            esta = false;
        }
    }

    public boolean eliminarProductos_Lista(int id_lista) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM ventas.producto_lista "
                    + " WHERE id_lista=?; "
            );

            consulta.setInt(1, id_lista);

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
