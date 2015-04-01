/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.EventoClinicoDAO;
import com.icp.sigipro.caballeriza.dao.TipoEventoDAO;
import com.icp.sigipro.caballeriza.modelos.Caballo;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.caballeriza.modelos.TipoEvento;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorEventoClinico", urlPatterns = {"/Caballeriza/EventoClinico"})
public class ControladorEventoClinico extends SIGIPROServlet {

    //Falta implementar
    //private final int[] permisos = {1, 43, 44, 45};
    //-----------------
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
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermiso(43, listaPermisos);

        String redireccion = "EventoClinico/Agregar.jsp";
        EventoClinico c = new EventoClinico();
        TipoEventoDAO tipoeventodao = new TipoEventoDAO();
        UsuarioDAO usrDAO = new UsuarioDAO();
        List<TipoEvento> listatipos = tipoeventodao.obtenerTiposEventos();
        List<Usuario> listaresponsables = usrDAO.obtenerUsuarios();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("evento", c);
        request.setAttribute("listatipos", listatipos);
        request.setAttribute("listaresponsables", listaresponsables);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermisos(permisos, listaPermisos);
        String redireccion = "EventoClinico/index.jsp";
        List<EventoClinico> eventosclinicos = dao.obtenerEventosClinicos();
        request.setAttribute("listaEventosClinicos", eventosclinicos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermisos(permisos, listaPermisos);
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
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermiso(42, listaPermisos);
        String redireccion = "EventoClinico/Editar.jsp";
        int id_evento = Integer.parseInt(request.getParameter("id_evento"));
        EventoClinico eventoclinico = dao.obtenerEventoClinico(id_evento);
        TipoEventoDAO tipodao = new TipoEventoDAO();
        UsuarioDAO usrDAO = new UsuarioDAO();
        List<TipoEvento> listatipos = tipodao.obtenerTiposEventos();
        List<Usuario> listaresponsables = usrDAO.obtenerUsuarios();
        request.setAttribute("listatipos", listatipos);
        request.setAttribute("evento", eventoclinico);
        request.setAttribute("listaresponsables", listaresponsables);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "EventoClinico/Agregar.jsp";
        EventoClinico c = construirObjeto(request);

       // System.out.println(request.getParameter("imagen2").getBytes());
        resultado = dao.insertarEventoClinico(c);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        //bitacora.setBitacora(c.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_CABALLO, request.getRemoteAddr());
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
        resultado = dao.editarEventoClinico(c);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        //bitacora.setBitacora(c.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CABALLO,request.getRemoteAddr());
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
        UsuarioDAO usuariodao = new UsuarioDAO();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha;
        java.sql.Date fechaSQL;
        try {
            fecha = formatoFecha.parse(request.getParameter("fecha"));
            fechaSQL = new java.sql.Date(fecha.getTime());
            e.setFecha(fechaSQL);
        } catch (ParseException ex) {

        }
        String respon=request.getParameter("responsable");
        
        Usuario responsable;
        if(respon == ""){
            responsable = null;
        }
        else{
            responsable = usuariodao.obtenerUsuario(Integer.parseInt(request.getParameter("responsable")));
        }
        e.setDescripcion(request.getParameter("descripcion"));
        String tipodeevento= request.getParameter("tipoevento");
        String[] tiposeleccionado;
        tiposeleccionado=tipodeevento.split(",");
        e.setTipo_evento(tipoeventodao.obtenerTipoEvento(Integer.parseInt(tiposeleccionado[0])));
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
