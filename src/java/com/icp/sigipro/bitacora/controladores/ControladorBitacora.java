/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.bitacora.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.core.SIGIPROServlet;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorBitacora", urlPatterns = {"/Bitacora/Bitacora"})
public class ControladorBitacora extends SIGIPROServlet {

    private final int[] permisos = {1, 1, 1};
    private BitacoraDAO dao = new BitacoraDAO();

    protected final Class clase = ControladorBitacora.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
      {
        add("index");
        add("ver");
      }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
   protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     List<Integer> listaPermisos = getPermisosUsuario(request);
     validarPermisos(permisos, listaPermisos);
     String redireccion = "Bitacora/index.jsp";
     List<Bitacora> bitacoras = dao.obtenerBitacoras();
     request.setAttribute("listaBitacoras", bitacoras);
     redireccionar(request, response, redireccion);
   }

   protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
     List<Integer> listaPermisos = getPermisosUsuario(request);
     validarPermisos(permisos, listaPermisos);
     String redireccion = "Bitacora/Ver.jsp";
     int id_bitacora = Integer.parseInt(request.getParameter("id_bitacora"));
     try {
       Bitacora b = dao.obtenerBitacora(id_bitacora);
       request.setAttribute("bitacora", b);
       JSONObject json = new JSONObject(b.getEstado());
       Iterator<?> keys = json.keys();
       request.setAttribute("llaves", keys);
     }
     catch (Exception ex) {
       ex.printStackTrace();
     }
     redireccionar(request, response, redireccion);
   }

       // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
     @Override
     protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
     {
       if (accionesGet.contains(accion)) {
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
