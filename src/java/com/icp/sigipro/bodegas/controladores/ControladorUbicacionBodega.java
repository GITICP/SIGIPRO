/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.dao.UbicacionBodegaDAO;
import com.icp.sigipro.bodegas.modelos.UbicacionBodega;
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
@WebServlet(name = "ControladorUbicacionBodega", urlPatterns = {"/Bodegas/UbicacionesBodega"})
public class ControladorUbicacionBodega extends SIGIPROServlet {
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
            UbicacionBodegaDAO dao = new UbicacionBodegaDAO();
            HttpSession sesion = request.getSession();
            List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
            int[] permisos = {11, 12, 13};

            if (accion != null) {
                if (accion.equalsIgnoreCase("ver")) {
                    validarPermisos(permisos, listaPermisos);
                    redireccion = "UbicacionesBodega/Ver.jsp";
                    int id_ubicacion = Integer.parseInt(request.getParameter("id_ubicacion"));
                    UbicacionBodega ubicacion = dao.obtenerUbicacion(id_ubicacion);
                    request.setAttribute("ubicacion", ubicacion);
                } else if (accion.equalsIgnoreCase("agregar")) {
                    validarPermiso(11, listaPermisos);
                    redireccion = "UbicacionesBodega/Agregar.jsp";
                    UbicacionBodega ubicacion = new UbicacionBodega();
                    request.setAttribute("ubicacion", ubicacion);
                    request.setAttribute("accion", "Agregar");
                } else if (accion.equalsIgnoreCase("eliminar")) {
                    validarPermiso(13, listaPermisos);
                    int id_ubicacion = Integer.parseInt(request.getParameter("id_ubicacion"));
                    dao.eliminarUbicacion(id_ubicacion);
                    
                    //Funcion que genera la bitacora
                    BitacoraDAO bitacora = new BitacoraDAO();
                    bitacora.setBitacora(id_ubicacion,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_UBICACIONBODEGA,request.getRemoteAddr());
                    //*----------------------------*
                    
                    redireccion = "UbicacionesBodega/index.jsp";
                    List<UbicacionBodega> ubicaciones = dao.obtenerUbicaciones();
                    request.setAttribute("listaUbicaciones", ubicaciones);
                } else if (accion.equalsIgnoreCase("editar")) {
                    validarPermiso(12, listaPermisos);
                    redireccion = "UbicacionesBodega/Editar.jsp";
                    int id_ubicacion = Integer.parseInt(request.getParameter("id_ubicacion"));
                    UbicacionBodega ubicacion = dao.obtenerUbicacion(id_ubicacion);
                    request.setAttribute("ubicacion", ubicacion);
                    request.setAttribute("accion", "Editar");
                } else {
                    validarPermisos(permisos, listaPermisos);
                    redireccion = "UbicacionesBodega/index.jsp";
                    List<UbicacionBodega> ubicaciones = dao.obtenerUbicaciones();
                    request.setAttribute("listaUbicaciones", ubicaciones);
                }
            } else {
                validarPermisos(permisos, listaPermisos);
                redireccion = "UbicacionesBodega/index.jsp";
                List<UbicacionBodega> ubicaciones = dao.obtenerUbicaciones();
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

        UbicacionBodega ubicacion = new UbicacionBodega();

        ubicacion.setNombre(request.getParameter("nombre"));
        ubicacion.setDescripcion(request.getParameter("descripcion"));

        UbicacionBodegaDAO dao = new UbicacionBodegaDAO();
        String id = request.getParameter("id_ubicacion");
        String redireccion;

        if (id.isEmpty() || id.equals("0")) {
            resultado = dao.insertarUbicacion(ubicacion);
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(ubicacion.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_UBICACIONBODEGA,request.getRemoteAddr());
            //*----------------------------*
            
            redireccion = "UbicacionesBodega/Agregar.jsp";
        } else {
            ubicacion.setId_ubicacion(Integer.parseInt(id));
            resultado = dao.editarUbicacion(ubicacion);
            
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(ubicacion.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_UBICACIONBODEGA,request.getRemoteAddr());
            //*----------------------------*
            
            redireccion = "UbicacionesBodega/Editar.jsp";
        }

        if (resultado) {
            redireccion = String.format("UbicacionesBodega/Ver.jsp", id);
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
