/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.compras.controladores;

import com.icp.sigipro.compras.dao.ProveedorDAO;
import com.icp.sigipro.compras.modelos.Proveedor;
import com.icp.sigipro.core.SIGIPROServlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorProveedor", urlPatterns = {"/Compras/Proveedores"})
public class ControladorProveedor extends SIGIPROServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            throws ServletException, IOException {
        
        String redireccion = "";
        String accion = request.getParameter("accion");

        if (accion.equalsIgnoreCase("ver")) {
            redireccion= "Proveedores/Ver.jsp";
            int id_proveedor = Integer.parseInt(request.getParameter("id_proveedor"));
            ProveedorDAO p = new ProveedorDAO();
            Proveedor proveedor = p.obtenerProveedor(id_proveedor);
            request.setAttribute("proveedor", proveedor);
            //request.setAttribute("accion", "Editar");
            /*int userId = Integer.parseInt(request.getParameter("userId"));
            dao.deleteUser(userId);
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());*/
        } else if (accion.equalsIgnoreCase("agregar")) {
            redireccion = "Proveedores/Agregar.jsp";
            Proveedor proveedor = new Proveedor();
            request.setAttribute("proveedor", proveedor);
            request.setAttribute("accion", "Agregar");
        } else if (accion.equalsIgnoreCase("eliminar")) {
            int id_proveedor = Integer.parseInt(request.getParameter("id_proveedor"));
            ProveedorDAO p = new ProveedorDAO();
            p.eliminarProveedor(id_proveedor);
            redireccion= "Proveedores/index.jsp";
            request.setAttribute("proveedores", p.obtenerProveedores());
            //request.setAttribute("accion", "Editar");            
            /*
                        int userId = Integer.parseInt(request.getParameter("userId"));
            dao.deleteUser(userId);
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());*/
        } else {
            redireccion= "Proveedores/Editar.jsp";
            int id_proveedor = Integer.parseInt(request.getParameter("id_proveedor"));
            ProveedorDAO p = new ProveedorDAO();
            Proveedor proveedor = p.obtenerProveedor(id_proveedor);
            request.setAttribute("proveedor", proveedor);
            request.setAttribute("accion", "Editar");


            /*editar
            /*forward = INSERT_OR_EDIT; 
            forward = INSERT_OR_EDIT;
            int userId = Integer.parseInt(request.getParameter("userId"));
            User user = dao.getUserById(userId);
            request.setAttribute("user", user);*/
        }

        RequestDispatcher vista = request.getRequestDispatcher(redireccion);
        vista.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        
        if ( id == null || id.isEmpty() || "0".equals(id) )
        {
            resultado = p.insertarProveedor(proveedor);
        }
        else
        {
            proveedor.setId_proveedor(Integer.parseInt(id));
            resultado = p.editarProveedor(proveedor);
        }
        if(resultado)
        {
            RequestDispatcher vista = request.getRequestDispatcher("/Compras/Proveedores/index.jsp");
            vista.forward(request, response);
        }
        else
        {
            RequestDispatcher vista = request.getRequestDispatcher("/Fallo");
            vista.forward(request, response);
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
