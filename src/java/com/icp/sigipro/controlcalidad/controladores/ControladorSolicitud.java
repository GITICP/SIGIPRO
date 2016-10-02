/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.google.gson.Gson;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.SolicitudDAO;
import com.icp.sigipro.controlcalidad.dao.TipoMuestraDAO;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Grupo;
import com.icp.sigipro.controlcalidad.modelos.Muestra;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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
@WebServlet(name = "ControladorSolicitud", urlPatterns = {"/ControlCalidad/Solicitud"})
public class ControladorSolicitud extends SIGIPROServlet {

    //Solicitar, Recibir, Anular
    private final int[] permisos = {550, 551, 552, 553, 554, 555, 556};
    //-----------------
    private final SolicitudDAO dao = new SolicitudDAO();
    private final TipoMuestraDAO tipomuestradao = new TipoMuestraDAO();
    private final UsuarioDAO usuariodao = new UsuarioDAO();

    protected final Class clase = ControladorSolicitud.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("historial");
            add("ver");
            add("agregar");
            add("editar");
            add("solicitudesajax");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
            add("recibir");
            add("anular");
            add("agregargrupo");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {

        validarPermiso(550, request);

        String redireccion = "Solicitud/Agregar.jsp";
        SolicitudCC s = new SolicitudCC();
        request.setAttribute("solicitud", s);

        List<TipoMuestra> tipomuestras = tipomuestradao.obtenerTiposDeMuestraSolicitud();
        request.setAttribute("tipomuestras", tipomuestras);
        request.setAttribute("tipomuestraparse", this.parseListaTipoMuestra(tipomuestras));
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);

    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Solicitud/index.jsp";
        request.setAttribute("boolrecibir", this.verificarRecibirSolicitud(request));
        request.setAttribute("boolanular", this.verificarAnularSolicitud(request));
        request.setAttribute("boolrealizar", this.verificarRealizarSolicitud(request));

        if (this.verificarAnularSolicitud(request) || this.verificarVerTodasSolicitud(request) || this.verificarRecibirSolicitud(request)) {
            List<SolicitudCC> solicitudes = dao.obtenerTodasSolicitudes();
            request.setAttribute("listaSolicitudes", solicitudes);
        } else {
            int id_usuario = (int) request.getSession().getAttribute("idusuario");
            List<SolicitudCC> solicitudes = dao.obtenerSeccionSolicitudes(id_usuario);
            request.setAttribute("listaSolicitudes", solicitudes);
        }

        redireccionar(request, response, redireccion);
    }

    protected void getSolicitudesajax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        String resultado = "";

        try {
            List<SolicitudCC> solicitudes = dao.obtenerTodasSolicitudes();
            Gson gson = new Gson();
            resultado = gson.toJson(solicitudes);

        } catch (Exception sig_ex) {
            // Enviar error al AJAX
        }

        out.print(resultado);

        out.flush();
    }

    protected void getHistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Solicitud/Historial.jsp";
        request.setAttribute("boolrecibir", this.verificarRecibirSolicitud(request));
        request.setAttribute("boolanular", this.verificarAnularSolicitud(request));
        request.setAttribute("boolrealizar", this.verificarRealizarSolicitud(request));

        if (this.verificarAnularSolicitud(request) || this.verificarVerTodasSolicitud(request) || this.verificarRecibirSolicitud(request)) {
            List<SolicitudCC> solicitudes = dao.obtenerSolicitudesHistorial();
            request.setAttribute("listaSolicitudes", solicitudes);
        } else {
            int id_usuario = (int) request.getSession().getAttribute("idusuario");
            List<SolicitudCC> solicitudes = dao.obtenerSeccionSolicitudesHistorial(id_usuario);
            request.setAttribute("listaSolicitudes", solicitudes);
        }
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Solicitud/Ver.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));

        try {
            SolicitudCC s = dao.obtenerSolicitud(id_solicitud);
            request.setAttribute("solicitud", s);
            request.setAttribute("boolrecibir", this.verificarRecibirSolicitud(request));
            request.setAttribute("boolanular", this.verificarAnularSolicitud(request));
            request.setAttribute("booleditar", this.verificarEditarSolicitud(request));
            request.setAttribute("boolrealizar", this.verificarRealizarSolicitud(request));

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("No se pudo obtener la solicitud. Notifique al administrador del sistema."));
        }

        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        validarPermiso(553, request);
        String redireccion = "Solicitud/Editar.jsp";
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        SolicitudCC solicitud = dao.obtenerSolicitud(id_solicitud);
        List<List<String>> listaSolicitud = dao.obtenerSolicitudEditar(id_solicitud);
        //Lista los ids de los tipos de muestras ya solicitadas
        String idTipoMuestras = "";
        String idGrupos = "";
        for (List<String> s : listaSolicitud) {
            idTipoMuestras += s.get(0);
            idTipoMuestras += ",";
            idGrupos += s.get(5);
            idGrupos += ",";
        }
        if (!idTipoMuestras.equals("")) {
            idTipoMuestras = idTipoMuestras.substring(0, idTipoMuestras.length() - 1);
        }
        if (!idGrupos.equals("")) {
            idGrupos = idGrupos.substring(0, idGrupos.length() - 1);
        }

        List<TipoMuestra> tipomuestras = tipomuestradao.obtenerTiposDeMuestraSolicitud();

        solicitud.prepararEditarSolicitud(request);
        request.setAttribute("listaSolicitud", listaSolicitud);
        request.setAttribute("listaTM", idTipoMuestras);
        request.setAttribute("listaGrupos", idGrupos);
        request.setAttribute("solicitud", solicitud);
        request.setAttribute("tipomuestras", tipomuestras);
        request.setAttribute("tipomuestraparse", this.parseListaTipoMuestra(tipomuestras));
        request.setAttribute("numero_solicitud", solicitud.getNumero_solicitud());
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAnular(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(552, request);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_anular"));
        String observaciones = request.getParameter("observaciones");
        boolean resultado = false;
        try {
            resultado = dao.anularSolicitud(id_solicitud, observaciones);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_solicitud, Bitacora.ACCION_ANULAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUDCC, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud anulada correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser anulada ya que tiene otros objetos asociados."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser anulada ya que tiene otros objetos asociados."));
            this.getIndex(request, response);
        }

    }

    protected void postRecibir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(551, request);

        boolean resultado = false;
        String usuario = request.getParameter("usuario_recibo");
        String contrasenna = request.getParameter("passw");

        boolean autenticacion = usuariodao.autorizarRecibo(usuario, contrasenna);

        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud_recibir"));

        if (autenticacion) {
            try {
                SolicitudCC solicitud = new SolicitudCC();

                solicitud.setId_solicitud(id_solicitud);
                Usuario usuario_recibido = usuariodao.obtenerUsuario(usuario);
                solicitud.setUsuario_recibido(usuario_recibido);

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

                Timestamp fecha_recibido = new java.sql.Timestamp(new Date().getTime());
                solicitud.setFecha_recibido(fecha_recibido);

                resultado = dao.recibirSolicitud(solicitud);

                if (resultado) {
                    //Funcion que genera la bitacora 
                    bitacora.setBitacora(solicitud.parseJSON(), Bitacora.ACCION_RECIBIR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUDCC, request.getRemoteAddr());
                    //----------------------------
                    request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud recibida correctamente"));
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser recibida por un error del sistema."));
                }
                this.getIndex(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser recibida por un error del sistema."));
                this.getIndex(request, response);

            }
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("El usuario o contraseña son incorrectos."));
            this.getIndex(request, response);
        }

    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {

        validarPermiso(550, request);

        boolean resultado = false;
        SolicitudCC s = construirObjeto(request);
        s.setEstado("Solicitado");
        Usuario usuario = usuariodao.obtenerUsuario((String) request.getSession().getAttribute("usuario"));
        s.setUsuario_solicitante(usuario);

        String objeto_por_asociar = request.getParameter("objeto-relacionado");
        s.setTipoAsociacion(objeto_por_asociar);
        s.asociar(request);

        resultado = dao.entregarSolicitud(s);
        if (resultado) {
            for (AnalisisGrupoSolicitud ags : s.getAnalisis_solicitud()) {
                ags.getGrupo().setSolicitud(s);
                dao.insertarMuestrasGrupo(ags.getGrupo());
                bitacora.setBitacora(ags.getGrupo().parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_GRUPO, request.getRemoteAddr());
                for (String a : ags.getLista_analisis()) {
                    Analisis analisis = new Analisis();
                    analisis.setId_analisis(Integer.parseInt(a));
                    ags.setAnalisis(analisis);
                    dao.insertarAnalisisGrupoSolicitud(ags);
                    bitacora.setBitacora(ags.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ANALISISGRUPOSOLICITUD, request.getRemoteAddr());
                }
            }
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud agregada correctamente, número generado automáticamente: " + s.getNumero_solicitud() + "."));
            //Funcion que genera la bitacora
            bitacora.setBitacora(s.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUDCC, request.getRemoteAddr());
            //*----------------------------*
            this.getIndex(request, response);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser agregada. Inténtelo de nuevo."));
            this.getAgregar(request, response);
        }

    }

    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {

        validarPermiso(553, request);

        boolean resultado = false;
        SolicitudCC s = construirObjeto(request);
        int id_solicitud = Integer.parseInt(request.getParameter("id_solicitud"));
        s.setId_solicitud(id_solicitud);

        String objeto_por_asociar = request.getParameter("objeto-relacionado");
        s.setTipoAsociacion(objeto_por_asociar);
        s.asociar(request);

        if (s.tieneTipoAsociacion()) {
            dao.editarSolicitud(s);
        }

        String idGrupos = request.getParameter("listaGrupos");
        String[] grupos = idGrupos.replace(" ", "").split(",");

        resultado = dao.eliminarGrupo(grupos);

        if (resultado) {
            for (AnalisisGrupoSolicitud ags : s.getAnalisis_solicitud()) {
                ags.getGrupo().setSolicitud(s);
                dao.insertarMuestrasGrupo(ags.getGrupo());
                bitacora.setBitacora(ags.getGrupo().parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_GRUPO, request.getRemoteAddr());
                for (String a : ags.getLista_analisis()) {
                    Analisis analisis = new Analisis();
                    analisis.setId_analisis(Integer.parseInt(a));
                    ags.setAnalisis(analisis);
                    dao.insertarAnalisisGrupoSolicitud(ags);
                    bitacora.setBitacora(ags.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ANALISISGRUPOSOLICITUD, request.getRemoteAddr());
                }
            }
            //Funcion que genera la bitacora
            bitacora.setBitacora(s.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_SOLICITUDCC, request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Solicitud editado correctamente"));
            this.getIndex(request, response);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Solicitud no pudo ser editado. Inténtelo de nuevo."));
            request.setAttribute("id_solicitud", id_solicitud);
            this.getEditar(request, response);
        }
    }

    protected void postAgregargrupo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(556, request);

        String redireccion = "Solicitud/Ver.jsp";

        Grupo grupo = construirGrupo(request);

        try {
            dao.insertarGrupoConAnalisis(grupo);

            request.setAttribute("mensaje", helper.mensajeDeExito("Agrupación creada con éxito."));
        } catch (SIGIPROException ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(ex.getMessage()));
        }

        try {
            SolicitudCC s = dao.obtenerSolicitud(grupo.getSolicitud().getId_solicitud());
            request.setAttribute("solicitud", s);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("No se pudo obtener la solicitud. Notifique al administrador del sistema."));
        }

        redireccionar(request, response, redireccion);
    }
  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private SolicitudCC construirObjeto(HttpServletRequest request) {
        SolicitudCC s = new SolicitudCC();

        Timestamp fecha_solicitud = new java.sql.Timestamp(new Date().getTime());
        s.setFecha_solicitud(fecha_solicitud);

        String lista = request.getParameter("listaMuestras");
        String[] listaMuestras = lista.split(",");

        s.setAnalisis_solicitud(new ArrayList<AnalisisGrupoSolicitud>());

        if (listaMuestras.length > 0 && !listaMuestras[0].equals("")) {
            for (String i : listaMuestras) {
                String tipo_muestra = request.getParameter("tipomuestra_" + i);
                String identificadores = request.getParameter("identificadores_" + i);
                String fecha = request.getParameter("fechadescarte_" + i).replace(" ", "");
                String[] analisis = request.getParameterValues("analisis_" + i);

                TipoMuestra tm = new TipoMuestra();
                tm.setId_tipo_muestra(Integer.parseInt(tipo_muestra));
                String[] listaIdentificadores = identificadores.split(",");

                for (String l : listaIdentificadores) {
                    AnalisisGrupoSolicitud ags = new AnalisisGrupoSolicitud();

                    Muestra muestra = new Muestra();

                    Grupo grupo = new Grupo();

                    muestra.setIdentificador(l);
                    muestra.setTipo_muestra(tm);
                    if (!fecha.equals("")) {
                        SimpleDateFormat formatoFechaDescarte = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date fecha_descarte;
                        java.sql.Date fecha_descarteSQL;
                        try {
                            fecha_descarte = formatoFechaDescarte.parse(fecha);
                            fecha_descarteSQL = new java.sql.Date(fecha_descarte.getTime());
                            muestra.setFecha_descarte_estimada(fecha_descarteSQL);
                        } catch (ParseException ex) {

                        }
                    }
                    List<Muestra> muestras = new ArrayList<Muestra>();
                    muestras.add(muestra);
                    grupo.setGrupos_muestras(muestras);
                    ags.setGrupo(grupo);
                    ags.setLista_analisis(analisis);
                    s.getAnalisis_solicitud().add(ags);
                }

            }
        }

        return s;
    }

    private Grupo construirGrupo(HttpServletRequest request) {

        Grupo grupo = new Grupo();

        SolicitudCC solicitud = new SolicitudCC();
        solicitud.setId_solicitud(Integer.parseInt(request.getParameter("id_solicitud")));

        grupo.setSolicitud(solicitud);

        List<Muestra> muestras = new ArrayList<Muestra>();
        String[] ids_muestras = request.getParameterValues("ids_muestras");

        for (String id_muestra : ids_muestras) {
            Muestra m = new Muestra();
            m.setId_muestra(Integer.parseInt(id_muestra));
            muestras.add(m);
        }

        grupo.setGrupos_muestras(muestras);

        List<Analisis> analisis = new ArrayList<Analisis>();
        String[] ids_analisis = request.getParameter("ids_analisis").split(",");

        for (String id_analisis : ids_analisis) {
            Analisis a = new Analisis();
            a.setId_analisis(Integer.parseInt(id_analisis));
            analisis.add(a);
        }

        grupo.setAnalisis(analisis);

        return grupo;
    }

    private boolean verificarRecibirSolicitud(HttpServletRequest request) throws AuthenticationException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(551, listaPermisos);
    }

    private boolean verificarRealizarSolicitud(HttpServletRequest request) throws AuthenticationException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(541, listaPermisos);
    }

    private boolean verificarAnularSolicitud(HttpServletRequest request) throws AuthenticationException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(552, listaPermisos);
    }

    private boolean verificarVerTodasSolicitud(HttpServletRequest request) throws AuthenticationException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(554, listaPermisos);
    }

    private boolean verificarEditarSolicitud(HttpServletRequest request) throws AuthenticationException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        return verificarPermiso(553, listaPermisos);
    }

    public List<String> parseListaTipoMuestra(List<TipoMuestra> tipomuestra) {
        List<String> respuesta = new ArrayList<String>();
        for (TipoMuestra tm : tipomuestra) {
            String tipo = "[";
            tipo += tm.getId_tipo_muestra() + ",";
            tipo += "\"" + tm.getNombre() + "\"]";
            respuesta.add(tipo);
        }
        return respuesta;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        } else {
            lista_acciones = accionesPost;
        }
        if (lista_acciones.contains(accion.toLowerCase())) {
            String nombreMetodo = accionHTTP + Character.toUpperCase(accion.charAt(0)) + accion.substring(1);
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        } else {
            Method metodo = clase.getDeclaredMethod(accionHTTP + "Index", HttpServletRequest.class, HttpServletResponse.class);
            metodo.invoke(this, request, response);
        }
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
}
