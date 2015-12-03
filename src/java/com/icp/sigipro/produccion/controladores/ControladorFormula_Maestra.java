/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.produccion.dao.Formula_MaestraDAO;
import com.icp.sigipro.produccion.modelos.Formula_Maestra;
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
@WebServlet(name = "ControladorFormula_Maestra", urlPatterns = {"/Produccion/Formula_Maestra"})
public class ControladorFormula_Maestra extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {635};
    //-----------------
    private final Formula_MaestraDAO dao = new Formula_MaestraDAO();
    

    protected final Class clase = ControladorFormula_Maestra.class;
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
        validarPermiso(635, request);

        String redireccion = "Formula_Maestra/Agregar.jsp";
        Formula_Maestra f_m = new Formula_Maestra();
        request.setAttribute("formula_maestra", f_m);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Formula_Maestra/index.jsp";
        List<Formula_Maestra> formulas_maestras = dao.obtenerFormulas_Maestras();
        request.setAttribute("listaFormulas", formulas_maestras);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Formula_Maestra/Ver.jsp";
        int id_formula_maestra = Integer.parseInt(request.getParameter("id_formula_maestra"));
        try {
            Formula_Maestra f_m = dao.obtenerFormula_Maestra(id_formula_maestra);
            request.setAttribute("formula_maestra", f_m);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(635, request);
        String redireccion = "Formula_Maestra/Editar.jsp";
        int id_formula_maestra = Integer.parseInt(request.getParameter("id_formula_maestra"));
        Formula_Maestra f_m = dao.obtenerFormula_Maestra(id_formula_maestra);
        request.setAttribute("formula_maestra", f_m);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(635, request);
        int id_formula_maestra = Integer.parseInt(request.getParameter("id_formula_maestra"));
        boolean resultado = false;
        try{
            resultado = dao.eliminarFormula_Maestra(id_formula_maestra);
            if (resultado){
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_formula_maestra,Bitacora.ACCION_ELIMINAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_FORMULAMAESTRA,request.getRemoteAddr()); 
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Fórmula Maestra eliminada correctamente")); 
            }
            else{
               request.setAttribute("mensaje", helper.mensajeDeError("Fórmula Maestra no pudo ser eliminada ya que tiene protocolos asociadas."));  
            }
            this.getIndex(request, response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Fórmula Maestra no pudo ser eliminada ya que tiene protocolos asociadas."));  
            this.getIndex(request, response);
        }
        
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(635, request);
        boolean resultado = false;
        Formula_Maestra f_m = construirObjeto(request);
        resultado = dao.insertarFormula_Maestra(f_m);
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Fórmula Maestra agregada correctamente"));        
            //Funcion que genera la bitacora
            bitacora.setBitacora(f_m.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_FORMULAMAESTRA,request.getRemoteAddr());
            //*----------------------------*
            this.getIndex(request, response);
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("Fórmula Maestra no pudo ser agregada. Inténtelo de nuevo."));        
            this.getAgregar(request, response);
        }

    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        validarPermiso(635, request);
        boolean resultado = false;
        Formula_Maestra f_m = construirObjeto(request);
        int id_formula_maestra = Integer.parseInt(request.getParameter("id_formula_maestra"));
        f_m.setId_formula_maestra(id_formula_maestra);
        resultado = dao.editarFormula_Maestra(f_m);
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(f_m.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_FORMULAMAESTRA,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Fórmula Maestra editada correctamente"));
            this.getIndex(request, response);
        }
        else{
            request.setAttribute("mensaje", helper.mensajeDeError("Fórmula Maestra no pudo ser editada. Inténtelo de nuevo."));
            request.setAttribute("id_formula_maestra",id_formula_maestra);
            this.getEditar(request, response);
        }
    }
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Formula_Maestra construirObjeto(HttpServletRequest request) {
        Formula_Maestra f_m = new Formula_Maestra();
        f_m.setNombre(request.getParameter("nombre"));
        f_m.setDescripcion(request.getParameter("descripcion"));

        return f_m;
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
