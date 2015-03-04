/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.configuracion.seccion;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.SIGIPROServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Walter
 */
@WebServlet(name = "AgregarSeccion", urlPatterns = {"/Configuracion/Secciones/Agregar"})
public class AgregarSeccion extends SIGIPROServlet
{

  private final int permiso = 8;

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
          throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {

      ServletContext context = this.getServletContext();
      context.getRequestDispatcher("/Configuracion/Secciones/Agregar.jsp").forward(request, response);

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
          throws ServletException, IOException
  {
    processRequest(request, response);
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
          throws ServletException, IOException
  {

    response.setContentType("text/html;charset=UTF-8");

    PrintWriter out;
    out = response.getWriter();
    request.setCharacterEncoding("UTF-8");
    try {
      String nombre_seccion;
      nombre_seccion = request.getParameter("nombre_seccion");
      String descripcion;
      descripcion = request.getParameter("descripcion");

      SeccionDAO s = new SeccionDAO();
      boolean nombre_valido = s.validarNombreSeccion(nombre_seccion, 0);
      if (nombre_valido) {
        
        Seccion seccion = new Seccion();
        seccion.setNombre_seccion(nombre_seccion);
        seccion.setDescripcion(descripcion);
        
        boolean insercionExitosa = s.insertarSeccion(seccion);
        
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(seccion.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SECCION,request.getRemoteAddr());
        //*----------------------------*

        if (insercionExitosa) {
          request.setAttribute("mensaje", "<div class=\"alert alert-success alert-dismissible\" role=\"alert\">"
                                          + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                          + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                          + "Sección ingresada correctamente."
                                          + "</div>");
        }
        else {
          request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                                        + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                        + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                        + "La sección no pudo ser ingresada."
                                        + "</div>");
        }

      }
      else {
        request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                                        + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                                        + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                                        + "Ya existe una sección con el nombre ingresado."
                                        + "</div>");
      }
      request.getRequestDispatcher("/Configuracion/Secciones/").forward(request, response);

    }
    finally {
      out.close();
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
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
