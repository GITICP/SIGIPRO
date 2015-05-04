/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bioterio.controladores;

import com.icp.sigipro.bioterio.dao.AnalisisParasitologicoDAO;
import com.icp.sigipro.bioterio.modelos.AnalisisParasitologico;
import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperFechas;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amed
 */
@WebServlet(name = "ControladorAnalisisParasitologicos", urlPatterns = {"/Bioterio/AnalisisParasitologico"})
public class ControladorAnalisisParasitologicos extends SIGIPROServlet
{

    private int[] permisos = {206, 255, 0};
    private int permiso_por_buscar;
    private final AnalisisParasitologicoDAO dao = new AnalisisParasitologicoDAO();
    private final HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    private final String contexto = "/Bioterio/";
    private boolean especie_consultada;

    protected final Class clase = ControladorAnalisisParasitologicos.class;
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
        validarPermiso(permiso_por_buscar, listaPermisos);
        String redireccion = contexto + "AnalisisParasitologico/index.jsp";

        try {
            List<AnalisisParasitologico> analisis = dao.obtenerAnalisisParasitologicos(especie_consultada);
            request.setAttribute("lista_analisis", analisis);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }

        redireccionar(request, response, redireccion);
    }

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(permiso_por_buscar, listaPermisos);
        String redireccion = contexto + "AnalisisParasitologico/Agregar.jsp";
        UsuarioDAO usuarios_dao = new UsuarioDAO();
        try {
            List<Usuario> usuarios = usuarios_dao.obtenerUsuariosSeccion(4);
            request.setAttribute("usuarios", usuarios);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", sig_ex.getMessage());
        }

        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = contexto + "AnalisisParasitologico/Editar.jsp";
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        request.setAttribute("accion", "Editar");
        try {
            AnalisisParasitologico analisis = dao.obtenerAnalisisParasitologico(id_analisis);
            UsuarioDAO usuarios_dao = new UsuarioDAO();
            List<Usuario> usuarios = usuarios_dao.obtenerUsuariosSeccion(4);
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("analisis", analisis);
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
        String redireccion = contexto + "AnalisisParasitologico/Ver.jsp";
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        try {
            AnalisisParasitologico analisis = dao.obtenerAnalisisParasitologico(id_analisis);
            request.setAttribute("analisis", analisis);
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
        String redireccion = contexto + "AnalisisParasitologico//Agregar.jsp";
        try {
            AnalisisParasitologico analisis = construirObjeto(request);

            dao.insertarAnalisisParasitologico(analisis);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(analisis.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = contexto + "AnalisisParasitologico/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Análisis Parasitológico agregado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<AnalisisParasitologico> analisis = dao.obtenerAnalisisParasitologicos(especie_consultada);
            request.setAttribute("lista_analisis", analisis);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = contexto + "AnalisisParasitologico/Editar.jsp";
        try {
            AnalisisParasitologico analisis = construirObjeto(request);
            dao.editarAnalisisParasitologico(analisis);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(analisis.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            redireccion = contexto + "AnalisisParasitologico/index.jsp";
            request.setAttribute("mensaje", helper.mensajeDeExito("Análisis editado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<AnalisisParasitologico> analisis = dao.obtenerAnalisisParasitologicos(especie_consultada);
            request.setAttribute("lista_analisis", analisis);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

    protected void postEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(permiso_por_buscar, listaPermisos);
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        String redireccion = contexto + "AnalisisParasitologico/index.jsp";
        try {
            AnalisisParasitologico analisis = dao.obtenerAnalisisParasitologico(id_analisis);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }
        try {
            dao.eliminarAnalisisParasitologico(id_analisis);

            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(id_analisis, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUD, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Análisis eliminado correctamente."));
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            List<AnalisisParasitologico> lista_analisis = dao.obtenerAnalisisParasitologicos(especie_consultada);
            request.setAttribute("conejos", lista_analisis);
        }
        catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        }
        redireccionar(request, response, redireccion);
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private AnalisisParasitologico construirObjeto(HttpServletRequest request) throws SIGIPROException
    {
        AnalisisParasitologico analisis = new AnalisisParasitologico();

        HelperFechas helper_fechas = HelperFechas.getSingletonHelperFechas();

        analisis.setEspecie(especie_consultada);

        String id_analisis = request.getParameter("id_analisis");
        if (!id_analisis.isEmpty()) {
            analisis.setId_analisis(Integer.parseInt(id_analisis));
        }
        else {
            analisis.setFecha(helper_fechas.getFecha_hoy());
        }

        analisis.setNumero_informe(request.getParameter("numero_informe"));

        analisis.setRecetado_por(request.getParameter("recetado_por"));
        UsuarioDAO usr_dao = new UsuarioDAO();
        Usuario usr = usr_dao.obtenerUsuario(Integer.parseInt(request.getParameter("responsable")));
        analisis.setResponsable(usr);
        analisis.setResultados(request.getParameter("resultados"));
        analisis.setTratamiento_dosis(request.getParameter("tratamiento"));

        try {
            analisis.setFecha_tratamiento(helper_fechas.formatearFecha(request.getParameter("fecha_tratamiento")));
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }

        return analisis;
    }

  // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        List<String> lista_acciones;
        especie_consultada = Boolean.parseBoolean(request.getParameter("especie"));
        permisos = (especie_consultada) ? new int[]{206,0,0} : new int[]{255,0,0};
        permiso_por_buscar = (especie_consultada) ? 206 : 255;
        request.setAttribute("especie", especie_consultada);
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
    protected void validarPermiso(int permiso, List<Integer> permisosUsuario) throws AuthenticationException, NullPointerException
    {
        try {
            if (!(permisosUsuario.contains(permiso) || permisosUsuario.contains(1))) {
                throw new AuthenticationException("Usuario no tiene permisos para acceder a la acción.");
            }
        }
        catch (NullPointerException e) {
            throw new AuthenticationException("Expiró la sesión.");
        }
    }

    @Override
    protected int getPermiso()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
}
