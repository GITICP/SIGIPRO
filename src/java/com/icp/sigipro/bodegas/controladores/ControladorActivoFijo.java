/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bodegas.dao.ActivoFijoDAO;
import com.icp.sigipro.bodegas.dao.UbicacionDAO;
import com.icp.sigipro.bodegas.modelos.ActivoFijo;
import com.icp.sigipro.bodegas.modelos.Ubicacion;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.SeccionDAO;
import com.icp.sigipro.seguridad.modelos.Seccion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ControladorActivoFijo", urlPatterns = {"/Bodegas/ActivosFijos"})
public class ControladorActivoFijo extends SIGIPROServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorActivoFijo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorActivoFijo at " + request.getContextPath() + "</h1>");
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
            SeccionDAO sec = new SeccionDAO();
            List<Seccion> secciones = sec.obtenerSecciones();
            UbicacionDAO ubi = new UbicacionDAO();
            List<Ubicacion> ubicaciones = ubi.obtenerUbicaciones();
            ActivoFijoDAO dao = new ActivoFijoDAO();
            HttpSession sesion = request.getSession();
            List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
            int[] permisos = {11, 12, 13};

            if (accion != null) {
                if (accion.equalsIgnoreCase("ver")) {
                    validarPermisos(permisos, listaPermisos);
                    redireccion = "ActivosFijos/Ver.jsp";
                    int id_activo_fijo = Integer.parseInt(request.getParameter("id_activo_fijo"));
                    ActivoFijo activofijo = dao.obtenerActivoFijo(id_activo_fijo);
                    request.setAttribute("activofijo", activofijo);
                } else if (accion.equalsIgnoreCase("agregar")) {
                    validarPermiso(11, listaPermisos);
                    redireccion = "ActivosFijos/Agregar.jsp";
                    ActivoFijo activofijo = new ActivoFijo();
                    request.setAttribute("secciones", secciones);
                    request.setAttribute("ubicaciones", ubicaciones);
                    request.setAttribute("activofijo", activofijo);
                    request.setAttribute("accion", "Agregar");
                } else if (accion.equalsIgnoreCase("eliminar")) {
                    validarPermiso(13, listaPermisos);
                    int id_activo_fijo = Integer.parseInt(request.getParameter("id_activo_fijo"));
                    dao.eliminarActivoFijo(id_activo_fijo);
                    redireccion = "ActivosFijos/index.jsp";
                    List<ActivoFijo> activosfijos = dao.obtenerActivosFijos();
                    request.setAttribute("listaActivosFijos", activosfijos);
                } else if (accion.equalsIgnoreCase("editar")) {
                    validarPermiso(12, listaPermisos);
                    redireccion = "ActivosFijos/Editar.jsp";
                    int id_ubicacion = Integer.parseInt(request.getParameter("id_ubicacion"));
                    //Ubicacion ubicacion = dao.obtenerUbicacion(id_ubicacion);
                    //request.setAttribute("ubicacion", ubicacion);
                    request.setAttribute("accion", "Editar");
                } else {
                    validarPermisos(permisos, listaPermisos);
                    redireccion = "ActivosFijos/index.jsp";
                    List<ActivoFijo> activosfijos = dao.obtenerActivosFijos();
                    request.setAttribute("listaActivosFijos", activosfijos);
                }
            } else {
                validarPermisos(permisos, listaPermisos);
                redireccion = "ActivosFijos/index.jsp";
                List<ActivoFijo> activosfijos = dao.obtenerActivosFijos();
                request.setAttribute("listaActivosFijos", activosfijos);
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

        ActivoFijo activofijo = new ActivoFijo();

        activofijo.setPlaca(Integer.parseInt(request.getParameter("placa")));
        activofijo.setEquipo(Integer.parseInt(request.getParameter("equipo")));
        activofijo.setMarca(request.getParameter("marca"));
        String fechamovimiento = request.getParameter("fecha_movimiento");
        activofijo.setFecha_movimiento(fechamovimiento);
        activofijo.setId_seccion(1);
        activofijo.setId_ubicacion(2);
        String fecharegistro = request.getParameter("fecha_registro");
        activofijo.setFecha_registro(fecharegistro);
        activofijo.setEstado(request.getParameter("estado"));

        ActivoFijoDAO dao = new ActivoFijoDAO();
        String id = request.getParameter("id_activo_fijo");
        String redireccion;

        if (id.isEmpty() || id.equals("0")) {
            resultado = dao.insertarActivoFijo(activofijo);
            redireccion = "ActivosFijos/Agregar.jsp";
        } else {
            activofijo.setId_activo_fijo(Integer.parseInt(id));
            resultado = dao.editarActivoFijo(activofijo);
            redireccion = "Ubicaciones/Editar.jsp";
        }

        if (resultado) {
            redireccion = String.format("ActivosFijos/Ver.jsp", id);
        }

        request.setAttribute("activofijo", activofijo);
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
