/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.produccion.modelos.Inoculo;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class InoculoDAO extends DAO {

    public InoculoDAO() {
    }
    private final UsuarioDAO uDAO = new UsuarioDAO();
    private final Veneno_ProduccionDAO vDAO = new Veneno_ProduccionDAO();

    public List<Inoculo> obtenerInoculos() throws SIGIPROException{
        List<Inoculo> resultado = new ArrayList<Inoculo>();
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.inoculo; ");
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Inoculo inoculo = new Inoculo();
                
                inoculo.setId_inoculo(rs.getInt("id_inoculo"));
                inoculo.setFecha_preparacion(rs.getDate("fecha_preparacion"));
                inoculo.setIdentificador(rs.getString("identificador"));
                inoculo.setEncargado_preparacion(uDAO.obtenerUsuario(rs.getInt("encargado_preparacion")));
                inoculo.setVeneno(vDAO.obtenerVeneno_Produccion(rs.getInt("id_veneno")));
                inoculo.setPeso(rs.getInt("peso"));
                resultado.add(inoculo);
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
    
    public Inoculo obtenerInoculo(int id_inoculo) throws SIGIPROException{
        Inoculo resultado = new Inoculo();
        
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.inoculo where id_inoculo = ?; ");
            consulta.setInt(1, id_inoculo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Inoculo inoculo = new Inoculo();
                
                inoculo.setId_inoculo(rs.getInt("id_inoculo"));
                inoculo.setFecha_preparacion(rs.getDate("fecha_preparacion"));
                inoculo.setIdentificador(rs.getString("identificador"));
                inoculo.setEncargado_preparacion(uDAO.obtenerUsuario(rs.getInt("encargado_preparacion")));
                inoculo.setVeneno(vDAO.obtenerVeneno_Produccion(rs.getInt("id_veneno")));
                inoculo.setPeso(rs.getInt("peso"));
                resultado = inoculo;
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
    
    public boolean insertarInoculo(Inoculo p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.inoculo (fecha_preparacion, identificador, encargado_preparacion, "
                    + "id_veneno, peso)"
                    + " VALUES (?,?,?,?,?) RETURNING id_inoculo");

            consulta.setDate(1, p.getFecha_preparacion());
            consulta.setString(2, p.getIdentificador());
            consulta.setInt(3, p.getEncargado_preparacion().getID());
            consulta.setInt(4, p.getVeneno().getId_veneno());
            consulta.setInt(5, p.getPeso());

            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
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

    public boolean editarInoculo(Inoculo p) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE produccion.inoculo"
                    + " SET fecha_preparacion=?, identificador=?, encargado_preparacion=?, id_veneno=?, peso=?"
                    + " WHERE id_inoculo=?; "
            );

            consulta.setDate(1, p.getFecha_preparacion());
            consulta.setString(2, p.getIdentificador());
            consulta.setInt(3, p.getEncargado_preparacion().getID());
            consulta.setInt(4, p.getVeneno().getId_veneno());
            consulta.setInt(5, p.getPeso());
            consulta.setInt(6, p.getId_inoculo());
            
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

    public boolean eliminarInoculo(int id_inoculo) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.inoculo "
                    + " WHERE id_inoculo=?; "
            );

            consulta.setInt(1, id_inoculo);

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
