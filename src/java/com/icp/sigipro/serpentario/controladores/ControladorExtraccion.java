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
import com.icp.sigipro.serpentario.dao.EspecieDAO;
import com.icp.sigipro.serpentario.dao.ExtraccionDAO;
import com.icp.sigipro.serpentario.dao.SerpienteDAO;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Extraccion;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import com.icp.sigipro.serpentario.modelos.SerpientesExtraccion;
import com.icp.sigipro.serpentario.modelos.UsuariosExtraccion;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "ControladorExtraccion", urlPatterns = {"/Serpentario/Extraccion"})
public class ControladorExtraccion extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1};
    //-----------------
    private ExtraccionDAO dao = new ExtraccionDAO();

    protected final Class clase = ControladorExtraccion.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editarserpientes");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("centrifugado");
            add("inicioliofilizacion");
            add("finliofilizacion");
            add("editarserpientes");
            
        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Extraccion/index.jsp";
        List<Extraccion> extracciones = dao.obtenerExtracciones();
        request.setAttribute("listaExtracciones", extracciones);
        redireccionar(request, response, redireccion);
    }
    
    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Extraccion/Ver.jsp";
        int id_extraccion = Integer.parseInt(request.getParameter("id_extraccion"));
        try {
            Extraccion e = dao.obtenerExtraccion(id_extraccion);
            request.setAttribute("extraccion", e);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(43, listaPermisos);

        String redireccion = "Extraccion/Agregar.jsp";
        Extraccion e = new Extraccion();
        EspecieDAO especiedao = new EspecieDAO();
        List<Especie> especies = especiedao.obtenerEspecies();
        UsuarioDAO usuariodao = new UsuarioDAO();
        List<Usuario> usuarios = usuariodao.obtenerUsuarios();
        SerpienteDAO serpientedao = new SerpienteDAO();
        List<Serpiente> serpientes = serpientedao.obtenerSerpientes();
        request.setAttribute("helper", HelpersHTML.getSingletonHelpersHTML());
        request.setAttribute("extraccion", e);
        request.setAttribute("especies",especies);
        request.setAttribute("usuarios",usuarios);
        request.setAttribute("serpientes",serpientes);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }
    
    protected void getEditarserpientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(43, listaPermisos);
        
        String redireccion = "Extraccion/EditarSerpientes.jsp";
        
        int id_extraccion = Integer.parseInt(request.getParameter("id_extraccion"));
        
        List <Serpiente> serpientes = dao.obtenerSerpientesExtraccion(id_extraccion);
        request.setAttribute("serpientes",serpientes);
        
        redireccionar(request, response, redireccion);
        
    }
  // </editor-fold>
    
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Extraccion/Agregar.jsp";
        Extraccion e = construirObjeto(request);
        
        resultado = dao.insertarExtraccion(e);
        
        List<UsuariosExtraccion> usuariosextraccion = construirUsuarioExtraccion(request,e);
        List<SerpientesExtraccion> serpientesextraccion = construirSerpienteExtraccion(request,e);
        
        dao.insertarSerpientesExtraccion(serpientesextraccion);
        dao.insertarUsuariosExtraccion(usuariosextraccion);
        
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EXTRACCION,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Extraccion agregada correctamente"));
            redireccion = "Extraccion/index.jsp";
        }
        request.setAttribute("listaExtracciones", dao.obtenerExtracciones());
        redireccionar(request, response, redireccion);
    }
    
    
  // </editor-fold>
    
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Extraccion construirObjeto(HttpServletRequest request) {
        Extraccion e = new Extraccion();
        
        EspecieDAO especiedao = new EspecieDAO();
        UsuarioDAO usuariodao = new UsuarioDAO();
        
        Especie especie = especiedao.obtenerEspecie(Integer.parseInt(request.getParameter("especie")));
        e.setEspecie(especie);
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        java.sql.Date fecha_registro = new java.sql.Date(new Date().getTime());
        e.setFecha_registro(fecha_registro);
        
        e.setNumero_extraccion(request.getParameter("numero_extraccion"));
        
        String ingreso_cv = request.getParameter("ingreso_cv");
        if (ingreso_cv.equals("Coleccion Viva")){
            e.setIngreso_cv(true);
        }else{
            e.setIngreso_cv(false);
        }
        
        java.util.Date fecha_extraccion;
        java.sql.Date fecha_extraccionSQL;
        try {
          fecha_extraccion = formatoFecha.parse(request.getParameter("fecha_extraccion"));
          fecha_extraccionSQL = new java.sql.Date(fecha_extraccion.getTime());
          e.setFecha_extraccion(fecha_extraccionSQL);
        } catch (ParseException ex) {
            
        }
        
        e.setVolumen_extraido(Float.parseFloat(request.getParameter("volumen_extraido")));
        
        Usuario usuario_registro = usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString());
        e.setUsuario_registro(usuario_registro);
        //-------------
        return e;
    }
    
    private List<UsuariosExtraccion> construirUsuarioExtraccion(HttpServletRequest request, Extraccion e){
        UsuarioDAO usuariodao = new UsuarioDAO();
        List<UsuariosExtraccion> resultado = new ArrayList<UsuariosExtraccion>();
        String[] usuariosextraccion = request.getParameterValues("usuarios_extraccion");
        for (String usuarioextraccion : usuariosextraccion){
            Usuario usuario = usuariodao.obtenerUsuario(usuarioextraccion);
            UsuariosExtraccion ue = new UsuariosExtraccion (e,usuario);
            resultado.add(ue);
        }
        return resultado;
    }
    
    private List<SerpientesExtraccion> construirSerpienteExtraccion(HttpServletRequest request,Extraccion e){
        SerpienteDAO serpientedao = new SerpienteDAO();
        List<SerpientesExtraccion> resultado = new ArrayList<SerpientesExtraccion>();
        String[] serpientesextraccion = request.getParameterValues("serpientes_extraccion");
        for (String serpienteextraccion : serpientesextraccion){
            Serpiente serpiente = serpientedao.obtenerSerpiente(Integer.parseInt(serpienteextraccion));
            SerpientesExtraccion se = new SerpientesExtraccion (serpiente,e);
            resultado.add(se);
        }
        return resultado;
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
