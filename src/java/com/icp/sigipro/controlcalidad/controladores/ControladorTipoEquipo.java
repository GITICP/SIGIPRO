/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.TipoEquipoDAO;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
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
@WebServlet(name = "ControladorTipoEquipo", urlPatterns = {"/ControlCalidad/TipoEquipo"})
public class ControladorTipoEquipo extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {500, 501, 502, 503};
    //-----------------
    private final TipoEquipoDAO dao = new TipoEquipoDAO();
    

    protected final Class clase = ControladorTipoEquipo.class;
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

        String redireccion = "TipoEquipo/Agregar.jsp";
        TipoEquipo te = new TipoEquipo();
        request.setAttribute("tipoequipo", te);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermisosMultiple(permisos, request);
        String redireccion = "TipoEquipo/index.jsp";
        List<TipoEquipo> tipoequipos = dao.obtenerTipoEquipos();
        request.setAttribute("listaTipos", tipoequipos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermisosMultiple(permisos, request);
        String redireccion = "TipoEquipo/Ver.jsp";
        int id_tipo_equipo = Integer.parseInt(request.getParameter("id_tipo_equipo"));
        try {
            TipoEquipo te = dao.obtenerTipoEquipo(id_tipo_equipo);
            request.setAttribute("tipoequipo", te);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(501, request);
        String redireccion = "TipoEquipo/Editar.jsp";
        int id_tipo_equipo = Integer.parseInt(request.getParameter("id_tipo_equipo"));
        TipoEquipo tipoequipo = dao.obtenerTipoEquipo(id_tipo_equipo);
        request.setAttribute("tipoequipo", tipoequipo);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(502, request);
        int id_tipo_equipo = Integer.parseInt(request.getParameter("id_tipo_equipo"));
        boolean resultado = false;
        try{
            resultado = dao.eliminarTipoEquipo(id_tipo_equipo);
            if (resultado){
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_tipo_equipo,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOEQUIPO,request.getRemoteAddr()); 
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Equipo eliminado correctamente")); 
            }
            else{
               request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Equipo no pudo ser eliminado ya que tiene equipos asociados."));  
            }
            this.getIndex(request, response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Equipo no pudo ser eliminado ya que tiene equipos asociados."));  
            this.getIndex(request, response);
        }
        
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(500, request);
        boolean resultado = false;
        TipoEquipo te = construirObjeto(request);
        resultado = dao.insertarTipoEquipo(te);
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Equipo agregado correctamente"));        
            //Funcion que genera la bitacora
            bitacora.setBitacora(te.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOEQUIPO,request.getRemoteAddr());
            //*----------------------------*
            this.getIndex(request, response);
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Equipo no pudo ser agregado. Inténtelo de nuevo."));        
            this.getAgregar(request, response);
        }

    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(501, request);
        boolean resultado = false;
        TipoEquipo te = construirObjeto(request);
        int id_tipo_equipo = Integer.parseInt(request.getParameter("id_tipo_equipo"));
        te.setId_tipo_equipo(id_tipo_equipo);
        resultado = dao.editarTipoEquipo(te);
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(te.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOEQUIPO,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Equipo editado correctamente"));
            this.getIndex(request, response);
        }
        else{
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Equipo no pudo ser editado. Inténtelo de nuevo."));
            request.setAttribute("id_tipo_equipo",id_tipo_equipo);
            this.getEditar(request, response);
        }
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private TipoEquipo construirObjeto(HttpServletRequest request) {
        TipoEquipo te = new TipoEquipo();
        te.setNombre(request.getParameter("nombre"));
        te.setDescripcion(request.getParameter("descripcion"));
        te.setCertificable(Boolean.parseBoolean(request.getParameter("certificable")));

        return te;
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
