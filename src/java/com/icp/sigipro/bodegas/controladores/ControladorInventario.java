/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bodegas.dao.InventarioDAO;
import com.icp.sigipro.bodegas.dao.ProductoInternoDAO;
import com.icp.sigipro.bodegas.modelos.Ingreso;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.bodegas.modelos.ProductoInterno;
import com.icp.sigipro.configuracion.dao.SeccionDAO;
import com.icp.sigipro.configuracion.modelos.Seccion;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
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
 * @author ld.conejo
 */
@WebServlet(name = "ControladorInventario", urlPatterns = {"/Bodegas/Inventarios"})
public class ControladorInventario extends SIGIPROServlet {

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
            out.println("<title>Servlet ControladorInventario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorInventario at " + request.getContextPath() + "</h1>");
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
        try {
            String redireccion = "";
            String mensaje = null;
            String explicacion = null;

            String accion = request.getParameter("accion");
            
            InventarioDAO dao = new InventarioDAO();
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

            HttpSession sesion = request.getSession();
            //Ocupamos definir permisos
            List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
            int permiso = 30;
            //-------------------------*

            if (accion != null) {
                if (accion.equalsIgnoreCase("ver")) {
                    validarPermiso(permiso, listaPermisos);
                    redireccion = "Inventarios/Ver.jsp";

                    int id = Integer.parseInt(request.getParameter("id_inventario"));

                    //try {
                        //No tiene implementada la parte de Boga porque no me funciono
                        Inventario i = dao.obtenerInventario(id);
                        request.setAttribute("inventario", i);
                    /*}
                    catch (SIGIPROException ex) {
                        mensaje = ex.getMessage();
                        request.setAttribute("mensaje", helper.mensajeDeError(mensaje));
                    }
                    catch (SQLException ex) {
                        mensaje = "Ha ocurrido un error con la base de datos. Favor inténtelo nuevamente y si el problema persiste contacte al administrador del sistema.";
                        request.setAttribute("mensaje", helper.mensajeDeError(mensaje + explicacion));
                        ex.printStackTrace();
                    }
                    catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
                        mensaje = "Ha ocurrido un error inesperado. Favor contacte al administrador del sistema.";
                        request.setAttribute("mensaje", helper.mensajeDeError(mensaje + explicacion));
                        ex.printStackTrace();
                    }*/
                }
                else {
                    validarPermiso(permiso, listaPermisos);
                    redireccion = "Inventarios/index.jsp";
                    try {
                        List<Inventario> inventario = dao.obtenerInventarios();
                        request.setAttribute("listaInventarios", inventario);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else {
                validarPermiso(permiso, listaPermisos);
                redireccion = "Inventarios/index.jsp";
                try {
                    //Funcion vieja, ya que me dio error el obtenerTodo del DAO
                    List<Inventario> inventarios = dao.obtenerInventarios();
                    request.setAttribute("listaInventarios", inventarios);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            RequestDispatcher vista = request.getRequestDispatcher(redireccion);
            vista.forward(request, response);
        }
        catch (AuthenticationException ex) {
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
        processRequest(request, response);
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
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
