/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.produccion.dao.CronogramaDAO;
import com.icp.sigipro.produccion.modelos.Semanas_cronograma;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Amed
 */
public class Semanas_cronogramaDAO extends DAO {

    public Semanas_cronogramaDAO() {
    }

    public List<Semanas_cronograma> obtenerSemanas_cronograma(int id_cronograma) throws SIGIPROException {

        List<Semanas_cronograma> resultado = new ArrayList<Semanas_cronograma>();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.semanas_cronograma where id_cronograma=? order by id_semana asc; ");
            consulta.setInt(1, id_cronograma);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Semanas_cronograma semanas_cronograma = new Semanas_cronograma();
                CronogramaDAO cDAO = new CronogramaDAO();
                
                semanas_cronograma.setId_semana(rs.getInt("id_semana"));
                semanas_cronograma.setCronograma(cDAO.obtenerCronograma(id_cronograma));
                semanas_cronograma.setFecha(rs.getString("fecha"));
                semanas_cronograma.setSangria(rs.getString("sangria"));
                semanas_cronograma.setPlasma_proyectado(rs.getString("plasma_proyectado"));
                semanas_cronograma.setPlasma_real(rs.getString("plasma_real"));
                semanas_cronograma.setAntivenenos_lote(rs.getString("antivenenos_lote"));
                semanas_cronograma.setAntivenenos_proyectada(rs.getString("antivenenos_proyectada"));
                semanas_cronograma.setAntivenenos_bruta(rs.getString("antivenenos_bruta"));
                semanas_cronograma.setAntivenenos_neta(rs.getString("antivenenos_neta"));
                semanas_cronograma.setEntregas_cantidad(rs.getString("entregas_cantidad"));
                semanas_cronograma.setEntregar_destino(rs.getString("entregas_destino"));
                semanas_cronograma.setEntregas_lote(rs.getString("entregas_lote"));
                semanas_cronograma.setObservaciones(rs.getString("observaciones"));
                
                resultado.add(semanas_cronograma);

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

    public Semanas_cronograma obtenerSemana_cronograma(int id_semana) throws SIGIPROException {

        Semanas_cronograma resultado = new Semanas_cronograma();

        try {
            PreparedStatement consulta;
            consulta = getConexion().prepareStatement(" SELECT * FROM produccion.semanas_cronograma where id_semana = ?; ");
            consulta.setInt(1, id_semana);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                CronogramaDAO cDAO = new CronogramaDAO();
                
                resultado.setId_semana(rs.getInt("id_semana"));
                resultado.setCronograma(cDAO.obtenerCronograma(rs.getInt("id_cronograma")));
                resultado.setFecha(rs.getString("fecha"));
                resultado.setSangria(rs.getString("sangria"));
                resultado.setPlasma_proyectado(rs.getString("plasma_proyectado"));
                resultado.setPlasma_real(rs.getString("plasma_real"));
                resultado.setAntivenenos_lote(rs.getString("antivenenos_lote"));
                resultado.setAntivenenos_proyectada(rs.getString("antivenenos_proyectada"));
                resultado.setAntivenenos_bruta(rs.getString("antivenenos_bruta"));
                resultado.setAntivenenos_neta(rs.getString("antivenenos_neta"));
                resultado.setEntregas_cantidad(rs.getString("entregas_cantidad"));
                resultado.setEntregar_destino(rs.getString("entregas_destino"));
                resultado.setEntregas_lote(rs.getString("entregas_lote"));
                resultado.setObservaciones(rs.getString("observaciones"));
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
    
    public void crearSemanas_cronograma(Date startdate, int id_cronograma) throws SIGIPROException
{
        Calendar calendar = new GregorianCalendar();

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.YEAR, 1); 
        java.util.Date enddate = cal.getTime();

        List<java.util.Date> dates = new ArrayList<java.util.Date>();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate))
        {
            java.util.Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }

        for (java.util.Date d: dates){
            insertarSemanas_cronograma_Vacia(new java.sql.Date(d.getTime()).toString(), id_cronograma);
        }
    }
    
    public boolean insertarSemanas_cronograma_Vacia(String fecha, int id_cronograma) throws SIGIPROException {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.semanas_cronograma (id_cronograma, fecha)"
                    + " VALUES (?,?) RETURNING id_semana");

            consulta.setInt(1, id_cronograma);
            consulta.setString(2, fecha);
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
    
    public boolean insertarSemanas_cronograma(Semanas_cronograma sc) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO produccion.semanas_cronograma (id_cronograma, fecha, sangria, "
                    + "plasma_proyectado, plasma_real, antivenenos_lote, antivenenos_proyectada, antivenenos_bruta, antivenenos_neta, entregas_cantidad, entregas_destino, entregas_lote, observaciones)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id_semana");

            consulta.setInt(1, sc.getCronograma().getId_cronograma());
            consulta.setString(2, sc.getFecha());
            consulta.setString(3, sc.getSangria());
            consulta.setString(4, sc.getPlasma_proyectado());
            consulta.setString(5, sc.getPlasma_real());
            consulta.setString(6, sc.getAntivenenos_lote());
            consulta.setString(7, sc.getAntivenenos_proyectada());
            consulta.setString(8, sc.getAntivenenos_bruta());
            consulta.setString(9, sc.getAntivenenos_neta());
            consulta.setString(10, sc.getEntregas_cantidad());
            consulta.setString(11, sc.getEntregar_destino());
            consulta.setString(12, sc.getEntregas_lote());
            consulta.setString(13, sc.getObservaciones());
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

    public boolean editarSemanas_cronograma(Semanas_cronograma sc) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE produccion.semanas_cronograma"
                    + " SET id_cronograma=?, fecha=?, sangria=?, plasma_proyectado=?, plasma_real=?, antivenenos_lote=?, antivenenos_proyectada=?, "
                    + "antivenenos_bruta=?, antivenenos_neta=?, entregas_cantidad=?, entregas_destino=?, entregas_lote=?, observaciones=?"
                    + " WHERE id_semana=?; "
            );

            consulta.setInt(1, sc.getCronograma().getId_cronograma());
            consulta.setString(2, sc.getFecha());
            consulta.setString(3, sc.getSangria());
            consulta.setString(4, sc.getPlasma_proyectado());
            consulta.setString(5, sc.getPlasma_real());
            consulta.setString(6, sc.getAntivenenos_lote());
            consulta.setString(7, sc.getAntivenenos_proyectada());
            consulta.setString(8, sc.getAntivenenos_bruta());
            consulta.setString(9, sc.getAntivenenos_neta());
            consulta.setString(10, sc.getEntregas_cantidad());
            consulta.setString(11, sc.getEntregar_destino());
            consulta.setString(12, sc.getEntregas_lote());
            consulta.setString(13, sc.getObservaciones());
            consulta.setInt(14, sc.getId_semana());
            
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

    public boolean eliminarSemanas_cronograma(int id_semana) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.semanas_cronograma "
                    + " WHERE id_semana=?; "
            );

            consulta.setInt(1, id_semana);

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
    
    public boolean eliminarSemanas_del_Cronograma(int id_cronograma) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " DELETE FROM produccion.semanas_cronograma "
                    + " WHERE id_cronograma=?; "
            );

            consulta.setInt(1, id_cronograma);

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

    public void editarSemana(int id, String columna, String nuevoValor) throws SIGIPROException {
        try {
            String statement = "UPDATE produccion.semanas_cronograma SET "+columna+"='"+nuevoValor+"' WHERE id_semana="+id+";";
            PreparedStatement consulta = getConexion().prepareStatement(
                    statement
            );
            
            if (consulta.executeUpdate() == 1) {

            }
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar la edición");
        }    
    }
}
