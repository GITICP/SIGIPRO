/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.produccion.modelos.Inoculo;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Venenos_Inoculo;
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
public class Venenos_InoculoDAO extends DAO {

    public Venenos_InoculoDAO() {
    }
    private final UsuarioDAO uDAO = new UsuarioDAO();
    private final Veneno_ProduccionDAO vDAO = new Veneno_ProduccionDAO();
    private final InoculoDAO iDAO = new InoculoDAO();

    public List<Venenos_Inoculo> obtenerVenenosInoculo(int id_inoculo) throws SIGIPROException{
        List<Venenos_Inoculo> resultado = new ArrayList<Venenos_Inoculo>();
        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.venenos_inoculo WHERE id_inoculo=?; ");
            consulta.setInt(1, id_inoculo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Venenos_Inoculo vi = new Venenos_Inoculo();
                
                vi.setCantidad(rs.getInt("cantidad"));
                vi.setVeneno(vDAO.obtenerVeneno_Produccion(rs.getInt("id_veneno")));
                vi.setInoculo(iDAO.obtenerInoculo(rs.getInt("id_inoculo")));
                
                resultado.add(vi);
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
    
    public boolean insertarVenenosInoculo(int id_veneno, int id_inoculo, int cantidad) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.venenos_inoculo (id_veneno, id_inoculo, cantidad)"
                    + " VALUES (?,?,?) RETURNING id_inoculo");

            consulta.setInt(1, id_veneno);
            consulta.setInt(2, id_inoculo);
            consulta.setInt(3, cantidad);

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

    public boolean editarVenenosInoculo(int id_veneno, int id_inoculo, int cantidad) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE produccion.venenos_inoculo"
                    + " SET cantidad=?"
                    + " WHERE id_inoculo=? AND id_veneno=?; "
            );

            consulta.setInt(1, cantidad);
            consulta.setInt(2, id_inoculo);
            consulta.setInt(3, id_veneno);
            
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

    public boolean eliminarVenenosInoculo(int id_inoculo, int id_veneno) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.venenos_inoculo "
                    + " WHERE id_inoculo=? AND id_veneno=?; "
            );

            consulta.setInt(1, id_inoculo);
            consulta.setInt(2, id_veneno);

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

    public boolean eliminarVenenos(int id_inoculo) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.venenos_inoculo "
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
