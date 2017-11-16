/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.ventas.controladores;
import com.icp.sigipro.core.SIGIPROException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.icp.sigipro.ventas.dao.Producto_OrdenDAO;
import com.icp.sigipro.ventas.modelos.Producto_Orden;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Josue
 */
@WebServlet(name = "ControladorObtenerProductosOrdenCompraAjax", urlPatterns = {"/productos_orden_compra"})
public class ControladorObtenerProductosOrdenCompraAjax extends HttpServlet {

    private ServletContext context;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
        //super.init(config); //To change body of generated methods, choose Tools | Templates.
    }
    
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
            out.println("<title>Servlet ControladorObtenerProductosOrdenCompraAjax</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorObtenerProductosOrdenCompraAjax at " + request.getContextPath() + "</h1>");
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
        HttpSession sesion = request.getSession();
        StringBuffer sb = new StringBuffer();
        Producto_OrdenDAO nDAO = new Producto_OrdenDAO();
        boolean hayNotificaciones = false;
        String id = request.getParameter("id");
        int id_orden = Integer.parseInt(id);
        try {
            List<Producto_Orden> resultado = nDAO.obtenerProductosOrden(id_orden);
            sb.append("<productos>");
            for (Producto_Orden n : resultado){
                sb.append("<producto>");                
                sb.append("<id>").append(n.getProducto().getId_producto()).append("</id>");
                sb.append("<nombre>").append(n.getProducto().getNombre()).append("</nombre>");
                sb.append("<cantidad>").append(n.getCantidad()).append("</cantidad>");
                sb.append("<fecha>").append(n.getFecha_S()).append("</fecha>");             
                sb.append("</producto>");
                hayNotificaciones = true;
            }
            sb.append("</productos>");
            if (hayNotificaciones) {
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } 
            else {
                //nothing to show
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (SIGIPROException ex) {
            Logger.getLogger(ControladorObtenerProductosIntencionDeVentaAjax.class.getName()).log(Level.SEVERE, null, ex);
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

}
