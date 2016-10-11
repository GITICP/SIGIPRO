/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.google.gson.Gson;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.dao.SubBodegaDAO;
import com.icp.sigipro.bodegas.modelos.InventarioSubBodega;
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
import com.icp.sigipro.utilidades.HelperParseXML;
import com.icp.sigipro.utilidades.HelperXML;
import java.io.File;
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

    //CRUD, Activar Actividad, Aprobaciones (4), Activar Respuesta, Realizar Actividad, Cerrar,Aprobacion Coordinacion[Respuesta], Aprobaciones Gestion, activar y retirar, Aprobacion Regencia [Respuesta]
    private final int[] permisos = {670, 671, 672, 673, 674, 675, 676, 677, 678, 679, 680, 681, 682};
    //-----------------
    private final Actividad_ApoyoDAO dao = new Actividad_ApoyoDAO();
    private final ProduccionXSLTDAO produccionxsltdao = new ProduccionXSLTDAO();
    private final SubBodegaDAO subbodegadao = new SubBodegaDAO();
    private final SeccionDAO secciondao = new SeccionDAO();
    private final Categoria_AADAO categoriadao = new Categoria_AADAO();
    private final UsuarioDAO usuariodao = new UsuarioDAO();
    
    private final HelperTransformaciones helper_transformaciones = HelperTransformaciones.getHelperTransformaciones();
    private HelperParseXML helper_parser = new HelperParseXML();
    
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
            add("retirar");
            add("incluir");
            
            add("completar");
            add("cerrar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("editar");
            add("realizar");
            add("repetir");
            add("rechazar");
            add("aprobar");
            
            add("aprobarrespuesta");
            add("rechazarrespuesta");
            add("completarrespuesta");
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
        request.setAttribute("listaSubbodegas", helper_parser.parseListaSubbodegas(subbodegadao.obtenerSubBodegas()));
        request.setAttribute("listaSecciones", helper_parser.parseListaSecciones(secciondao.obtenerSecciones()));
        request.setAttribute("categorias", categoriadao.obtenerCategorias_AA());
        request.setAttribute("orden", "");
        request.setAttribute("cantidad", 0);
        request.setAttribute("contador", 0);
        redireccionar(request, response, redireccion);
    }
    
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response, int id_categoria_aa) throws ServletException, IOException, SIGIPROException {
        validarPermiso(670, request);
        
        String redireccion = "Actividad_Apoyo/Agregar.jsp";
        Actividad_Apoyo aa = new Actividad_Apoyo();
        try {
            Categoria_AA categoria = new Categoria_AA();
            categoria.setId_categoria_aa(id_categoria_aa);
            aa.setCategoria(categoria);
        } catch (Exception e) {
            
        }
        request.setAttribute("actividad", aa);
        request.setAttribute("accion", "Agregar");
        request.setAttribute("listaSubbodegas", helper_parser.parseListaSubbodegas(subbodegadao.obtenerSubBodegas()));
        request.setAttribute("listaSecciones", helper_parser.parseListaSecciones(secciondao.obtenerSecciones()));
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
    
    protected void getIndexactividades(HttpServletRequest request, HttpServletResponse response, int id_categoria_aa) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/indexBotonesActividad.jsp";
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
        int version = Integer.parseInt(request.getParameter("version"));
        ProduccionXSLT xslt;
        Actividad_Apoyo aa;
        
        try {
            aa = dao.obtenerActividad_Apoyo(id_actividad, version);
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
        int version = Integer.parseInt(request.getParameter("version"));
        Actividad_Apoyo actividad = dao.obtenerActividad_Apoyo(id_actividad,version);
        List<Respuesta_AA> respuestas = dao.obtenerRespuestas(actividad);
        request.setAttribute("actividad", actividad);
        request.setAttribute("listaRespuestas", respuestas);
        redireccionar(request, response, redireccion);
        
    }
    
    protected void getVeractividad(HttpServletRequest request, HttpServletResponse response, Actividad_Apoyo aa) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Actividad_Apoyo/VerActividad.jsp";
        Actividad_Apoyo actividad = dao.obtenerActividad_Apoyo(aa.getId_actividad(),aa.getVersion());
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
        Actividad_Apoyo aprobaciones = dao.obtenerRequerimientosAprobacion(id_respuesta);
        boolean resultado = false;
        try {
            resultado = dao.activarVersionRespuesta(version, id_respuesta, aprobaciones.isRequiere_coordinacion(), aprobaciones.isRequiere_regencia());
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
            raa.setActividad(dao.obtenerActividad_Apoyo(raa.getActividad().getId_actividad(),raa.getVersion_usada()));
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
            r.setActividad(dao.obtenerActividad_Apoyo(r.getActividad().getId_actividad(),r.getVersion_usada()));
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
        int version = Integer.parseInt(request.getParameter("version"));
        Actividad_Apoyo aa = dao.obtenerActividad_Apoyo(id_actividad,version);
        
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
        request.setAttribute("listaSubbodegas", helper_parser.parseListaSubbodegas(subbodegas));
        request.setAttribute("listaSecciones", helper_parser.parseListaSecciones(secciones));
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
    
    protected void getRetirar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(681, request);
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        boolean estado = dao.obtenerEstado(id_actividad);
        if (estado) {
            boolean resultado = false;
            try {
                resultado = dao.retirarActividad_Apoyo(id_actividad);
                if (resultado) {
                    //Funcion que genera la bitacora 
                    bitacora.setBitacora(id_actividad, Bitacora.ACCION_RETIRAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
                    //----------------------------
                    request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo retirada correctamente"));
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser retirada ya que tiene referencias asociados."));
                }
                this.getIndexnormal(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser retirada ya que tiene referencias asociados."));
                this.getIndexnormal(request, response);
            }
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser retirada ya que ya se encuentra retirada."));
            this.getIndexnormal(request, response);
        }
    }
    
    protected void getIncluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(681, request);
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        boolean estado = dao.obtenerEstado(id_actividad);
        if (!estado) {
            boolean resultado = false;
            try {
                resultado = dao.incluirActividad_Apoyo(id_actividad);
                if (resultado) {
                    //Funcion que genera la bitacora 
                    bitacora.setBitacora(id_actividad, Bitacora.ACCION_INCLUIR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
                    //----------------------------
                    request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo inlcuida correctamente"));
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser incluida ya que tiene referencias asociados."));
                }
                this.getIndexnormal(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser incluida ya que tiene referencias asociados."));
                this.getIndexnormal(request, response);
            }
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser incluida ya que ya se encuentra incluida."));
            this.getIndexnormal(request, response);
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
        int version = Integer.parseInt(request.getParameter("version"));
        Actividad_Apoyo actividad = dao.obtenerActividad_Apoyo(id_actividad,version);
        if (actividad.isAprobacion_gestion()) {
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
        respuesta.setActividad(dao.obtenerActividad_Apoyo(respuesta.getActividad().getId_actividad(),respuesta.getVersion_usada()));
        if (respuesta.getActividad().isAprobacion_gestion()) {
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
    
    protected void getCerrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(678, request);
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        int estado = dao.obtenerEstadoRespuesta(id_respuesta);
        //Si el estado es incompleto
        if (estado == 2) {
            boolean resultado = false;
            try {
                int version = Integer.parseInt(request.getParameter("version"));
                int id_usuario = (int) request.getSession().getAttribute("idusuario");
                Respuesta_AA raa = dao.obtenerAprobacionesRespuesta(id_respuesta);
                raa.setId_respuesta(id_respuesta);
                raa.setVersion(version);
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                raa.setUsuario_cerrar(usuario);
                resultado = dao.cerrarRespuesta(raa);
                if (resultado) {
                    //Funcion que genera la bitacora 
                    bitacora.setBitacora(id_respuesta, Bitacora.ACCION_CERRAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAPXP, request.getRemoteAddr());
                    //----------------------------
                    request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo cerrada correctamente"));
                } else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser cerrada."));
                }
                this.getVeractividad(request, response, dao.obtenerIdActividad(id_respuesta));
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser cerrada."));
                this.getVeractividad(request, response, dao.obtenerIdActividad(id_respuesta));
            }
        }
    }
    
    protected void getCompletar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(677, request);
        String redireccion = "Actividad_Apoyo/Completar.jsp";
        
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        Respuesta_AA respuesta = dao.obtenerRespuesta(id_respuesta);
        respuesta.setActividad(dao.obtenerActividad_Apoyo(respuesta.getActividad().getId_actividad(),respuesta.getVersion_usada()));
        if (respuesta.getEstado() == 2) {
            request.setAttribute("respuesta", respuesta);
            ProduccionXSLT xslt;
            try {
                xslt = produccionxsltdao.obtenerProduccionXSLTFormulario();
                System.out.println(respuesta.getRespuesta().getString());
                String formulario = helper_transformaciones.transformar(xslt, respuesta.getRespuesta());
                request.setAttribute("cuerpo_formulario", formulario);
                request.setAttribute("respuesta", respuesta);
            } catch (TransformerException | SIGIPROException | SQLException ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
            }
            
            redireccionar(request, response, redireccion);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("No se ha realizado el paso."));
            this.getIndex(request, response);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, SIGIPROException, IOException, SQLException, ParserConfigurationException, SAXException {
        boolean resultado = false;
        
        Actividad_Apoyo aa = construirObjeto(parametros, request);
        resultado = dao.insertarActividad_Apoyo(aa);
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo agregada correctamente"));
            //Funcion que genera la bitacora
            bitacora.setBitacora(aa.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
            //*----------------------------*
            this.getIndexactividades(request, response, aa.getCategoria().getId_categoria_aa());
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser agregado. Inténtelo de nuevo."));
            this.getAgregar(request, response, aa.getCategoria().getId_categoria_aa());
        }
        
    }
    
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, SIGIPROException, IOException, SQLException, ParserConfigurationException, SAXException {
        boolean resultado = false;
        
        Actividad_Apoyo aa = construirObjeto(parametros, request);
        Actividad_Apoyo aprobaciones = dao.obtenerAprobaciones(aa.getId_actividad());
        aa.setAprobacion_gestion(aprobaciones.isAprobacion_gestion());
        int version = dao.obtenerUltimaVersion(aa.getId_actividad());
        resultado = dao.editarActividad_Apoyo(aa, version + 1);
        if (resultado) {
            //Funcion que genera la bitacora
            bitacora.setBitacora(aa.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo editada correctamente"));
            this.getIndexactividades(request, response, aa.getCategoria().getId_categoria_aa());
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser editada. Inténtelo de nuevo."));
            request.setAttribute("id_actividad", aa.getId_actividad());
            this.getIndexactividades(request, response, aa.getCategoria().getId_categoria_aa());
        }
        
    }
    
    protected void postAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        //1 - Calidad, 2 - Regente, 3 - Coordinador, 4 - Director, 5 - Gestion
        int actor = Integer.parseInt(request.getParameter("actor"));
        int version = Integer.parseInt(request.getParameter("version"));
        Actividad_Apoyo aa = dao.obtenerAprobaciones(id_actividad);
        aa.setId_actividad(id_actividad);
        aa.setVersion(version);
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
                case 5:
                    validarPermiso(680, request);
                    if (aa.isAprobacion_calidad() && aa.isAprobacion_coordinador() && aa.isAprobacion_regente() && aa.isAprobacion_direccion()) {
                        resultado = dao.aprobarActividad_Apoyo(id_actividad, actor);
                        aa.setAprobacion_gestion(true);
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
            this.getVeractividad(request, response, aa);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser aprobada."));
            this.getVeractividad(request, response, aa);
        }
        
    }
    
    protected void postRechazar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(670, request);
        boolean resultado = false;
        Actividad_Apoyo aa = new Actividad_Apoyo();
        int id_actividad = Integer.parseInt(request.getParameter("id_actividad"));
        int version = Integer.parseInt(request.getParameter("version"));
        aa.setId_actividad(id_actividad);
        aa.setVersion(version);
        String observaciones = request.getParameter("observaciones");
        String actor = request.getParameter("actor");
        aa.setObservaciones(observaciones + " - Rechazada por: " + actor);
        resultado = dao.rechazarActividad_Apoyo(aa.getId_actividad(), aa.getObservaciones());
        if (resultado) {
            //Funcion que genera la bitacora
            bitacora.setBitacora(aa.parseJSON(), Bitacora.ACCION_RECHAZAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ACTIVIDADAPOYO, request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Actividad de Apoyo rechazada correctamente"));
            this.getVeractividad(request, response, aa);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Actividad de Apoyo no pudo ser rechazada. Inténtelo de nuevo."));
            this.getVeractividad(request, response, aa);
        }
    }
    
    protected void postAprobarrespuesta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        //1- Coordinacion, 2-Regencia
        int actor = Integer.parseInt(request.getParameter("actor"));
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        int version = Integer.parseInt(request.getParameter("version"));
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        Respuesta_AA raa = dao.obtenerAprobacionesRespuesta(id_respuesta);
        raa.setId_respuesta(id_respuesta);
        raa.setVersion(version);
        Usuario usuario = new Usuario();
        usuario.setId_usuario(id_usuario);
        if (actor == 1) {
            raa.setUsuario_aprobar_coordinacion(usuario);
            raa.setAprobacion_coordinacion(true);
        } else {
            raa.setAprobacion_regencia(true);
            raa.setUsuario_aprobar_regencia(usuario);
        }
        resultado = dao.aprobarRespuesta(raa, actor);
        if (resultado) {
            //Funcion que genera la bitacora
            bitacora.setBitacora(raa.parseJSON(), Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAAA, request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Respuesata de Actividad de Apoyo aprobada correctamente"));
            this.getVeractividad(request, response, dao.obtenerIdActividad(id_respuesta));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Respuessta Actividad de Apoyo no pudo ser aprobada. Inténtelo de nuevo."));
            this.getVeractividad(request, response, dao.obtenerIdActividad(id_respuesta));
        }
    }
    
    protected void postRechazarrespuesta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;
        //1- Coordinacion, 2-Regencia
        int actor = Integer.parseInt(request.getParameter("actor"));
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        int version = Integer.parseInt(request.getParameter("version"));
        String observaciones = request.getParameter("observaciones_respuesta");
        Respuesta_AA raa = new Respuesta_AA();
        Actividad_Apoyo requisitos = dao.obtenerRequerimientosAprobacion(id_respuesta);
        raa.setId_respuesta(id_respuesta);
        raa.setVersion(version);
        if (actor == 1) {
            observaciones += " [Rechazado por Coordinación]";
        } else {
            observaciones += " [Rechazado por Regencia]";
        }
        raa.setObservaciones(observaciones);
        resultado = dao.rechazarRespuesta(raa, actor,requisitos);
        if (resultado) {
            //Funcion que genera la bitacora
            bitacora.setBitacora(raa.parseJSON(), Bitacora.ACCION_RECHAZAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAAA, request.getRemoteAddr());
            //*----------------------------*
            request.setAttribute("mensaje", helper.mensajeDeExito("Respuesata de Actividad de Apoyo rechazada correctamente"));
            this.getVeractividad(request, response, dao.obtenerIdActividad(id_respuesta));
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Respuessta Actividad de Apoyo no pudo ser rechazada. Inténtelo de nuevo."));
            this.getVeractividad(request, response, dao.obtenerIdActividad(id_respuesta));
        }
    }
    
    protected void postRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_actividad = Integer.parseInt(this.obtenerParametro("id_actividad"));
        int version = Integer.parseInt(this.obtenerParametro("version"));
        Actividad_Apoyo actividad = dao.obtenerActividad_Apoyo(id_actividad,version);
        
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
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            //Creacion del nombre con la fecha y hora actual
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
            String nombre_fecha = ft.format(dNow);
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Imagenes" + File.separatorChar + "Realizar Actividad de Apoyo" + File.separatorChar + actividad.getNombre() + "_" + nombre_fecha;
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            helper_parser.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            //parametros = upload.parseRequest(request);
            helper_parser = new HelperParseXML(parametros);
            String string_xml_resultado = helper_parser.parseRespuestaXML(resultado, null, ubicacion);
            
            resultado.setRespuestaString(string_xml_resultado);
            dao.insertarRespuesta(resultado);
            bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAAA, request.getRemoteAddr());
            
            request.setAttribute("mensaje", helper.mensajeDeExito("Respuesta registrada correctamente."));
            this.getVeractividad(request, response, actividad);
            
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
        resultado.setActividad(dao.obtenerActividad_Apoyo(resultado.getActividad().getId_actividad(),resultado.getVersion_usada()));
        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setId_usuario(id_usuario);
        resultado.setUsuario_realizar(u);
        
        String redireccion = "Actividad_Apoyo/index.jsp";
        
        resultado.setNombre(this.obtenerParametro("nombre"));
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        
        Timestamp fecha = new java.sql.Timestamp(new Date().getTime());
        resultado.setFecha(fecha);
        
        try {
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            //Creacion del nombre con la fecha y hora actual
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
            String nombre_fecha = ft.format(dNow);
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Imagenes" + File.separatorChar + "Realizar Actividad de Apoyo" + File.separatorChar + resultado.getActividad().getNombre() + "_" + nombre_fecha;
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            helper_parser.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            //parametros = upload.parseRequest(request);
            helper_parser = new HelperParseXML(parametros);
            String string_xml_resultado = helper_parser.parseRespuestaXML(resultado, null, ubicacion);
            resultado.setRespuestaString(string_xml_resultado);
            int version = dao.obtenerUltimaVersionRespuesta(id_respuesta);
            dao.repetirRespuesta(resultado, version + 1);
            bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_REPETIR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAAA, request.getRemoteAddr());
            
            request.setAttribute("mensaje", helper.mensajeDeExito("Respuesta registrada correctamente."));
            this.getVeractividad(request, response, resultado.getActividad());
            
        } catch (SQLException | ParserConfigurationException | SAXException | IOException | DOMException | IllegalArgumentException | TransformerException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        }
        
    }
    
    protected void postCompletar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(677,request);
        int id_respuesta = Integer.parseInt(this.obtenerParametro("id_respuesta"));
        
        Respuesta_AA resultado = dao.obtenerRespuesta(id_respuesta);
        
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
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            //Creacion del nombre con la fecha y hora actual
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
            String nombre_fecha = ft.format(dNow);
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Imagenes" + File.separatorChar + "Realizar Actividad de Apoyo" + File.separatorChar + resultado.getActividad().getNombre() + "_" + nombre_fecha;
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            helper_parser.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            //parametros = upload.parseRequest(request);
            helper_parser = new HelperParseXML(parametros);
            String string_xml_resultado = helper_parser.parseRespuestaXML(resultado, null, ubicacion);
            
            resultado.setRespuestaString(string_xml_resultado);
            int version = dao.obtenerUltimaVersionRespuesta(id_respuesta);
            dao.completarRespuesta(resultado,version+1);
            
            bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_COMPLETAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAAA, request.getRemoteAddr());
            
            request.setAttribute("mensaje", helper.mensajeDeExito("Respuesta completada correctamente."));
            this.getVeractividad(request, response, resultado.getActividad());
            
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
        
        HashMap<Integer, HashMap> formulario = helper_parser.parseFormularioXML(items, null, aa);
        System.out.println(formulario);
        String orden = this.obtenerParametro("orden");
        String xml = helper_parser.parseJSONXML(formulario, orden, "actividad");
        System.out.println(xml);
        aa.setEstructuraString(xml);
        return aa;
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
