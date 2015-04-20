/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.BarraFuncionalidad;
import com.icp.sigipro.seguridad.modelos.Funcionalidad;
import com.icp.sigipro.seguridad.modelos.Modulo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Boga
 */
public class BarraFuncionalidadDAO
{    

    public BarraFuncionalidadDAO() {    }
    
    public BarraFuncionalidad obtenerModulos(int usuario, List<Integer> permisos)
    {
      if ( !(permisos.contains(1)) )
      {
        return consultarModulos(usuario,  " ;With sub_modulos as "
                                        + " ( "
                                        + "Select distinct emp.id_menu_principal, emp.id_padre, emp.tag, emp.redirect "
                                        + "From seguridad.entradas_menu_principal emp "
                                        + "inner join "
                                        + "( "
                                        + "Select distinct permisos.id_permiso, pmp.id_menu_principal "
                                        + "From seguridad.permisos_menu_principal pmp "
                                        + "inner join "
                                        + "( "
                                        + " Select id_permiso "
                                        + " From seguridad.permisos_roles "
                                        + " Where id_rol in "
                                        + " ( "
                                        + "   Select id_rol "
                                        + "   From seguridad.roles_usuarios "
                                        + "   Where id_usuario = ? "
                                        + "     And "
                                        + "     ( "
                                        + "       fecha_activacion = fecha_desactivacion "
                                        + "       or "
                                        + "       (fecha_activacion < current_date and fecha_desactivacion > current_date) "
                                        + "     ) "
                                        + " ) "
                                        + ") as permisos "
                                        + "on permisos.id_permiso = pmp.id_permiso "
                                        + ") as permisos_menu "
                                        + "on permisos_menu.id_menu_principal = emp.id_menu_principal "
                                        + "UNION "
                                        + "Select * "
                                        + "from seguridad.entradas_menu_principal "
                                        + "Where id_menu_principal = 109 and exists "
                                        + "( "
                                        + " SELECT 1 FROM "
                                        + " ( "
                                        + "   SELECT id_usuario "
                                        + "   FROM bodega.usuarios_sub_bodegas_ingresos "
                                        + "   where id_usuario = ? "
                                        + "   UNION "
                                        + "   SELECT id_usuario "
                                        + "   FROM bodega.usuarios_sub_bodegas_egresos "
                                        + "   where id_usuario = ? "
                                        + "   UNION "
                                        + "   SELECT id_usuario "
                                        + "   FROM bodega.usuarios_sub_bodegas_ver "
                                        + "   where id_usuario = ? "
                                        + "   UNION "
                                        + "   SELECT id_usuario "
                                        + "   FROM bodega.sub_bodegas "
                                        + "   WHERE id_usuario = ? "
                                        + "  ) as sub_bodegas "
                                        + ")  "
                                        + ") "
                                        + "Select * "
                                        + "from seguridad.entradas_menu_principal "
                                        + "Where redirect is null "
                                        + "UNION "
                                        + "Select * "
                                        + "From seguridad.entradas_menu_principal "
                                        + "Where id_menu_principal in (select id_menu_principal from sub_modulos) "
                                        + "order by redirect desc, id_padre asc", false);
      }
      else
      {
        return consultarModulos(usuario, "Select * from seguridad.entradas_menu_principal order by redirect desc, id_padre asc;", true);
      }
    }
    
    public BarraFuncionalidad consultarModulos(int usuario, String consultaSQL, boolean admin)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        BarraFuncionalidad resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement(consultaSQL);
                if (!admin)
                {
                  consulta.setInt(1, usuario);
                  consulta.setInt(2, usuario);
                  consulta.setInt(3, usuario);
                  consulta.setInt(4, usuario);
                  consulta.setInt(5, usuario);
                }
                ResultSet resultadoConsulta = consulta.executeQuery();
                resultado = llenarBarraFuncionalidad(resultadoConsulta);
                resultado.eliminarModulosVacios();
                resultadoConsulta.close();
                conexion.close();
            }
            catch(SQLException ex)
            {
                resultado = null;
            }
        }
        return resultado;
    }
    
    @SuppressWarnings("Convert2Diamond")
    private BarraFuncionalidad llenarBarraFuncionalidad(ResultSet resultadoConsulta) throws SQLException
    {
        BarraFuncionalidad resultado = new BarraFuncionalidad();
        
        while(resultadoConsulta.next())
        {
            int id_menu  = resultadoConsulta.getInt("id_menu_principal");
            int id_padre = resultadoConsulta.getInt("id_padre");
            String tag = resultadoConsulta.getString("tag");
            if (id_menu % 10 == 0)
            {
                Modulo m = new Modulo();
                m.setId_modulo(id_menu);
                m.setNombre(tag);
                if (id_padre == 0) {
                    resultado.agregarModulo(m);
                } else {
                    m.setId_padre(id_padre);
                    resultado.agregarSubModulo(m);
                }
            }
            else
            {
                Funcionalidad f = new Funcionalidad();
                f.setId_funcionalidad(id_menu);
                f.setId_padre(id_padre);
                f.setTag(tag);
                f.setRedirect(resultadoConsulta.getString("redirect"));
                
                resultado.agregarFuncionalidad(f);
            }
        }
        return resultado;
    }
}
