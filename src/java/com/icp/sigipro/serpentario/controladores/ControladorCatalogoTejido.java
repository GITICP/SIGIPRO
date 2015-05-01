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
import com.icp.sigipro.serpentario.dao.CatalogoTejidoDAO;
import com.icp.sigipro.serpentario.dao.EventoDAO;
import com.icp.sigipro.serpentario.dao.SerpienteDAO;
import com.icp.sigipro.serpentario.modelos.CatalogoTejido;
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
@WebServlet(name = "ControladorCatalogoTejido", urlPatterns = {"/Serpentario/CatalogoTejido"})
public class ControladorCatalogoTejido extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 314};
    //-----------------
    private CatalogoTejidoDAO dao = new CatalogoTejidoDAO();
    private EventoDAO eventodao = new EventoDAO();
    private SerpienteDAO serpientedao = new SerpienteDAO();
    private BitacoraDAO bitacora = new BitacoraDAO();
    
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

    protected final Class clase = ControladorCatalogoTejido.class;
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
        String redireccion = "CatalogoTejido/index.jsp";
        List<CatalogoTejido> cts = dao.obtenerCatalogosTejidos();
        request.setAttribute("listaCT", cts);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "CatalogoTejido/Ver.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Serpiente serpiente = serpientedao.obtenerSerpiente(id_serpiente);
        request.setAttribute("serpiente", serpiente);
        request.setAttribute("accion", "ver");
        CatalogoTejido ct = dao.obtenerCatalogoTejido(serpiente);
        request.setAttribute("catalogotejido", ct);

        redireccionar(request, response, redireccion);

    }
    
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(314, listaPermisos);
        String redireccion = "CatalogoTejido/Editar.jsp";
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente"));
        Serpiente serpiente = serpientedao.obtenerSerpiente(id_serpiente);
        request.setAttribute("serpiente", serpiente);
        request.setAttribute("accion", "editar");
        CatalogoTejido ct = dao.obtenerCatalogoTejido(serpiente);
        request.setAttribute("catalogotejido", ct);
        
        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
        protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "CatalogoTejido/index.jsp";
        CatalogoTejido ct = construirCT(request);
        
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        Usuario usuario = usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario"));
        ct.setUsuario(usuario);
        
        resultado = dao.insertarSerpiente(ct);
       
        if (resultado){
             //Funcion que genera la bitacora
            bitacora.setBitacora(ct.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATALOGOTEJIDO,request.getRemoteAddr());
            //*----------------------------*
            Evento e = this.setEvento(ct.getSerpiente(), 8, request);
            eventodao.insertarEvento(e);
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EVENTO,request.getRemoteAddr());
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpiente "+ct.getSerpiente().getNumero_serpiente()+" agregada a Catálogo de Tejidos correctamente."));
            redireccion = "CatalogoTejido/index.jsp";
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Serpiente no pudo ser agregada a Catálogo de Tejidos por problemas con el Número de Ingreso. Este debe ser único."));
        }
        request.setAttribute("listaCT", dao.obtenerCatalogosTejidos());
        redireccionar(request, response, redireccion);    
    }
          
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "CatalogoTejido/Editar.jsp";
        String deceso = request.getParameter("deceso");
        int id_serpiente=0;
        CatalogoTejido ct = this.construirCT(request);
        int id_catalogo_tejido = Integer.parseInt(request.getParameter("id_ct"));
        ct.setId_catalogo_tejido(id_catalogo_tejido);
        id_serpiente = ct.getSerpiente().getId_serpiente();
        resultado=dao.editarCatalogoTejido(ct);
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(ct.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATALOGOTEJIDO,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Evento de serpiente "+id_serpiente+" editado correctamente."));
            redireccion = "CatalogoTejido/index.jsp";
        }
        request.setAttribute("listaCT", dao.obtenerCatalogosTejidos());
        redireccionar(request, response, redireccion);
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    
    private CatalogoTejido construirCT(HttpServletRequest request){
        CatalogoTejido ct = new CatalogoTejido();
        try{
            ct.setNumero_catalogo_tejido(Integer.parseInt(request.getParameter("numero_catalogo_tejido")));
        }catch (Exception e){
            
        }
        ct.setObservaciones(request.getParameter("observacionesCT"));
        ct.setNumero_caja(request.getParameter("numero_caja"));
        ct.setEstado(request.getParameter("estado"));
        ct.setPosicion(request.getParameter("posicion"));
        int id_serpiente = Integer.parseInt(request.getParameter("id_serpiente_catalogo_tejido"));
        ct.setSerpiente(serpientedao.obtenerSerpiente(id_serpiente));
        
        return ct;
    }
  
        //Para Coleccion Viva, Deceso
    private Evento setEvento(Serpiente serpiente,int evento,HttpServletRequest request){
        Evento e = new Evento();
        e.setId_categoria(evento);
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
