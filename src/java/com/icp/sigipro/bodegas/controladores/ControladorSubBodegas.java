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
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
    String redireccion = "SubBodegas/index.jsp";
    // List<ProductoInterno> productos = dao.obtenerProductos();
    // request.setAttribute("listaProductos", productos);
    redireccionar(request, response, redireccion);
  }

  protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermisos(permisos, listaPermisos);
    String redireccion = "SubBodegas/Ver.jsp";
    int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
    try {
      SubBodega s = dao.buscar(id_sub_bodega);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    List<Integer> listaPermisos = getPermisosUsuario(request);
    validarPermiso(12, listaPermisos);
    String redireccion = "SubBodegas/Editar.jsp";
    int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
    // ProductoInterno producto = dao.obtenerProductoInterno(id_producto);
    // Obtener todo y asociaciones restantes
    // Setear
    // request.setAttribute("producto", producto);
    request.setAttribute("accion", "Editar");
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
    
    
    
    redireccionar(request, response, redireccion);
  }
  
  protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String id = request.getParameter("id_sub_bodega");
  }
  
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
  private SubBodega construirObjeto(HttpServletRequest request) {
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
    if (accionesGet.contains(accion)) {
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
