/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.EventoClinicoDAO;
import com.icp.sigipro.caballeriza.dao.GrupoDeCaballosDAO;
import com.icp.sigipro.caballeriza.dao.TipoEventoDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.caballeriza.modelos.GrupoDeCaballos;
import com.icp.sigipro.caballeriza.modelos.TipoEvento;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorEventoClinico", urlPatterns = {"/Caballeriza/EventoClinico"})
public class ControladorEventoClinico extends SIGIPROServlet {

    private final int[] permisos = {1, 55, 56};
    private EventoClinicoDAO dao = new EventoClinicoDAO();

    protected final Class clase = ControladorEventoClinico.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(55, listaPermisos);

        String redireccion = "EventoClinico/Agregar.jsp";
        EventoClinico c = new EventoClinico();
        TipoEventoDAO tipoeventodao = new TipoEventoDAO();
        GrupoDeCaballosDAO gdcDAO = new GrupoDeCaballosDAO();
        UsuarioDAO usr_dao = new UsuarioDAO();
        List<GrupoDeCaballos> grupos_caballos = gdcDAO.obtenerGruposDeCaballosConCaballos();
        List<TipoEvento> listatipos = tipoeventodao.obtenerTiposEventos();
        List<Usuario> lista_usuarios = usr_dao.obtenerUsuariosSeccion(6, 1);
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("evento", c);
        request.setAttribute("grupos_caballos", grupos_caballos);
        request.setAttribute("usuarios_cab_prod", lista_usuarios);
        request.setAttribute("listatipos", listatipos);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "EventoClinico/index.jsp";
        List<EventoClinico> eventosclinicos = dao.obtenerEventosClinicos();
        request.setAttribute("listaEventosClinicos", eventosclinicos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "EventoClinico/Ver.jsp";
        int id_evento = Integer.parseInt(request.getParameter("id_evento"));
        try {
            EventoClinico g = dao.obtenerEventoClinico(id_evento);
            List<Caballo> listacaballos = dao.obtenerCaballosEvento(id_evento);
            request.setAttribute("caballos", listacaballos);
            request.setAttribute("eventoclinico", g);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(56, listaPermisos);
        String redireccion = "EventoClinico/Editar.jsp";
        int id_evento = Integer.parseInt(request.getParameter("id_evento"));
        EventoClinico eventoclinico = dao.obtenerEventoClinicoConCaballos(id_evento);
        GrupoDeCaballosDAO gdcDAO = new GrupoDeCaballosDAO();
        List<GrupoDeCaballos> grupos_caballos = gdcDAO.obtenerGruposDeCaballosConCaballos();
        TipoEventoDAO tipodao = new TipoEventoDAO();
        List<TipoEvento> listatipos = tipodao.obtenerTiposEventos();
        UsuarioDAO usr_dao = new UsuarioDAO();
        List<Usuario> lista_usuarios = usr_dao.obtenerUsuariosSeccion(6, 1);
        request.setAttribute("usuarios_cab_prod", lista_usuarios);
        request.setAttribute("listatipos", listatipos);
        request.setAttribute("evento", eventoclinico);
        request.setAttribute("grupos_caballos", grupos_caballos);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);
    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "EventoClinico/Agregar.jsp";
        EventoClinico c = construirObjeto(request);
        String[] ids_caballos = request.getParameterValues("caballos");
        if(ids_caballos == null) ids_caballos = new String[]{};
        resultado = dao.insertarEventoClinico(c, ids_caballos);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(c.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_EVENTO_CLINICO, request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento Clínico agregado correctamente"));
            redireccion = "EventoClinico/index.jsp";
        }
        request.setAttribute("listaEventosClinicos", dao.obtenerEventosClinicos());
        redireccionar(request, response, redireccion);
    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "EventoClinico/Editar.jsp";
        EventoClinico c = construirObjeto(request);
        c.setId_evento(Integer.parseInt(request.getParameter("id_evento")));
        String[] ids_caballos = request.getParameterValues("caballos");
        if(ids_caballos == null) ids_caballos = new String[]{};
        resultado = dao.editarEventoClinico(c, ids_caballos);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(c.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO_CLINICO,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento Clínico editado correctamente"));
            redireccion = "EventoClinico/index.jsp";
        }
        request.setAttribute("listaEventosClinicos", dao.obtenerEventosClinicos());
        redireccionar(request, response, redireccion);
    }

    private EventoClinico construirObjeto(HttpServletRequest request) throws SIGIPROException {
        EventoClinico e = new EventoClinico();
        TipoEventoDAO tipoeventodao = new TipoEventoDAO();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha;
        java.sql.Date fechaSQL;
        try {
            fecha = formatoFecha.parse(request.getParameter("fecha"));
            fechaSQL = new java.sql.Date(fecha.getTime());
            e.setFecha(fechaSQL);
        } catch (ParseException ex) {

        }
        e.setDescripcion(request.getParameter("descripcion"));
        e.setObservaciones(request.getParameter("observaciones"));
        String tipodeevento= request.getParameter("tipoevento");
        String[] tiposeleccionado;
        tiposeleccionado=tipodeevento.split(",");
        e.setTipo_evento(tipoeventodao.obtenerTipoEvento(Integer.parseInt(tiposeleccionado[0])));
        Usuario responsable = new Usuario();
        responsable.setId_usuario(Integer.parseInt(request.getParameter("responsable")));
        e.setResponsable(responsable);
        
        return e;
    }

    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        } else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        } else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
