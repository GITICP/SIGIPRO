/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Catalogo_PT;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Catalogo_PTDAO extends DAO{
  public boolean insertarCatalogo_PT(Catalogo_PT p) throws SIGIPROException {
        
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.catalogo_pt (nombre, descripcion, vida_util)"
                                                                        + " VALUES (?,?,?) RETURNING id_catalogo_pt");

            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getDescripcion());
            consulta.setInt(3, p.getVida_util());


            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
            }
            resultadoConsulta.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    public boolean editarCatalogo_PT(Catalogo_PT p) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE produccion.catalogo_pt "
                    + " SET  nombre=?, descripcion=?, vida_util=?"
                    + " WHERE id_catalogo_pt=?; "
            );
            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getDescripcion());
            consulta.setInt(3, p.getVida_util());
            consulta.setInt(4, p.getId_catalogo_pt());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }
        return resultado;
    }
     public boolean eliminarCatalogo_PT(int id_catalogo_pt) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.catalogo_pt "
                    + " WHERE id_catalogo_pt=?; "
            );

            consulta.setInt(1, id_catalogo_pt);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la eliminación");
        }
        return resultado;
    }
    public Catalogo_PT obtenerCatalogo_PT(int id) throws SIGIPROException
    {

        Catalogo_PT catalogo_pt = new Catalogo_PT();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("SELECT * FROM produccion.catalogo_pt where id_catalogo_pt = ?");

            consulta.setInt(1, id);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                catalogo_pt.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
                catalogo_pt.setNombre(rs.getString("nombre"));
                catalogo_pt.setDescripcion(rs.getString("descripcion"));
                catalogo_pt.setVida_util(rs.getInt("vida_util"));

            }
            rs.close();
            consulta.close();
            cerrarConexion();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return catalogo_pt;
    }
    public List<Catalogo_PT> obtenerCatalogos_PT()
    {
        List<Catalogo_PT> resultado = new ArrayList<Catalogo_PT>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_catalogo_pt, nombre, descripcion, vida_util FROM produccion.catalogo_pt;");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Catalogo_PT catalogo_pt = new Catalogo_PT();
                catalogo_pt.setId_catalogo_pt(rs.getInt("id_catalogo_pt"));
                catalogo_pt.setNombre(rs.getString("nombre"));
                catalogo_pt.setDescripcion(rs.getString("descripcion"));
                catalogo_pt.setVida_util(rs.getInt("vida_util"));
                resultado.add(catalogo_pt);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
}
