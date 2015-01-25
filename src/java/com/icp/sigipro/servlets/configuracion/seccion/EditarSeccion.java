/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.configuracion.seccion;

import com.icp.sigipro.basededatos.SingletonBD;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.core.SIGIPROServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author Walter
 */
@WebServlet(name = "EditarSeccion", urlPatterns = {"/Configuracion/Secciones/EditarSeccion"})
public class EditarSeccion extends SIGIPROServlet {

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
            
            String idSeccion = request.getParameter("id");
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditarSeccion</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>IdSeccion =  " + idSeccion + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
            throws ServletException, IOException {
       request.setCharacterEncoding("UTF-8");
       try
        {
            int idSeccion;
            idSeccion = Integer.parseInt(request.getParameter("editarIdSeccion"));
            String nombre;
            nombre = request.getParameter("editarNombre");
            String descripcion;
            descripcion = request.getParameter("editarDescripcion");
     
            
            SeccionDAO s = new SeccionDAO();
            
            boolean resultado = s.editarSeccion(idSeccion, nombre, descripcion);
            
            if(resultado)
            {
                request.setAttribute("mensaje", "<div class=\"alert alert-success alert-dismissible\" role=\"alert\">" +
                                                    "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n" +
                                                    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>" +
                                                        "Sección editada correctamente." +
                                                "</div>");
            }
            else
            {
                request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">" +
                                                    "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n" +
                                                    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>" +
                                                        "Sección no pudo ser editada." +
                                                "</div>");
            }
            request.getRequestDispatcher("/Configuracion/Secciones/").forward(request, response);
            
            
            
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        } 
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

  @Override
  protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}