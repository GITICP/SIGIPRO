/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bodegas.dao.ProductoInternoDAO;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Boga
 */
@WebServlet(name = "ControladorCatalogoInterno", urlPatterns = {"/Bodegas/CatalogoInterno"})
public class ControladorCatalogoInterno extends HttpServlet
{

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      /* TODO output your page here. You may use following sample code. */
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet ControladorCatalogoInterno</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet ControladorCatalogoInterno at " + request.getContextPath() + "</h1>");
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
      ProductoInternoDAO dao = new ProductoInternoDAO();
      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      int[] permisos = {11, 12, 13};

      if (accion != null) {
        if (accion.equalsIgnoreCase("ver")) {
          validarPermisos(permisos, listaPermisos);
          redireccion = "CatalogoInterno/Ver.jsp";
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          ProductoInterno producto = dao.obtenerProductoInterno(id_producto);
          request.setAttribute("producto", producto);
        }
        else if (accion.equalsIgnoreCase("agregar")) {
          validarPermiso(11, listaPermisos);
          redireccion = "CatalogoInterno/Agregar.jsp";
          ProductoInterno producto = new ProductoInterno();
          request.setAttribute("producto", producto);
          request.setAttribute("accion", "Agregar");
        }
        else if (accion.equalsIgnoreCase("eliminar")) {
          validarPermiso(13, listaPermisos);
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          dao.eliminarProductoInterno(id_producto);
          redireccion = "CatalogoInterno/index.jsp";
          request.setAttribute("listaProductos", dao.obtenerProductos());
        }
        else if (accion.equalsIgnoreCase("editar")) {
          validarPermiso(12, listaPermisos);
          redireccion = "CatalogoInterno/Editar.jsp";
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          ProductoInterno producto = dao.obtenerProductoInterno(id_producto);
          request.setAttribute("producto", producto);
          request.setAttribute("accion", "Editar");
        }
        else {
          validarPermisos(permisos, listaPermisos);
          redireccion = "CatalogoInterno/index.jsp";
          List<ProductoInterno> productos = dao.obtenerProductos();
          request.setAttribute("listaProductos", productos);
        }
      }
      else {
        validarPermisos(permisos, listaPermisos);
        redireccion = "CatalogoInterno/index.jsp";
        List<ProductoInterno> productos = dao.obtenerProductos();
        request.setAttribute("listaProductos", productos);
      }

      RequestDispatcher vista = request.getRequestDispatcher(redireccion);
      vista.forward(request, response);
    }
    catch (AuthenticationException ex){
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

    ProductoInterno productoInterno = new ProductoInterno();

    productoInterno.setNombre(request.getParameter("nombre"));
    productoInterno.setCodigo_icp(request.getParameter("codigoICP"));
    productoInterno.setStock_minimo(Integer.parseInt(request.getParameter("stockMinimo")));
    productoInterno.setStock_maximo(Integer.parseInt(request.getParameter("stockMaximo")));
    productoInterno.setUbicacion(request.getParameter("ubicacion"));
    productoInterno.setPresentacion(request.getParameter("presentacion"));
    productoInterno.setDescripcion(request.getParameter("descripcion"));

    ProductoInternoDAO dao = new ProductoInternoDAO();
    String id = request.getParameter("id_producto");
    String redireccion;

    if (id.isEmpty() || id.equals("0")) {
      resultado = dao.insertarProductoInterno(productoInterno);
      redireccion = "CatalogoInterno/index.jsp";

      List<ProductoInterno> productos = dao.obtenerProductos();
      request.setAttribute("listaProductos", productos);
    }
    else {
      productoInterno.setId_producto(Integer.parseInt(id));

      resultado = dao.editarProductoInterno(productoInterno);

      redireccion = String.format("CatalogoInterno/Ver.jsp", id);
      request.setAttribute("producto", productoInterno);
    }

    RequestDispatcher vista = request.getRequestDispatcher(redireccion);
    vista.forward(request, response);
  }

  @Override
  public String getServletInfo()
  {
    return "Short description";
  }

  private void validarPermiso(int permiso, List<Integer> permisosUsuario) throws AuthenticationException
  {
    if (!(permisosUsuario.contains(permiso) || permisosUsuario.contains(1))) {
      throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
    }
  }

  private void validarPermisos(int[] permisos, List<Integer> permisosUsuario) throws AuthenticationException
  {
    if ( !(permisosUsuario.contains(permisos[0]) || permisosUsuario.contains(permisos[1]) || permisosUsuario.contains(permisos[2]) || permisosUsuario.contains(1) ) ) {
      throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
    }
  }
}
