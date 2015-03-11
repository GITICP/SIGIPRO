/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.dao;

import com.icp.sigipro.bodegas.modelos.InventarioSubBodega;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.DAO;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Boga
 */
public class SubBodegaDAO extends DAO<SubBodega>
{
    
    public static final String INGRESAR = "bodega.usuarios_sub_bodegas_ingresos";
    public static final String EGRESAR = "bodega.usuarios_sub_bodegas_egresos";
    public static final String VER = "bodega.usuarios_sub_bodegas_ver";

    public SubBodegaDAO()
    {
        super(SubBodega.class, "bodega", "sub_bodegas");
    }

    public List<SubBodega> obtenerSubBodegas() throws SIGIPROException
    {

        List<SubBodega> listaResultado = new ArrayList<SubBodega>();

        try {
            PreparedStatement consulta = getConexion().prepareStatement(
                    " SELECT sb.id_sub_bodega, sb.nombre, u.nombre_completo, s.nombre_seccion"
                    + " FROM bodega.sub_bodegas sb INNER JOIN seguridad.secciones s on sb.id_seccion = s.id_seccion "
                    + "                            INNER JOIN seguridad.usuarios u on u.id_usuario = sb.id_usuario;");

            ResultSet resultados = consulta.executeQuery();

            while (resultados.next()) {
                listaResultado.add(construirSubBodega(resultados));
            }
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al obtener subbodegas.");
        }
        return listaResultado;
    }

    private SubBodega construirSubBodega(ResultSet resultados) throws SQLException
    {
        SubBodega sb = new SubBodega();
        sb.setId_sub_bodega(resultados.getInt("id_sub_bodega"));
        sb.setNombre(resultados.getString("nombre"));

        Usuario u = new Usuario();
        u.setNombreCompleto(resultados.getString("nombre_completo"));

        Seccion s = new Seccion();
        s.setNombre_seccion(resultados.getString("nombre_seccion"));

        sb.setUsuario(u);
        sb.setSeccion(s);

        return sb;
    }

    @Override
    public int insertar(SubBodega param) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, SQLException
    {
        PreparedStatement objetoConsulta = getConexion().prepareStatement(" INSERT INTO " + this.nombreModulo + "." + this.nombreTabla
                                                                          + " (id_seccion, id_usuario, nombre) VALUES (?,?,?);");

        objetoConsulta.setInt(1, param.getSeccion().getId_seccion());
        objetoConsulta.setInt(2, param.getUsuario().getId_usuario());
        objetoConsulta.setString(3, param.getNombre());

        return ejecutarConsultaSinResultado(objetoConsulta);
    }

    public SubBodega buscarSubBodega(int id) throws SIGIPROException
    {
        SubBodega s = new SubBodega();
        try {
            String codigoConsulta = "SELECT sb.nombre, u.id_usuario, u.nombre_usuario, u.nombre_completo, s.id_seccion, s.nombre_seccion FROM " + nombreModulo + "." + nombreTabla + " sb INNER JOIN seguridad.secciones s on s.id_seccion = sb.id_seccion INNER JOIN seguridad.usuarios u on u.id_usuario = sb.id_usuario WHERE id_sub_bodega = ?";

            PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
            consulta.setInt(1, id);
            ResultSet resultado = ejecutarConsulta(consulta);

            if (resultado.next()) {
                s.setId_sub_bodega(id);
                s.setNombre(resultado.getString("nombre"));

                Seccion seccion = new Seccion();
                seccion.setId_seccion(resultado.getInt("id_seccion"));
                seccion.setNombre_seccion(resultado.getString("nombre_seccion"));

                Usuario usuario = new Usuario();
                usuario.setId_usuario(resultado.getInt("id_usuario"));
                usuario.setNombreCompleto(resultado.getString("nombre_completo"));
                usuario.setNombreUsuario(resultado.getString("nombre_usuario"));

                s.setSeccion(seccion);
                s.setUsuario(usuario);
            } 
        } catch (SQLException ex) {
            throw new SIGIPROException("Error al obtener sub bodega");
        }
        return s;
    }
    
    public SubBodega buscarSubBodegaEInventarios(int id) throws SIGIPROException
    {
        SubBodega sub_bodega = null;
        try {
            String codigoConsulta = " SELECT sb.id_sub_bodega, isb.id_inventario_sub_bodega,sb.nombre, ci.id_producto, ci.nombre as nombre_producto, ci.codigo_icp, isb.cantidad, isb.fecha_vencimiento "
                                  + " FROM " + nombreModulo + "." + nombreTabla + " sb "
                                  + "   INNER JOIN bodega.inventarios_sub_bodegas isb on isb.id_sub_bodega = sb.id_sub_bodega and isb.cantidad > 0"
                                  + "   INNER JOIN bodega.catalogo_interno ci on ci.id_producto = isb.id_producto " 
                                  + " WHERE sb.id_sub_bodega = ?";

            PreparedStatement consulta = getConexion().prepareStatement(codigoConsulta);
            consulta.setInt(1, id);
            ResultSet resultado = ejecutarConsulta(consulta);

            if ( resultado.next() ) {
                sub_bodega = new SubBodega();
                
                sub_bodega.setId_sub_bodega(id);
                sub_bodega.setNombre(resultado.getString("nombre"));
                
                List<InventarioSubBodega> inventarios = new ArrayList<InventarioSubBodega>();
                do {
                    InventarioSubBodega inventario_sb = new InventarioSubBodega();
                    
                    inventario_sb.setCantidad(resultado.getInt("cantidad"));
                    inventario_sb.setId_inventario_sub_bodega(resultado.getInt("id_inventario_sub_bodega"));
                    inventario_sb.setFecha_vencimiento(resultado.getDate("fecha_vencimiento"));
                    
                    ProductoInterno p = new ProductoInterno();
                    
                    p.setId_producto(resultado.getInt("id_producto"));
                    p.setNombre(resultado.getString("nombre_producto"));
                    p.setCodigo_icp(resultado.getString("codigo_icp"));
                    
                    inventario_sb.setProducto(p);
                    inventario_sb.setSub_bodega(sub_bodega);
                    
                    inventarios.add(inventario_sb);
                } while ( resultado.next() );
                
                sub_bodega.setInventarios(inventarios);
            } else {
                throw new SIGIPROException("No se encontraron registros de inventario para esta sub bodega");
            }
        } catch (SQLException ex) {
            throw new SIGIPROException("Error al obtener sub bodega");
        }
        return sub_bodega;
    }

    @Override
    public List<SubBodega> buscarPor(String[] campos, Object valor)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(SubBodega param)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(SubBodega param)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean insertar(SubBodega param, String[] idsIngresos, String[] idsEgresos, String[] idsVer) throws SIGIPROException
    {

        boolean resultado = false;
        try {
            /*
             ------------------------------------
             METER COMO TRANSACCIÓN
             ------------------------------------
             */

            PreparedStatement consultaInsertar = getConexion().prepareStatement(" INSERT INTO " + this.nombreModulo + "." + this.nombreTabla
                                                                                + " (id_seccion, id_usuario, nombre) VALUES (?,?,?) RETURNING id_sub_bodega");

            consultaInsertar.setInt(1, param.getSeccion().getId_seccion());
            consultaInsertar.setInt(2, param.getUsuario().getId_usuario());
            consultaInsertar.setString(3, param.getNombre());

            ResultSet idSubBodega = consultaInsertar.executeQuery();

            if (idSubBodega.next()) {
                param.setId_sub_bodega(idSubBodega.getInt("id_sub_bodega"));
            }
            else {
                throw new SIGIPROException("Error al insertar sub bodega");
            }

            PreparedStatement consultaEliminarIngresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ingresos WHERE id_sub_bodega = ?");
            PreparedStatement consultaEliminarEgresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_egresos WHERE id_sub_bodega = ?");
            PreparedStatement consultaEliminarVer = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ver WHERE id_sub_bodega = ?");

            consultaEliminarIngresos.setInt(1, param.getId_sub_bodega());
            consultaEliminarEgresos.setInt(1, param.getId_sub_bodega());
            consultaEliminarVer.setInt(1, param.getId_sub_bodega());

            consultaEliminarIngresos.executeUpdate();
            consultaEliminarEgresos.executeUpdate();
            consultaEliminarVer.executeUpdate();

            PreparedStatement insertarIngresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ingresos (id_sub_bodega, id_usuario) VALUES (?,?)");
            PreparedStatement insertarEgresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_egresos (id_sub_bodega, id_usuario) VALUES (?,?)");
            PreparedStatement insertarVer = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ver (id_sub_bodega, id_usuario) VALUES (?,?)");

            for (String idIngreso : idsIngresos) {
                insertarIngresos.setInt(1, param.getId_sub_bodega());
                insertarIngresos.setInt(2, Integer.parseInt(idIngreso));
                insertarIngresos.addBatch();
            }

            for (String idEgreso : idsEgresos) {
                insertarEgresos.setInt(1, param.getId_sub_bodega());
                insertarEgresos.setInt(2, Integer.parseInt(idEgreso));
                insertarEgresos.addBatch();
            }
            
            for (String idVer : idsVer) {
                insertarVer.setInt(1, param.getId_sub_bodega());
                insertarVer.setInt(2, Integer.parseInt(idVer));
                insertarVer.addBatch();
            }

            insertarIngresos.executeBatch();
            insertarEgresos.executeBatch();
            insertarVer.executeBatch();
            
            resultado = true;
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al insertar sub bodega");
        }

        return resultado;
    }
    
    public boolean editarSubBodega(SubBodega param, String[] idsIngresos, String[] idsEgresos, String[] idsVer) throws SIGIPROException
    {

        boolean resultado = false;
        try {
            /*
             ------------------------------------
             METER COMO TRANSACCIÓN
             ------------------------------------
             */

            PreparedStatement consultaInsertar = getConexion().prepareStatement(" UPDATE " + this.nombreModulo + "." + this.nombreTabla
                                                                                + " SET id_seccion = ?, id_usuario = ?, nombre = ? WHERE id_sub_bodega = ?");

            consultaInsertar.setInt(1, param.getSeccion().getId_seccion());
            consultaInsertar.setInt(2, param.getUsuario().getId_usuario());
            consultaInsertar.setString(3, param.getNombre());
            consultaInsertar.setInt(4, param.getId_sub_bodega());

            if (consultaInsertar.executeUpdate() != 1) {
                throw new SIGIPROException("Error al editar sub bodega");
            }

            PreparedStatement consultaEliminarIngresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ingresos WHERE id_sub_bodega = ?");
            PreparedStatement consultaEliminarEgresos = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_egresos WHERE id_sub_bodega = ?");
            PreparedStatement consultaEliminarVer = getConexion().prepareStatement("DELETE FROM bodega.usuarios_sub_bodegas_ver WHERE id_sub_bodega = ?");

            consultaEliminarIngresos.setInt(1, param.getId_sub_bodega());
            consultaEliminarEgresos.setInt(1, param.getId_sub_bodega());
            consultaEliminarVer.setInt(1, param.getId_sub_bodega());

            consultaEliminarIngresos.executeUpdate();
            consultaEliminarEgresos.executeUpdate();
            consultaEliminarVer.executeUpdate();

            PreparedStatement insertarIngresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ingresos (id_sub_bodega, id_usuario) VALUES (?,?)");
            PreparedStatement insertarEgresos = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_egresos (id_sub_bodega, id_usuario) VALUES (?,?)");
            PreparedStatement insertarVer = getConexion().prepareStatement("INSERT INTO bodega.usuarios_sub_bodegas_ver (id_sub_bodega, id_usuario) VALUES (?,?)");

            for (String idIngreso : idsIngresos) {
                insertarIngresos.setInt(1, param.getId_sub_bodega());
                insertarIngresos.setInt(2, Integer.parseInt(idIngreso));
                insertarIngresos.addBatch();
            }

            for (String idEgreso : idsEgresos) {
                insertarEgresos.setInt(1, param.getId_sub_bodega());
                insertarEgresos.setInt(2, Integer.parseInt(idEgreso));
                insertarEgresos.addBatch();
            }
            
            for (String idVer : idsVer) {
                insertarVer.setInt(1, param.getId_sub_bodega());
                insertarVer.setInt(2, Integer.parseInt(idVer));
                insertarVer.addBatch();
            }

            insertarIngresos.executeBatch();
            insertarEgresos.executeBatch();
            insertarVer.executeBatch();
            
            resultado = true;
        }
        catch (SQLException ex) {
            throw new SIGIPROException("Error al editar sub bodega");
        }

        return resultado;
    }
    
    public boolean registrarIngreso(InventarioSubBodega inventario_sub_bodega) throws SIGIPROException {
        
        boolean resultado = false;
        
        try {
            String primera_parte_consulta = " WITH upsert AS "
                                          + " (UPDATE bodega.inventarios_sub_bodegas "
                                          + "  SET cantidad = cantidad + ? "
                                          + "  WHERE id_producto = ? and id_sub_bodega = ? and fecha_vencimiento";
            String segunda_parte_consulta = "     INSERT INTO bodega.inventarios_sub_bodegas(id_producto, "
                                          + "                                                id_sub_bodega, "
                                          + "                                                fecha_vencimiento, "
                                          + "                                                cantidad"
                                          + "                                               ) "
                                          + "                                               SELECT ?, "
                                          + "                                                      ?, "
                                          + "                                                      ?, "          
                                          + "                                                      ?  "
                                          + "                                                      WHERE NOT EXISTS (SELECT * FROM upsert); ";
            
            String consulta_final;
            boolean fechas_null = false;
            if ( inventario_sub_bodega.getFecha_vencimiento() != null ){
                consulta_final = primera_parte_consulta + " = ? RETURNING *) " + segunda_parte_consulta;
            } else {
                fechas_null = true;
                consulta_final = primera_parte_consulta + " is null RETURNING *) " + segunda_parte_consulta;
            }
             
            PreparedStatement upsert_inventario = getConexion().prepareStatement( consulta_final );
            
            if (fechas_null) {
                upsert_inventario.setInt(1, inventario_sub_bodega.getCantidad());
                upsert_inventario.setInt(7, inventario_sub_bodega.getCantidad());

                upsert_inventario.setInt(2, inventario_sub_bodega.getProducto().getId_producto());
                upsert_inventario.setInt(4, inventario_sub_bodega.getProducto().getId_producto());

                upsert_inventario.setInt(3, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());
                upsert_inventario.setInt(5, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());
                
                upsert_inventario.setNull(6, java.sql.Types.DATE);
                
            } else {
                upsert_inventario.setInt(1, inventario_sub_bodega.getCantidad());
                upsert_inventario.setInt(8, inventario_sub_bodega.getCantidad());

                upsert_inventario.setInt(2, inventario_sub_bodega.getProducto().getId_producto());
                upsert_inventario.setInt(5, inventario_sub_bodega.getProducto().getId_producto());

                upsert_inventario.setInt(3, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());
                upsert_inventario.setInt(6, inventario_sub_bodega.getSub_bodega().getId_sub_bodega());
                
                upsert_inventario.setDate(4, inventario_sub_bodega.getFecha_vencimiento());
                upsert_inventario.setDate(7, inventario_sub_bodega.getFecha_vencimiento());
            }
            
            upsert_inventario.executeUpdate();
            resultado = true;
            
        } catch (SQLException ex) {
            throw new SIGIPROException("Error al registrar ingreso. Inténtelo nuevamente.");
        }
        
        return resultado;
    }
    
    public List<Usuario> usuariosPermisos(String tabla_por_buscar, int id_sub_bodega) throws SIGIPROException {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        
        try{
            PreparedStatement consulta_permisos = getConexion().prepareStatement(" SELECT u.id_usuario, u.nombre_completo, u.nombre_usuario "
                                                                           + " FROM " + tabla_por_buscar + " p_sb "
                                                                           + " INNER JOIN seguridad.usuarios u ON u.id_usuario = p_sb.id_usuario "
                                                                           + " WHERE id_sub_bodega = ?;");
            
            consulta_permisos.setInt(1, id_sub_bodega);
            
            ResultSet resultados = consulta_permisos.executeQuery();
            
            while( resultados.next() ) {
                Usuario u = new Usuario();
                
                u.setId_usuario(resultados.getInt("id_usuario"));
                u.setNombre_completo(resultados.getString("nombre_completo"));
                u.setNombre_usuario(resultados.getString("nombre_usuario"));
                
                usuarios.add(u);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener los usuarios con permisos.");
        }
        
        return usuarios;
    }
    
    public boolean consumirArticulo(int id_inventario, int cantidad) throws SIGIPROException {
        boolean resultado = false;
        
        try {
            PreparedStatement actualizar_inventario = getConexion().prepareStatement(" UPDATE bodega.inventarios_sub_bodegas SET cantidad = cantidad - ? WHERE id_inventario_sub_bodega = ?; ");
            
            actualizar_inventario.setInt(1, cantidad);
            actualizar_inventario.setInt(2, id_inventario);
            
            if( actualizar_inventario.executeUpdate() != 1 ){
                throw new SIGIPROException("Error al consumir de la sub bodega. Inténtelo nuevamente.");
            } else {
                resultado = true;
            }
            
        } catch(SQLException ex) {
            throw new SIGIPROException("Error de conexión con la base de datos. Contacte al administrador del sistema.");
        }
        
        return resultado;
    }

    public String[] parsearAsociacion(String pivote, String asociacionesCodificadas) {
        String[] idsTemp = asociacionesCodificadas.split(pivote);
        return Arrays.copyOfRange(idsTemp, 1, idsTemp.length);
    }
}
