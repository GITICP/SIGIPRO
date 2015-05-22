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
import com.icp.sigipro.serpentario.dao.RestriccionDAO;
import com.icp.sigipro.serpentario.dao.VenenoDAO;
import com.icp.sigipro.serpentario.modelos.Restriccion;
import com.icp.sigipro.serpentario.modelos.Veneno;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorRestriccion", urlPatterns = {"/Serpentario/Restriccion"})
public class ControladorRestriccion extends SIGIPROServlet {

    //Agregar, editar, eventos, decesos
    private final int[] permisos = {1, 360,361,362};
    private RestriccionDAO dao = new RestriccionDAO();
    private BitacoraDAO bitacora = new BitacoraDAO();
    private UsuarioDAO usuariodao = new UsuarioDAO();
    private VenenoDAO venenodao = new VenenoDAO();
    
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();


    protected final Class clase = ControladorRestriccion.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("eliminar");
            
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
  
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(360, listaPermisos);

        String redireccion = "Restriccion/Agregar.jsp";
        Restriccion r = new Restriccion();
        int id_veneno = Integer.parseInt(request.getParameter("id_veneno"));
        r.setVeneno(venenodao.obtenerVeneno(id_veneno));
        request.setAttribute("restriccion", r);
        request.setAttribute("usuarios", dao.obtenerUsuarios(r));
        request.setAttribute("veneno",r.getVeneno());
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Restriccion/index.jsp";
        List<Restriccion> restricciones = dao.obtenerRestricciones();
        int año = Calendar.getInstance().get(Calendar.YEAR);
        request.setAttribute("listaRestricciones", restricciones);
        request.setAttribute("venenos", venenodao.obtenerVenenos());
        request.setAttribute("ano", año);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Restriccion/Ver.jsp";
        int id_restriccion = Integer.parseInt(request.getParameter("id_restriccion"));
        try {
            Restriccion r = dao.obtenerRestriccion(id_restriccion);
            request.setAttribute("restriccion", r);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(361, listaPermisos);
        String redireccion = "Restriccion/Editar.jsp";
        int id_restriccion = Integer.parseInt(request.getParameter("id_restriccion"));
        Restriccion restriccion = dao.obtenerRestriccion(id_restriccion);
        request.setAttribute("restriccion", restriccion);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(362, listaPermisos);
        int id_restriccion = Integer.parseInt(request.getParameter("id_restriccion"));
        String redireccion = "Restriccion/index.jsp";
        boolean resultado = false;
        try{
            resultado = dao.eliminarRestriccion(id_restriccion);
            if (resultado){
                //Funcion que genera la bitacora 
                BitacoraDAO bitacora = new BitacoraDAO(); 
                bitacora.setBitacora(id_restriccion,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_RESTRICCION,request.getRemoteAddr()); 
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Restricción de Solicitudes eliminada correctamente")); 
            }
            else{
               request.setAttribute("mensaje", helper.mensajeDeError("Restricción de Solicitudes no pudo ser eliminada."));  
            }
            this.getIndex(request, response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Restricción de Solicitudes no pudo ser eliminada."));  
            this.getIndex(request, response);
        }
        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Restriccion/index.jsp";
        Restriccion r = construirObjeto(request);
        
        String[] usuarios = request.getParameterValues("usuarios_restriccion");
        for (String usuario : usuarios){
            Usuario u = new Usuario();
            u.setId_usuario(Integer.parseInt(usuario));
            
            r.setUsuario(u);
            
            resultado = dao.insertarRestriccion(r);
            
            if (resultado){
                bitacora.setBitacora(r.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_RESTRICCION,request.getRemoteAddr());
            }
        }
        request.setAttribute("mensaje", helper.mensajeDeExito("Restricciones de Solicitudes agregadas correctamente"));
        this.getIndex(request, response);
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Restriccion/Editar.jsp";
        Restriccion r = construirObjeto(request);
        r.setId_restriccion(Integer.parseInt(request.getParameter("id_restriccion")));
        resultado = dao.editarRestriccion(r);
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(r.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_RESTRICCION,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Restricción de Solicitud editada correctamente"));
            redireccion = "Restriccion/index.jsp";
        }
        this.getIndex(request, response);
    }
  // </editor-fold>  
    
    
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Restriccion construirObjeto(HttpServletRequest request) {
        Restriccion r = new Restriccion();
        r.setCantidad_anual(Float.parseFloat(request.getParameter("cantidad_anual")));
        Veneno veneno = new Veneno();
        veneno.setId_veneno(Integer.parseInt(request.getParameter("id_veneno")));
        r.setVeneno(veneno);
        return r;
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
