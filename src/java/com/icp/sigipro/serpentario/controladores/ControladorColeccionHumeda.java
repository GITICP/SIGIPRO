/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.serpentario.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.serpentario.dao.ColeccionHumedaDAO;
import com.icp.sigipro.serpentario.dao.EventoDAO;
import com.icp.sigipro.serpentario.dao.SerpienteDAO;
import com.icp.sigipro.serpentario.modelos.ColeccionHumeda;
import com.icp.sigipro.serpentario.modelos.Evento;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorColeccionHumeda", urlPatterns = {"/Serpentario/ColeccionHumeda"})
public class ControladorColeccionHumeda extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 314};
    //-----------------
    private ColeccionHumedaDAO dao = new ColeccionHumedaDAO();
    private EventoDAO eventodao = new EventoDAO();
    private SerpienteDAO serpientedao = new SerpienteDAO();
    private BitacoraDAO bitacora = new BitacoraDAO();
    
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    protected final Class clase = ControladorColeccionHumeda.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("editar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "ColeccionHumeda/index.jsp";
        List<ColeccionHumeda> chs = dao.obtenerColeccionesHumedas();
        request.setAttribute("listaCH", chs);
        redireccionar(request, response, redireccion);
    }
    
    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "ColeccionHumeda/Ver.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Serpiente serpiente = serpientedao.obtenerSerpiente(id_serpiente);
        request.setAttribute("serpiente", serpiente);
        request.setAttribute("accion", "ver");
        ColeccionHumeda ch = dao.obtenerColeccionHumeda(serpiente);
        request.setAttribute("coleccionhumeda", ch);

        redireccionar(request, response, redireccion);

    }
    
    
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(314, listaPermisos);
        String redireccion = "ColeccionHumeda/Editar.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Serpiente serpiente = serpientedao.obtenerSerpiente(id_serpiente);
        request.setAttribute("serpiente", serpiente);
        request.setAttribute("accion", "editar");
        ColeccionHumeda ch = dao.obtenerColeccionHumeda(serpiente);
        request.setAttribute("coleccionhumeda", ch);
        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "ColeccionHumeda/index.jsp";
        ColeccionHumeda ch = construirCH(request);
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        Usuario usuario = usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario"));
        ch.setUsuario(usuario);
        
        resultado = dao.insertarSerpiente(ch);
       
        if (resultado){
             //Funcion que genera la bitacora
            bitacora.setBitacora(ch.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_COLECCIONHUMEDA,request.getRemoteAddr());
            //*----------------------------*
            Evento e = this.setEvento(ch.getSerpiente(), "Colección Húmeda", request);
            eventodao.insertarEvento(e);
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente "+ch.getSerpiente().getNumero_serpiente()+" agregada a Colección Húmeda correctamente."));
            redireccion = "ColeccionHumeda/index.jsp";
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Serpiente no pudo ser agregada a Colección Húmeda por problemas con el Número de Ingreso. Este debe ser único."));
        }
        request.setAttribute("listaCH", dao.obtenerColeccionesHumedas());
        redireccionar(request, response, redireccion);
    }
    

        
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "ColeccionHumeda/index.jsp";
        String deceso = request.getParameter("deceso");
        int id_serpiente=0;
        ColeccionHumeda ch = this.construirCH(request);
        int id_coleccion_humeda = Integer.parseInt(request.getParameter("id_ch"));
        ch.setId_coleccion_humeda(id_coleccion_humeda);
        id_serpiente = ch.getSerpiente().getId_serpiente();
        resultado=dao.editarColeccionHumeda(ch);
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(ch.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_COLECCIONHUMEDA,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento de serpiente "+id_serpiente+" editado correctamente."));
            redireccion = "ColeccionHumeda/index.jsp";
        }
        request.setAttribute("listaCH", dao.obtenerColeccionesHumedas());
        redireccionar(request, response, redireccion);
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private ColeccionHumeda construirCH(HttpServletRequest request){
        ColeccionHumeda ch = new ColeccionHumeda();
        try{
            ch.setNumero_coleccion_humeda(Integer.parseInt(request.getParameter("numero_coleccion_humeda")));
        }catch(Exception e){
            
        }
        ch.setObservaciones(request.getParameter("observacionesCH"));
        ch.setProposito(request.getParameter("proposito"));
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente_coleccion_humeda"));
        ch.setSerpiente(serpientedao.obtenerSerpiente(id_serpiente));
        
        return ch;
    }
  
    //Para Coleccion Viva, Deceso
    private Evento setEvento(Serpiente serpiente,String evento,HttpServletRequest request){
        Evento e = new Evento();
        e.setEvento(evento);
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        e.setFecha_evento(date);
        e.setSerpiente(serpiente);
        UsuarioDAO usuariodao = new UsuarioDAO();
        e.setUsuario(usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString()));
        
        return e;
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
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
  protected int getPermiso()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  // </editor-fold>
}
