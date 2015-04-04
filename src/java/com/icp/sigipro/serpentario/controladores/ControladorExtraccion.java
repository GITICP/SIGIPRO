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
import com.icp.sigipro.serpentario.dao.EventoDAO;
import com.icp.sigipro.serpentario.dao.ExtraccionDAO;
import com.icp.sigipro.serpentario.dao.SerpienteDAO;
import com.icp.sigipro.serpentario.modelos.Centrifugado;
import com.icp.sigipro.serpentario.modelos.Especie;
import com.icp.sigipro.serpentario.modelos.Evento;
import com.icp.sigipro.serpentario.modelos.Extraccion;
import com.icp.sigipro.serpentario.modelos.HelperSerpiente;
import com.icp.sigipro.serpentario.modelos.Liofilizacion;
import com.icp.sigipro.serpentario.modelos.Serpiente;
import com.icp.sigipro.serpentario.modelos.SerpientesExtraccion;
import com.icp.sigipro.serpentario.modelos.UsuariosExtraccion;
import com.icp.sigipro.utilidades.HelpersHTML;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private final int[] permisos = {1,320,321};
    //-----------------
    private ExtraccionDAO dao = new ExtraccionDAO();
    private BitacoraDAO bitacora = new BitacoraDAO();
    private UsuarioDAO usuariodao = new UsuarioDAO();
    private SerpienteDAO serpientedao = new SerpienteDAO();
    private EventoDAO eventodao = new EventoDAO();
    
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();

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
            add("registrar");
            add("centrifugado");
            add("liofilizacioninicio");
            add("liofilizacionfin");
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
        
        for (Extraccion e : extracciones){
            System.out.println(e.getNumero_extraccion());
            System.out.println(e.isIsSerpiente());
            System.out.println(e.isIsRegistro());
            System.out.println(e.isIsCentrifugado());
        }
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
            List<Serpiente> serpientesextraccion = dao.obtenerSerpientesExtraccion(id_extraccion);
            List<Usuario> usuariosextraccion = dao.obtenerUsuariosExtraccion(id_extraccion);
            Centrifugado centrifugado = dao.obtenerCentrifugado(id_extraccion);
            Liofilizacion liofilizacion = dao.obtenerLiofilizacion(id_extraccion);
            request.setAttribute("extraccion", e);
            request.setAttribute("listaSerpientes", serpientesextraccion);
            request.setAttribute("listaUsuarios", usuariosextraccion);
            request.setAttribute("ejemplares",serpientesextraccion.size());
            request.setAttribute("centrifugado", centrifugado);
            System.out.println(centrifugado);
            request.setAttribute("liofilizacion", liofilizacion);

            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(320, listaPermisos);

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
        validarPermiso(320, listaPermisos);
        
        String redireccion = "Extraccion/EditarSerpientes.jsp";
        
        Extraccion extraccion = dao.obtenerExtraccion(Integer.parseInt(request.getParameter("id_extraccion")));
        
        request.setAttribute("id_extraccion", extraccion.getId_extraccion());
        request.setAttribute("numero_extraccion", extraccion.getNumero_extraccion());

        List<Serpiente> serpientes;
        
        if (extraccion.isIngreso_cv()){
            serpientes = serpientedao.obtenerSerpientes(extraccion.getEspecie().getId_especie());
        }else{
            serpientes = serpientedao.obtenerSerpientesCuarentena(extraccion.getEspecie().getId_especie());
        }
        request.setAttribute("serpientes",serpientes);
        request.setAttribute("accion", "Editarserpientes");
        redireccionar(request, response, redireccion);
        
    }
    
    protected void getEditarserpientes(HttpServletRequest request, HttpServletResponse response,int id_extraccion) throws ServletException, IOException{
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(320, listaPermisos);
        
        String redireccion = "Extraccion/EditarSerpientes.jsp";
        
        Extraccion extraccion = dao.obtenerExtraccion(id_extraccion);
        
        request.setAttribute("id_extraccion", extraccion.getId_extraccion());
        request.setAttribute("numero_extraccion", extraccion.getNumero_extraccion());
        List<Serpiente> serpientes;
        
        if (extraccion.isIngreso_cv()){
            serpientes = serpientedao.obtenerSerpientes(extraccion.getEspecie().getId_especie());
        }else{
            serpientes = serpientedao.obtenerSerpientesCuarentena(extraccion.getEspecie().getId_especie());
        }
        request.setAttribute("serpientes",serpientes);
        request.setAttribute("accion", "Editarserpientes");
        redireccionar(request, response, redireccion);
        
    }
  // </editor-fold>
    
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    
  protected void postEditarserpientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        boolean resultado = false;
        String redireccion = "Extraccion/EditarSerpientes.jsp";
        
        int id_extraccion = Integer.parseInt(request.getParameter("id_extraccion"));
        String serpientes = request.getParameter("serpientes");
                
        List<SerpientesExtraccion> serpientesextraccion = dao.parsearSerpientesExtraccion(serpientes, id_extraccion);
        Extraccion extraccion = dao.obtenerExtraccion(id_extraccion);
        resultado = dao.insertarSerpientesExtraccion(serpientesextraccion);
        
        //Funcion que genera la bitacora
        for (SerpientesExtraccion i:serpientesextraccion){
            Evento evento = new Evento();
            evento.setEvento("Extraccion");
            evento.setExtraccion(extraccion);
            evento.setSerpiente(i.getSerpiente());
            evento.setFecha_evento(extraccion.getFecha_extraccion());
            Usuario usuario_evento = usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString());
            evento.setUsuario(usuario_evento);
            eventodao.insertarExtraccion(evento);
            bitacora.setBitacora(i.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SERPIENTESEXTRACCION,request.getRemoteAddr());
        }
        
        this.actualizarSerpientes(request, serpientesextraccion);
        if (resultado){
            request.setAttribute("mensaje", helper.mensajeDeExito("Serpientes agregadas correctamente"));
            redireccion = "Extraccion/index.jsp";
        }
        List<Extraccion> extracciones = dao.obtenerExtracciones();
        request.setAttribute("listaExtracciones", extracciones);
        redireccionar(request, response, redireccion);
        
    }
    
  protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        try{
            String redireccion = "Extraccion/Agregar.jsp";
            Extraccion e = construirObjeto(request);

            resultado = dao.insertarExtraccion(e);

            List<UsuariosExtraccion> usuariosextraccion = construirUsuarioExtraccion(request,e);

            dao.insertarUsuariosExtraccion(usuariosextraccion);
                                    
            if (resultado){
                //Funcion que genera la bitacora
                bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EXTRACCION,request.getRemoteAddr());
                for (UsuariosExtraccion i:usuariosextraccion){
                    bitacora.setBitacora(i.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_USUARIOSEXTRACCION,request.getRemoteAddr());
                }
                request.setAttribute("mensaje", helper.mensajeDeExito("Extracción agregada correctamente"));
                redireccion = "Extraccion/index.jsp";
                request.setAttribute("id_extraccion", e.getId_extraccion());
            }
            request.setAttribute("accion", "editarserpientes");
            this.getEditarserpientes(request, response,e.getId_extraccion());
        
        }catch(Exception e){
            request.setAttribute("mensaje", helper.mensajeDeError("Problemas con el numero de extracción."));
            String redireccion = "Extraccion/index.jsp";
            List<Extraccion> extracciones = dao.obtenerExtracciones();
            request.setAttribute("listaExtracciones", extracciones);
            redireccionar(request, response, redireccion);
        }
    }
  
  protected void postRegistrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Extraccion/index.jsp";
        
        int id_extraccion = Integer.parseInt(request.getParameter("id_extraccion"));
        Extraccion e = dao.obtenerExtraccion(id_extraccion);
        
        java.sql.Date fecha_registro = new java.sql.Date(new Date().getTime());
        e.setFecha_registro(fecha_registro);
        
        e.setVolumen_extraido(Float.parseFloat(request.getParameter("volumen_extraido")));
        
        Usuario usuario_registro = usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString());
        e.setUsuario_registro(usuario_registro);
        
        resultado = dao.editarExtraccion(e);
        
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(e.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_EXTRACCION,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Extracción editada correctamente"));
            redireccion = "Extraccion/index.jsp";
        }
        request.setAttribute("listaExtracciones", dao.obtenerExtracciones());
        redireccionar(request, response, redireccion);
    }
    
  protected void postCentrifugado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Extraccion/index.jsp";
        
        int id_extraccion = Integer.parseInt(request.getParameter("id_extraccion"));

        Centrifugado c = new Centrifugado();
        
        c.setId_extraccion(id_extraccion);
        java.sql.Date fecha_registro = new java.sql.Date(new Date().getTime());
        c.setFecha_volumen_recuperado(fecha_registro);
        
        c.setVolumen_recuperado(Float.parseFloat(request.getParameter("volumen_recuperado")));
        
        Usuario usuario_registro = usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString());
        c.setUsuario(usuario_registro);
        
        resultado = dao.insertarCentrifugado(c);
        
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(c.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_CENTRIFUGADO,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Centrifugado registrado correctamente"));
            redireccion = "Extraccion/index.jsp";
        }
        request.setAttribute("listaExtracciones", dao.obtenerExtracciones());
        redireccionar(request, response, redireccion);
    }
  
    protected void postLiofilizacioninicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Extraccion/index.jsp";
        
        int id_extraccion = Integer.parseInt(request.getParameter("id_extraccion"));

        Liofilizacion l = new Liofilizacion();
        
        l.setId_extraccion(id_extraccion);
        java.sql.Date fecha_registro = new java.sql.Date(new Date().getTime());
        
        l.setFecha_inicio(fecha_registro);
        
        Usuario usuario_registro = usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString());
        l.setUsuario_inicio(usuario_registro);
        
        resultado = dao.insertarLiofilizacionInicio(l);
              
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(l.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_LIOFILIZACION,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Liofilización registrado correctamente"));
            redireccion = "Extraccion/index.jsp";
        }
        request.setAttribute("listaExtracciones", dao.obtenerExtracciones());
        redireccionar(request, response, redireccion);
    }
    
    protected void postLiofilizacionfin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Extraccion/index.jsp";
        
        int id_extraccion = Integer.parseInt(request.getParameter("id_extraccion"));

        Liofilizacion l = dao.obtenerLiofilizacion(id_extraccion);
       
        java.sql.Date fecha_registro = new java.sql.Date(new Date().getTime());
        l.setFecha_fin(fecha_registro);
        
        l.setPeso_recuperado(Float.parseFloat(request.getParameter("peso_recuperado")));
        
        Usuario usuario_registro = usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString());
        l.setUsuario_fin(usuario_registro);
        
        resultado = dao.insertarLiofilizacionFin(l);
                
        if (resultado){
            //Funcion que genera la bitacora
            bitacora.setBitacora(l.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_LIOFILIZACION,request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Liofilización finalizada correctamente"));
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
        
        Especie especie = especiedao.obtenerEspecie(Integer.parseInt(request.getParameter("especie")));
        e.setEspecie(especie);
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        java.sql.Date fecha_registro = new java.sql.Date(new Date().getTime());
        e.setFecha_extraccion(fecha_registro);
        
        e.setNumero_extraccion(request.getParameter("numero_extraccion"));
        
        if (request.getParameter("ingreso_cv") != null){
            e.setIngreso_cv(true);
        }else{
            e.setIngreso_cv(false);
        }
        
        return e;
    }
    
    private boolean actualizarSerpientes(HttpServletRequest request, List<SerpientesExtraccion> se){
        boolean resultado = true;
        for(SerpientesExtraccion i:se){
            String tcabeza = request.getParameter("talla_cabeza_"+Integer.toString(i.getSerpiente().getId_serpiente()));
            String tcola = request.getParameter("talla_cola_"+Integer.toString(i.getSerpiente().getId_serpiente()));
            String tpeso = request.getParameter("peso_"+Integer.toString(i.getSerpiente().getId_serpiente()));
            String tsexo = request.getParameter("sexo_"+Integer.toString(i.getSerpiente().getId_serpiente()));
            Serpiente serpiente = i.getSerpiente();
            if (!tcabeza.equals("")){
                float talla_cabeza = Float.parseFloat(tcabeza);
                serpiente.setTalla_cabeza(talla_cabeza);
            }
            if (!tcola.equals("")){
                float talla_cola = Float.parseFloat(tcola);
                serpiente.setTalla_cola(talla_cola);
            }
            if (!tpeso.equals("")){
                float peso = Float.parseFloat(tpeso);
                serpiente.setPeso(peso);
            }
            if (!tsexo.equals("")){
                String sexo = tsexo;
                serpiente.setSexo(sexo);
            }
            List<HelperSerpiente> cambios = serpientedao.editarSerpiente(serpiente);
            
            for (HelperSerpiente j : cambios){
                Evento e = new Evento();
                java.sql.Date fecha_evento = new java.sql.Date(new Date().getTime());
                e.setSerpiente(i.getSerpiente());
                e.setFecha_evento(fecha_evento);
                Usuario usuario_evento = usuariodao.obtenerUsuario(request.getSession().getAttribute("usuario").toString());
                e.setUsuario(usuario_evento);
                if (j.getCampo_cambiado()=="sexo"){
                    e.setEvento("Sexo");
                    e.setValor_cambiado(j.getValor_cambiado());
                    eventodao.insertarCambio(e);
                }if (j.getCampo_cambiado()=="talla_cabeza"){
                    e.setEvento("Talla CabezaCloaca");
                    e.setValor_cambiado(j.getValor_cambiado());
                    eventodao.insertarCambio(e);
                }if (j.getCampo_cambiado()=="talla_cola"){
                    e.setEvento("Talla Cola");
                    e.setValor_cambiado(j.getValor_cambiado());
                    eventodao.insertarCambio(e);
                }if (j.getCampo_cambiado()=="peso"){
                    e.setEvento("Peso");
                    e.setValor_cambiado(j.getValor_cambiado());
                    eventodao.insertarCambio(e);
                }
            }
        }
        return resultado;
    }
    
    private List<UsuariosExtraccion> construirUsuarioExtraccion(HttpServletRequest request, Extraccion e){
        List<UsuariosExtraccion> resultado = new ArrayList<UsuariosExtraccion>();
        String[] usuariosextraccion = request.getParameterValues("usuarios_extraccion");
        for (String usuarioextraccion : usuariosextraccion){
            Usuario usuario = usuariodao.obtenerUsuario(Integer.parseInt(usuarioextraccion));
            UsuariosExtraccion ue = new UsuariosExtraccion (e,usuario);
            resultado.add(ue);
        }
        return resultado;
    }
    
    private List<SerpientesExtraccion> construirSerpienteExtraccion(HttpServletRequest request,Extraccion e){
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
