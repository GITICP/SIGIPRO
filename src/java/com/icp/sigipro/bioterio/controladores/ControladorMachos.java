/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.MachoDAO;
import com.icp.sigipro.bioterio.modelos.Macho;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorMachos", urlPatterns = {"/Conejera/Machos"})
public class ControladorMachos extends SIGIPROServlet
{

    private final int[] permisos = {251, 1, 1};
    private final MachoDAO dao = new MachoDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    protected final Class clase = ControladorMachos.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
            add("eliminar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(251, listaPermisos);
        String redireccion = "Machos/index.jsp";
        try {
            List<Macho> machos = dao.obtenerMachos();
            request.setAttribute("conejos", machos);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);
    }

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(251, listaPermisos);
        String redireccion = "Machos/Agregar.jsp";
        Macho conejo = new Macho();
        request.setAttribute("conejo", conejo);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Machos/Editar.jsp";
        int id_coneja = Integer.parseInt(request.getParameter("id_macho"));
        request.setAttribute("accion", "Editar");
        try {
            Macho conejo = dao.obtenerMacho(id_coneja);
            request.setAttribute("conejo", conejo);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Machos/Ver.jsp";
        int id_coneja = Integer.parseInt(request.getParameter("id_macho"));
        try {
            Macho conejo = dao.obtenerMacho(id_coneja);
            request.setAttribute("conejo", conejo);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Machos/Agregar.jsp";
        try {
            Macho conejo = construirObjeto(request);

            dao.insertarMacho(conejo);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(conejo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = "Machos/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Macho agregado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<Macho> machos = dao.obtenerMachos();
            request.setAttribute("conejos", machos);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Machos/Editar.jsp";
        try {
            Macho conejo = construirObjeto(request);
            dao.editarMacho(conejo);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(conejo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = "Machos/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Macho editado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<Macho> machos = dao.obtenerMachos();
            request.setAttribute("conejos", machos);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        int id_macho = Integer.parseInt(request.getParameter("id_macho"));
        String redireccion = "Machos/index.jsp";
        Macho conejo;
        try {
            conejo = dao.obtenerMacho(id_macho);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            dao.eliminarMacho(id_macho);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_macho, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Macho eliminado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<Macho> machos = dao.obtenerMachos();
            request.setAttribute("conejos", machos);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Macho construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        Macho conejo = new Macho();

        int id_macho = Integer.parseInt(request.getParameter("id_macho"));
        conejo.setId_macho(id_macho);
        conejo.setIdentificacion(request.getParameter("identificacion"));
        conejo.setDescripcion(request.getParameter("descripcion"));
        String fecha_ingreso_str = request.getParameter("fecha_ingreso");
        String fecha_retiro_str = request.getParameter("fecha_retiro");
        String fecha_preseleccion_str = request.getParameter("fecha_preseleccion");
        conejo.setId_padre(request.getParameter("id_padre"));
        conejo.setId_madre(request.getParameter("id_madre"));

        try {
            HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();
            conejo.setFecha_preseleccion(helper_fechas.formatearFecha(fecha_preseleccion_str));
            conejo.setFecha_ingreso(helper_fechas.formatearFecha(fecha_ingreso_str));
            conejo.setFecha_retiro(helper_fechas.formatearFecha(fecha_retiro_str));
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }

        return conejo;
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        }
        else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
        else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
}
