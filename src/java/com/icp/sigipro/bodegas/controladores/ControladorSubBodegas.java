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
import java.io.PrintWriter;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boga
 */
@WebServlet(name = "ControladorSubBodegas", urlPatterns = {"/Bodegas/SubBodegas"})
public class ControladorSubBodegas extends SIGIPROServlet
{

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet ControladorSubBodegas</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet ControladorSubBodegas at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    try {
      String redireccion = "";
      String accion = request.getParameter("accion");

      SubBodegaDAO dao = new SubBodegaDAO();
      SeccionDAO daoSecciones = new SeccionDAO();
      UsuarioDAO daoUsuarios = new UsuarioDAO();

      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      int[] permisos = {1, 1, 1};

      if (accion != null) {
        if (accion.equalsIgnoreCase("ver")) {
          validarPermisos(permisos, listaPermisos);
          redireccion = "SubBodegas/Ver.jsp";
          int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
          try{
            SubBodega s = dao.buscar(id_sub_bodega);
          } catch (Exception ex){
            ex.printStackTrace();
          }
          
          // request.setAttribute("producto", producto);
        }
        else if (accion.equalsIgnoreCase("agregar")) {
          validarPermiso(11, listaPermisos);
          redireccion = "SubBodegas/Agregar.jsp";

          SubBodega sb = new SubBodega();

          List<Seccion> secciones = daoSecciones.obtenerSecciones();
          List<Usuario> usuarios = daoUsuarios.obtenerUsuarios();

          request.setAttribute("sub_bodega", sb);
          request.setAttribute("secciones", secciones);
          request.setAttribute("usuarios", usuarios);
          request.setAttribute("accion", "Agregar");
        }
        else if (accion.equalsIgnoreCase("eliminar")) {
          validarPermiso(13, listaPermisos);
          int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
          // dao.eliminarProductoInterno(id_producto);
          redireccion = "SubBodegas/index.jsp";
          // Obtener todo
          // Setear
          // request.setAttribute("listaProductos", productos);
        }
        else if (accion.equalsIgnoreCase("editar")) {
          validarPermiso(12, listaPermisos);
          redireccion = "SubBodegas/Editar.jsp";
          int id_sub_bodega = Integer.parseInt(request.getParameter("id_sub_bodega"));
          // ProductoInterno producto = dao.obtenerProductoInterno(id_producto);
          // Obtener todo y asociaciones restantes
          // Setear
          // request.setAttribute("producto", producto);
          request.setAttribute("accion", "Editar");
        }
        else {
          validarPermisos(permisos, listaPermisos);
          redireccion = "SubBodegas/index.jsp";
          // List<ProductoInterno> productos = dao.obtenerProductos();
          // request.setAttribute("listaProductos", productos);
        }
      }
      else {
        validarPermisos(permisos, listaPermisos);
        redireccion = "SubBodegas/index.jsp";
        // Obtener todo y asociaciones restantes
        // Setear
        // request.setAttribute("listaProductos", productos);
      }

      RequestDispatcher vista = request.getRequestDispatcher(redireccion);
      vista.forward(request, response);
    }
    catch (AuthenticationException ex) {
      RequestDispatcher vista = request.getRequestDispatcher("/index.jsp");
      vista.forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    request.setCharacterEncoding("UTF-8");
    boolean resultado = false;
    SubBodegaDAO dao = new SubBodegaDAO();

    String redireccion = "SubBodegas/Agregar.jsp";
    
    SubBodega sb = new SubBodega();
    
    sb.setNombre(request.getParameter("nombre"));
    
    Seccion seccion = new Seccion();
    Usuario usuario = new Usuario();
    
    seccion.setId_seccion(Integer.parseInt(request.getParameter("seccion")));
    usuario.setId_usuario(Integer.parseInt(request.getParameter("usuario")));
    
    sb.setSeccion(seccion);
    sb.setUsuario(usuario);

    String id = request.getParameter("id_sub_bodega");

    if (id.isEmpty() || id.equals("0")) {
      try {
        if (dao.insertar(sb) == 0) {
          resultado = true;
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
        resultado = false;
      }
    }
    else {
      // Tareas de sacar de cuarentena
    }

    if (resultado) {
      redireccion = "Ingresos/index.jsp";
      try {
        
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    RequestDispatcher vista = request.getRequestDispatcher(redireccion);
    vista.forward(request, response);
  }

  @Override
  public String getServletInfo()
  {
    return "Short description";
  }// </editor-fold>

  @Override
  protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
