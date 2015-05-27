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
import com.icp.sigipro.serpentario.dao.RestriccionDAO;
import com.icp.sigipro.serpentario.dao.SolicitudDAO;
import com.icp.sigipro.serpentario.dao.VenenoDAO;
import com.icp.sigipro.serpentario.modelos.EntregasSolicitud;
import com.icp.sigipro.serpentario.modelos.Lote;
import com.icp.sigipro.serpentario.modelos.LotesEntregasSolicitud;
import com.icp.sigipro.serpentario.modelos.Restriccion;
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
import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorSolicitudVeneno", urlPatterns = {"/Serpentario/SolicitudVeneno"})
public class ControladorSolicitudVeneno extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 350, 351, 352, 353};
    //-----------------
    private SolicitudDAO dao = new SolicitudDAO();
    private VenenoDAO venenodao = new VenenoDAO();
    private LoteDAO lotedao = new LoteDAO();
    private EspecieDAO especiedao = new EspecieDAO();
    private UsuarioDAO usuariodao = new UsuarioDAO();
    private RestriccionDAO restricciondao = new RestriccionDAO();
    
    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();


    protected final Class clase = ControladorSolicitudVeneno.class;
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
        String redireccion = "SolicitudVeneno/Agregar.jsp";
        Solicitud s = new Solicitud();
        int id_veneno = Integer.parseInt(request.getParameter("id_veneno"));
        Veneno veneno = venenodao.obtenerVeneno(id_veneno);
        String nombre_usuario = request.getSession().getAttribute("usuario").toString();
        Usuario usuario = usuariodao.obtenerUsuario(nombre_usuario);
        
        if (veneno.getCantidadAsMiligramos()>veneno.getCantidad_minima() || veneno.getCantidad_minima()==0){
            Restriccion restriccion = restricciondao.obtenerRestriccion(id_veneno,usuario.getId_usuario());
            if (restriccion.getCantidad_anual()==0){
                request.setAttribute("solicitud", s);
                request.setAttribute("restriccion",restriccion);
                request.setAttribute("veneno", veneno);
                request.setAttribute("accion", "Agregar");
                redireccionar(request, response, redireccion);
            }
            else if((restriccion.getCantidad_anual()-restriccion.getCantidad_consumida())>0){
                request.setAttribute("solicitud", s);
                request.setAttribute("restriccion",restriccion);
                request.setAttribute("veneno", veneno);
                request.setAttribute("accion", "Agregar");
                redireccionar(request, response, redireccion);
            }else{
                request.setAttribute("mensaje", helper.mensajeDeError("El Usuario autenticado cuenta con "+restriccion.getCantidad_anual()+ 
                    " mg de restricción sobre el Tipo de Veneno "+veneno.getEspecie().getGenero_especie()+" y ha consumido "+restriccion.getCantidad_consumida()+" mg de Veneno. "
                        + "Ya no puede solicitar más veneno. "));
                this.getIndex(request, response);
            }
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("El Tipo de Veneno "+veneno.getEspecie().getGenero_especie()+" cuenta con "+veneno.getCantidadAsMiligramos()+ 
                    " lo cual es inferior al mínimo requerido de "+veneno.getCantidad_minima()+"."));
            this.getIndex(request, response);
        }
    }

    protected void getEntregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(353, listaPermisos);

        String redireccion = "SolicitudVeneno/Entregar.jsp";
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
        String redireccion = "SolicitudVeneno/index.jsp";
        request.setAttribute("booladmin", this.verificarAdminSolicitud(request));
        request.setAttribute("boolentrega",this.verificarEntregaSolicitud(request));
        request.setAttribute("venenos", venenodao.obtenerVenenos());
        List<Solicitud> solicitudes = dao.obtenerSolicitudes();
        request.setAttribute("listaSolicitudes", solicitudes);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "SolicitudVeneno/Ver.jsp";
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
        String redireccion = "SolicitudVeneno/Editar.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        Solicitud solicitud = dao.obtenerSolicitud(id_solicitud);
        if(solicitud.getEstado().equals("Solicitado")){
            request.setAttribute("solicitud", solicitud);
            List<Veneno> venenos = venenodao.obtenerVenenos();
            request.setAttribute("venenos", venenos);
            request.setAttribute("accion", "Editar");
            redireccionar(request, response, redireccion);
        }else{
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            request.setAttribute("mensaje", helper.mensajeDeError("No puede editar una solicitud en proceso."));
            redireccion = "SolicitudVeneno/index.jsp";
            request.setAttribute("booladmin", this.verificarAdminSolicitud(request));
            request.setAttribute("boolentrega", this.verificarEntregaSolicitud(request));
            redireccionar(request, response, redireccion);
        }
    }

       protected void getAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(352, listaPermisos);
        boolean resultado = false;
        String redireccion = "SolicitudVeneno/index.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        Solicitud s = dao.obtenerSolicitud(id_solicitud);
        
        if (s.getEstado().equals("Solicitado")){
            s.setEstado("Aprobado");
            s.setObservaciones("La solicitud fue aprobada.");
            resultado = dao.editarSolicitud(s);

            if (resultado){
                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_APROBAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUDS,request.getRemoteAddr());
                //*----------------------------*
                redireccion = "SolicitudVeneno/index.jsp";
                request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
                HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud aprobada correctamente"));
            }
            request.setAttribute("booladmin", this.verificarAdminSolicitud(request));
            request.setAttribute("boolentrega", this.verificarEntregaSolicitud(request));
            redireccionar(request, response, redireccion);
        }else{
            redireccion = "SolicitudVeneno/index.jsp";
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
            request.setAttribute("booladmin", this.verificarAdminSolicitud(request));
            request.setAttribute("boolentrega", this.verificarEntregaSolicitud(request));
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no puede ser aprobada."));
            redireccionar(request, response, redireccion);
        }
    }
    // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Post">
  
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        Solicitud s = construirObjeto(request);
        s.setEstado("Solicitado");
        Usuario usuario = usuariodao.obtenerUsuario((String)request.getSession().getAttribute("usuario"));
        s.setUsuario(usuario);
        resultado = dao.insertarSolicitud(s);
        
        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud registrada correctamente"));
        if (resultado){
            //Funcion que genera la bitacora
            BitacoraDAO bitacora = new BitacoraDAO();
            bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_AGREGAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUDS,request.getRemoteAddr());
            //*----------------------------*
        }
        this.getIndex(request, response);
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "SolicitudVeneno/Editar.jsp";
        Solicitud s = construirObjeto(request);
        s.setId_solicitud(Integer.parseInt(request.getParameter("id_solicitud")));
        String estado = request.getParameter("estado");
        s.setEstado(estado);
        if(estado.equals("Solicitado")){
            resultado = dao.editarSolicitud(s);
            
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editada correctamente"));
            if (resultado){
                //Funcion que genera la bitacora
                BitacoraDAO bitacora = new BitacoraDAO();
                bitacora.setBitacora(s.parseJSON(),Bitacora.ACCION_EDITAR,request.getSession().getAttribute("usuario"),Bitacora.TABLA_SOLICITUDS,request.getRemoteAddr());
                //*----------------------------*
                redireccion = "SolicitudVeneno/index.jsp";
                request.setAttribute("booladmin", this.verificarAdminSolicitud(request));
                request.setAttribute("boolentrega", this.verificarEntregaSolicitud(request));
            }
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            redireccionar(request, response, redireccion);
        }else{
            request.setAttribute("mensaje", helper.mensajeDeError("No se puede editar una solicitud en proceso."));
         
            redireccion = "SolicitudVeneno/index.jsp";
            request.setAttribute("booladmin", this.verificarAdminSolicitud(request));
            request.setAttribute("boolentrega", this.verificarEntregaSolicitud(request));
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            redireccionar(request, response, redireccion);
        }
    }

    protected void postRechazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "SolicitudVeneno/index.jsp";
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
                redireccion = "SolicitudVeneno/index.jsp";
                request.setAttribute("booladmin", this.verificarAdminSolicitud(request));
                request.setAttribute("boolentrega", this.verificarEntregaSolicitud(request));
                request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud rechazada correctamente"));
            }
            redireccionar(request, response, redireccion);
        }else{
            redireccion = "SolicitudVeneno/index.jsp";
            request.setAttribute("listaSolicitudes", dao.obtenerSolicitudes());
            request.setAttribute("booladmin", this.verificarAdminSolicitud(request));
            request.setAttribute("boolentrega", this.verificarEntregaSolicitud(request));
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no puede ser rechazazda."));
            redireccionar(request, response, redireccion);
        }
    }
    
    protected void postEntregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        String redireccion = "SolicitudVeneno/index.jsp";
        String usuario = request.getParameter("usuario_recibo");
        String contrasenna = request.getParameter("passw");
        
        boolean autenticacion = usuariodao.autorizarRecibo(usuario, contrasenna);
        Solicitud s = dao.obtenerSolicitud(Integer.parseInt(request.getParameter("id_solicitud_entregar")));
        
        if(autenticacion){
            if (s.getEstado().equals("Aprobado")){
                String requestLotes = request.getParameter("lotes");
                List<Lote> lotes = dao.parsearLotes(requestLotes);
                
                float cantidad_entregada = this.construirCantidadEntregada(request,lotes);
                
                s.setEstado("Entregado");
                s.setObservaciones("La solicitud fue entregada.");
                dao.editarSolicitud(s);

                EntregasSolicitud entrega = this.construirEntrega(request, s);
                resultado = dao.insertarEntrega(entrega);

                if (resultado){
                    
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
                        redireccion = "SolicitudVeneno/index.jsp";
                        request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud entregada correctamente"));
                        this.getIndex(request, response);
                    }else{
                        redireccion = "SolicitudVeneno/index.jsp";
                        request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser entregada"));
                        this.getIndex(request, response);
                    }
                    this.getIndex(request, response);
                }
            }else{
                request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no puede ser entregada."));
                this.getIndex(request, response);
            }
        }else{
            s = dao.obtenerSolicitud(Integer.parseInt(request.getParameter("id_solicitud_entregar")));
            List <Lote> lotes = lotedao.obtenerLotes(s.getEspecie());
            request.setAttribute("solicitud", s);
            request.setAttribute("lotes", lotes);
            request.setAttribute("accion", "Entregar");
            redireccion = "SolicitudVeneno/Entregar.jsp";
            request.setAttribute("mensaje", helper.mensajeDeError("El usuario o contraseña son incorrectos, o el usuario no pertenece a la sección solicitante."));
            redireccionar(request,response,redireccion);
        }
    }
    
  // </editor-fold>
  
  // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
  
    private boolean verificarAdminSolicitud(HttpServletRequest request) throws AuthenticationException{
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(352,listaPermisos);
    }
    
    private boolean verificarEntregaSolicitud(HttpServletRequest request) throws AuthenticationException{
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(353,listaPermisos);
    }
    
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
        es.setCantidad_entregada(Float.parseFloat(request.getParameter("cantidad_entregada")));
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
    
    private float construirCantidadEntregada(HttpServletRequest request, List<Lote> lotes) {
        float cantidad_entregada = 0;
        for (Lote i : lotes){
            String columna = "cantidad_"+Integer.toString(i.getId_lote());
            String cantidad = request.getParameter(columna);
            float cantidad_lote = Float.parseFloat(cantidad);
            
            cantidad_entregada += cantidad_lote;
            
        }
        return cantidad_entregada;
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