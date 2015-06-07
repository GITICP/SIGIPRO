/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.ConejoProduccionDAO;
import com.icp.sigipro.bioterio.modelos.ConejoProduccion;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorConejosProduccion", urlPatterns = {"/Conejera/ConejosProduccion"})
public class ControladorConejosProduccion extends SIGIPROServlet {
private final int[] permisos = {256, 1, 1};
    private final ConejoProduccionDAO dao = new ConejoProduccionDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    protected final Class clase = ControladorConejosProduccion.class;
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
            add("trasladar");
        }
    };
 
    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(251, listaPermisos);
        String redireccion = "ConejosProduccion/index.jsp";
        try {
            List<ConejoProduccion> machos = dao.obtenerConejosProduccion();
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
        String redireccion = "ConejosProduccion/Agregar.jsp";
        ConejoProduccion conejo = new ConejoProduccion();
        request.setAttribute("conejo", conejo);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "ConejosProduccion/Editar.jsp";
        int id_coneja = Integer.parseInt(request.getParameter("id_produccion"));
        request.setAttribute("accion", "Editar");
        try {
            ConejoProduccion conejo = dao.obtenerConejoProduccion(id_coneja);
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
        String redireccion = "ConejosProduccion/Ver.jsp";
        int id_coneja = Integer.parseInt(request.getParameter("id_produccion"));
        try {
            ConejoProduccion conejo = dao.obtenerConejoProduccion(id_coneja);
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
        String redireccion = "ConejosProduccion/Agregar.jsp";
        try {
            ConejoProduccion conejo = construirObjeto(request);

            dao.insertarConejoProduccion(conejo);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(conejo.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = "ConejosProduccion/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Grupo de conejos de producción agregado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<ConejoProduccion> machos = dao.obtenerConejosProduccion();
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
        String redireccion = "ConejosProduccion/Editar.jsp";
        try {
            ConejoProduccion conejo = construirObjeto(request);
            dao.editarConejoProduccion(conejo);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(conejo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = "ConejosProduccion/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Grupo de conejos de producción editado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<ConejoProduccion> machos = dao.obtenerConejosProduccion();
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
        int id_produccion = Integer.parseInt(request.getParameter("id_produccion"));
        String redireccion = "ConejosProduccion/index.jsp";
        ConejoProduccion conejo;
        try {
            conejo = dao.obtenerConejoProduccion(id_produccion);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            dao.eliminarConejoProduccion(id_produccion);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_produccion, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Grupo de conejos de producción eliminado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<ConejoProduccion> machos = dao.obtenerConejosProduccion();
            request.setAttribute("conejos", machos);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
    protected void postTrasladar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "ConejosProduccion/Ver.jsp";
        int id_produccion = Integer.parseInt(request.getParameter("id_produccion"));
        int cant_vivos = Integer.parseInt(request.getParameter("cant_vivos"));
        try {
            ConejoProduccion conejo = dao.obtenerConejoProduccion(id_produccion);
            if (cant_vivos <= conejo.getCantidad())
            { conejo.setMortalidad(conejo.getCantidad() - cant_vivos);
              dao.editarConejoProduccion(conejo);
              BitacoraDAO bitacora = new BitacoraDAO();
              bitacora.setBitacora(conejo.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());
              redireccion = "ConejosProduccion/index.jsp";
              request.setAttribute("mensaje", helper.mensajeDeExito("Grupo de conejos de producción trasladado correctamente."));
            }
            else
            {
              request.setAttribute("mensaje", helper.mensajeDeError("La cantidad de vivos ingresados es mayor a la cantidad de conejos en el grupo"));
            }
            request.setAttribute("conejo", conejo);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<ConejoProduccion> machos = dao.obtenerConejosProduccion();
            request.setAttribute("conejos", machos);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }
  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private ConejoProduccion construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        ConejoProduccion conejo = new ConejoProduccion();

        int id_produccion = Integer.parseInt(request.getParameter("id_produccion"));
        conejo.setId_produccion(id_produccion);
        conejo.setIdentificador(request.getParameter("identificador"));
        conejo.setDetalle_procedencia(request.getParameter("detalle_procedencia"));
        conejo.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
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
