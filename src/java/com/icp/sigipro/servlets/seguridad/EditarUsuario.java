/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad;

import com.icp.sigipro.basededatos.SingletonBD;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Boga
 */
@WebServlet(name = "EditarUsuario", urlPatterns = {"/Seguridad/EditarUsuario"})
public class EditarUsuario extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditarUsuario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditarUsuario at " + request.getContextPath() + "</h1>");
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
        
        try
        {
            int idUsuario;
            idUsuario = Integer.parseInt(request.getParameter("editarIDUsuario"));
            String nomCompleto;
            nomCompleto = request.getParameter("editarNombreCompleto");
            String correo;
            correo = request.getParameter("editarCorreoElectronico");
            String cedula;
            cedula = request.getParameter("editarCedula");
            String departamento;
            departamento = request.getParameter("editarDepartamento");
            String puesto;
            puesto = request.getParameter("editarPuesto");
            String fechaActivacion;
            fechaActivacion = request.getParameter("editarFechaActivacion");
            String fechaDesactivacion;
            fechaDesactivacion = request.getParameter("editarFechaDesactivacion");
            
            SingletonBD s = SingletonBD.getSingletonBD();
            
            boolean resultado = s.editarUsuario(idUsuario, nomCompleto, correo, cedula, departamento, puesto, fechaActivacion, fechaDesactivacion);
            
            if(resultado)
            {
                request.setAttribute("mensaje", "<div class=\"alert alert-success alert-dismissible\" role=\"alert\">" +
                                                    "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n" +
                                                    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>" +
                                                        "Usuario editado correctamente." +
                                                "</div>");
            }
            else
            {
                request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">" +
                                                    "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n" +
                                                    "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>" +
                                                        "Usuario no pudo ser editado." +
                                                "</div>");
            }
            request.getRequestDispatcher("/Seguridad/Usuarios.jsp").forward(request, response);
            
            
            
        }
        catch (Exception ex)
        {
            String hola = "hola";
            hola.toUpperCase();
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

}
