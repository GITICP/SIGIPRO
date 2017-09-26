/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.TipoPatronControlDAO;
import com.icp.sigipro.controlcalidad.modelos.TipoPatronControl;
import com.icp.sigipro.core.SIGIPROServlet;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorTipoPatronControl", urlPatterns = {"/ControlCalidad/TipoPatronControl"})
public class ControladorTipoPatronControl extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {580, 581, 582, 583};
    //-----------------
    private final TipoPatronControlDAO dao = new TipoPatronControlDAO();

    protected final Class clase = ControladorTipoPatronControl.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
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
  
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(500, request);

        String redireccion = "TipoPatronControl/Agregar.jsp";
        TipoPatronControl tpc = new TipoPatronControl();
        request.setAttribute("tipopatroncontrol", tpc);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermisosMultiple(permisos, request);
        String redireccion = "TipoPatronControl/index.jsp";
        List<TipoPatronControl> tipos_patroncontrol = dao.obtenerTiposPatronesControles();
        request.setAttribute("listaTipos", tipos_patroncontrol);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermisosMultiple(permisos, request);
        String redireccion = "TipoPatronControl/Ver.jsp";
        int id_tipo_patroncontrol = Integer.parseInt(request.getParameter("id_tipo_patroncontrol"));
        try {
            TipoPatronControl tpc = dao.obtenerTipoPatronControl(id_tipo_patroncontrol);
            request.setAttribute("tipopatroncontrol", tpc);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(501, request);
        String redireccion = "TipoPatronControl/Editar.jsp";
        int id_tipo_patroncontrol = Integer.parseInt(request.getParameter("id_tipo_patroncontrol"));
        TipoPatronControl tipo_patron = dao.obtenerTipoPatronControl(id_tipo_patroncontrol);
        request.setAttribute("tipopatroncontrol", tipo_patron);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(502, request);
        int id_tipo_patroncontrol = Integer.parseInt(request.getParameter("id_tipo_patroncontrol"));
        boolean resultado = false;
        try{
            resultado = dao.eliminarTipoPatronControl(id_tipo_patroncontrol);
            if (resultado){
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_tipo_patroncontrol,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOPATRONESCONTROLES,request.getRemoteAddr()); 
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Patrón/Control eliminado correctamente")); 
            }
            else{
               request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Patrón/Control no pudo ser eliminado ya que tiene patrones o controles asociados."));  
            }
            this.getIndex(request, response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Patrón/Control no pudo ser eliminado ya que tiene patrones o controles asociados."));  
            this.getIndex(request, response);
        }
        
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(580, request);
        boolean resultado = false;
        TipoPatronControl tpc = construirObjeto(request);
        resultado = dao.insertarTipoPatronControl(tpc);
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Patrón/Control agregado correctamente"));        
            //Funcion que genera la bitacora
            bitacora.setBitacora(tpc.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOPATRONESCONTROLES,request.getRemoteAddr());
            //*----------------------------*
            this.getIndex(request, response);
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Patrón/Control no pudo ser agregado. Inténtelo de nuevo."));        
            this.getAgregar(request, response);
        }

    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(501, request);
        boolean resultado = false;
        TipoPatronControl tpc = construirObjeto(request);
        int id_tipo_patroncontrol = Integer.parseInt(request.getParameter("id_tipo_patroncontrol"));
        tpc.setId_tipo_patroncontrol(id_tipo_patroncontrol);
        resultado = dao.editarTipoPatronControl(tpc);
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(tpc.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOPATRONESCONTROLES,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Patrón/Control editado correctamente"));
            this.getIndex(request, response);
        }
        else{
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Patrón/Control no pudo ser editado. Inténtelo de nuevo."));
            request.setAttribute("id_tipo_patroncontrol",id_tipo_patroncontrol);
            this.getEditar(request, response);
        }
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private TipoPatronControl construirObjeto(HttpServletRequest request) {
        TipoPatronControl tpc = new TipoPatronControl();
        tpc.setNombre(request.getParameter("nombre"));
        tpc.setDescripcion(request.getParameter("descripcion"));
        tpc.setTipo(request.getParameter("tipo"));

        return tpc;
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
