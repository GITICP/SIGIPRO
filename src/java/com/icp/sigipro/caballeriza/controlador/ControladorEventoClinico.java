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
        List<TipoEvento> listaeventos = tipoeventodao.obtenerTiposEventos();
        HttpSession sesion = request.getSession();
        String nombre_usr = (String) sesion.getAttribute("usuario");
        int id_usuario = usrDAO.obtenerIDUsuario(nombre_usr);
        Usuario responsable = usrDAO.obtenerUsuario(id_usuario);
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("eventoclinico", c);
        request.setAttribute("listaeventos", listaeventos);
        request.setAttribute("responsable", responsable);
        request.setAttribute("accion", "Agregar");

        redireccionar(request, response, redireccion);
    }
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermisos(permisos, listaPermisos);
        String redireccion = "EventoClinico/index.jsp";
        List<EventoClinico> eventosclinicos = dao.obtenerEventosClinicos();
        request.setAttribute("listaEventosClinicos", eventosclinicos);
        redireccionar(request, response, redireccion);
    }
        protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermisos(permisos, listaPermisos);
        String redireccion = "EventoClinico/Ver.jsp";
        int id_evento = Integer.parseInt(request.getParameter("id_evento"));
        try {
            EventoClinico g = dao.obtenerEventoClinico(id_evento);
            request.setAttribute("eventoclinico", g);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        //List<Integer> listaPermisos = getPermisosUsuario(request);
        //validarPermiso(42, listaPermisos);
        String redireccion = "EventoClinico/Editar.jsp";
        int id_evento = Integer.parseInt(request.getParameter("id_evento"));
        EventoClinico eventoclinico = dao.obtenerEventoClinico(id_evento);
        TipoEventoDAO tipodao = new TipoEventoDAO();
        List<TipoEvento> listatipos = tipodao.obtenerTiposEventos();
        request.setAttribute("listatipos",listatipos);      
        request.setAttribute("eventoclinico", eventoclinico);
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
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
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
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento Clínico editado correctamente"));
            redireccion = "EventoClinico/index.jsp";
        }
        request.setAttribute("listaEventosClinicos", dao.obtenerEventosClinicos());
        redireccionar(request, response, redireccion);
    }    

    private EventoClinico construirObjeto(HttpServletRequest request) throws SIGIPROException {
        EventoClinico c = new EventoClinico();
        
        TipoEventoDAO tipoeventodao = new TipoEventoDAO();
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        String tipo=request.getParameter("tipoevento");
//        String nombre=request.getParameter("nombre");
//        String micro=request.getParameter("numero_microchip");
//        String sexo=request.getParameter("sexo");
//        String color=request.getParameter("color");
//        String sennas=request.getParameter("otras_sennas");
//        String estado=request.getParameter("estado");
        
        TipoEvento tipoevento;
        if(tipo == ""){
            tipoevento = tipoeventodao.obtenerTipoEvento(0);
        }
        else{
            tipoevento = tipoeventodao.obtenerTipoEvento(Integer.parseInt(request.getParameter("tipoevento")));
        }
        c.setTipo_evento(tipoevento);
        String id = request.getParameter("id_evento");
        if (id.isEmpty() || id.equals("0")) {            
            java.util.Date hoy = new java.util.Date();
            Date hoysql = new Date(hoy.getTime());
            c.setFecha(hoysql);
        }
        else {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date fecha;
            java.sql.Date fechaSQL;
            try {
                fecha = formatoFecha.parse(request.getParameter("fecha"));
                fechaSQL = new java.sql.Date(fecha.getTime());
                c.setFecha(fechaSQL);
            } catch (ParseException ex) {
            }
        }
                

        c.setResponsable(usuariodao.obtenerUsuario(Integer.parseInt(request.getParameter("respondable"))));
        c.setDescripcion(request.getParameter("descripcion"));

        //-------------
        return c;
    } 

  @Override
  protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
  {
      List<String> lista_acciones;
      if (accionHTTP.equals("get")){
          lista_acciones = accionesGet; 
      } else {
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
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
