/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.ventas.modelos.Intencion_venta;
import java.sql.Array;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Intencion_ventaDAO extends DAO {
    
    public ClienteDAO cDAO = new ClienteDAO();
    public Producto_ventaDAO pDAO = new Producto_ventaDAO();
    
    public Intencion_ventaDAO() {
    }
    
    public List<Intencion_venta> obtenerIntenciones_venta() throws SIGIPROException {

        List<Intencion_venta> resultado = new ArrayList<Intencion_venta>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.intencion_venta; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Intencion_venta intencion = new Intencion_venta();
                
                intencion.setId_intencion(rs.getInt("id_intencion"));
                intencion.setCliente(cDAO.obtenerCliente(rs.getInt("id_cliente")));
                intencion.setObservaciones(rs.getString("observaciones"));
                intencion.setEstado(rs.getString("estado"));
                resultado.add(intencion);

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

    public Intencion_venta obtenerIntencion_venta(int id_intencion) throws SIGIPROException {

        Intencion_venta resultado = new Intencion_venta();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.intencion_venta where id_intencion = ?; ");
            consulta.setInt(1, id_intencion);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                resultado.setId_intencion(rs.getInt("id_intencion"));
                resultado.setCliente(cDAO.obtenerCliente(rs.getInt("id_cliente")));
                resultado.setObservaciones(rs.getString("observaciones"));
                resultado.setEstado(rs.getString("estado"));
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
    
    public int CantidadIntencionesConCliente(int id_cliente) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM ventas.intencion_venta where id_cliente = ?; ");
            consulta.setInt(1, id_cliente);
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
    
    public int insertarIntencion_venta(Intencion_venta p) throws SIGIPROException {

        int resultado = 0;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO ventas.intencion_venta (id_cliente, observaciones, estado)"
                    + " VALUES (?,?,?) RETURNING id_intencion");

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setString(2, p.getObservaciones());
            consulta.setString(3, p.getEstado());
            
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = resultadoConsulta.getInt("id_intencion");
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

    public boolean editarIntencion_venta(Intencion_venta p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE ventas.intencion_venta"
                    + " SET id_cliente=?, observaciones=?, estado=?"
                    + " WHERE id_intencion=?; "
            );

            consulta.setInt(1, p.getCliente().getId_cliente());
            consulta.setString(2, p.getObservaciones());
            consulta.setString(3, p.getEstado());
            consulta.setInt(4, p.getId_intencion());
            
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

    public boolean eliminarIntencion_venta(int id_intencion) throws SIGIPROException {

        boolean resultado = false;
        CotizacionDAO cDAO = new CotizacionDAO();
        Orden_compraDAO oDAO = new Orden_compraDAO();
        if(cDAO.CantidadDeCotizacionesConIntencion(id_intencion) > 0 || oDAO.CantidadDEOrdenesConIntencion(id_intencion) > 0){
            throw new SIGIPROException("Imposible de eliminar: Intención o Solicitud de venta relacionada con otras secciones.");
        }
        else{
            try {
                PreparedStatement consulta = getConexion().prepareStatement(
                        " DELETE FROM ventas.intencion_venta "
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
        }
        return resultado;
    }
        

}
