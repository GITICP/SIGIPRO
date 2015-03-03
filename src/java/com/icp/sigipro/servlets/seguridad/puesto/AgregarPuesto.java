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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Walter
 */
@WebServlet(name = "AgregarPuesto", urlPatterns = {"/Seguridad/Puestos/Agregar"})
public class AgregarPuesto extends SIGIPROServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            ServletContext context = this.getServletContext();
            context.getRequestDispatcher("/Seguridad/Puestos/Agregar.jsp").forward(request, response);

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

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out;
        out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        try {
            String nombre_puesto;
            nombre_puesto = request.getParameter("nombre_puesto");
            String descripcion;
            descripcion = request.getParameter("descripcion");

            PuestoDAO p = new PuestoDAO();
            boolean nombre_valido = p.validarNombrePuesto(nombre_puesto, 0);
            if (nombre_valido) {
                
                Puesto puesto = new Puesto();
                
                puesto.setNombre_puesto(nombre_puesto);
                puesto.setDescripcion(descripcion);

                boolean insercionExitosa = p.insertarPuesto(puesto);
                
                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(puesto.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_PUESTO,request.getRemoteAddr());
                //*----------------------------*

                if (insercionExitosa) {
                    request.setAttribute("mensaje", "<div class=\"alert alert-success alert-dismissible\" role=\"alert\">"
                            + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                            + "Puesto ingresado correctamente."
                            + "</div>");
                } else {
                    request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                            + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                            + "El puesto no pudo ser ingresado."
                            + "</div>");
                }

            } else {
                request.setAttribute("mensaje", "<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                        + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                        + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                        + "Ya existe un puesto con el nombre ingresado."
                        + "</div>");
            }
            request.getRequestDispatcher("/Seguridad/Puestos/").forward(request, response);

        } finally {
            out.close();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
