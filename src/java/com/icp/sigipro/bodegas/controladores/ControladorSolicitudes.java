/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bodegas.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.dao.InventarioDAO;
import com.icp.sigipro.bodegas.dao.SolicitudDAO;
import com.icp.sigipro.bodegas.dao.SubBodegaDAO;
import com.icp.sigipro.bodegas.modelos.Inventario;
import com.icp.sigipro.bodegas.modelos.Solicitud;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
 * @author Amed
 */
@WebServlet(name = "ControladorSolicitudes", urlPatterns = {"/Bodegas/Solicitudes"})
public class ControladorSolicitudes extends SIGIPROServlet
{

    SubBodegaDAO sb_dao = new SubBodegaDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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
            throws ServletException, IOException
    {
        try {
            String redireccion = "";
            String accion = request.getParameter("accion");
            SolicitudDAO dao = new SolicitudDAO();
            HttpSession sesion = request.getSession();
            List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
            int[] permisos = {24, 25, 1};
            int usuario_solicitante;
            UsuarioDAO usrDAO = new UsuarioDAO();
            boolean boolAdmin = false;
            if (verificarPermiso(25, listaPermisos)) {
                usuario_solicitante = 0;
                boolAdmin = true;
            }
            else {
                String nombre_usr = (String) sesion.getAttribute("usuario");
                int id_usuario = usrDAO.obtenerIDUsuario(nombre_usr);
                Usuario us = usrDAO.obtenerUsuario(id_usuario);
                usuario_solicitante = us.getIdSeccion();
            }

            if (accion != null) {
                validarPermisos(permisos, listaPermisos);
                if (accion.equalsIgnoreCase("ver")) {
                    redireccion = "Solicitudes/Ver.jsp";
                    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
                    Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
                    request.setAttribute("solicitud", solicitud);
                }
                else if (accion.equalsIgnoreCase("agregar")) {
                    redireccion = "Solicitudes/Agregar.jsp";
                    Solicitud solicitud = new Solicitud();
                    InventarioDAO inventarioDAO = new InventarioDAO();
                    Usuario usr = usrDAO.obtenerUsuario((int) sesion.getAttribute("idusuario"));
                    List<Inventario> inventarios = inventarioDAO.obtenerInventarios(usr.getIdSeccion(), 1);
                    request.setAttribute("inventarios", inventarios);
                    request.setAttribute("solicitud", solicitud);
                    request.setAttribute("accion", "Agregar");
                }
                else if (accion.equalsIgnoreCase("eliminar")) {
                    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
                    dao.eliminarSolicitud(id_solicitud);

                    //Funcion que genera la bitacora
                    BitacoraDAO bitacora = new BitacoraDAO();
                    bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
                    //*----------------------------* 

                    redireccion = "Solicitudes/index.jsp";
                    List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
                    request.setAttribute("listaSolicitudes", solicitudes);
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                    request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud eliminada correctamente"));

                }
                else if (accion.equalsIgnoreCase("editar")) {
                    redireccion = "Solicitudes/Editar.jsp";
                    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
                    Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
                    Usuario usr = usrDAO.obtenerUsuario(usrDAO.obtenerIDUsuario((String) sesion.getAttribute("usuario")));
                    InventarioDAO inventarioDAO = new InventarioDAO();
                    List<Inventario> inventarios = inventarioDAO.obtenerInventarios(usr.getIdSeccion(), 1);
                    request.setAttribute("inventarios", inventarios);
                    request.setAttribute("solicitud", solicitud);
                    request.setAttribute("accion", "Editar");
                }
                else if (accion.equalsIgnoreCase("aprobar")) {
                    redireccion = "Solicitudes/index.jsp";
                    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
                    Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

                    if (solicitud.getCantidad() <= solicitud.getInventario().getStock_actual()) {
                        solicitud.setEstado("Aprobada");
                        boolean resta;
                        InventarioDAO inventarioDAO = new InventarioDAO();
                        resta = inventarioDAO.restarInventario(solicitud.getId_inventario(), -(solicitud.getCantidad()));
                        boolean resultado;
                        resultado = dao.editarSolicitud(solicitud);

                        //Funcion que genera la bitacora
                        BitacoraDAO bitacora = new BitacoraDAO();
                        bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
                        //*----------------------------* 

                        if (resultado && resta) {
                            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud aprobada"));
                        }
                        else {
                            request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
                        }
                    }
                    else {
                        request.setAttribute("mensaje", helper.mensajeDeError("No se puede aprobar la solicitud, la cantidad solicitada es mayor a las existencias del producto"));
                    }

                    List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
                    List<SubBodega> lista_sub_bodegas = new ArrayList<SubBodega>();
                    try {
                        lista_sub_bodegas = sb_dao.obtenerSubBodegas();
                    }
                    catch (SIGIPROException sig_ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
                    }
                    request.setAttribute("lista_sub_bodegas", lista_sub_bodegas);
                    request.setAttribute("booladmin", boolAdmin);
                    request.setAttribute("listaSolicitudes", solicitudes);
                }
                else if (accion.equalsIgnoreCase("cerrar")) {
                    redireccion = "Solicitudes/index.jsp";
                    int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
                    Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                    boolean resta;
                    InventarioDAO inventarioDAO = new InventarioDAO();
                    resta = inventarioDAO.restarInventario(solicitud.getId_inventario(), solicitud.getCantidad());
                    solicitud.setEstado("Cerrada");
                    boolean resultado;
                    resultado = dao.editarSolicitud(solicitud);

                    //Funcion que genera la bitacora
                    BitacoraDAO bitacora = new BitacoraDAO();
                    bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
                    //*----------------------------* 

                    if (resultado && resta) {
                        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud cerrada"));
                    }
                    else {
                        request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
                    }
                    List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
                    List<SubBodega> lista_sub_bodegas = new ArrayList<SubBodega>();
                    try {
                        lista_sub_bodegas = sb_dao.obtenerSubBodegas();
                    }
                    catch (SIGIPROException sig_ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
                    }
                    request.setAttribute("lista_sub_bodegas", lista_sub_bodegas);
                    request.setAttribute("booladmin", boolAdmin);
                    request.setAttribute("listaSolicitudes", solicitudes);
                }
                else {
                    validarPermisos(permisos, listaPermisos);
                    redireccion = "Solicitudes/index.jsp";
                    List<SubBodega> lista_sub_bodegas = new ArrayList<SubBodega>();
                    try {
                        lista_sub_bodegas = sb_dao.obtenerSubBodegas();
                    }
                    catch (SIGIPROException sig_ex) {
                        request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
                    }
                    List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
                    request.setAttribute("booladmin", boolAdmin);
                    request.setAttribute("listaSolicitudes", solicitudes);
                    request.setAttribute("lista_sub_bodegas", lista_sub_bodegas);
                }
            }
            else {
                validarPermisos(permisos, listaPermisos);
                redireccion = "Solicitudes/index.jsp";
                List<SubBodega> lista_sub_bodegas = new ArrayList<SubBodega>();
                try {
                    lista_sub_bodegas = sb_dao.obtenerSubBodegas();
                }
                catch (SIGIPROException sig_ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
                }
                List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
                request.setAttribute("booladmin", boolAdmin);
                request.setAttribute("listaSolicitudes", solicitudes);
                request.setAttribute("lista_sub_bodegas", lista_sub_bodegas);
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

        String redireccion;
        SolicitudDAO dao = new SolicitudDAO();
        UsuarioDAO usrDAO = new UsuarioDAO();
        boolean boolAdmin = false;
        int usuario_solicitante;
        HttpSession sesion = request.getSession();
        List<Integer> listaPermisos = (List<Integer>) sesion.getAttribute("listaPermisos");
        int[] permisos = {24, 25, 1};
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (verificarPermiso(25, listaPermisos)) {
            boolAdmin = true;
            usuario_solicitante = 0;
        }
        else {
            String nombre_usr = (String) sesion.getAttribute("usuario");
            int id_usuario = usrDAO.obtenerIDUsuario(nombre_usr);
            Usuario us = usrDAO.obtenerUsuario(id_usuario);
            usuario_solicitante = us.getIdSeccion();
        }
        String accionindex = request.getParameter("accionindex");
        if (accionindex.equals("")) {
            request.setCharacterEncoding("UTF-8");
            boolean resultado = false;
            Solicitud solicitud = new Solicitud();
            Integer id_inventario;
            try {
                id_inventario = Integer.parseInt(request.getParameter("seleccioninventario"));
            }
            catch (java.lang.NumberFormatException e) {
                id_inventario = 0;
            }
            Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String estado = request.getParameter("estado");
            String fch_sol = request.getParameter("fecha_solicitud");
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fecha_solicitud;
            java.sql.Date fecha_solicitudSQL;
            try {
                fecha_solicitud = formatoFecha.parse(fch_sol);
                fecha_solicitudSQL = new java.sql.Date(fecha_solicitud.getTime());
                solicitud.setFecha_solicitud(fecha_solicitudSQL);
            }
            catch (ParseException ex) {
                Logger.getLogger(ControladorSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
            }

            solicitud.setId_inventario(id_inventario);
            solicitud.setCantidad(cantidad);
            solicitud.setEstado(estado);

            String id = request.getParameter("id_solicitud");

            if (id.isEmpty() || id.equals("0")) {
                java.util.Date hoy = new java.util.Date();
                Date hoysql = new Date(hoy.getTime());
                String nombre_usr = (String) sesion.getAttribute("usuario");
                int usuario_solicitante2 = usrDAO.obtenerIDUsuario(nombre_usr);
                solicitud.setId_usuario(usuario_solicitante2);
                solicitud.setFecha_solicitud(hoysql);
                solicitud.setEstado("Pendiente");
                resultado = dao.insertarSolicitud(solicitud);

                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
                //*----------------------------*

                redireccion = "Solicitudes/Agregar.jsp";
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud ingresada correctamente"));
            }
            else {
                Integer id_us = Integer.parseInt(request.getParameter("id_usuario"));
                solicitud.setId_usuario(id_us);
                solicitud.setId_solicitud(Integer.parseInt(id));
                resultado = dao.editarSolicitud(solicitud);

                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
                //*----------------------------*        

                redireccion = "Solicitudes/Editar.jsp";
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editada correctamente"));
            }

            if (resultado) {
                redireccion = "Solicitudes/index.jsp";
                request.setAttribute("booladmin", boolAdmin);
                List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
                request.setAttribute("listaSolicitudes", solicitudes);
                List<SubBodega> lista_sub_bodegas = new ArrayList<SubBodega>();
                try {
                    lista_sub_bodegas = sb_dao.obtenerSubBodegas();
                }
                catch (SIGIPROException sig_ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
                }
                request.setAttribute("lista_sub_bodegas", lista_sub_bodegas);
            }
            else {
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }

            request.setAttribute("solicitud", solicitud);
            RequestDispatcher vista = request.getRequestDispatcher(redireccion);
            vista.forward(request, response);
        }

        else if (accionindex.equals("accionindex_rechazar")) {
            redireccion = "Solicitudes/index.jsp";
            String obs = request.getParameter("observaciones");
            int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_rech"));
            Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
            solicitud.setEstado("Rechazada");
            solicitud.setObservaciones(obs);
            boolean resultado;
            resultado = dao.editarSolicitud(solicitud);

            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_RECHAZAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
            //*----------------------------*
            if (resultado) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud rechazada"));
            }
            else {
                request.setAttribute("mensaje", helper.mensajeDeError("Ocurrió un error al procesar su petición"));
            }

            List<SubBodega> lista_sub_bodegas = new ArrayList<SubBodega>();
            try {
                lista_sub_bodegas = sb_dao.obtenerSubBodegas();
            }
            catch (SIGIPROException sig_ex) {
                request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
            }
            request.setAttribute("lista_sub_bodegas", lista_sub_bodegas);

            List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
            request.setAttribute("booladmin", boolAdmin);
            request.setAttribute("listaSolicitudes", solicitudes);
            RequestDispatcher vista = request.getRequestDispatcher(redireccion);
            vista.forward(request, response);

        }
        else {
            redireccion = "Solicitudes/index.jsp";
            String usuario = request.getParameter("usr");
            String contrasena = request.getParameter("passw");
            String ids = request.getParameter("ids-por-entregar");
            // int id_seccion = solicitud.getUsuario().getIdSeccion();
            // boolean auth = usrDAO.AutorizarRecibo(usuario, contrasena, id_seccion);
            if (ids == "") {
                request.setAttribute("mensaje", helper.mensajeDeAdvertencia("Debe seleccionar al menos una solicitud"));
            }
            else {
                boolean auth = usrDAO.AutorizarRecibo(usuario, contrasena);
                if (auth) {
                    int id_us_recibo = usrDAO.obtenerIDUsuario(usuario);
                    boolean resultado = false;
                    try {
                        if (request.getParameter("entrega_sub_bodega") == null) {
                            resultado = dao.entregarMasivo(ids, id_us_recibo, null, 0);
                        }
                        else {
                            String[] ids_parseados = parsearAsociacion("#af#", ids);
                            HashMap fechas_vencimiento = new HashMap(ids_parseados.length);
                            int id_sub_bodega = Integer.parseInt(request.getParameter("sub_bodega"));
                            for (String id : ids_parseados) {
                                String fecha = request.getParameter("fecha_vencimiento_" + id);
                                fechas_vencimiento.put(id, fecha);
                            }
                            resultado = dao.entregarMasivo(ids, id_us_recibo, fechas_vencimiento, id_sub_bodega);
                        }

                        //Funcion que genera la bitacora
                        //BitacoraDAO bitacora = new BitacoraDAO();
                        // bitacora.setBitacora(solicitud.parseJSON(),Bitacora.ACCION_ENTREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUD,request.getRemoteAddr());
                        //*----------------------------*
                    }
                    catch (SIGIPROException ex) {
                        Logger.getLogger(ControladorSolicitudes.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
                    }

                    if (resultado) {
                        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitudes entregadas"));
                        //Funcion que genera la bitacora
                        //bitacora.setBitacora(inventario.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_INVENTARIO,request.getRemoteAddr());
                        //*----------------------------*

                    }
                }
                else { //request.setAttribute("mensaje_auth", helper.mensajeDeError("El usuario o contraseña son incorrectos, o el usuario no pertenece a la sección solicitante."));
                    request.setAttribute("mensaje", helper.mensajeDeError("El usuario o contraseña son incorrectos"));
                    //request.setAttribute("show_modal_auth", true);

                }
            }
            List<Solicitud> solicitudes = dao.obtenerSolicitudes(usuario_solicitante);
            List<SubBodega> lista_sub_bodegas = new ArrayList<SubBodega>();
                try {
                    lista_sub_bodegas = sb_dao.obtenerSubBodegas();
                }
                catch (SIGIPROException sig_ex) {
                    request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
                }
                request.setAttribute("lista_sub_bodegas", lista_sub_bodegas);
            request.setAttribute("booladmin", boolAdmin);
            request.setAttribute("listaSolicitudes", solicitudes);
            RequestDispatcher vista = request.getRequestDispatcher(redireccion);
            vista.forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

    protected int getPermiso()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String[] parsearAsociacion(String pivote, String asociacionesCodificadas)
    {
        String[] idsTemp = asociacionesCodificadas.split(pivote);
        return Arrays.copyOfRange(idsTemp, 1, idsTemp.length);
    }
}
