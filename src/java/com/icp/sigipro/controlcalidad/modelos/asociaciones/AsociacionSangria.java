/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.modelos.asociaciones;

import com.icp.sigipro.caballeriza.dao.SangriaDAO;
import com.icp.sigipro.caballeriza.modelos.Sangria;
import com.icp.sigipro.controlcalidad.dao.ResultadoDAO;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public class AsociacionSangria extends AsociacionSolicitud {

    private Sangria sangria;
    private int dia;
    private final SangriaDAO sangria_dao = new SangriaDAO();
    private final ResultadoDAO resultado_dao = new ResultadoDAO();
    private final AsociacionLALSangria asociacion_informe = new AsociacionLALSangria(this);

    public AsociacionSangria(SolicitudCC p_solicitud) {
        tipo = "sangria";
        tabla = "caballeriza.sangrias";
        solicitud = p_solicitud;
    }

    @Override
    public void asociar(HttpServletRequest request) {
        int id_sangria = Integer.parseInt(request.getParameter("sangria"));
        try {
            sangria = sangria_dao.obtenerSangria(id_sangria);
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        dia = Integer.parseInt(request.getParameter("dia"));
        solicitud.setDescripcion("Sangría grupo " + sangria.getGrupo().getNombre() + ", día " + dia);
    }

    @Override
    public void asociar(ResultSet rs) throws SQLException {
        dia = Integer.parseInt(rs.getString("informacion_referencia_adicional"));
        int id_sangria = rs.getInt("id_referenciado");
        sangria = new Sangria();
        sangria.setId_sangria(id_sangria);
    }

    @Override
    public void asociarResultado(Resultado resultado, HttpServletRequest request) {
        asociacion_informe.asociar(resultado, request);
    }

    @Override
    public void prepararEditarSolicitud(HttpServletRequest request) throws SIGIPROException {
        request.setAttribute("tipo", "sangria");
        request.setAttribute("id_sangria", sangria.getId_sangria());
        request.setAttribute("dia", dia);
        request.setAttribute("sangrias", sangria_dao.obtenerSangriasLALPendiente());
    }

    @Override
    public void prepararGenerarInforme(HttpServletRequest request) throws SIGIPROException {
        request.setAttribute("tipo", "sangria");
        request.setAttribute("id_sangria", sangria.getId_sangria());
        request.setAttribute("dia", dia);
        request.setAttribute("sangria", sangria_dao.obtenerSangria(sangria.getId_sangria()));
        request.setAttribute("caballos_sangria", sangria_dao.obtenerCaballosSangriaDia(sangria.getId_sangria(), dia));
    }

    @Override
    public void prepararEditarInforme(HttpServletRequest request) throws SIGIPROException {
        request.setAttribute("tipo", "sangria");
        request.setAttribute("id_sangria", sangria.getId_sangria());
        request.setAttribute("dia", dia);
        request.setAttribute("sangria", sangria_dao.obtenerSangria(sangria.getId_sangria()));
        request.setAttribute("caballos_sangria", sangria_dao.obtenerCaballosSangriaDia(sangria.getId_sangria(), dia));
        List<ObjetoAsociacionMultiple> caballos_resultado = resultado_dao.obtenerCaballosResultado(sangria.getId_sangria(), dia);
        request.setAttribute("caballos_resultado", caballos_resultado);
        List<Integer> ids_caballos = new ArrayList<>();
        for (ObjetoAsociacionMultiple osm : caballos_resultado) {
            ids_caballos.addAll(osm.getIds());
        }
        request.setAttribute("ids_caballos_con_resultado", ids_caballos);
    }

    @Override
    public List<PreparedStatement> insertarSQLSolicitud(Connection conexion) throws SQLException {

        List<PreparedStatement> resultado = new ArrayList<>();

        PreparedStatement consulta = conexion.prepareStatement(
                " UPDATE control_calidad.solicitudes SET "
                + " tipo_referencia = ?, "
                + " tabla_referencia = ?, "
                + " id_referenciado = ?,"
                + " informacion_referencia_adicional = ?"
                + " where id_solicitud = ?; "
        );

        consulta.setString(1, tipo);
        consulta.setString(2, tabla);
        consulta.setInt(3, sangria.getId_sangria());
        consulta.setString(4, String.valueOf(dia));
        consulta.setInt(5, solicitud.getId_solicitud());
        consulta.addBatch();

        resultado.add(consulta);

        return resultado;

    }

    @Override
    public List<PreparedStatement> editarSQLInforme(Connection conexion) throws SQLException {
        
        List<PreparedStatement> resultado = new ArrayList<>();
        
        resultado.addAll(resetearSolicitudAnterior(conexion));
        resultado.addAll(asociacion_informe.editarSQL(conexion));
        
        return resultado;
    }

    @Override
    public List<PreparedStatement> insertarSQLInforme(Connection conexion) throws SQLException {
        return asociacion_informe.insertarSQL(conexion);
    }

    @Override
    public List<PreparedStatement> resetear(Connection conexion) throws SQLException {
        List<PreparedStatement> resultado = new ArrayList<>(); 
        
        PreparedStatement consulta_sangrias_caballos = conexion.prepareStatement(
                  " UPDATE caballeriza.sangrias_caballos SET "
                + " id_resultado_lal_dia" + dia + " = ? "
                + " WHERE id_sangria = ? AND id_caballo IN ( "
                + "                                 SELECT DISTINCT(c.id_caballo) "
                + "                                 FROM (  SELECT * "
                + "                                         FROM control_calidad.grupos g "
                + "                                             INNER JOIN control_calidad.grupos_muestras gm ON gm.id_grupo = g.id_grupo "
                + "                                             INNER JOIN control_calidad.muestras m ON m.id_muestra = gm.id_muestra "
                + "                                         WHERE g.id_solicitud = ? "
                + "                                      ) AS muestras_caballos "
                + "                                 INNER JOIN caballeriza.caballos c ON c.numero = CAST(muestras_caballos.identificador AS int)"
                + "                                        ); "
        );
        
        consulta_sangrias_caballos.setNull(1, java.sql.Types.INTEGER);
        consulta_sangrias_caballos.setInt(2, sangria.getId_sangria());
        consulta_sangrias_caballos.setInt(3, solicitud.getId_solicitud());
        consulta_sangrias_caballos.addBatch();
        
        resultado.add(consulta_sangrias_caballos);
        
        return resultado;
    }
}
