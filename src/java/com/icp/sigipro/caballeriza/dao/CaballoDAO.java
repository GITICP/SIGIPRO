/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.dao;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.caballeriza.modelos.Inoculo;
import com.icp.sigipro.caballeriza.modelos.Peso;
import com.icp.sigipro.caballeriza.modelos.SangriaCaballo;
import com.icp.sigipro.caballeriza.modelos.SangriaPruebaCaballo;
import com.icp.sigipro.caballeriza.modelos.TipoEvento;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Walter
 */
public class CaballoDAO
{

    private Connection conexion;

    public CaballoDAO()
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        conexion = s.conectar();
    }

    public boolean insertarCaballo(Caballo c)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO caballeriza.caballos (nombre, numero_microchip,fecha_nacimiento,fecha_ingreso,sexo,color,otras_sennas,estado,id_grupo_de_caballo, numero) "
                                                                        + " VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING id_caballo");
            consulta.setString(1, c.getNombre());
            consulta.setString(2, c.getNumero_microchip());
            consulta.setDate(3, c.getFecha_nacimiento());
            consulta.setDate(4, c.getFecha_ingreso());
            consulta.setString(5, c.getSexo());
            consulta.setString(6, c.getColor());
            consulta.setString(7, c.getOtras_sennas());
            consulta.setString(8, c.getEstado());
            if (c.getGrupo_de_caballos() == null) {
                consulta.setNull(9, java.sql.Types.INTEGER);
            }
            else {
                consulta.setInt(9, c.getGrupo_de_caballos().getId_grupo_caballo());
            }
            consulta.setInt(10, c.getNumero());
            ResultSet resultadoConsulta = consulta.executeQuery();
            if (resultadoConsulta.next()) {
                resultado = true;
                c.setId_caballo(resultadoConsulta.getInt("id_caballo"));
            }
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public boolean editarCaballo(Caballo c)
    {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.caballos "
                    + " SET otras_sennas=?,  estado=?, id_grupo_de_caballo=?, color=?, "
                    + "     nombre=?, numero_microchip=?, sexo=?, "
                    + "     fecha_nacimiento=?, fecha_ingreso=? "
                    + " WHERE id_caballo=?; "
            );
            consulta.setString(1, c.getOtras_sennas());
            consulta.setString(2, c.getEstado());
            if (c.getGrupo_de_caballos() == null) {
                consulta.setNull(3, java.sql.Types.INTEGER);
            }
            else {
                consulta.setInt(3, c.getGrupo_de_caballos().getId_grupo_caballo());
            }
            consulta.setString(4, c.getColor());
            consulta.setString(5, c.getNombre());
            consulta.setString(6, c.getNumero_microchip());
            consulta.setString(7, c.getSexo());
            consulta.setDate(8, c.getFecha_nacimiento());
            consulta.setDate(9, c.getFecha_ingreso());
            consulta.setInt(10, c.getId_caballo());

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;

    }

    public boolean eliminarCaballoDeGrupos(int id_caballo)
    {
        boolean resultado = false;

        try {
            //IMPLEMENTAR EL INSERT DE EVENTOS CADA VEZ QUE CAMBIA UN DATO
            //Caballo caballo = this.obtenerCaballo(c.getId_caballo());
            //------------------------------------------------------------
            PreparedStatement consulta = getConexion().prepareStatement(
                    " UPDATE caballeriza.caballos "
                    + " SET id_grupo_de_caballo=?,"
                    + " WHERE id_caballo=?; "
            );
            consulta.setInt(1, 0);
            consulta.setInt(2, id_caballo);

            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;

    }

    public boolean insertarImagen(InputStream imagen, int id_caballo, long size)
    {
        boolean resultado = false;
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    "UPDATE caballeriza.caballos "
                    + "SET fotografia=? "
                    + "WHERE id_caballo=?; ");

            consulta.setBinaryStream(1, imagen, size);
            consulta.setInt(2, id_caballo);
            if (consulta.executeUpdate() == 1) {
                resultado = true;
            }
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<Caballo> obtenerCaballosGrupo(int id_grupo_de_caballo)
    {
        List<Caballo> resultado = new ArrayList<Caballo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.caballos where id_grupo_de_caballo = ?");
            consulta.setInt(1, id_grupo_de_caballo);
            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();
            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getString("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerGrupoDeCaballos(rs.getInt("id_grupo_de_caballo")));
                caballo.setFecha_ingreso(rs.getDate("fecha_nacimiento"));
                caballo.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                caballo.setSexo(rs.getString("sexo"));
                caballo.setColor(rs.getString("color"));
                caballo.setOtras_sennas(rs.getString("otras_sennas"));
                caballo.setEstado(rs.getString("estado"));
                caballo.setNumero(rs.getInt("numero"));
                resultado.add(caballo);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<Caballo> obtenerCaballosRestantes()
    {
        List<Caballo> resultado = new ArrayList<Caballo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT id_caballo, nombre, numero_microchip, numero FROM caballeriza.caballos where id_grupo_de_caballo is null AND estado = ?;");

            consulta.setString(1, Caballo.VIVO);

            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();

            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getString("numero_microchip"));
                caballo.setNumero(rs.getInt("numero"));

                resultado.add(caballo);
            }
            rs.close();
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public Caballo obtenerCaballo(int id_caballo) throws SIGIPROException
    {
        Caballo caballo = new Caballo();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT c.*, pc.id_peso, pc.peso, pc.fecha "
                    + " FROM caballeriza.caballos c "
                    + "           LEFT JOIN caballeriza.pesos_caballos pc ON pc.id_caballo = ? "
                    + " WHERE c.id_caballo = ? "
            );
            consulta.setInt(1, id_caballo);
            consulta.setInt(2, id_caballo);
            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();
            if (rs.next()) {
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getString("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerGrupoDeCaballos(rs.getInt("id_grupo_de_caballo")));
                caballo.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                caballo.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                caballo.setSexo(rs.getString("sexo"));
                caballo.setColor(rs.getString("color"));
                caballo.setOtras_sennas(rs.getString("otras_sennas"));
                caballo.setEstado(rs.getString("estado"));
                caballo.setNumero(rs.getInt("numero"));
                byte[] imagen = rs.getBytes("fotografia");
                caballo.setFotografia(imagen);
                if (rs.getDate("fecha") != null) {
                    do {
                        Peso p = new Peso();
                        p.setId_peso(rs.getInt("id_peso"));
                        p.setId_caballo(id_caballo);
                        p.setFecha(rs.getDate("fecha"));
                        p.setPeso(rs.getFloat("peso"));
                        
                        caballo.agregarPeso(p);
                    }
                    while (rs.next());
                }
            }
            
            PreparedStatement consulta_eventos = getConexion().prepareStatement(
                    " SELECT ec.id_evento, ec.fecha, ec.responsable, ec.descripcion, u.id_usuario, u.nombre_completo, te.id_tipo_evento, te.nombre "
                  + " FROM (SELECT * FROM caballeriza.eventos_clinicos_caballos WHERE id_caballo = ?) as eventos "
                  + "   INNER JOIN caballeriza.eventos_clinicos ec ON ec.id_evento = eventos.id_evento "
                  + "   INNER JOIN seguridad.usuarios u ON ec.responsable = u.id_usuario "
                  + "   INNER JOIN caballeriza.tipos_eventos te ON ec.id_tipo_evento = te.id_tipo_evento "
            );
            consulta_eventos.setInt(1, id_caballo);
            ResultSet rs_eventos = consulta_eventos.executeQuery();

            while (rs_eventos.next()) {
                EventoClinico evento = new EventoClinico();
                evento.setId_evento(rs_eventos.getInt("id_evento"));
                evento.setFecha(rs_eventos.getDate("fecha"));
                evento.setDescripcion(rs_eventos.getString("descripcion"));

                Usuario responsable = new Usuario();
                responsable.setId_usuario(rs_eventos.getInt("id_usuario"));
                responsable.setNombre_completo(rs_eventos.getString("nombre_completo"));
                evento.setResponsable(responsable);

                TipoEvento tipo_evento = new TipoEvento();

                tipo_evento.setId_tipo_evento(rs_eventos.getInt("id_tipo_evento"));
                tipo_evento.setNombre(rs_eventos.getString("nombre"));
                evento.setTipo_evento(tipo_evento);

                caballo.agregarEvento(evento);
            }
            
            
            rs_eventos.close();
            rs.close();
            consulta_eventos.close();
            consulta.close();
            conexion.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener el caballo.");
        }
        return caballo;
    }

    public List<Caballo> obtenerCaballos()
    {
        List<Caballo> resultado = new ArrayList<Caballo>();
        try {
            PreparedStatement consulta = getConexion().prepareStatement(" SELECT * FROM caballeriza.caballos ");
            ResultSet rs = consulta.executeQuery();
            GrupoDeCaballosDAO dao = new GrupoDeCaballosDAO();
            while (rs.next()) {
                Caballo caballo = new Caballo();
                caballo.setId_caballo(rs.getInt("id_caballo"));
                caballo.setNombre(rs.getString("nombre"));
                caballo.setNumero_microchip(rs.getString("numero_microchip"));
                caballo.setGrupo_de_caballos(dao.obtenerGrupoDeCaballos(rs.getInt("id_grupo_de_caballo")));
                caballo.setFecha_ingreso(rs.getDate("fecha_nacimiento"));
                caballo.setFecha_ingreso(rs.getDate("fecha_ingreso"));
                caballo.setSexo(rs.getString("sexo"));
                caballo.setColor(rs.getString("color"));
                caballo.setOtras_sennas(rs.getString("otras_sennas"));
                caballo.setEstado(rs.getString("estado"));
                caballo.setNumero(rs.getInt("numero"));
                resultado.add(caballo);
            }
            consulta.close();
            conexion.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    public List<EventoClinico> ObtenerEventosCaballo(int id_caballo) throws SIGIPROException
    {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT ec.id_evento, ec.fecha, ec.responsable, ec.descripcion, u.id_usuario, u.nombre_completo, te.id_tipo_evento, te.nombre "
                  + " FROM (SELECT * FROM caballeriza.eventos_clinicos_caballos WHERE id_caballo = ?) as eventos "
                  + "   INNER JOIN caballeriza.eventos_clinicos ec ON ec.id_evento = eventos.id_evento "
                  + "   INNER JOIN seguridad.usuarios u ON ec.responsable = u.id_usuario "
                  + "   INNER JOIN caballeriza.tipos_eventos te ON ec.id_tipo_evento = te.id_tipo_evento "
            );
            consulta.setInt(1, id_caballo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                EventoClinico evento = new EventoClinico();
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setDescripcion(rs.getString("descripcion"));

                Usuario responsable = new Usuario();
                responsable.setId_usuario(rs.getInt("id_usuario"));
                responsable.setNombre_completo(rs.getString("nombre_completo"));
                evento.setResponsable(responsable);

                TipoEvento tipo_evento = new TipoEvento();

                tipo_evento.setId_tipo_evento(rs.getInt("id_tipo_evento"));
                tipo_evento.setNombre(rs.getString("nombre"));
                evento.setTipo_evento(tipo_evento);

                resultado.add(evento);
            }
            rs.close();
            consulta.close();
            conexion.close();

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    public List<Inoculo> ObtenerInoculosCaballo(int id_caballo) throws SIGIPROException
    {
        List<Inoculo> resultado = new ArrayList<Inoculo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("select mnn,baa,bap,cdd,lms,tetox,otro, i.id_inoculo, fecha "
                                                                        + " from caballeriza.inoculos i "
                                                                        + " left outer join caballeriza.inoculos_caballos ic "
                                                                        + " on i.id_inoculo = ic.id_inoculo "
                                                                        + " where id_caballo=?; ");
            consulta.setInt(1, id_caballo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Inoculo inoculo = new Inoculo();
                inoculo.setId_inoculo(rs.getInt("id_inoculo"));
                inoculo.setBaa(rs.getString("baa"));
                inoculo.setMnn(rs.getString("mnn"));
                inoculo.setBap(rs.getString("bap"));
                inoculo.setCdd(rs.getString("cdd"));
                inoculo.setLms(rs.getString("lms"));
                inoculo.setTetox(rs.getString("tetox"));
                inoculo.setOtro(rs.getString("otro"));
                inoculo.setFecha(rs.getDate("fecha"));
                resultado.add(inoculo);
            }
            rs.close();
            consulta.close();
            conexion.close();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    public List<SangriaPruebaCaballo> ObtenerSangriasPruebaCaballo(int id_caballo) throws SIGIPROException
    {
        List<SangriaPruebaCaballo> resultado = new ArrayList<SangriaPruebaCaballo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("select sp.id_sangria_prueba, muestra, fecha_recepcion_muestra, hematrocito, hemoglobina "
                                                                        + " from caballeriza.sangrias_pruebas sp "
                                                                        + " left outer join caballeriza.sangrias_pruebas_caballos spc "
                                                                        + " on sp.id_sangria_prueba= spc.id_sangria_prueba"
                                                                        + " where id_caballo=?; ");
            consulta.setInt(1, id_caballo);
            ResultSet rs = consulta.executeQuery();
            SangriaPruebaDAO spdao = new SangriaPruebaDAO();

            while (rs.next()) {
                SangriaPruebaCaballo sangriapc = new SangriaPruebaCaballo();
                sangriapc.setSangria_prueba(spdao.obtenerSangriaPrueba(rs.getInt("id_sangria_prueba")));
                sangriapc.setHematrocito(rs.getFloat("hematrocito"));
                sangriapc.setHemoglobina(rs.getFloat("hemoglobina"));
                resultado.add(sangriapc);
            }
            rs.close();
            consulta.close();
            conexion.close();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    public List<SangriaCaballo> ObtenerSangriasCaballo(int id_caballo) throws SIGIPROException
    {
        List<SangriaCaballo> resultado = new ArrayList<SangriaCaballo>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement("select s.id_sangria, fecha_dia1, sangre_dia1, sangre_dia2, sangre_dia3, plasma_dia1, plasma_dia2, plasma_dia3, lal_dia1, lal_dia2, lal_dia3 "
                                                                        + " from caballeriza.sangrias s "
                                                                        + " left outer join caballeriza.sangrias_caballos sc "
                                                                        + " on s.id_sangria= sc.id_sangria "
                                                                        + " where id_caballo=?; ");
            consulta.setInt(1, id_caballo);
            ResultSet rs = consulta.executeQuery();
            SangriaDAO sdao = new SangriaDAO();

            while (rs.next()) {
                SangriaCaballo sangriac = new SangriaCaballo();
                sangriac.setSangria(sdao.obtenerSangria(rs.getInt("id_sangria")));
                sangriac.setSangre_dia1(rs.getFloat("sangre_dia1"));
                sangriac.setSangre_dia2(rs.getFloat("sangre_dia2"));
                sangriac.setSangre_dia3(rs.getFloat("sangre_dia3"));
                sangriac.setPlasma_dia1(rs.getFloat("plasma_dia1"));
                sangriac.setPlasma_dia2(rs.getFloat("plasma_dia2"));
                sangriac.setPlasma_dia3(rs.getFloat("plasma_dia3"));
                sangriac.setLal_dia1(rs.getFloat("lal_dia1"));
                sangriac.setLal_dia2(rs.getFloat("lal_dia2"));
                sangriac.setLal_dia3(rs.getFloat("lal_dia3"));
                resultado.add(sangriac);
            }
            rs.close();
            consulta.close();
            conexion.close();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    public List<EventoClinico> ObtenerEventosCaballoRestantes(int id_caballo) throws SIGIPROException
    {
        List<EventoClinico> resultado = new ArrayList<EventoClinico>();
        TipoEventoDAO dao = new TipoEventoDAO();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT  ec.id_evento, ec.id_tipo_evento, ec.fecha, ec.descripcion, u.id_usuario, u.nombre_completo "
                    + " FROM caballeriza.eventos_clinicos ec "
                    + "      INNER JOIN seguridad.usuarios u ON u.id_usuario = ec.responsable "
                    + "      LEFT OUTER JOIN caballeriza.tipos_eventos tp ON ec.id_tipo_evento = tp.id_tipo_evento "
                    + " WHERE id_evento NOT IN (SELECT id_evento FROM caballeriza.eventos_clinicos_caballos ecc where id_caballo =?) order by ec.id_tipo_evento;");
            consulta.setInt(1, id_caballo);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                EventoClinico evento = new EventoClinico();
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setTipo_evento(dao.obtenerTipoEvento(rs.getInt("id_tipo_evento")));
                evento.setDescripcion(rs.getString("descripcion"));

                Usuario responsable = new Usuario();
                responsable.setId_usuario(rs.getInt("id_usuario"));
                responsable.setNombre_completo(rs.getString("nombre_completo"));
                evento.setResponsable(responsable);

                resultado.add(evento);
            }
            rs.close();
            consulta.close();
            conexion.close();

        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error de comunicación con la base de datos.");
        }
        return resultado;
    }

    public boolean agregarPeso(Peso p) throws SIGIPROException
    {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement("INSERT INTO caballeriza.pesos_caballos (id_caballo, fecha, peso) VALUES (?,?,?);");

            consulta.setInt(1, p.getId_caballo());
            consulta.setDate(2, p.getFecha());
            consulta.setFloat(3, p.getPeso());

            if (consulta.executeUpdate() != 1) {
                throw new SQLException();
            }
            else {
                resultado = true;
            }

            consulta.close();
            conexion.close();

            return resultado;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al registrar el peso.");
        }
    }
    
    public boolean editarPeso(Peso p) throws SIGIPROException
    {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement("UPDATE caballeriza.pesos_caballos SET fecha = ?, peso = ? WHERE id_peso = ?;");

            consulta.setDate(1, p.getFecha());
            consulta.setFloat(2, p.getPeso());
            consulta.setInt(3, p.getId_peso());

            if (consulta.executeUpdate() != 1) {
                throw new SQLException();
            }
            else {
                resultado = true;
            }

            consulta.close();
            conexion.close();

            return resultado;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al editar el peso.");
        }
    }
    
    public boolean eliminarPeso(int id_peso) throws SIGIPROException
    {
        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement("DELETE FROM caballeriza.pesos_caballos WHERE id_peso = ?;");

            consulta.setInt(1, id_peso);

            if (consulta.executeUpdate() != 1) {
                throw new SQLException();
            }
            else {
                resultado = true;
            }

            consulta.close();
            conexion.close();

            return resultado;

        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al elimianr el peso.");
        }
    }

    private Connection getConexion()
    {
        try {
            if (conexion.isClosed()) {
                SingletonBD s = SingletonBD.getSingletonBD();
                conexion = s.conectar();
            }
        }
        catch (Exception ex) {
            conexion = null;
        }
        return conexion;
    }
}
