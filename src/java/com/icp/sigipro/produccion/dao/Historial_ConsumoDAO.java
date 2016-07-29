/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.bioterio.dao.*;
import com.icp.sigipro.bioterio.modelos.Cepa;
import com.icp.sigipro.bioterio.modelos.EntregaRatonera;
import com.icp.sigipro.bioterio.modelos.SolicitudRatonera;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.modelos.Historial_Consumo;
import com.icp.sigipro.produccion.modelos.Veneno_Produccion;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.dao.LoteDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Historial_ConsumoDAO extends DAO {

    public Historial_ConsumoDAO() {
    }
 
    public List<Historial_Consumo> obtenerHistoriales() throws SIGIPROException {

        List<Historial_Consumo> resultado = new ArrayList<Historial_Consumo>();

        try {
            PreparedStatement consulta2;
            consulta2 = getConexion().prepareStatement(" SELECT * FROM produccion.historial_consumos; ");
            ResultSet rs2 = consulta2.executeQuery();
            while (!rs2.isClosed() && rs2.next()) {
                //System.out.println("---------Entró al while");
                
                Historial_Consumo historial = new Historial_Consumo();
                
                historial.setId_historial(rs2.getInt("id_historial_consumo"));
                int id_veneno = rs2.getInt("id_veneno");
                historial.setFecha(rs2.getDate("fecha"));
                historial.setCantidad(rs2.getInt("cantidad"));
                historial.setId_usuario(rs2.getInt("id_usuario"));
                historial.setVeneno(new Veneno_ProduccionDAO().obtenerVeneno_Produccion(id_veneno));
                historial.setId_veneno(id_veneno);
                historial.setUsuario(new UsuarioDAO().obtenerUsuario(historial.getId_usuario()));
                resultado.add(historial);
            }
            rs2.close();
            consulta2.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la solicitud");
        }
        return resultado;
    }
    
    public boolean insertarHistorial(int id_veneno, java.sql.Date fecha ,int cantidad, int id_usuario) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.historial_consumos (id_veneno, fecha, cantidad, id_usuario)"
                    + " VALUES (?,?,?,?) RETURNING id_historial_consumo");
            
            consulta.setInt(1, id_veneno);
            consulta.setDate(2, fecha);
            consulta.setInt(3, cantidad);
            consulta.setInt(4, id_usuario);

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
    
}
