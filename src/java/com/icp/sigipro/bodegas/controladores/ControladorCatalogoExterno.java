/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.dao.ProductoExternoDAO;
import com.icp.sigipro.bodegas.dao.ProductoExterno_InternoDAO;
import com.icp.sigipro.bodegas.dao.ProductoInternoDAO;
import com.icp.sigipro.bodegas.modelos.ProductoExterno;
import com.icp.sigipro.bodegas.modelos.ProductoExternoInterno;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.compras.dao.ProveedorDAO;
import com.icp.sigipro.compras.modelos.Proveedor;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
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
      int[] permisos = {21, 22, 23};

      if (accion != null) {
        if (accion.equalsIgnoreCase("ver")) {
          validarPermisos(permisos, listaPermisos);
          redireccion = "CatalogoExterno/Ver.jsp";
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          ProductoExterno producto = dao.obtenerProductoExterno(id_producto);
          ProductoInternoDAO PrIn = new ProductoInternoDAO();
          List<ProductoInterno> productos_internos = PrIn.obtenerProductosInternos_Externo(id_producto);
           request.setAttribute("productos_internos", productos_internos);
          request.setAttribute("producto", producto);
        }
        else if (accion.equalsIgnoreCase("agregar")) {
          validarPermiso(21, listaPermisos);
          redireccion = "CatalogoExterno/Agregar.jsp";
          ProductoExterno producto = new ProductoExterno();
          ProveedorDAO pr = new ProveedorDAO();
          List<Proveedor> proveedores = pr.obtenerProveedores();  
          ProductoInternoDAO PrIn = new ProductoInternoDAO();
          List<ProductoInterno> productos_internos = null;
          List<ProductoInterno> productos_internos_restantes = PrIn.obtenerProductosInternosRestantes(0);
          request.setAttribute("productos_internos", productos_internos);
          request.setAttribute("productos_internos_restantes", productos_internos_restantes);
          request.setAttribute("producto", producto);
          request.setAttribute("proveedores", proveedores);
          request.setAttribute("accion", "Agregar");
        }
        else if (accion.equalsIgnoreCase("eliminar")) {
          validarPermiso(23, listaPermisos);
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
          redireccion = "CatalogoExterno/index.jsp";
          
          try {
          dao.eliminarProductoExterno(id_producto);
          
          //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_producto,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATALOGOEXTERNO,InetAddress.getLocalHost().getHostName());
            //*----------------------------*

            request.setAttribute("mensaje", helper.mensajeDeExito("Producto del Catálogo Externo eliminado correctamente"));
          }
          catch (SIGIPROException ex)
          {  request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
          }
          List<ProductoExterno> productos = dao.obtenerProductos();
          request.setAttribute("listaProductos", productos);
        }
        else if (accion.equalsIgnoreCase("editar")) {
          validarPermiso(22, listaPermisos);
          redireccion = "CatalogoExterno/Editar.jsp";
          int id_producto = Integer.parseInt(request.getParameter("id_producto"));
          ProductoExterno producto = dao.obtenerProductoExterno(id_producto);
          ProveedorDAO pr = new ProveedorDAO();
          List<Proveedor> proveedores = pr.obtenerProveedores();
          ProductoInternoDAO PrIn = new ProductoInternoDAO();
          List<ProductoInterno> productos_internos = PrIn.obtenerProductosInternos_Externo(id_producto);
          List<ProductoInterno> productos_internos_restantes = PrIn.obtenerProductosInternosRestantes(id_producto);
          request.setAttribute("productos_internos", productos_internos);
          request.setAttribute("productos_internos_restantes", productos_internos_restantes);
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
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    ProductoExterno productoExterno = new ProductoExterno();

    productoExterno.setProducto(request.getParameter("producto"));
    productoExterno.setCodigo_Externo(request.getParameter("codigoExterno"));
    productoExterno.setMarca(request.getParameter("marca"));
    productoExterno.setId_Proveedor(Integer.parseInt(request.getParameter("proveedor")));
      

    ProductoExternoDAO dao = new ProductoExternoDAO();
    String id = request.getParameter("id_producto");
    String lista = request.getParameter("listaProductosInternos");
    String redireccion;

    if (id.isEmpty() || id.equals("0")) {
      resultado = dao.insertarProductoExterno(productoExterno);
      
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(productoExterno.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATALOGOEXTERNO,request.getRemoteAddr());
        //*----------------------------*
            
      redireccion = "CatalogoExterno/Agregar.jsp";
      request.setAttribute("mensaje", helper.mensajeDeExito("Producto del Catálogo Externo ingresado correctamente"));
    }
    else {
      productoExterno.setId_producto_ext(Integer.parseInt(id));
      resultado = dao.editarProductoExterno(productoExterno);
      
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(productoExterno.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATALOGOEXTERNO,request.getRemoteAddr());
        //*----------------------------*
        
      redireccion = "CatalogoExterno/Editar.jsp";
      request.setAttribute("mensaje", helper.mensajeDeExito("Producto del Catálogo Externo editado correctamente"));
    }

    if (resultado) {
      redireccion = "CatalogoExterno/index.jsp";
      List<ProductoExterno> productos = dao.obtenerProductos();
      request.setAttribute("listaProductos", productos);
      ProductoExterno_InternoDAO exin = new ProductoExterno_InternoDAO();
      exin.eliminarProductoExterno_Interno_Existentes(productoExterno.getId_producto_ext());
      List<ProductoExternoInterno> ExtInt = exin.parsearProductosExternos_Internos(lista, productoExterno.getId_producto_ext());
      boolean e = true;
      for (ProductoExternoInterno i : ExtInt) {
            e = exin.insertarProductoExterno_Interno(i);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(i.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATALOGOEXTERNOINTERNO,request.getRemoteAddr());
            //*----------------------------*
            if (!e){break;}
        }
      if (!e){request.setAttribute("mensaje", helper.mensajeDeAdvertencia("Producto del Catálogo Externo ingresado correctamente, pero sin Productos Internos Asociados"));}
    }
    else{
      request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
    }
    
    request.setAttribute("producto", productoExterno);
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
