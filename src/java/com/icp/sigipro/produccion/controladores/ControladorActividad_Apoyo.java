/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.google.gson.Gson;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.dao.SubBodegaDAO;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.core.formulariosdinamicos.ProduccionXSLT;
import com.icp.sigipro.core.formulariosdinamicos.ProduccionXSLTDAO;
import com.icp.sigipro.produccion.dao.Actividad_ApoyoDAO;
import com.icp.sigipro.produccion.dao.Categoria_AADAO;
import com.icp.sigipro.produccion.modelos.Actividad_Apoyo;
import com.icp.sigipro.produccion.modelos.Categoria_AA;
import com.icp.sigipro.produccion.modelos.Respuesta_AA;
import com.icp.sigipro.seguridad.dao.SeccionDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Seccion;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperTransformaciones;
import com.icp.sigipro.utilidades.HelperXML;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorActividad_Apoyo", urlPatterns = {"/Produccion/Actividad_Apoyo"})
public class ControladorActividad_Apoyo extends SIGIPROServlet {

    //CRUD, Activar Actividad, Aprobaciones (4), Activar Respuesta, Realizar Actividad
    private final int[] permisos = {670, 671, 672, 673, 674, 675, 676, 677};
    //-----------------
    private final Actividad_ApoyoDAO dao = new Actividad_ApoyoDAO();
    private final ProduccionXSLTDAO produccionxsltdao = new ProduccionXSLTDAO();
    private final SubBodegaDAO subbodegadao = new SubBodegaDAO();
    private final SeccionDAO secciondao = new SeccionDAO();
    private final Categoria_AADAO categoriadao = new Categoria_AADAO();
    private final UsuarioDAO usuariodao = new UsuarioDAO();

    private final HelperTransformaciones helper_transformaciones = HelperTransformaciones.getHelperTransformaciones();

    private int nombre_campo;

    protected final Class clase = ControladorActividad_Apoyo.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("indexnormal");
            add("indexactividades");
            add("veractividad");
            add("verrespuesta");
            add("verhistorialrespuesta");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("realizar");
            add("verhistorial");
            add("activar");
            add("activarrespuesta");
            add("repetir");
            add("actividadesajax");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregareditar");
            add("realizar");
            add("repetir");
            add("rechazar");
            add("aprobar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        validarPermiso(670, request);

        String redireccion = "Actividad_Apoyo/Agregar.jsp";
        Actividad_Apoyo aa = new Actividad_Apoyo();
        try {
            Categoria_AA categoria = new Categoria_AA();
            categoria.setId_categoria_aa(Integer.parseInt(request.getParameter("id_categoria_aa")));
            aa.setCategoria(categoria);
        } catch (Exception e) {

        }
        request.setAttribute("actividad", aa);
        request.setAttribute("accion", "Agregar");
        request.setAttribute("listaSubbodegas", this.parseListaSubbodegas(subbodegadao.obtenerSubBodegas()));
        request.setAttribute("listaSecciones", this.parseListaSecciones(secciondao.obtenerSecciones()));
        request.setAttribute("categorias", categoriadao.obtenerCategorias_AA());
        request.setAttribute("orden", "");
        request.setAttribute("cantidad", 0);
        request.setAttribute("contador", 0);
        redireccionar(request, response, redireccion);
    }

    protected void getIndexnormal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/index.jsp";
        List<Actividad_Apoyo> actividades = dao.obtenerActividades_Apoyo();
        request.setAttribute("listaActividades", actividades);
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/indexBotonesCategoria.jsp";
        List<Categoria_AA> categorias = categoriadao.obtenerCategorias_AA();
        request.setAttribute("listaCategorias", categorias);
        redireccionar(request, response, redireccion);
    }

    protected void getIndexactividades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/indexBotonesActividad.jsp";
        int id_categoria_aa = Integer.parseInt(request.getParameter("id_categoria_aa"));
        Categoria_AA categoria = categoriadao.obtenerCategoria_AA(id_categoria_aa);
        request.setAttribute("categoria", categoria);
        List<Actividad_Apoyo> actividades = dao.obtenerActividades_Apoyo(id_categoria_aa);
        request.setAttribute("listaActividades", actividades);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/Ver.jsp";
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        ProduccionXSLT xslt;
        Actividad_Apoyo aa;

        try {
            aa = dao.obtenerActividad_Apoyo(id_actividad);
            xslt = produccionxsltdao.obtenerProduccionXSLTVerFormulario();
            if (aa.getEstructura() != null) {
                String formulario = helper_transformaciones.transformar(xslt, aa.getEstructura());
                request.setAttribute("cuerpo_datos", formulario);
            } else {
                request.setAttribute("cuerpo_datos", null);
            }
            request.setAttribute("actividad", aa);
            redireccionar(request, response, redireccion);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

    }

    protected void getVeractividad(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/VerActividad.jsp";
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        Actividad_Apoyo actividad = dao.obtenerActividad_Apoyo(id_actividad);
        List<Respuesta_AA> respuestas = dao.obtenerRespuestas(actividad);
        request.setAttribute("actividad", actividad);
        request.setAttribute("listaRespuestas", respuestas);
        redireccionar(request, response, redireccion);

    }
    
    protected void getVeractividad(HttpServletRequest request, HttpServletResponse response,int id_actividad) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/VerActividad.jsp";
        Actividad_Apoyo actividad = dao.obtenerActividad_Apoyo(id_actividad);
        List<Respuesta_AA> respuestas = dao.obtenerRespuestas(actividad);
        request.setAttribute("actividad", actividad);
        request.setAttribute("listaRespuestas", respuestas);
        redireccionar(request, response, redireccion);

    }

    protected void getVerhistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/VerHistorial.jsp";
        int id_historial = Integer.parseInt(request.getParameter("id_historial"));
        ProduccionXSLT xslt;
        Actividad_Apoyo aa;

        try {
            aa = dao.obtenerHistorial(id_historial);
            xslt = produccionxsltdao.obtenerProduccionXSLTVerFormulario();
            if (aa.getEstructura() != null) {
                String formulario = helper_transformaciones.transformar(xslt, aa.getEstructura());
                request.setAttribute("cuerpo_datos", formulario);
            } else {
                request.setAttribute("cuerpo_datos", null);
            }
            request.setAttribute("actividad", aa);
            redireccionar(request, response, redireccion);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

    }
    
    protected void getActivarrespuesta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(676, request);
        int id_historial = Integer.parseInt(request.getParameter("id_historial"));
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        int version = dao.obtenerVersionRespuesta(id_historial);
        boolean resultado = false;
        try {
            resultado = dao.activarVersionRespuesta(version, id_respuesta);
            if (resultado) {
                //Funcion que genera la bitacora 
                Respuesta_AA respuesta = new Respuesta_AA();
                respuesta.setId_historial(id_historial);
                respuesta.setId_respuesta(id_respuesta);
                respuesta.setVersion(version);
                bitacora.setBitacora(respuesta.parseJSON(), Bitacora.ACCION_ACTIVAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAAA, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Versión de Respuesta activado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Versión de Respuesta no pudo ser activado."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Versión de Respuesta no pudo ser activado."));
            this.getIndex(request, response);
        }

    }
    
    protected void getVerhistorialrespuesta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/VerHistorialRespuesta.jsp";
        int id_historial = Integer.parseInt(request.getParameter("id_historial"));
        ProduccionXSLT xslt;
        Respuesta_AA raa;

        try {
            raa = dao.obtenerHistorialRespuesta(id_historial);
            raa.setActividad(dao.obtenerActividad_Apoyo(raa.getActividad().getId_actividad()));
            xslt = produccionxsltdao.obtenerProduccionXSLTVerResultado();
            if (raa.getRespuesta() != null) {
                String formulario = helper_transformaciones.transformar(xslt, raa.getRespuesta());
                request.setAttribute("cuerpo_datos", formulario);
            } else {
                request.setAttribute("cuerpo_datos", null);
            }
            request.setAttribute("respuesta", raa);
            redireccionar(request, response, redireccion);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

    }
    
    protected void getVerrespuesta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/VerRespuesta.jsp";
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        ProduccionXSLT xslt;
        Respuesta_AA r;

        try {
            r = dao.obtenerRespuesta(id_respuesta);
            r.setActividad(dao.obtenerActividad_Apoyo(r.getActividad().getId_actividad()));
            xslt = produccionxsltdao.obtenerProduccionXSLTVerResultado();
            if (r.getRespuesta() != null) {
                String formulario = helper_transformaciones.transformar(xslt, r.getRespuesta());
                request.setAttribute("cuerpo_datos", formulario);
            } else {
                request.setAttribute("cuerpo_datos", null);
            }
            request.setAttribute("respuesta", r);
            redireccionar(request, response, redireccion);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

    }

    protected void getActivar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(671, request);
        int id_historial = Integer.parseInt(request.getParameter("id_historial"));
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        int version = dao.obtenerVersion(id_historial);
        boolean resultado = false;
        try {
            resultado = dao.activarVersion(version, id_actividad);
            if (resultado) {
                //Funcion que genera la bitacora 
                Actividad_Apoyo actividad = new Actividad_Apoyo();
                actividad.setId_historial(id_historial);
                actividad.setId_actividad(id_actividad);
                actividad.setVersion(version);
                bitacora.setBitacora(actividad.parseJSON(), Bitacora.ACCION_ACTIVAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Versión de Actividad de Apoyo activada correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Versión de Actividad de Apoyo no pudo ser activada."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Versión de Actividad de Apoyo no pudo ser activado."));
            this.getIndex(request, response);
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, SIGIPROException, IOException, SQLException, ParserConfigurationException, SAXException {
        validarPermiso(670, request);
        String redireccion = "Actividad_Apoyo/Editar.jsp";
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        Actividad_Apoyo aa = dao.obtenerActividad_Apoyo(id_actividad);

        HelperXML xml = new HelperXML(aa.getEstructura(), "produccion");

        HashMap<Integer, HashMap> diccionario_formulario = xml.getDictionary();

        System.out.println(diccionario_formulario);
        List<Integer> lista = new ArrayList<Integer>();
        int cantidad = 0;
        if (diccionario_formulario != null) {
            Set<Integer> it = diccionario_formulario.keySet();
            lista.addAll(it);

            for (Integer i : lista) {
                if (diccionario_formulario.get(i).containsKey("cantidad")) {
                    try {
                        cantidad += ((Integer) diccionario_formulario.get(i).get("cantidad"));
                    } catch (Exception e) {

                    }
                }

            }
        }

        List<SubBodega> subbodegas = subbodegadao.obtenerSubBodegas();
        List<Seccion> secciones = secciondao.obtenerSecciones();

        request.setAttribute("actividad", aa);
        request.setAttribute("lista", lista);
        request.setAttribute("contador", lista.size());
        request.setAttribute("listaSubbodegas", this.parseListaSubbodegas(subbodegas));
        request.setAttribute("listaSecciones", this.parseListaSecciones(secciones));
        request.setAttribute("categorias", categoriadao.obtenerCategorias_AA());
        request.setAttribute("subbodegas", subbodegas);
        request.setAttribute("secciones", secciones);
        request.setAttribute("cantidad", cantidad);
        request.setAttribute("diccionario", diccionario_formulario);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);
    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(670, request);
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        boolean resultado = false;
        try {
            resultado = dao.eliminarActividad_Apoyo(id_actividad);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_actividad, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo eliminada correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser eliminado ya que tiene referencias asociados."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser eliminado ya que tiene referencias asociados."));
            this.getIndex(request, response);
        }

    }
    
    protected void getActividadesajax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        String resultado = "";

        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));

        try {
            List<Respuesta_AA> respuestas = dao.obtenerRespuestasAjax(id_actividad);
            System.out.println(respuestas);
            Gson gson = new Gson();
            resultado = gson.toJson(respuestas);

        } catch (Exception sig_ex) {
            // Enviar error al AJAX
        }

        out.print(resultado);

        out.flush();
    }

    protected void getRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(677, request);
        String redireccion = "Actividad_Apoyo/Realizar.jsp";

        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        Actividad_Apoyo actividad = dao.obtenerActividad_Apoyo(id_actividad);
        if (actividad.isAprobacion_direccion()) {
            request.setAttribute("actividad", actividad);
            ProduccionXSLT xslt;
            try {
                xslt = produccionxsltdao.obtenerProduccionXSLTFormulario();

                System.out.println(actividad.getEstructura().getString());

                String formulario = helper_transformaciones.transformar(xslt, actividad.getEstructura());

                request.setAttribute("cuerpo_formulario", formulario);

            } catch (TransformerException | SIGIPROException | SQLException ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
            }

            redireccionar(request, response, redireccion);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("No se ha aprobado el paso."));
            this.getIndex(request, response);
        }
    }

    protected void getRepetir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(677, request);
        String redireccion = "Actividad_Apoyo/Repetir.jsp";

        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        Respuesta_AA respuesta = dao.obtenerRespuesta(id_respuesta);
        respuesta.setActividad(dao.obtenerActividad_Apoyo(respuesta.getActividad().getId_actividad()));
        if (respuesta.getActividad().isAprobacion_direccion()) {
            request.setAttribute("respuesta", respuesta);
            ProduccionXSLT xslt;
            try {
                xslt = produccionxsltdao.obtenerProduccionXSLTFormulario();
                System.out.println(respuesta.getActividad().getEstructura().getString());
                String formulario = helper_transformaciones.transformar(xslt, respuesta.getActividad().getEstructura());
                request.setAttribute("cuerpo_formulario", formulario);
                request.setAttribute("respuesta", respuesta);
            } catch (TransformerException | SIGIPROException | SQLException ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
            }
            redireccionar(request, response, redireccion);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("No se ha aprobado el paso."));
            this.getIndex(request, response);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, SIGIPROException, IOException, SQLException, ParserConfigurationException, SAXException {
        boolean resultado = false;

        Actividad_Apoyo aa = construirObjeto(parametros, request);
        if (aa.getId_actividad() == 0) {
            resultado = dao.insertarActividad_Apoyo(aa);
            if (resultado) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo agregada correctamente"));
                //Funcion que genera la bitacora
                bitacora.setBitacora(aa.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
                //*----------------------------*
                this.getIndex(request, response);
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser agregado. Inténtelo de nuevo."));
                this.getAgregar(request, response);
            }
        } else {
            resultado = dao.editarActividad_Apoyo(aa);
            if (resultado) {
                //Funcion que genera la bitacora
                bitacora.setBitacora(aa.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
                //*----------------------------*
                request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo editada correctamente"));
                this.getIndex(request, response);
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser editada. Inténtelo de nuevo."));
                request.setAttribute("id_actividad", aa.getId_actividad());
                this.getIndex(request, response);
            }
        }
    }

    protected void postAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        //1 - Calidad, 2 - Regente, 3 - Coordinador, 4 - Director
        int actor = Integer.parseInt(request.getParameter("actor"));
        Actividad_Apoyo aa = dao.obtenerAprobaciones(id_actividad);
        boolean resultado = false;
        try {
            switch (actor) {
                case 1:
                    validarPermiso(672, request);
                    resultado = dao.aprobarActividad_Apoyo(id_actividad, actor);
                    aa.setAprobacion_calidad(true);
                    break;
                case 2:
                    validarPermiso(673, request);
                    if (aa.isAprobacion_calidad()) {
                        resultado = dao.aprobarActividad_Apoyo(id_actividad, actor);
                        aa.setAprobacion_regente(true);
                    }
                    break;
                case 3:
                    validarPermiso(674, request);
                    if (aa.isAprobacion_calidad()) {
                        resultado = dao.aprobarActividad_Apoyo(id_actividad, actor);
                        aa.setAprobacion_coordinador(true);
                    }
                    break;
                case 4:
                    validarPermiso(675, request);
                    if (aa.isAprobacion_calidad() && aa.isAprobacion_coordinador() && aa.isAprobacion_regente()) {
                        resultado = dao.aprobarActividad_Apoyo(id_actividad, actor);
                        aa.setAprobacion_direccion(true);
                    }
                    break;
            }
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(aa.parseJSON(), Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo aprobada correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser aprobada. Le faltan otras aprobaciones para estar validado."));
            }
            this.getVeractividad(request, response,aa.getId_actividad());
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser aprobada."));
            this.getVeractividad(request, response,aa.getId_actividad());
        }

    }

    protected void postRechazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(670, request);
        boolean resultado = false;
        Actividad_Apoyo aa = new Actividad_Apoyo();
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        aa.setId_actividad(id_actividad);
        String observaciones = request.getParameter("observaciones");
        String actor = request.getParameter("actor");
        aa.setObservaciones(observaciones + " - Rechazada por: " + actor);
        resultado = dao.rechazarActividad_Apoyo(aa.getId_actividad(), aa.getObservaciones());
        if (resultado) {
            //Funcion que genera la bitacora
            bitacora.setBitacora(aa.parseJSON(), Bitacora.ACCION_RECHAZAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo rechazada correctamente"));
            this.getIndex(request, response);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser rechazada. Inténtelo de nuevo."));
            this.getIndex(request, response);
        }
    }

    protected void postRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_actividad = Integer.parseInt(this.obtenerParametro("id_actividad"));

        Actividad_Apoyo actividad = dao.obtenerActividad_Apoyo(id_actividad);

        Respuesta_AA resultado = new Respuesta_AA();
        resultado.setActividad(actividad);

        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setId_usuario(id_usuario);

        resultado.setUsuario_realizar(u);

        resultado.setNombre(this.obtenerParametro("nombre"));

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        Timestamp fecha = new java.sql.Timestamp(new Date().getTime());
        resultado.setFecha(fecha);

        String redireccion = "Actividad_Apoyo/index.jsp";

        try {
            InputStream binary_stream = resultado.getActividad().getEstructura().getBinaryStream();

            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documento_resultado = parser.parse(binary_stream);
            Element elemento_resultado = documento_resultado.getDocumentElement();

            NodeList lista_nodos = elemento_resultado.getElementsByTagName("campo");

            for (int i = 0; i < lista_nodos.getLength(); i++) {
                Node nodo = lista_nodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String nombre_campo_resultado;
                    Node nodo_valor;
                    String valor;
                    String tipo_campo = elemento.getElementsByTagName("tipo").item(0).getTextContent();
                    switch (tipo_campo) {
                        case ("seleccion"):
                            nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                            String[] opciones = this.obtenerParametros(nombre_campo_resultado);
                            List<String> lista_opciones = new ArrayList<String>();
                            lista_opciones.addAll(Arrays.asList(opciones));
                            NodeList elemento_opciones = elemento.getElementsByTagName("opciones").item(0).getChildNodes();
                            for (int j = 0; j < elemento_opciones.getLength(); j++) {
                                Node opcion = elemento_opciones.item(j);
                                Element elemento_opcion = (Element) opcion;
                                String nombre_opcion = elemento_opcion.getElementsByTagName("valor").item(0).getTextContent();
                                if (lista_opciones.contains(nombre_opcion)) {
                                    nodo_valor = elemento_opcion.getElementsByTagName("check").item(0);
                                    nodo_valor.setTextContent("true");
                                }
                            }
                            break;
                        case ("usuario"):
                            nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                            String[] usuarios = this.obtenerParametros(nombre_campo_resultado);
                            List<String> lista_usuarios = new ArrayList<String>();
                            lista_usuarios.addAll(Arrays.asList(usuarios));

                            nodo_valor = elemento.getElementsByTagName("seccion").item(0);
                            int seccion = Integer.parseInt(nodo_valor.getTextContent());
                            List<Usuario> usuarios_seccion = usuariodao.obtenerUsuariosProduccion(seccion);
                            List<String> nombre_usuarios = new ArrayList<>();
                            List<Integer> id_usuarios = new ArrayList<>();

                            for (String id : lista_usuarios) {
                                id_usuarios.add(Integer.parseInt(id));
                            }
                            for (Usuario usuario : usuarios_seccion) {
                                if (id_usuarios.contains(usuario.getId_usuario())) {
                                    nombre_usuarios.add(usuario.getNombre_completo());
                                }
                            }
                            nodo_valor = elemento.getElementsByTagName("valor").item(0);
                            nodo_valor.setTextContent(nombre_usuarios.toString());
                            break;
                        case ("subbodega"):
                            nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                            valor = this.obtenerParametro(nombre_campo_resultado);
                            nodo_valor = elemento.getElementsByTagName("valor").item(0);
                            nodo_valor.setTextContent(valor);
                            valor = elemento.getElementsByTagName("cantidad").item(0).getTextContent();
                            if (valor.equals("true")) {
                                String nombre_cantidad_resultado = elemento.getElementsByTagName("nombre-cantidad").item(0).getTextContent();
                                String valor_cantidad = this.obtenerParametro(nombre_cantidad_resultado);
                                nodo_valor = elemento.getElementsByTagName("valor-cantidad").item(0);
                                nodo_valor.setTextContent(valor_cantidad);
                            }
                            break;
                        default:
                            nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                            nodo_valor = elemento.getElementsByTagName("valor").item(0);
                            valor = this.obtenerParametro(nombre_campo_resultado);
                            nodo_valor.setTextContent(valor);
                            break;
                    }

                }
            }

            String string_xml_resultado;
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(documento_resultado), new StreamResult(writer));
            string_xml_resultado = writer.getBuffer().toString().replaceAll("\n|\r", "");

            System.out.println(string_xml_resultado);

            resultado.setRespuestaString(string_xml_resultado);
            dao.insertarRespuesta(resultado);
            bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAAA, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Respuesta registrada correctamente."));
            this.getVeractividad(request, response, actividad.getId_actividad());

        } catch (SQLException | ParserConfigurationException | SAXException | IOException | DOMException | IllegalArgumentException | TransformerException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        }

    }

    protected void postRepetir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_respuesta = Integer.parseInt(this.obtenerParametro("id_respuesta"));
        Respuesta_AA resultado = dao.obtenerRespuesta(id_respuesta);
        resultado.setActividad(dao.obtenerActividad_Apoyo(resultado.getActividad().getId_actividad()));
        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setId_usuario(id_usuario);
        resultado.setUsuario_realizar(u);
        String redireccion = "Actividad_Apoyo/index.jsp";
        try {
            InputStream binary_stream = resultado.getActividad().getEstructura().getBinaryStream();

            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documento_resultado = parser.parse(binary_stream);
            Element elemento_resultado = documento_resultado.getDocumentElement();

            NodeList lista_nodos = elemento_resultado.getElementsByTagName("campo");

            for (int i = 0; i < lista_nodos.getLength(); i++) {
                Node nodo = lista_nodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String nombre_campo_resultado;
                    Node nodo_valor;
                    String valor;
                    String tipo_campo = elemento.getElementsByTagName("tipo").item(0).getTextContent();
                    switch (tipo_campo) {
                        case ("seleccion"):
                            nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                            String[] opciones = this.obtenerParametros(nombre_campo_resultado);
                            List<String> lista_opciones = new ArrayList<String>();
                            lista_opciones.addAll(Arrays.asList(opciones));
                            NodeList elemento_opciones = elemento.getElementsByTagName("opciones").item(0).getChildNodes();
                            for (int j = 0; j < elemento_opciones.getLength(); j++) {
                                Node opcion = elemento_opciones.item(j);
                                Element elemento_opcion = (Element) opcion;
                                String nombre_opcion = elemento_opcion.getElementsByTagName("valor").item(0).getTextContent();
                                if (lista_opciones.contains(nombre_opcion)) {
                                    nodo_valor = elemento_opcion.getElementsByTagName("check").item(0);
                                    nodo_valor.setTextContent("true");
                                }
                            }
                            break;
                        case ("usuario"):
                            nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                            String[] usuarios = this.obtenerParametros(nombre_campo_resultado);
                            List<String> lista_usuarios = new ArrayList<String>();
                            lista_usuarios.addAll(Arrays.asList(usuarios));

                            nodo_valor = elemento.getElementsByTagName("seccion").item(0);
                            int seccion = Integer.parseInt(nodo_valor.getTextContent());
                            List<Usuario> usuarios_seccion = usuariodao.obtenerUsuariosProduccion(seccion);
                            List<String> nombre_usuarios = new ArrayList<>();
                            List<Integer> id_usuarios = new ArrayList<>();

                            for (String id : lista_usuarios) {
                                id_usuarios.add(Integer.parseInt(id));
                            }
                            for (Usuario usuario : usuarios_seccion) {
                                if (id_usuarios.contains(usuario.getId_usuario())) {
                                    nombre_usuarios.add(usuario.getNombre_completo());
                                }
                            }
                            nodo_valor = elemento.getElementsByTagName("valor").item(0);
                            nodo_valor.setTextContent(nombre_usuarios.toString());
                            break;
                        case ("subbodega"):
                            nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                            valor = this.obtenerParametro(nombre_campo_resultado);
                            nodo_valor = elemento.getElementsByTagName("valor").item(0);
                            nodo_valor.setTextContent(valor);
                            valor = elemento.getElementsByTagName("cantidad").item(0).getTextContent();
                            if (valor.equals("true")) {
                                String nombre_cantidad_resultado = elemento.getElementsByTagName("nombre-cantidad").item(0).getTextContent();
                                String valor_cantidad = this.obtenerParametro(nombre_cantidad_resultado);
                                nodo_valor = elemento.getElementsByTagName("valor-cantidad").item(0);
                                nodo_valor.setTextContent(valor_cantidad);
                            }
                            break;
                        default:
                            nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                            nodo_valor = elemento.getElementsByTagName("valor").item(0);
                            valor = this.obtenerParametro(nombre_campo_resultado);
                            nodo_valor.setTextContent(valor);
                            break;
                    }

                }
            }

            String string_xml_resultado;
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(documento_resultado), new StreamResult(writer));
            string_xml_resultado = writer.getBuffer().toString().replaceAll("\n|\r", "");

            System.out.println(string_xml_resultado);

            resultado.setRespuestaString(string_xml_resultado);
            dao.repetirRespuesta(resultado);
            bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_REPETIR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAAA, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Respuesta registrada correctamente."));
            this.getVeractividad(request, response, resultado.getActividad().getId_actividad());

        } catch (SQLException | ParserConfigurationException | SAXException | IOException | DOMException | IllegalArgumentException | TransformerException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        }

    }

  // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Actividad_Apoyo construirObjeto(List<FileItem> items, HttpServletRequest request) {
        Actividad_Apoyo aa = new Actividad_Apoyo();
        //Se crea un diccionario con los elementos del Formulario Dinamico
        HashMap<Integer, HashMap> diccionario_formulario = new HashMap<Integer, HashMap>();
        //Variable donde se define el ID actual del campo o tabla del formulario
        int id_actual = 0;
        //Variable donde se agrega el orden en el que el Formulario se agrego
        String orden = "";
        for (FileItem item : items) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String fieldValue;
                try {
                    fieldValue = item.getString("UTF-8").trim();
                } catch (UnsupportedEncodingException ex) {
                    fieldValue = item.getString();
                }
                switch (fieldName) {
                    case "nombre":
                        aa.setNombre(fieldValue);
                        break;
                    case "id_categoria_aa":
                        Categoria_AA categoria = new Categoria_AA();
                        categoria.setId_categoria_aa(Integer.parseInt(fieldValue));
                        aa.setCategoria(categoria);
                        break;
                    case "id_actividad":
                        int id_actividad = Integer.parseInt(fieldValue);
                        aa.setId_actividad(id_actividad);
                        break;
                    case "version":
                        int version = Integer.parseInt(fieldValue);
                        aa.setVersion(version);
                        break;
                    case "orden":
                        orden = fieldValue;
                        break;
                    default:
                        //Se agarra el valor y se divide, ya que la entrada tiene una estructura t_nombredelvalor_id
                        String[] values = fieldName.split("_");
                        if (values.length > 1) {
                            //Se obtiene el ID del campo a procesar
                            int id = Integer.parseInt(values[2]);
                            if (id == 0) {
                                id = id_actual;
                            } else {
                                id_actual = id;
                            }
                            //Se crea el Hash en el diccionario, en caso de que no se haya creado
                            if (!diccionario_formulario.containsKey(id)) {
                                HashMap<String, String> llaves = new HashMap<String, String>();
                                switch (values[0]) {
                                    case ("c"):
                                        llaves.put("tipo", "campo");
                                        break;
                                    case ("s"):
                                        llaves.put("tipo", "seleccion");
                                        break;
                                    case ("a"):
                                        llaves.put("tipo", "subbodega");
                                        break;
                                    case ("u"):
                                        llaves.put("tipo", "usuario");
                                        break;
                                }
                                diccionario_formulario.put(id, llaves);
                            }
                            switch (values[1]) {
                                default:
                                    diccionario_formulario.get(id).put(values[1], fieldValue);
                                    break;
                            }
                            break;
                        }
                }
            } else {
                try {
                    if (item.getSize() != 0) {
                        //En caso de que hayan archivos
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        if (!diccionario_formulario.isEmpty()) {
            //Se transforma el diccionario en un XML
            System.out.println(diccionario_formulario);
            String xml = this.parseDictXML(diccionario_formulario, orden);
            System.out.println(xml);
            aa.setEstructuraString(xml);
        }
        return aa;
    }

    private String parseDictXML(HashMap<Integer, HashMap> diccionario_formulario, String orden) {
        this.nombre_campo = 1;
        //Se obtiene el orden de los campos
        String[] orden_formulario = orden.split(",");
        HelperXML xml = new HelperXML("actividad");
        //Se itera sobre los IDS del orden de los campos
        for (String i : orden_formulario) {
            if (!i.equals("")) {
                int key = Integer.parseInt(i);
                Element campo = xml.agregarElemento("campo");
                HashMap<String, String> hash = diccionario_formulario.get(key);
                switch (hash.get("tipo")) {
                    case ("campo"):
                        this.crearCampo(xml, hash, campo);
                        break;
                    case ("seleccion"):
                        this.crearSeleccion(xml, hash, campo);
                        break;
                    case ("subbodega"):
                        this.crearSubbodega(xml, hash, campo);
                        break;
                    case ("usuario"):
                        this.crearUsuario(xml, hash, campo);
                        break;
                }
            }
        }

        return xml.imprimirXML();
    }

    private void crearCampo(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("nombre-campo", hash.get("nombre") + "_" + this.nombre_campo, campo);
        this.nombre_campo++;
        xml.agregarSubelemento("etiqueta", hash.get("nombre"), campo);
        xml.agregarSubelemento("valor", "", campo);
        switch (hash.get("tipocampo")) {
            case ("cc"):
                xml.agregarSubelemento("tipo", hash.get("tipocampo"), campo);
                xml.agregarSubelemento("especial", "true", campo);
                break;
            case ("sangria"):
                xml.agregarSubelemento("tipo", hash.get("tipocampo"), campo);
                xml.agregarSubelemento("especial", "true", campo);
                break;
            default:
                xml.agregarSubelemento("tipo", hash.get("tipocampo"), campo);
                break;
        }
    }

    private void crearSubbodega(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("nombre-campo", hash.get("nombre") + "_" + this.nombre_campo, campo);
        this.nombre_campo++;
        xml.agregarSubelemento("tipo", "subbodega", campo);
        xml.agregarSubelemento("etiqueta", hash.get("nombre"), campo);
        xml.agregarSubelemento("valor", "", campo);
        xml.agregarSubelemento("nombre-subbodega", hash.get("nombresubbodega"), campo);
        xml.agregarSubelemento("subbodega", hash.get("subbodega"), campo);
        if (hash.containsKey("cantidad")) {
            xml.agregarSubelemento("cantidad", "true", campo);
            xml.agregarSubelemento("nombre-cantidad", hash.get("nombre") + "_cantidad", campo);
            xml.agregarSubelemento("valor-cantidad", "", campo);
        } else {
            xml.agregarSubelemento("cantidad", "false", campo);
        }

    }

    private void crearUsuario(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("nombre-campo", hash.get("nombre") + "_" + this.nombre_campo, campo);
        this.nombre_campo++;
        xml.agregarSubelemento("etiqueta", hash.get("nombre"), campo);
        xml.agregarSubelemento("tipo", "usuario", campo);
        xml.agregarSubelemento("valor", "", campo);
        xml.agregarSubelemento("nombre-seccion", hash.get("nombreseccion"), campo);
        xml.agregarSubelemento("seccion", hash.get("seccion"), campo);
    }

    private void crearSeleccion(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("tipo", "seleccion", campo);
        xml.agregarSubelemento("nombre-campo", hash.get("snombre") + "_" + this.nombre_campo, campo);
        xml.agregarSubelemento("etiqueta", hash.get("snombre"), campo);
        this.nombre_campo++;
        Element opciones = xml.agregarElemento("opciones", campo);
        for (String i : hash.keySet()) {
            if (i.contains("opcion")) {
                Element opcion = xml.agregarElemento("opcion", opciones);
                xml.agregarSubelemento("etiqueta", hash.get(i), opcion);
                xml.agregarSubelemento("valor", hash.get(i) + "_" + this.nombre_campo, opcion);
                xml.agregarSubelemento("check", "false", opcion);
                this.nombre_campo++;
            }
        }

    }

    public List<String> parseListaSecciones(List<Seccion> secciones) {
        List<String> respuesta = new ArrayList<String>();
        for (Seccion s : secciones) {
            String tipo = "[";
            tipo += s.getId_seccion() + ",";
            tipo += "\"" + s.getNombre_seccion() + "\"]";
            respuesta.add(tipo);
        }
        System.out.println(respuesta.toString());
        return respuesta;
    }

    public List<String> parseListaSubbodegas(List<SubBodega> subbodegas) {
        List<String> respuesta = new ArrayList<String>();
        for (SubBodega s : subbodegas) {
            String tipo = "[";
            tipo += s.getId_sub_bodega() + ",";
            tipo += "\"" + s.getNombre() + "\"]";
            respuesta.add(tipo);
        }
        System.out.println(respuesta.toString());
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
            if (ServletFileUpload.isMultipartContent(request)) {
                this.obtenerParametros(request);
                accion = this.obtenerParametro("accion");
                System.out.println(accion);
            }
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

    private void obtenerParametros(HttpServletRequest request) {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            parametros = upload.parseRequest(request);
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  // </editor-fold>
}