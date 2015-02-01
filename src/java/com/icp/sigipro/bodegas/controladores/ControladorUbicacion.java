/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bodegas.dao.UbicacionDAO;
import com.icp.sigipro.bodegas.modelos.Ubicacion;
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
 * @author Walter
 */
@WebServlet(name = "ControladorUbicacion", urlPatterns = {"/Bodegas/Ubicaciones"})
public class ControladorUbicacion extends SIGIPROServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorUbicacion</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorUbicacion at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String redireccion = "";
            String accion = request.getParameter("accion");
            UbicacionDAO dao = new UbicacionDAO();
            HttpSession sesion = request.getSession();
            List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
            int[] permisos = {11, 12, 13};

            if (accion != null) {
                if (accion.equalsIgnoreCase("ver")) {
                    validarPermisos(permisos, listaPermisos);
                    redireccion = "Ubicaciones/Ver.jsp";
                    int id_ubicacion = Integer.parseInt(request.getParameter("id_ubicacion"));
                    Ubicacion ubicacion = dao.obtenerUbicacion(id_ubicacion);
                    request.setAttribute("ubicacion", ubicacion);
                } else if (accion.equalsIgnoreCase("agregar")) {
                    validarPermiso(11, listaPermisos);
                    redireccion = "Ubicaciones/Agregar.jsp";
                    Ubicacion ubicacion = new Ubicacion();
                    request.setAttribute("ubicacion", ubicacion);
                    request.setAttribute("accion", "Agregar");
                } else if (accion.equalsIgnoreCase("eliminar")) {
                    validarPermiso(13, listaPermisos);
                    int id_ubicacion = Integer.parseInt(request.getParameter("id_ubicacion"));
                    dao.eliminarUbicacion(id_ubicacion);
                    redireccion = "Ubicaciones/index.jsp";
                    List<Ubicacion> ubicaciones = dao.obtenerUbicaciones();
                    request.setAttribute("listaUbicaciones", ubicaciones);
                } else if (accion.equalsIgnoreCase("editar")) {
                    validarPermiso(12, listaPermisos);
                    redireccion = "Ubicaciones/Editar.jsp";
                    int id_ubicacion = Integer.parseInt(request.getParameter("id_ubicacion"));
                    Ubicacion ubicacion = dao.obtenerUbicacion(id_ubicacion);
                    request.setAttribute("ubicacion", ubicacion);
                    request.setAttribute("accion", "Editar");
                } else {
                    validarPermisos(permisos, listaPermisos);
                    redireccion = "Ubicaciones/index.jsp";
                    List<Ubicacion> ubicaciones = dao.obtenerUbicaciones();
                    request.setAttribute("listaUbicaciones", ubicaciones);
                }
            } else {
                validarPermisos(permisos, listaPermisos);
                redireccion = "Ubicaciones/index.jsp";
                List<Ubicacion> ubicaciones = dao.obtenerUbicaciones();
                request.setAttribute("listaUbicaciones", ubicaciones);
            }

            RequestDispatcher vista = request.getRequestDispatcher(redireccion);
            vista.forward(request, response);
        } catch (AuthenticationException ex) {
            RequestDispatcher vista = request.getRequestDispatcher("/index.jsp");
            vista.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        boolean resultado = false;

        Ubicacion ubicacion = new Ubicacion();

        ubicacion.setNombre(request.getParameter("nombre"));
        ubicacion.setDescripcion(request.getParameter("descripcion"));

        UbicacionDAO dao = new UbicacionDAO();
        String id = request.getParameter("id_ubicacion");
        String redireccion;

        if (id.isEmpty() || id.equals("0")) {
            resultado = dao.insertarUbicacion(ubicacion);
            redireccion = "Ubicaciones/Agregar.jsp";
        } else {
            ubicacion.setId_ubicacion(Integer.parseInt(id));
            resultado = dao.editarUbicacion(ubicacion);
            redireccion = "Ubicaciones/Editar.jsp";
        }

        if (resultado) {
            redireccion = String.format("Ubicaciones/Ver.jsp", id);
        }

        request.setAttribute("ubicacion", ubicacion);
        RequestDispatcher vista = request.getRequestDispatcher(redireccion);
        vista.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
