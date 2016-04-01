/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.produccion.dao.Categoria_AADAO;
import com.icp.sigipro.produccion.modelos.Categoria_AA;
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
@WebServlet(name = "ControladorCategoria_AA", urlPatterns = {"/Produccion/Categoria_AA"})
public class ControladorCategoria_AA extends SIGIPROServlet {

    //CRUD
    private final int[] permisos = {630};
    //-----------------
    private final Categoria_AADAO dao = new Categoria_AADAO();
    

    protected final Class clase = ControladorCategoria_AA.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("indexbotones");
            
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
        validarPermiso(630, request);

        String redireccion = "Categoria_AA/Agregar.jsp";
        Categoria_AA c_aa = new Categoria_AA();
        request.setAttribute("categoria_aa", c_aa);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Categoria_AA/index.jsp";
        List<Categoria_AA> categorias_aa = dao.obtenerCategorias_AA();
        request.setAttribute("listaCategorias", categorias_aa);
        redireccionar(request, response, redireccion);
    }
    
    protected void getIndexbotones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/indexBotonesCategoria.jsp";
        List<Categoria_AA> categorias = dao.obtenerCategorias_AA();
        request.setAttribute("listaCategorias", categorias);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Categoria_AA/Ver.jsp";
        int id_categoria_aa = Integer.parseInt(request.getParameter("id_categoria_aa"));
        try {
            Categoria_AA c_aa = dao.obtenerCategoria_AA(id_categoria_aa);
            request.setAttribute("categoria_aa", c_aa);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(630, request);
        String redireccion = "Categoria_AA/Editar.jsp";
        int id_categoria_aa = Integer.parseInt(request.getParameter("id_categoria_aa"));
        Categoria_AA c_aa = dao.obtenerCategoria_AA(id_categoria_aa);
        request.setAttribute("categoria_aa", c_aa);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(630, request);
        int id_categoria_aa = Integer.parseInt(request.getParameter("id_categoria_aa"));
        boolean resultado = false;
        try{
            resultado = dao.eliminarCategoria_AA(id_categoria_aa);
            if (resultado){
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_categoria_aa,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATEGORIA_AA,request.getRemoteAddr()); 
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Categoría de Actividad de Apoyo eliminada correctamente")); 
            }
            else{
               request.setAttribute("mensaje", helper.mensajeDeError("Categoría de Actividad de Apoyo no pudo ser eliminada ya que tiene actividades de apoyo asociadas."));  
            }
            this.getIndex(request, response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Categoría de Actividad de Apoyo no pudo ser eliminada ya que tiene actividades de apoyo asociadas."));  
            this.getIndex(request, response);
        }
        
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(630, request);
        boolean resultado = false;
        Categoria_AA c_aa = construirObjeto(request);
        resultado = dao.insertarCategoria_AA(c_aa);
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Categoría de Actividad de Apoyo agregada correctamente"));        
            //Funcion que genera la bitacora
            bitacora.setBitacora(c_aa.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATEGORIA_AA,request.getRemoteAddr());
            //*----------------------------*
            this.getIndexbotones(request, response);
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Categoría de Actividad de Apoyo no pudo ser agregada. Inténtelo de nuevo."));        
            this.getAgregar(request, response);
        }

    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(630, request);
        boolean resultado = false;
        Categoria_AA c_aa = construirObjeto(request);
        int id_categoria_aa = Integer.parseInt(request.getParameter("id_categoria_aa"));
        c_aa.setId_categoria_aa(id_categoria_aa);
        resultado = dao.editarCategoria_AA(c_aa);
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(c_aa.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CATEGORIA_AA,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Categoría de Actividad de Apoyo editada correctamente"));
            this.getIndexbotones(request, response);
        }
        else{
            request.setAttribute("mensaje", helper.mensajeDeError("Categoría de Actividad de Apoyo no pudo ser editada. Inténtelo de nuevo."));
            request.setAttribute("id_categoria_aa",id_categoria_aa);
            this.getEditar(request, response);
        }
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Categoria_AA construirObjeto(HttpServletRequest request) {
        Categoria_AA c_aa = new Categoria_AA();
        c_aa.setNombre(request.getParameter("nombre"));
        c_aa.setDescripcion(request.getParameter("descripcion"));

        return c_aa;
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
