/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.servlets.seguridad.puesto;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.PuestoDAO;
import com.icp.sigipro.seguridad.modelos.Puesto;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Walter
 */
@WebServlet(name = "EditarPuesto", urlPatterns = {"/Seguridad/Puestos/EditarPuesto"})
public class EditarPuesto extends SIGIPROServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String idPuesto = request.getParameter("id");
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditarPuesto</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditarPuesto at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            int idPuesto;
            idPuesto = Integer.parseInt(request.getParameter("editarIdPuesto"));
            String nombre;
            nombre = request.getParameter("editarNombre");
            String descripcion;
            descripcion = request.getParameter("editarDescripcion");

            PuestoDAO s = new PuestoDAO();

            boolean nombre_valido = s.validarNombrePuesto(nombre, idPuesto);
            if (nombre_valido) {
                
                Puesto puesto = new Puesto(idPuesto,nombre,descripcion);
                boolean resultado = s.editarPuesto(idPuesto, nombre, descripcion);
                
                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(puesto.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_PUESTO,request.getRemoteAddr());
                //*----------------------------*

                if (resultado) {
                    request.setAttribute("mensaje", "<div class=\"alert alert-success alert-dismissible\" role=\"alert\">"
                            + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                            + "Puesto editado correctamente."
                            + "</div>");
                } else {
                    request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                            + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                            + "Puesto no pudo ser editado."
                            + "</div>");
                }
                request.getRequestDispatcher("/Seguridad/Puestos/").forward(request, response);
            } else {
                request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                        + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                        + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                        + "Ya existe un puesto con el nombre ingresado."
                        + "</div>");
            }
            request.getRequestDispatcher("/Seguridad/Puestos/").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
