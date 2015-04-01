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
import com.icp.sigipro.serpentario.dao.LoteDAO;
import com.icp.sigipro.serpentario.dao.SolicitudDAO;
import com.icp.sigipro.serpentario.dao.VenenoDAO;
import com.icp.sigipro.serpentario.modelos.EntregasSolicitud;
import com.icp.sigipro.serpentario.modelos.Lote;
import com.icp.sigipro.serpentario.modelos.LotesEntregasSolicitud;
import com.icp.sigipro.serpentario.modelos.Solicitud;
import com.icp.sigipro.serpentario.modelos.Veneno;
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
@WebServlet(name = "ControladorSolicitudes", urlPatterns = {"/Serpentario/Solicitudes"})
public class ControladorSolicitudes extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 350, 351, 352, 353};
    //-----------------
    private SolicitudDAO dao = new SolicitudDAO();
    private VenenoDAO venenodao = new VenenoDAO();
    private LoteDAO lotedao = new LoteDAO();
    private EspecieDAO especiedao = new EspecieDAO();
    private UsuarioDAO usuariodao = new UsuarioDAO();

    protected final Class clase = ControladorSolicitudes.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
        {
            add("index");
            add("ver");
            add("agregar");
            add("editar");
            add("entregar");
            add("aprobar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregar");
            add("editar");
            add("rechazar");
            add("entregar");
           
            
        }
    };

  // <editor-fold defaultstate="collapsed" desc="Métodos Get">
  
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(350, listaPermisos);

        String redireccion = "Solicitudes/Agregar.jsp";
        Solicitud s = new Solicitud();
        List<Veneno> venenos = venenodao.obtenerVenenos();
        request.setAttribute("solicitud", s);
        request.setAttribute("venenos", venenos);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getEntregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(353, listaPermisos);

        String redireccion = "Solicitudes/Entregar.jsp";
        Solicitud s = new Solicitud();
        s = dao.obtenerSolicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        List <Lote> lotes = lotedao.obtenerLotes(s.getEspecie());
        request.setAttribute("solicitud", s);
        request.setAttribute("lotes", lotes);
        request.setAttribute("accion", "Entregar");
        redireccionar(request, response, redireccion);
    }
    
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Solicitudes/index.jsp";
        request.setAttribute("booladmin", true);

        List<Solicitud> solicitudes = dao.obtenerSolicitudes();
        request.setAttribute("listaSolicitudes", solicitudes);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Solicitudes/Ver.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        try {
            Solicitud s = dao.obtenerSolicitud(id_solicitud);
            request.setAttribute("solicitud", s);
            try{
                EntregasSolicitud e = dao.obtenerEntrega(s);
                request.setAttribute("entrega", e);
            }catch (Exception ex){
                request.setAttribute("entrega", null);
            }
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(351, listaPermisos);
        String redireccion = "Solicitudes/Editar.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
        if(solicitud.getEstado().equals("Solicitado")){
            request.setAttribute("solicitud", solicitud);
            request.setAttribute("accion", "Editar");
            redireccionar(request, response, redireccion);
        }else{
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            request.setAttribute("mensaje", helper.mensajeDeError("No puede editar una solicitud en proceso."));
            redireccion = "Solicitudes/index.jsp";
            redireccionar(request, response, redireccion);
        }
    }

       protected void getAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Solicitudes/index.jsp";
        Solicitud s = dao.obtenerSolicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        
        if (s.getEstado().equals("Solicitado")){
            s.setEstado("Aprobado");
            s.setObservaciones("La solicitud fue aprobada.");
            resultado = dao.editarSolicitud(s);

            if (resultado){
                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_APROBAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUDS,request.getRemoteAddr());
                //*----------------------------*
                redireccion = "Solicitudes/index.jsp";
                request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
                HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud aprobada correctamente"));
            }
            redireccionar(request, response, redireccion);
        }else{
            redireccion = "Solicitudes/index.jsp";
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no puede ser aprobada."));
            redireccionar(request, response, redireccion);
        }
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Solicitudes/Agregar.jsp";
        Solicitud s = construirObjeto(request);
        s.setEstado("Solicitado");
        Usuario usuario = usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario"));
        s.setUsuario(usuario);
        resultado = dao.insertarSolicitud(s);
        //Funcion que genera la bitacora
        BitacoraDAO bitacora = new BitacoraDAO();
        bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUDS,request.getRemoteAddr());
        //*----------------------------*
        HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud registrada correctamente"));
        if (resultado){
            redireccion = "Solicitudes/index.jsp";
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
        }
        redireccionar(request, response, redireccion);
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Solicitudes/Editar.jsp";
        Solicitud s = construirObjeto(request);
        s.setId_solicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        if(s.getEstado().equals("Solicitado")){
            resultado = dao.editarSolicitud(s);
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUDS,request.getRemoteAddr());
            //*----------------------------*
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editada correctamente"));
            if (resultado){
                redireccion = "Solicitudes/index.jsp";
            }
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            redireccionar(request, response, redireccion);
        }else{
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            request.setAttribute("mensaje", helper.mensajeDeError("No se puede editar una solicitud en proceso."));
         
            redireccion = "Solicitudes/index.jsp";
            
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            redireccionar(request, response, redireccion);
        }
    }

    protected void postRechazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Solicitudes/index.jsp";
        Solicitud s = dao.obtenerSolicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        if (s.getEstado().equals("Solicitado")){
            s.setEstado("Rechazado");
            s.setObservaciones(request.getParameter("observaciones"));
            resultado = dao.editarSolicitud(s);

            if (resultado){
                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_RECHAZAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUDS,request.getRemoteAddr());
                //*----------------------------*
                redireccion = "Solicitudes/index.jsp";
                request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
                HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud rechazada correctamente"));
            }
            redireccionar(request, response, redireccion);
        }else{
            redireccion = "Solicitudes/index.jsp";
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no puede ser rechazazda."));
            redireccionar(request, response, redireccion);
        }
    }
    
    protected void postEntregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "Solicitudes/index.jsp";
        Solicitud s = dao.obtenerSolicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        
        if (s.getEstado().equals("Aprobado")){
            s.setEstado("Entregado");
            s.setObservaciones("La solicitud fue entregada.");
            dao.editarSolicitud(s);
            
            EntregasSolicitud entrega = this.construirEntrega(request, s);
            resultado = dao.insertarEntrega(entrega);
            
            if (resultado){
                String requestLotes = request.getParameter("lotes");
                List<Lote> lotes = dao.parsearLotes(requestLotes);
                List<LotesEntregasSolicitud> lotesentrega = this.construirLotes(request, entrega, lotes);

                resultado = dao.insertarLotesEntrega(lotesentrega);

                if (resultado){
                    //Funcion que genera la bitacora
                    BitacoraDAO bitacora = new BitacoraDAO();
                    bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_ENTREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUDS,request.getRemoteAddr());
                    bitacora.setBitacora(entrega.parseJSON(), Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_ENTREGASSOLICITUD,request.getRemoteAddr());
                    
                    for (LotesEntregasSolicitud i : lotesentrega){
                        bitacora.setBitacora(i.parseJSON(), Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_LOTESENTREGASSOLICITUD,request.getRemoteAddr());
                    }
                    //*----------------------------*
                    redireccion = "Solicitudes/index.jsp";
                    request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                    request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud entregada correctamente"));
                }else{
                    redireccion = "Solicitudes/index.jsp";
                    request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
                    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                    request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser entregada"));
                }
                redireccionar(request, response, redireccion);
            }
        }else{
            redireccion = "Solicitudes/index.jsp";
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no puede ser entregada."));
            redireccionar(request, response, redireccion);
        }
    }
    
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private Solicitud construirObjeto(HttpServletRequest request) {
        Solicitud s = new Solicitud();
        s.setEspecie(especiedao.obtenerEspecie(Integer.parseInt(request.getParameter("especie"))));
        s.setCantidad(Float.parseFloat(request.getParameter("cantidad")));
        s.setProyecto(request.getParameter("proyecto"));
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        java.sql.Date fecha_solicitud = new java.sql.Date(new Date().getTime());
        s.setFecha_solicitud(fecha_solicitud);

        return s;
    }
    
    private EntregasSolicitud construirEntrega(HttpServletRequest request, Solicitud s) {
        EntregasSolicitud es = new EntregasSolicitud();
        es.setCantidad_entregada(Float.parseFloat(request.getParameter("cantidad_entregad")));
        es.setSolicitud(s);
        es.setUsuario_entrega(usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario")));
        es.setUsuario_recibo(usuariodao.obtenerUsuario(request.getParameter("usuario_recibo")));
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        java.sql.Date fecha_entrega = new java.sql.Date(new Date().getTime());
        es.setFecha_entrega(fecha_entrega);

        return es;
    }
    
    private List<LotesEntregasSolicitud> construirLotes(HttpServletRequest request, EntregasSolicitud es, List<Lote> lotes) {
        List <LotesEntregasSolicitud> les = new ArrayList<LotesEntregasSolicitud>();
        for (Lote i : lotes){
            LotesEntregasSolicitud le = new LotesEntregasSolicitud();
            
            le.setEntrega_solicitud(es);
            le.setLote(i);
            String columna = "cantidad_"+Integer.toString(i.getId_lote());
            String cantidad = request.getParameter(columna);
            le.setCantidad(Float.parseFloat(cantidad));
            
            les.add(le);
        }
        return les;
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