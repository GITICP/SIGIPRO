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
    public List<BarraFuncionalidad> obtenerModulos(int usuario)
    {
        SingletonBD s = SingletonBD.getSingletonBD();
        Connection conexion = s.conectar();
        List<BarraFuncionalidad> resultado = null;
        
        if (conexion!=null)
        {
            try
            {
                PreparedStatement consulta;
                consulta = conexion.prepareStatement("Select id_menu_principal, id_padre, tag, redirect " +
                                                    "From seguridad.entradas_menu_principal " +
                                                    "Where permiso in " +
                                                    "( " +
                                                    "	Select id_permiso " +
                                                    "	From seguridad.permisos_roles " +
                                                    "	Where id_rol in " +
                                                    "		( " +
                                                    "			Select id_rol " +
                                                    "			From seguridad.roles_usuarios " +
                                                    "			Where id_usuario = ? " +
                                                    "			And ( " +
                                                    "				fecha_activacion = fecha_desactivacion " +
                                                    "			      or " +
                                                    "				(fecha_activacion < current_date and fecha_desactivacion > current_date) " +
                                                    "			    ) " +
                                                    "		) " +
                                                    ") " +
                                                    "order  by id_padre, redirect desc ");
                consulta.setInt(1, usuario);
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
            if (resultadoConsulta.getInt("id_menu_principal") == resultadoConsulta.getInt("id_padre"))
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
