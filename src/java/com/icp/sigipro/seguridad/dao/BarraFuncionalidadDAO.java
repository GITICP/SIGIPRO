/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.dao;
import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.seguridad.modelos.BarraFuncionalidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Boga
 */
public class BarraFuncionalidadDAO
{    

    public BarraFuncionalidadDAO() {    }
    
    public List<BarraFuncionalidad> obtenerModulos(int usuario, List<Integer> permisos)
    {
      if ( !(permisos.contains(1)) )
      {
        return consultarModulos(usuario,  " ;With sub_modulos as "
                                        + " ( "
                                        + "    Select distinct emp.id_menu_principal, emp.id_padre, emp.tag, emp.redirect "
                                        + "    From seguridad.entradas_menu_principal emp "
                                        + "    inner join "
                                        + "    ( "
                                        + "      Select distinct permisos.id_permiso, pmp.id_menu_principal "
                                        + "      From seguridad.permisos_menu_principal pmp "
                                        + "      inner join "
                                        + "      ( "
                                        + "        Select id_permiso "
                                        + "        From seguridad.permisos_roles "
                                        + "        Where id_rol in "
                                        + "        ( "
                                        + "          Select id_rol "
                                        + "          From seguridad.roles_usuarios "
                                        + "          Where id_usuario = ? "
                                        + "            And "
                                        + "            ( "
                                        + "              fecha_activacion = fecha_desactivacion "
                                        + "              or "
                                        + "              (fecha_activacion < current_date and fecha_desactivacion > current_date) "
                                        + "            ) "
                                        + "        ) "
                                        + "      ) as permisos "
                                        + "      on permisos.id_permiso = pmp.id_permiso "
                                        + "    ) as permisos_menu "
                                        + "    on permisos_menu.id_menu_principal = emp.id_menu_principal "
                                        + "    UNION "
                                        + "    Select * "
                                        + "    from seguridad.entradas_menu_principal "
                                        + "    Where id_menu_principal = 109 and exists "
                                        + "    ( "
                                        + "        SELECT 1 FROM "
                                        + "        ( "
                                        + "          SELECT id_usuario "
                                        + "          FROM bodega.usuarios_sub_bodegas_ingresos "
                                        + "          where id_usuario = ? "
                                        + "          UNION "
                                        + "          SELECT id_usuario "
                                        + "          FROM bodega.usuarios_sub_bodegas_egresos "
                                        + "          where id_usuario = ? "
                                        + "          UNION "
                                        + "          SELECT id_usuario "
                                        + "          FROM bodega.usuarios_sub_bodegas_ver "
                                        + "          where id_usuario = ? "
                                        + "          UNION "
                                        + "          SELECT id_usuario "
                                        + "          FROM bodega.sub_bodegas "
                                        + "          WHERE id_usuario = ? "
                                        + "         ) as sub_bodegas "
                                        + "    ) "
                                        + "  ) "
                                        + "  Select * "
                                        + "  from seguridad.entradas_menu_principal "
                                        + "  Where id_menu_principal in (select id_padre from sub_modulos) and redirect is not null "
                                        + "  UNION "
                                        + "  Select * "
                                        + "  From seguridad.entradas_menu_principal "
                                        + "  Where id_menu_principal in (select id_menu_principal from sub_modulos) and redirect is not null "
                                        + "  order by id_menu_principal asc;", false);
      }
      else
      {
        return consultarModulos(usuario, "Select * from seguridad.entradas_menu_principal where redirect is not null order by id_menu_principal asc;", true);
      }
    }
    
    public List<BarraFuncionalidad> consultarModulos(int usuario, String consultaSQL, boolean admin)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<BarraFuncionalidad> resultado = null;
        
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
    private List<BarraFuncionalidad> llenarBarraFuncionalidad(ResultSet resultadoConsulta) throws SQLException
    {
        List<BarraFuncionalidad> resultado = new ArrayList<BarraFuncionalidad>();
        BarraFuncionalidad temp = new BarraFuncionalidad();
        String modulo;
        
        while(resultadoConsulta.next())
        {
            if (resultadoConsulta.getInt("id_padre") == 0)
            {
                modulo = resultadoConsulta.getString("tag");
                temp = new BarraFuncionalidad(resultadoConsulta.getString("tag"));
                resultado.add(temp);
            }
            else
            {
                String[] funcionalidad = new String[2];
                funcionalidad[0] = resultadoConsulta.getString("tag");
                funcionalidad[1] = resultadoConsulta.getString("redirect");
                temp.agregarFuncionalidad(funcionalidad);
            }
        }
        return resultado;
    }
}
