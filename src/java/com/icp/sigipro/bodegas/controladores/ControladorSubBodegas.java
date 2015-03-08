/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bodegas.dao.SubBodegaDAO;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Boga
 */
@WebServlet(name = "ControladorSubBodegas", urlPatterns = {"/Bodegas/SubBodegas"})
public class ControladorSubBodegas extends SIGIPROServlet
{

    private final int[] permisos = {1, 1, 1};
    private SubBodegaDAO dao = new SubBodegaDAO();
    private SeccionDAO daoSecciones = new SeccionDAO();
    private UsuarioDAO daoUsuarios = new UsuarioDAO();

    protected final Class clase = ControladorSubBodegas.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
            add("eliminar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);

        validarPermiso(11, listaPermisos);
        String redireccion = "SubBodegas/Agregar.jsp";

        SubBodega sb = new SubBodega();

        List<Seccion> secciones = daoSecciones.obtenerSecciones();
        List<Usuario> usuarios = daoUsuarios.obtenerUsuarios();

        request.setAttribute("sub_bodega", sb);
        request.setAttribute("secciones", secciones);
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        try {
            request.setAttribute("listaSubBodegas", dao.obtenerSubBodegas());
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeAdvertencia("No se pudo obtener el listado completo. Refresque la página."));
            String mensaje_log = "Error de instanciación. Mensaje: " + ex.getMessage();
            Logger.getGlobal().log(Level.SEVERE, mensaje_log);
        }
        
        String redireccion = "SubBodegas/index.jsp";
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "SubBodegas/Ver.jsp";
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
        SubBodega sb = null;
        try {
            sb = dao.buscarSubBodega(id_sub_bodega);
            request.setAttribute("sub_bodega", sb);
            request.setAttribute("accion", "Editar");
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega que está buscando. Inténtelo nuevamente."));
            try {
                request.setAttribute("listaSubBodegas", dao.obtenerSubBodegas());
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega que está buscando ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
            redireccion = "SubBodegas/index.jsp";
        }
        
        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(12, listaPermisos);
        String redireccion = "SubBodegas/Editar.jsp";
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
        SubBodega sb;
        try {
            sb = dao.buscarSubBodega(id_sub_bodega);
            
            List<Usuario> usuarios_ingresos;
            List<Usuario> usuarios_egresos;
            List<Usuario> usuarios_ver;
            List<Usuario> usuarios;
            
            usuarios_ingresos = dao.usuariosPermisos(SubBodegaDAO.INGRESAR, id_sub_bodega);
            usuarios_egresos = dao.usuariosPermisos(SubBodegaDAO.EGRESAR, id_sub_bodega);
            usuarios_ver = dao.usuariosPermisos(SubBodegaDAO.VER, id_sub_bodega);
            usuarios = daoUsuarios.obtenerUsuarios();
            
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("usuarios_ingresos", usuarios_ingresos);
            request.setAttribute("usuarios_egresos", usuarios_egresos);
            request.setAttribute("usuarios_ver", usuarios_ver);
            request.setAttribute("sub_bodega", sb);
            request.setAttribute("accion", "Editar");
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega que está buscando. Inténtelo nuevamente."));
            try {
                request.setAttribute("listaSubBodegas", dao.obtenerSubBodegas());
            } catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError("No se encontró la sub bodega que está buscando ni se pudo obtener el listado completo. Notifique al administrador del sistema."));
            }
            redireccion = "SubBodegas/index.jsp";
        }
        
        redireccionar(request, response, redireccion);
    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(13, listaPermisos);
        int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
        // dao.eliminarProductoInterno(id_producto);
        String redireccion = "SubBodegas/index.jsp";
        // Obtener todo
        // Setear
        // request.setAttribute("listaProductos", productos);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "SubBodegas/Agregar.jsp";

        SubBodega sb = construirObjeto(request);

        try {
            dao = new SubBodegaDAO();
            String[] ids_ingresos = dao.parsearAsociacion("#i#", request.getParameter("ids-ingresos"));
            String[] ids_egresos = dao.parsearAsociacion("#e#", request.getParameter("ids-egresos"));
            String[] ids_ver = dao.parsearAsociacion("#v#", request.getParameter("ids-ver"));
            if (dao.insertar(sb, ids_ingresos, ids_egresos, ids_ver)) {
                try {
                    request.setAttribute("mensaje", helper.mensajeDeExito("SubBodega agregada correctamente."));
                    request.setAttribute("listaSubBodegas", dao.obtenerSubBodegas());
                    redireccion = "SubBodegas/index.jsp";
                }
                catch (SIGIPROException ex) {
                    request.setAttribute("mensaje", helper.mensajeDeAdvertencia("SubBodega se insertó correctamente, pero no se pudo obtener el listado completo. Refresque la página."));
                    String mensaje_log = "Error de instanciación. Mensaje: " + ex.getMessage();
                    Logger.getGlobal().log(Level.SEVERE, mensaje_log);
                    redireccion = "SubBodegas/index.jsp";
                }
            }
        }
        catch (SIGIPROException ex) {
            ex.printStackTrace();
        }

        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String id = request.getParameter("id_sub_bodega");
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private SubBodega construirObjeto(HttpServletRequest request)
    {
        SubBodega sb = new SubBodega();

        sb.setNombre(request.getParameter("nombre"));

        Seccion seccion = new Seccion();
        Usuario usuario = new Usuario();

        seccion.setId_seccion(Integer.parseInt(request.getParameter("seccion")));
        usuario.setId_usuario(Integer.parseInt(request.getParameter("usuario")));

        sb.setSeccion(seccion);
        sb.setUsuario(usuario);

        return sb;
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        List<String> listaAcciones;
        if (accionHTTP.equals("get")) {
            listaAcciones = accionesGet;
        }
        else {
            listaAcciones = accionesPost;
        }
        if (listaAcciones.contains(accion)) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
        else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
}
