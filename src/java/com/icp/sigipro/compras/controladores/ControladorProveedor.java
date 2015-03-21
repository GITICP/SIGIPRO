/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.compras.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.compras.dao.ProveedorDAO;
import com.icp.sigipro.compras.modelos.Proveedor;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
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
@WebServlet(name = "ControladorProveedor", urlPatterns = {"/Compras/Proveedores"})
public class ControladorProveedor extends SIGIPROServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControllerProveedor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControllerProveedor at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        try {
            String redireccion = "";
            String accion = request.getParameter("accion");

            ProveedorDAO p = new ProveedorDAO();
            HttpSession sesion = request.getSession();
            List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
            int[] permisos = {14, 15, 16};
            if (accion != null) {
                if (accion.equalsIgnoreCase("ver")) {
                    validarPermisos(permisos, listaPermisos);
                    redireccion = "Proveedores/Ver.jsp";
                    int id_proveedor = Integer.parseInt(request.getParameter("id_proveedor"));
                    Proveedor proveedor = p.obtenerProveedor(id_proveedor);
                    request.setAttribute("proveedor", proveedor);
                }
                else if (accion.equalsIgnoreCase("agregar")) {
                    validarPermiso(14, listaPermisos);
                    redireccion = "Proveedores/Agregar.jsp";
                    Proveedor proveedor = new Proveedor();
                    request.setAttribute("proveedor", proveedor);
                    request.setAttribute("accion", "Agregar");
                }
                else if (accion.equalsIgnoreCase("eliminar")) {
                    validarPermiso(15, listaPermisos);
                    int id_proveedor = Integer.parseInt(request.getParameter("id_proveedor"));
                    try {
                        p.eliminarProveedor(id_proveedor);

                        //Funcion que genera la bitacora
                        BitacoraDAO bitacora = new BitacoraDAO();
                        bitacora.setBitacora(id_proveedor, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PROVEEDOR, request.getRemoteAddr());
            //*----------------------------*

                        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                        request.setAttribute("mensaje", helper.mensajeDeExito("Proveedor eliminado correctamente"));
                    }
                    catch (SIGIPROException sig_ex) {
                        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                        request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
                    }
                    redireccion = "Proveedores/index.jsp";
                    request.setAttribute("proveedores", p.obtenerProveedores());
                }
                else {
                    validarPermiso(16, listaPermisos);
                    redireccion = "Proveedores/Editar.jsp";
                    int id_proveedor = Integer.parseInt(request.getParameter("id_proveedor"));
                    Proveedor proveedor = p.obtenerProveedor(id_proveedor);
                    request.setAttribute("proveedor", proveedor);
                    request.setAttribute("accion", "Editar");
                }
            }
            else {
                validarPermisos(permisos, listaPermisos);
                redireccion = "Proveedores/index.jsp";
                request.setAttribute("proveedores", p.obtenerProveedores());
            }

            RequestDispatcher vista = request.getRequestDispatcher(redireccion);
            vista.forward(request, response);
        }
        catch (AuthenticationException ex) {
            RequestDispatcher vista = request.getRequestDispatcher("/index.jsp");
            vista.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        boolean resultado = false;

        Proveedor proveedor = new Proveedor();

        proveedor.setNombre_proveedor(request.getParameter("nombreProveedor"));
        proveedor.setCorreo(request.getParameter("correo"));
        proveedor.setTelefono1(request.getParameter("telefono1"));
        proveedor.setTelefono2(request.getParameter("telefono2"));
        proveedor.setTelefono3(request.getParameter("telefono3"));

        ProveedorDAO p = new ProveedorDAO();
        String id = request.getParameter("id_proveedor");
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        String redireccion;

        if (id == null || id.isEmpty() || "0".equals(id)) {
            resultado = p.insertarProveedor(proveedor);
            request.setAttribute("mensaje", helper.mensajeDeExito("Proveedor ingresado correctamente"));
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(proveedor.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PROVEEDOR, request.getRemoteAddr());
            //*----------------------------*
            redireccion = "Proveedores/Agregar.jsp";
        }
        else {
            proveedor.setId_proveedor(Integer.parseInt(id));
            resultado = p.editarProveedor(proveedor);
            request.setAttribute("mensaje", helper.mensajeDeExito("Proveedor editado correctamente"));

            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(proveedor.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PROVEEDOR, request.getRemoteAddr());
            //*----------------------------*
            redireccion = "Proveedores/Editar.jsp";
        }
        if (resultado) {
            redireccion = "Proveedores/index.jsp";
            request.setAttribute("proveedores", p.obtenerProveedores());
        }

        else {
            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar la solicitud"));
        }
        RequestDispatcher vista = request.getRequestDispatcher(redireccion);
        vista.forward(request, response);

    }

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
