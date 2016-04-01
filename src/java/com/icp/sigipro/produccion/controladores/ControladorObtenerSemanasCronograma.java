/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;
import com.icp.sigipro.notificaciones.controladores.*;
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
import com.icp.sigipro.produccion.dao.Semanas_cronogramaDAO;
import com.icp.sigipro.produccion.modelos.Semanas_cronograma;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Josue
 */
@WebServlet(name = "ControladorObtenerSemanasCronograma", urlPatterns = {"/semanas_cronograma"})
public class ControladorObtenerSemanasCronograma extends HttpServlet {

    private ServletContext context;
    private final Semanas_cronogramaDAO sDAO = new Semanas_cronogramaDAO();
    
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
            out.println("<title>Servlet ControladorObtenerSemanasCronograma</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorObtenerSemanasCronograma at " + request.getContextPath() + "</h1>");
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
        StringBuffer sb = new StringBuffer();
        String id = request.getParameter("id_cronograma");
        try {
            List<Semanas_cronograma> resultado = sDAO.obtenerSemanas_cronograma(Integer.parseInt(id));
            sb.append("<semanas>");
            for (Semanas_cronograma s : resultado){
                sb.append("<semana>");
                sb.append("<id_semana>").append(s.getId_semana()).append("</id_semana>");
                sb.append("<id_cronograma>").append(s.getCronograma().getId_cronograma()).append("</id_cronograma>");
                sb.append("<fecha>").append(s.getFecha_S()).append("</fecha>");
                sb.append("<sangria>").append(s.getSangria()).append("</sangria>");
                sb.append("<plasma_proyectado>").append(s.getPlasma_proyectado()).append("</plasma_proyectado>");
                sb.append("<plasma_real>").append(s.getPlasma_proyectado()).append("</plasma_real>");
                sb.append("<antivenenos_lote>").append(s.getAntivenenos_lote()).append("</antivenenos_lote>");
                sb.append("<antivenenos_proyectada>").append(s.getAntivenenos_proyectada()).append("</antivenenos_proyectada>");
                sb.append("<antivenenos_bruta>").append(s.getAntivenenos_bruta()).append("</antivenenos_bruta>");
                sb.append("<antivenenos_neta>").append(s.getAntivenenos_neta()).append("</antivenenos_neta>");
                sb.append("<entregas_cantidad>").append(s.getEntregas_cantidad()).append("</entregas_cantidad>");
                sb.append("<entregas_destino>").append(s.getEntregar_destino()).append("</entregas_destino>");
                sb.append("<entregas_lote>").append(s.getEntregas_lote()).append("</entregas_lote>");
                sb.append("<observaciones>").append(s.getObservaciones()).append("</observaciones>");
                sb.append("</semana>");
            }
            sb.append("</semanas>");
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());
        }
        catch (SIGIPROException ex) {
            Logger.getLogger(ControladorObtenerNuevasNotificaciones.class.getName()).log(Level.SEVERE, null, ex);
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
