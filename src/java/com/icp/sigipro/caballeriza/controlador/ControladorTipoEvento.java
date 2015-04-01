
package com.icp.sigipro.caballeriza.controlador;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.caballeriza.dao.TipoEventoDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.caballeriza.dao.EventoClinicoDAO;
import com.icp.sigipro.caballeriza.modelos.EventoClinico;
import com.icp.sigipro.caballeriza.modelos.TipoEvento;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Walter
 */
@WebServlet(name = "ControladorTipoEvento", urlPatterns = {"/Caballeriza/TipoEvento"})
public class ControladorTipoEvento extends SIGIPROServlet {


    private final int[] permisos = {1, 46, 47, 48};

    private TipoEventoDAO dao = new TipoEventoDAO();

    protected final Class clase = ControladorTipoEvento.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");            
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");

        }
    };

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(46, listaPermisos);

        String redireccion = "TipoEvento/Agregar.jsp";
        TipoEvento g = new TipoEvento();
        request.setAttribute("tipoevento", g);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        try{
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "TipoEvento/index.jsp";
        List<TipoEvento> grupos = dao.obtenerTiposEventos();
        request.setAttribute("listaTipos", grupos);
        redireccionar(request, response, redireccion);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }        
    }
        protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "TipoEvento/Ver.jsp";
        int id_tipo_evento = Integer.parseInt(request.getParameter("id_tipo_evento"));
        try {
            TipoEvento g = dao.obtenerTipoEvento(id_tipo_evento);
            EventoClinicoDAO c = new EventoClinicoDAO();
            List<EventoClinico> eventos = c.obtenerEventosTipo(id_tipo_evento);
            request.setAttribute("eventos", eventos);
            request.setAttribute("tipoevento", g);
            redireccionar(request, response, redireccion);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        
    }
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(48, listaPermisos);
        String redireccion = "TipoEvento/Editar.jsp";
        int id_tipo_evento = Integer.parseInt(request.getParameter("id_tipo_evento"));
        TipoEvento tipoevento = dao.obtenerTipoEvento(id_tipo_evento);      
        request.setAttribute("tipoevento", tipoevento);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }
    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(47, listaPermisos);
        int id_tipo_evento = Integer.parseInt(request.getParameter("id_tipo_evento"));
        try{
            dao.eliminarTipoEvento(id_tipo_evento);
            String redireccion = "TipoEvento/index.jsp";
            
            //Funcion que genera la bitacora 
            BitacoraDAO bitacora = new BitacoraDAO(); 
            bitacora.setBitacora(id_tipo_evento,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPO_EVENTO,request.getRemoteAddr()); 
            //----------------------------
            
            List<TipoEvento> tiposeventso = dao.obtenerTiposEventos();
            request.setAttribute("listaTipos", tiposeventso);
            redireccionar(request, response, redireccion);
        }
        catch (SIGIPROException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }
        
    }    

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        boolean resultado = false;
        String redireccion = "TipoEvento/Agregar.jsp";
        TipoEvento g = construirObjeto(request);
        resultado = dao.insertarTipoEvento(g);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(g.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_TIPO_EVENTO, request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de evento agregado correctamente"));
            redireccion = "TipoEvento/index.jsp";
        }
        request.setAttribute("listaTipos", dao.obtenerTiposEventos());
        redireccionar(request, response, redireccion);
    }
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException
    {
        boolean resultado = false;
        String redireccion = "TipoEvento/Editar.jsp";
        TipoEvento g = construirObjeto(request);
        g.setId_tipo_evento(Integer.parseInt(request.getParameter("id_tipo_evento")));
        resultado = dao.editarTipoEvento(g);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(g.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPO_EVENTO,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de evento editado correctamente"));
        if (resultado){
            redireccion = "TipoEvento/index.jsp";
        }
        request.setAttribute("listaTipos", dao.obtenerTiposEventos());
        redireccionar(request, response, redireccion);
    }
    private TipoEvento construirObjeto(HttpServletRequest request) {
        TipoEvento g = new TipoEvento();
        
        g.setNombre(request.getParameter("nombre"));
        g.setDescripcion(request.getParameter("descripcion"));
        return g;
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
