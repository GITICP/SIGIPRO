/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.notificaciones.dao;

import com.icp.sigipro.core.DAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.notificaciones.modelos.Notificacion;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Josue
 */
public class Tipo_NotificacionesDAO extends DAO{
        public Tipo_NotificacionesDAO() {
    }

    /**
     *
     * @param id_tipo_notificacion
     * @param descripcion
     * @param icono
     * @param id_permisos
     * @param id_usuarios_bloqueados
     * @return
     * @throws SIGIPROException
     */
    public boolean insertarTipo_Notificacion(int id_tipo_notificacion, String descripcion, String icono, Integer[] id_permisos, Integer[] id_usuarios_bloqueados) throws SIGIPROException {

        boolean resultado = false;

        try {
            PreparedStatement consulta = getConexion().prepareStatement(" INSERT INTO calendario.tipo_notificaciones (id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)"
                    + " VALUES (?,?,?,?,?)");

            consulta.setInt(1, id_tipo_notificacion);
            consulta.setString(2, descripcion);
            consulta.setString(3, icono);
            Array a = getConexion().createArrayOf("int4", id_permisos);
            consulta.setArray(4, a);
            Array b = getConexion().createArrayOf("int4", id_usuarios_bloqueados);
            consulta.setArray(5, b);

            consulta.executeQuery();
            
            consulta.close();
            cerrarConexion();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
        return resultado;
    }

    /**
     *
     * @param esquema
     * @param tablaBase
     * @param id_tipo_notificacion
     * @param redirect
     * @return
     * @throws SIGIPROException
     */
    public boolean crearStoreProcedure(String esquema, String tablaBase, int id_tipo_notificacion, String redirect) throws SIGIPROException{

        try {
            String statement = "CREATE OR REPLACE FUNCTION "+esquema+".crear_notificacion_"+id_tipo_notificacion+"()\n" +
                "  RETURNS trigger AS\n" +
                "$BODY$\n" +
                "	DECLARE lista_usuarios integer[];\n" +
                "	DECLARE lista_permisos integer[];\n" +
                "	BEGIN\n" +
                "		lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = "+id_tipo_notificacion+");\n" +
                "		FOR e IN array_lower(lista_permisos, 1) .. array_upper(lista_permisos, 1)\n" +
                "		LOOP\n" +
                "		lista_usuarios := lista_usuarios || array\n" +
                "			(Select distinct seguridad.usuarios.id_usuario from \n" +
                "				seguridad.usuarios\n" +
                "				inner join seguridad.roles_usuarios as ru on (seguridad.usuarios.id_usuario = ru.id_usuario)\n" +
                "				inner join seguridad.roles as r on (ru.id_rol = r.id_rol)\n" +
                "				inner join seguridad.permisos_roles as pr on (r.id_rol = pr.id_rol)\n" +
                "				inner join (Select * from seguridad.permisos where id_permiso = lista_permisos[e]) as p on (pr.id_permiso = p.id_permiso));\n" +
                "		END LOOP;\n" +
                "		FOR i IN array_lower(lista_usuarios, 1) .. array_upper(lista_usuarios, 1)\n" +
                "		LOOP\n" +
                "			INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)\n" +
                "			VALUES ("+id_tipo_notificacion+",lista_usuarios[i],(Select current_timestamp), False, '"+redirect+"');\n" +
                "		END LOOP;\n" +
                "		RETURN NEW;\n" +
                "	END \n" +
                "$BODY$\n" +
                "  LANGUAGE plpgsql VOLATILE\n" +
                "  COST 100;\n" +
                "ALTER FUNCTION "+esquema+".crear_notificacion_"+id_tipo_notificacion+"()\n" +
                "  OWNER TO postgres;";
            
            PreparedStatement consulta = getConexion().prepareStatement(statement);

            consulta.executeQuery();
            
            consulta.close();
            cerrarConexion();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
    }
    
    /**
     *
     * @param esquema
     * @param tablaBase
     * @param id_tipo_notificacion
     * @return
     * @throws SIGIPROException
     */
    public boolean crearTrigger(String esquema, String tablaBase, int id_tipo_notificacion) throws SIGIPROException{
        try {
            String statement = "CREATE TRIGGER insertar_notificacion\n" +
                "  AFTER INSERT\n" +
                "  ON "+esquema+"."+tablaBase+"\n" +
                "  FOR EACH ROW\n" +
                "  EXECUTE PROCEDURE "+esquema+".crear_notificacion_"+id_tipo_notificacion+"();";
            
            PreparedStatement consulta = getConexion().prepareStatement(statement);

            consulta.executeQuery();

            consulta.close();
            cerrarConexion();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Se produjo un error al procesar el ingreso");
        }
    }
}
