/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bodegas.dao.ProductoExternoDAO;
import com.icp.sigipro.bodegas.modelos.ProductoExterno;
import com.icp.sigipro.compras.dao.ProveedorDAO;
import com.icp.sigipro.compras.modelos.Proveedor;
import com.icp.sigipro.core.SIGIPROServlet;
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
 * @author Amed
 */
@WebServlet(name = "ControladorCatalogoExterno", urlPatterns = {"/Bodegas/CatalogoExterno"})
public class ControladorCatalogoExterno extends SIGIPROServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      String redireccion = "";
      String accion = request.getParameter("accion");
      ProductoExternoDAO dao = new ProductoExternoDAO();
      HttpSession sesion = request.getSession();
      List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
      int[] permisos = {18, 19, 20};

      if (accion != null) {
        if (accion.equalsIgnoreCase("ver")) {
          validarPermisos(permisos, listaPermisos);
          redireccion = "CatalogoExterno/Ver.jsp";
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          ProductoExterno producto = dao.obtenerProductoExterno(id_producto);
          request.setAttribute("producto", producto);
        }
        else if (accion.equalsIgnoreCase("agregar")) {
          validarPermiso(18, listaPermisos);
          redireccion = "CatalogoExterno/Agregar.jsp";
          ProductoExterno producto = new ProductoExterno();
          ProveedorDAO pr = new ProveedorDAO();
          List<Proveedor> proveedores = pr.obtenerProveedores();
          request.setAttribute("producto", producto);
          request.setAttribute("proveedores", proveedores);
          request.setAttribute("accion", "Agregar");
        }
        else if (accion.equalsIgnoreCase("eliminar")) {
          validarPermiso(20, listaPermisos);
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          dao.eliminarProductoExterno(id_producto);
          redireccion = "CatalogoExterno/index.jsp";
          List<ProductoExterno> productos = dao.obtenerProductos();
          request.setAttribute("listaProductos", productos);
        }
        else if (accion.equalsIgnoreCase("editar")) {
          validarPermiso(19, listaPermisos);
          redireccion = "CatalogoExterno/Editar.jsp";
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          ProductoExterno producto = dao.obtenerProductoExterno(id_producto);
          ProveedorDAO pr = new ProveedorDAO();
          List<Proveedor> proveedores = pr.obtenerProveedores();
          request.setAttribute("producto", producto);
          request.setAttribute("accion", "Editar");
          request.setAttribute("proveedores", proveedores);
        }
        else {
          validarPermisos(permisos, listaPermisos);
          redireccion = "CatalogoExterno/index.jsp";
          List<ProductoExterno> productos = dao.obtenerProductos();
          request.setAttribute("listaProductos", productos);
        }
      }
      else {
        validarPermisos(permisos, listaPermisos);
        redireccion = "CatalogoExterno/index.jsp";
        List<ProductoExterno> productos = dao.obtenerProductos();
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

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    boolean resultado = false;

    ProductoExterno produtcoExterno = new ProductoExterno();

    produtcoExterno.setProducto(request.getParameter("producto"));
    produtcoExterno.setCodigo_Externo(request.getParameter("codigoExterno"));
    produtcoExterno.setMarca(request.getParameter("marca"));
    produtcoExterno.setId_Proveedor(Integer.parseInt(request.getParameter("proveedor")));


    ProductoExternoDAO dao = new ProductoExternoDAO();
    String id = request.getParameter("id_producto");
    String redireccion;

    if (id.isEmpty() || id.equals("0")) {
      resultado = dao.insertarProductoExterno(produtcoExterno);
      redireccion = "CatalogoExterno/Agregar.jsp";
    }
    else {
      produtcoExterno.setId_producto_ext(Integer.parseInt(id));
      resultado = dao.editarProductoExterno(produtcoExterno);
      redireccion = "CatalogoExterno/Editar.jsp";
    }
    
    
    if (resultado) {
      redireccion = String.format("CatalogoExterno/Ver.jsp", id);
    }
    
    request.setAttribute("producto", produtcoExterno);
    RequestDispatcher vista = request.getRequestDispatcher(redireccion);
    vista.forward(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
  
   protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
