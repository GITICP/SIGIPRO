/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.TipoReactivoDAO;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Veneno;
import com.icp.sigipro.utilidades.HelpersHTML;
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
@WebServlet(name = "ControladorTipoReactivo", urlPatterns = {"/ControlCalidad/TipoReactivo"})
public class ControladorTipoReactivo extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1,510};
    //-----------------
    private TipoReactivoDAO dao = new TipoReactivoDAO();
    
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    BitacoraDAO bitacora = new BitacoraDAO(); 

    protected final Class clase = ControladorTipoReactivo.class;
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
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(510, listaPermisos);

        String redireccion = "TipoReactivo/Agregar.jsp";
        TipoReactivo tr = new TipoReactivo();
        request.setAttribute("tiporeactivo", tr);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "TipoReactivo/index.jsp";
        List<TipoReactivo> tiporeactivos = dao.obtenerTipoReactivos();
        request.setAttribute("listaTipos", tiporeactivos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "TipoReactivo/Ver.jsp";
        int id_tipo_reactivo = Integer.parseInt(request.getParameter("id_tipo_reactivo"));
        try {
            TipoReactivo tr = dao.obtenerTipoReactivo(id_tipo_reactivo);
            request.setAttribute("tiporeactivo", tr);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(510, listaPermisos);
        String redireccion = "TipoReactivo/Editar.jsp";
        int id_tipo_reactivo = Integer.parseInt(request.getParameter("id_tipo_reactivo"));
        TipoReactivo tiporeactivo = dao.obtenerTipoReactivo(id_tipo_reactivo);
        request.setAttribute("tiporeactivo", tiporeactivo);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(510, listaPermisos);
        int id_tipo_reactivo = Integer.parseInt(request.getParameter("id_tipo_reactivo"));
        boolean resultado = false;
        try{
            resultado = dao.eliminarTipoReactivo(id_tipo_reactivo);
            if (resultado){
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_tipo_reactivo,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOREACTIVO,request.getRemoteAddr()); 
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Reactivo eliminado correctamente")); 
            }
            else{
               request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Reactivo no pudo ser eliminado ya que tiene reactivos asociados."));  
            }
            this.getIndex(request, response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Reactivo no pudo ser eliminado ya que tiene reactivos asociados."));  
            this.getIndex(request, response);
        }
        
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        TipoReactivo tr = construirObjeto(request);
        resultado = dao.insertarTipoReactivo(tr);
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Reactivo agregado correctamente"));        
            //Funcion que genera la bitacora
            bitacora.setBitacora(tr.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOREACTIVO,request.getRemoteAddr());
            //*----------------------------*
            this.getIndex(request, response);
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Reactivo no pudo ser agregado. Inténtelo de nuevo."));        
            this.getAgregar(request, response);
        }

    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        TipoReactivo tr = construirObjeto(request);
        int id_tipo_reactivo = Integer.parseInt(request.getParameter("id_tipo_reactivo"));
        tr.setId_tipo_reactivo(id_tipo_reactivo);
        resultado = dao.editarTipoReactivo(tr);
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(tr.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_TIPOREACTIVO,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Tipo de Reactivo editado correctamente"));
            this.getIndex(request, response);
        }
        else{
            request.setAttribute("mensaje", helper.mensajeDeError("Tipo de Reactivo no pudo ser editado. Inténtelo de nuevo."));
            request.setAttribute("id_tipo_reactivo",id_tipo_reactivo);
            this.getEditar(request, response);
        }
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private TipoReactivo construirObjeto(HttpServletRequest request) {
        TipoReactivo tr = new TipoReactivo();
        tr.setNombre(request.getParameter("nombre"));
        tr.setDescripcion(request.getParameter("descripcion"));
        tr.setMachote(request.getParameter("machote"));

        return tr;
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