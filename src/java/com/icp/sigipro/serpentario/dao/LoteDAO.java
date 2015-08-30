/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Extraccion;
import com.icp.sigipro.serpentario.modelos.Lote;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ld.conejo
 */
public class LoteDAO extends DAO {

    public LoteDAO() {
    }

    public boolean insertarLote(Lote l) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO serpentario.lote (id_especie,numero_lote,interno) "
                    + " VALUES (?,?,?) RETURNING id_lote");
            consulta.setInt(1, l.getEspecie().getId_especie());
            consulta.setString(2, l.getNumero_lote());
            consulta.setBoolean(3, l.isInterno());
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                l.setId_lote(rs.getInt("id_lote"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    /* No se usa por ahora
     public int obtenerProximoId(){
     boolean resultado = false;
     int nextval = 0;
     try{
     PreparedStatement consulta = getConexion().prepareStatement("SELECT last_value FROM serpentario.lote_id_lote_seq;");
     ResultSet resultadoConsulta = consulta.executeQuery();
     if (resultadoConsulta.next()){
     resultado=true;
     int currval = resultadoConsulta.getInt("last_value");
     if (currval==1){
     List<Lote> lotes = this.obtenerLotes();
     if (lotes == null){
     nextval = currval;
     }else{
     nextval = currval + 1;
     }
     }else{
     nextval = currval + 1;
     }
     }
     resultadoConsulta.close();
     consulta.close();
     cerrarConexion();
     }catch (Exception e){
            
     }
     return nextval;
     }
     */

    public float obtenerCantidadSolicitada(int id_lote) {
        float resultado = 0;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT lote.id_lote, sum(entrega.cantidad) as cantidad_actual "
                    + "FROM serpentario.lote as lote LEFT OUTER JOIN serpentario.lotes_entregas_solicitud as entrega ON lote.id_lote = entrega.id_lote "
                    + "WHERE lote.id_lote=? "
                    + "GROUP BY lote.id_lote; ");

            consulta.setInt(1, id_lote);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = rs.getFloat("cantidad_actual");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }

        return resultado;
    }

    public boolean insertarExtracciones(Lote l, String[] extracciones) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement("UPDATE serpentario.extraccion "
                    + "SET id_lote=? "
                    + "WHERE id_extraccion=?");
            consulta.setInt(1, l.getId_lote());
            for (String i : extracciones) {
                consulta.setInt(2, Integer.parseInt(i));
                resultado = consulta.executeUpdate() == 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
    
    public boolean eliminarExtracciones(Lote l) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        try {
            consulta = getConexion().prepareStatement("UPDATE serpentario.extraccion "
                    + "SET id_lote= null "
                    + "WHERE id_lote=?");
            consulta.setInt(1, l.getId_lote());
            if ( consulta.executeUpdate() == 1){
                resultado = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Extraccion> obtenerExtraccionesAccion(Lote lote) {
        List<Extraccion> resultado = new ArrayList<Extraccion>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_extraccion, numero_extraccion FROM serpentario.extraccion WHERE id_lote is null or id_lote=?; ");
            consulta.setInt(1, lote.getId_lote());
            rs = consulta.executeQuery();
            ExtraccionDAO extracciondao = new ExtraccionDAO();
            while (rs.next()) {
                Extraccion extraccion = new Extraccion();
                extraccion.setId_extraccion(rs.getInt("id_extraccion"));
                extraccion.setNumero_extraccion(rs.getString("numero_extraccion"));
                if (extracciondao.validarIsLiofilizacionFin(extraccion)) {
                    resultado.add(extraccion);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Extraccion> obtenerExtracciones(Lote lote) {
        List<Extraccion> resultado = new ArrayList<Extraccion>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT id_extraccion, numero_extraccion, interno FROM serpentario.extraccion WHERE id_lote=?; ");
            consulta.setInt(1, lote.getId_lote());
            rs = consulta.executeQuery();
            while (rs.next()) {
                Extraccion extraccion = new Extraccion();
                extraccion.setId_extraccion(rs.getInt("id_extraccion"));
                extraccion.setNumero_extraccion(rs.getString("numero_extraccion"));
                extraccion.setInterno(rs.getBoolean("interno"));
                resultado.add(extraccion);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Lote> obtenerLotes() {
        List<Lote> resultado = new ArrayList<Lote>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement("SELECT lote.id_lote,lote.numero_lote, lote.id_especie, especie.genero, especie.especie, sum(DISTINCT peso_recuperado) as cantidad_original, sum(entrega.cantidad) as cantidad_actual "
                    + "FROM serpentario.lote as lote "
                    + "LEFT OUTER JOIN serpentario.especies as especie ON lote.id_especie = especie.id_especie  "
                    + "LEFT OUTER JOIN serpentario.extraccion as extraccion ON lote.id_lote = extraccion.id_lote "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON extraccion.id_extraccion = liofilizacion.id_extraccion "
                    + "LEFT OUTER JOIN serpentario.lotes_entregas_solicitud as entrega ON lote.id_lote = entrega.id_lote "
                    + "GROUP BY lote.id_lote, especie.genero, especie.especie;");
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                int id_lote = rs.getInt("id_lote");
                lote.setId_lote(id_lote);
                lote.setNumero_lote(rs.getString("numero_lote"));
                Especie especie = new Especie();
                especie.setId_especie(rs.getInt("id_especie"));
                especie.setEspecie(rs.getString("especie"));
                especie.setGenero(rs.getString("genero"));
                lote.setEspecie(especie);
                try {
                    float cantidad_original = rs.getFloat("cantidad_original");
                    lote.setCantidad_original(cantidad_original);
                    float cantidad_entregada = rs.getFloat("cantidad_actual");
                    float cantidad_actual = (float) (cantidad_original - (cantidad_entregada * 0.001));
                    lote.setCantidad_actual(cantidad_actual);
                } catch (Exception e) {
                    lote.setCantidad_original(0);
                    lote.setCantidad_actual(0);
                }
                resultado.add(lote);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public List<Lote> obtenerLotes(Especie e) {
        List<Lote> resultado = new ArrayList<Lote>();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT lote.id_lote,lote.numero_lote, lote.id_especie, sum(DISTINCT peso_recuperado) as cantidad_original,sum(entrega.cantidad) as cantidad_actual "
                    + "FROM (serpentario.lote as lote LEFT OUTER JOIN serpentario.extraccion as extraccion ON lote.id_lote = extraccion.id_lote) "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON extraccion.id_extraccion = liofilizacion.id_extraccion "
                    + "LEFT OUTER JOIN serpentario.lotes_entregas_solicitud as entrega ON lote.id_lote = entrega.id_lote "
                    + "WHERE lote.id_especie = ? "
                    + "GROUP BY lote.id_lote;");
            consulta.setInt(1, e.getId_especie());
            rs = consulta.executeQuery();
            while (rs.next()) {
                Lote lote = new Lote();
                int id_lote = rs.getInt("id_lote");
                lote.setId_lote(id_lote);
                lote.setNumero_lote(rs.getString("numero_lote"));
                lote.setEspecie(e);
                float cantidad_original = rs.getFloat("cantidad_original");
                lote.setCantidad_original(cantidad_original);
                float cantidad_entregada = rs.getFloat("cantidad_actual");
                float cantidad_actual = (float) (cantidad_original - (cantidad_entregada * 0.001));
                if (cantidad_actual != 0.0) {
                    lote.setCantidad_actual(cantidad_actual);
                    resultado.add(lote);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public Lote obtenerLote(int id_lote) {
        Lote resultado = new Lote();
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" SELECT lote.id_lote,lote.numero_lote, lote.id_especie, lote.interno, sum(peso_recuperado) as cantidad_original "
                    + "FROM (serpentario.lote as lote LEFT OUTER JOIN serpentario.extraccion as extraccion ON lote.id_lote = extraccion.id_lote) "
                    + "LEFT OUTER JOIN serpentario.liofilizacion as liofilizacion ON extraccion.id_extraccion = liofilizacion.id_extraccion "
                    + "WHERE lote.id_lote = ? "
                    + "GROUP BY lote.id_lote;");
            consulta.setInt(1, id_lote);
            rs = consulta.executeQuery();
            EspecieDAO especiedao = new EspecieDAO();
            if (rs.next()) {
                Lote lote = new Lote();
                lote.setId_lote(id_lote);
                lote.setNumero_lote(rs.getString("numero_lote"));
                lote.setInterno(rs.getBoolean("interno"));
                lote.setEspecie(especiedao.obtenerEspecie(rs.getInt("id_especie")));
                try {
                    float cantidad_original = rs.getFloat("cantidad_original");
                    lote.setCantidad_original(cantidad_original);
                    float cantidad_entregada = this.obtenerCantidadSolicitada(id_lote);
                    float cantidad_actual = (float) (cantidad_original - (cantidad_entregada * 0.001));
                    lote.setCantidad_actual(cantidad_actual);
                } catch (Exception e) {
                    e.printStackTrace();
                    lote.setCantidad_original(0);
                    lote.setCantidad_actual(0);
                }
                resultado = lote;
            }
            resultado.setExtracciones(obtenerExtracciones(resultado));
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }

    public boolean insertarExtraccion(Extraccion e, float peso_comprado, int id_usuario, int id_lote) {
        boolean resultado = false;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        try {
            consulta = getConexion().prepareStatement(" INSERT INTO serpentario.extraccion (numero_extraccion,id_especie,ingreso_cv,fecha_extraccion,interno,id_lote,estado_serpientes) "
                    + " VALUES (?,?,true,now(),false,?,false) RETURNING id_extraccion");
            consulta.setString(1, e.getNumero_extraccion());
            consulta.setInt(2, e.getEspecie().getId_especie());
            consulta.setInt(3, id_lote);
            rs = consulta.executeQuery();
            if (rs.next()) {
                resultado = true;
                int id_extraccion = rs.getInt("id_extraccion");
                e.setId_extraccion(id_extraccion);
                consulta = getConexion().prepareStatement(" INSERT INTO serpentario.liofilizacion (id_extraccion, id_usuario_inicio, fecha_inicio, peso_recuperado) "
                        + " VALUES (?,?,now(),?) RETURNING id_extraccion");
                consulta.setInt(1, id_extraccion);
                consulta.setInt(2, id_usuario);
                consulta.setFloat(3, peso_comprado);

                consulta.executeQuery();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            cerrarSilencioso(rs);
            cerrarSilencioso(consulta);
            cerrarConexion();
        }
        return resultado;
    }
}
