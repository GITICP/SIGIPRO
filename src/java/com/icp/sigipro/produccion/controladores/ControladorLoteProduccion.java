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
import com.icp.sigipro.produccion.dao.LoteDAO;
import com.icp.sigipro.produccion.dao.PasoDAO;
import com.icp.sigipro.produccion.modelos.Lote;
import com.icp.sigipro.produccion.modelos.Protocolo;
import com.icp.sigipro.produccion.modelos.Respuesta_pxp;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperTransformaciones;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
@WebServlet(name = "ControladorLoteProduccion", urlPatterns = {"/Produccion/Lote"})
public class ControladorLoteProduccion extends SIGIPROServlet {

    //CRUD, Realizar, Aprobar, Activar Respuesta, Revisar, Verificar, Ver Estado, Aprobar Distribucion, Registro Fecha vencimiento
    private final int[] permisos = {660, 661, 662, 663, 664, 665, 666, 667, 668};
    //-----------------
    private final LoteDAO dao = new LoteDAO();
    private final ProduccionXSLTDAO produccionxsltdao = new ProduccionXSLTDAO();
    private final PasoDAO pasodao = new PasoDAO();
    private final UsuarioDAO usuariodao = new UsuarioDAO();
    private final SubBodegaDAO subbodegadao = new SubBodegaDAO();

    private final HelperTransformaciones helper_transformaciones = HelperTransformaciones.getHelperTransformaciones();

    protected final Class clase = ControladorLoteProduccion.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("eliminar");
            add("realizar");
            add("usuariosajax");
            add("ultimoslotesajax");
            add("lotesajax");
            add("verrespuesta");
            add("historial");
            add("verhistorial");
            add("repetir");
            add("activar");
            add("completar");
            add("imagen");
            add("verestado");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("realizar");
            add("verificar");
            add("completar");
            add("revisar");
            add("repetir");
            add("distribucion");
            add("vencimiento");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Lote/index.jsp";
        List<Lote> lotes = dao.obtenerLotes();
        request.setAttribute("listaLotes", lotes);
        redireccionar(request, response, redireccion);
    }

    protected void getImagen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);

        String path = request.getParameter("path");

        String nombre_imagen = request.getParameter("nombre");

        System.out.println(path);

        File file = new File(path);

        System.out.println(file.length());
        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "image/*");
            response.setContentLength((int) file.length());
            String nombre = "imagen-" + nombre_imagen + "." + this.getFileExtension(path);

            response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");

            ServletOutputStream os = response.getOutputStream();
            byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();

        }

    }

    protected void getHistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Lote/Historial.jsp";
        List<Lote> lotes = dao.obtenerLotesHistorial();
        request.setAttribute("listaLotes", lotes);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Lote/Ver.jsp";
        int id_lote = Integer.parseInt(request.getParameter("id_lote"));
        try {
            Lote l = dao.obtenerLote(id_lote);
            request.setAttribute("lote", l);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getActivar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(663, request);
        int id_historial = Integer.parseInt(request.getParameter("id_historial"));
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        int version = dao.obtenerVersion(id_historial);
        boolean resultado = false;
        try {
            resultado = dao.activarVersion(version, id_respuesta);
            if (resultado) {
                //Funcion que genera la bitacora 
                Respuesta_pxp respuesta = new Respuesta_pxp();
                respuesta.setId_historial(id_historial);
                respuesta.setId_respuesta(id_respuesta);
                respuesta.setVersion(version);
                bitacora.setBitacora(respuesta.parseJSON(), Bitacora.ACCION_ACTIVAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAPXP, request.getRemoteAddr());
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

    protected void getVer(HttpServletRequest request, HttpServletResponse response, int id_lote) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Lote/Ver.jsp";
        try {
            Lote l = dao.obtenerLote(id_lote);
            request.setAttribute("lote", l);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getVerrespuesta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Lote/VerRespuesta.jsp";
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        ProduccionXSLT xslt;
        Respuesta_pxp r;

        try {
            r = dao.obtenerRespuesta(id_respuesta);
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

    protected void getVerhistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Lote/VerHistorial.jsp";
        int id_historial = Integer.parseInt(request.getParameter("id_historial"));
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        ProduccionXSLT xslt;
        Respuesta_pxp r;

        try {
            r = dao.obtenerHistorial(id_historial);
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

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(660, request);
        int id_lote = Integer.parseInt(request.getParameter("id_lote"));
        boolean resultado = false;
        try {
            resultado = dao.eliminarLote(id_lote);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_lote, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_LOTEPRODUCCION, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Lote de Producción eliminado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Lote de Producción no pudo ser eliminado ya que tiene pasos asociadas."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Lote de Producción no pudo ser eliminado ya que tiene pasos asociadas."));
            this.getIndex(request, response);
        }

    }

    protected void getRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(661, request);
        String redireccion = "Lote/Realizar.jsp";

        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        Respuesta_pxp respuesta = dao.obtenerRespuesta(id_respuesta);
        if (respuesta.getEstado() == 3 || respuesta.getEstado() == 4) {
            request.setAttribute("respuesta", respuesta);
            ProduccionXSLT xslt;
            try {
                xslt = produccionxsltdao.obtenerProduccionXSLTFormulario();
                System.out.println(respuesta.getPaso().getEstructura().getString());
                String formulario = helper_transformaciones.transformar(xslt, respuesta.getPaso().getEstructura());
                request.setAttribute("cuerpo_formulario", formulario);
                request.setAttribute("paso", respuesta.getPaso());
                request.setAttribute("lote", respuesta.getLote());
            } catch (TransformerException | SIGIPROException | SQLException ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
            }

            redireccionar(request, response, redireccion);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("El paso no está habilitado."));
            this.getIndex(request, response);
        }
    }

    protected void getCompletar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(661, request);
        String redireccion = "Lote/Completar.jsp";

        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        Respuesta_pxp respuesta = dao.obtenerRespuesta(id_respuesta);
        if (respuesta.getEstado() == 5) {
            request.setAttribute("respuesta", respuesta);
            ProduccionXSLT xslt;
            try {
                xslt = produccionxsltdao.obtenerProduccionXSLTFormulario();
                System.out.println(respuesta.getRespuesta().getString());
                String formulario = helper_transformaciones.transformar(xslt, respuesta.getRespuesta());
                request.setAttribute("cuerpo_formulario", formulario);
                request.setAttribute("paso", respuesta.getPaso());
                request.setAttribute("lote", respuesta.getLote());
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

    protected void getRepetir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(661, request);
        String redireccion = "Lote/Repetir.jsp";

        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        Respuesta_pxp respuesta = dao.obtenerRespuesta(id_respuesta);
        if (respuesta.getEstado() == 5) {
            request.setAttribute("id_respuesta", id_respuesta);
            ProduccionXSLT xslt;
            try {
                xslt = produccionxsltdao.obtenerProduccionXSLTFormulario();
                System.out.println(respuesta.getPaso().getEstructura().getString());
                String formulario = helper_transformaciones.transformar(xslt, respuesta.getPaso().getEstructura());
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

    protected void getUsuariosajax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        String resultado = "";

        int id_seccion = Integer.parseInt(request.getParameter("id_seccion"));

        try {
            List<Usuario> usuarios = usuariodao.obtenerUsuariosSeccion(id_seccion);
            System.out.println(usuarios);
            Gson gson = new Gson();
            resultado = gson.toJson(usuarios);

        } catch (SIGIPROException sig_ex) {
            // Enviar error al AJAX
        }

        out.print(resultado);

        out.flush();
    }

    protected void getLotesajax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        String resultado = "";

        List<Lote> lotes = dao.obtenerLotesSimple();
        Gson gson = new Gson();
        resultado = gson.toJson(lotes);

        out.print(resultado);

        out.flush();
    }

    protected void getUltimoslotesajax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        String resultado = "";

        List<Lote> lotes = dao.obtenerUltimosLotes();
        Gson gson = new Gson();
        resultado = gson.toJson(lotes);

        out.print(resultado);

        out.flush();
    }

    protected void getVerestado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(666, request);

        String redireccion = "Lote/VerEstado.jsp";
        try {
            List<Lote> lotes = dao.obtenerLotesEstado();
            //Se supone que será un solo lote el que va a estar activado, pero en caso de cambios en el modelo de negocio, se consideraran varios lotes al mismo tiempo
            for (Lote lote : lotes) {
                List<Respuesta_pxp> respuestas = dao.obtenerRespuestasEstado(lote);
                lote.setRespuestas(respuestas);
            }

            request.setAttribute("listaLotes", lotes);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postVerificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(665, request);
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta_actual"));
        Respuesta_pxp respuesta = dao.obtenerRespuesta(id_respuesta);
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        int version = Integer.parseInt(request.getParameter("version"));
        boolean resultado = false;
        try {
            resultado = dao.verificarPaso(respuesta, id_usuario, version);
            if (respuesta.getPaso().isRequiere_ap()) {
                List<Respuesta_pxp> respuestas = dao.obtenerRespuestas(respuesta.getLote());
                dao.habilitarPasos(respuestas, respuesta);
            }
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(respuesta.parseJSON(), Bitacora.ACCION_VERIFICAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAPXP, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Paso de Protocolo verificado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser verificado."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser verificado."));
            this.getIndex(request, response);
        }
    }

    protected void postDistribucion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(667, request);
        int id_lote = Integer.parseInt(request.getParameter("id_lote"));
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        boolean resultado = false;
        try {
            resultado = dao.distribuirLote(id_lote, id_usuario);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_lote, Bitacora.ACCION_DISTRIBUIR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_LOTEPRODUCCION, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Lote de Producción aprobado para distribución correctamente."));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Lote de Producción no pudo ser aprobado para distribución."));
            }
            this.getHistorial(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Lote de Producción no pudo ser aprobado para distribución."));
            this.getHistorial(request, response);
        }
    }

    protected void postVencimiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(668, request);
        boolean resultado = false;
        int id_lote = Integer.parseInt(request.getParameter("id_lote"));

        Lote lote = new Lote();
        lote.setId_lote(id_lote);

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date fecha_vencimiento;
        java.sql.Date fecha_vencimientoSQL;
        try {
            fecha_vencimiento = formatoFecha.parse(request.getParameter("fecha_vencimiento"));
            fecha_vencimientoSQL = new java.sql.Date(fecha_vencimiento.getTime());
            lote.setFecha_vencimiento(fecha_vencimientoSQL);
        } catch (ParseException ex) {

        }

        try {
            resultado = dao.vencimientoLote(lote);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(lote.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_LOTEPRODUCCION, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Fecha de Vencimiento del Lote de Producción registrado correctamente."));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Fecha de Vencimiento del Lote de Producción no pudo ser registrado correctamente."));
            }
            this.getHistorial(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Fecha de Vencimiento del Lote de Producción no pudo ser registrado correctamente."));
            this.getHistorial(request, response);
        }
    }

    protected void postCompletar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(661, request);
        int id_respuesta = Integer.parseInt(this.obtenerParametro("id_respuesta"));
        Respuesta_pxp resultado = dao.obtenerRespuesta(id_respuesta);

        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setId_usuario(id_usuario);
        resultado.setUsuario_realizar(u);

        String redireccion = "Lote/index.jsp";
        //Se crea el Path en la carpeta del Proyecto
        String fullPath = helper_archivos.obtenerDireccionArchivos();
        String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Imagenes" + File.separatorChar + "Realizar Lote" + File.separatorChar + resultado.getLote().getNombre();
        //-------------------------------------------
        //Crea los directorios si no estan creados aun
        this.crearDirectorio(ubicacion);
        //--------------------------------------------
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(ubicacion));
        ServletFileUpload upload = new ServletFileUpload(factory);
        //parametros = upload.parseRequest(request);
        try {
            String string_xml_resultado = parseXML(resultado, ubicacion);
            resultado.setRespuestaString(string_xml_resultado);
            int version = dao.obtenerUltimaVersionRespuesta(id_respuesta);
            dao.editarRespuesta(resultado, version + 1);
            bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_COMPLETAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAPXP, request.getRemoteAddr());

            request.setAttribute("mensaje", helper.mensajeDeExito("Respuesta completada correctamente."));
            this.getIndex(request, response);

        } catch (SQLException | ParserConfigurationException | SAXException | IOException | DOMException | IllegalArgumentException | TransformerException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        }

    }

    protected void postRevisar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(664, request);
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta_actual"));
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        int version = Integer.parseInt(request.getParameter("version"));
        boolean resultado = false;
        try {
            resultado = dao.revisarPaso(id_respuesta, id_usuario, version);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_respuesta, Bitacora.ACCION_REVISAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAPXP, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Paso de Protocolo revisado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser revisado."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser verificado."));
            this.getIndex(request, response);
        }

    }

    protected void postAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(660, request);
        boolean resultado = false;
        Lote l = new Lote();
        int id_protocolo = Integer.parseInt(request.getParameter("id_protocolo"));
        String nombre = request.getParameter("nombre");
        Protocolo protocolo = new Protocolo();
        protocolo.setId_protocolo(id_protocolo);
        l.setProtocolo(protocolo);
        l.setNombre(nombre);
        resultado = dao.insertarLote(l);
        if (resultado) {
            request.setAttribute("mensaje", helper.mensajeDeExito("Lote de Producción iniciado correctamente"));
            //Funcion que genera la bitacora
            bitacora.setBitacora(l.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_FORMULAMAESTRA, request.getRemoteAddr());
            //*----------------------------*
            this.getIndex(request, response);
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Lote de Producción no pudo ser iniciado. Inténtelo de nuevo."));
            this.getIndex(request, response);
        }

    }

    protected void postRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException {
        validarPermiso(661, request);

        int id_respuesta = Integer.parseInt(this.obtenerParametro("id_respuesta"));

        Respuesta_pxp resultado = dao.obtenerRespuesta(id_respuesta);

        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setId_usuario(id_usuario);

        resultado.setUsuario_realizar(u);

        String redireccion = "Lote/index.jsp";

        if (resultado.getEstado() == 3 || resultado.getEstado() == 4) {

            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Imagenes" + File.separatorChar + "Realizar Lote" + File.separatorChar + resultado.getLote().getNombre();
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            //parametros = upload.parseRequest(request);

            try {
                String string_xml_resultado = parseXML(resultado, ubicacion);
                System.out.println(string_xml_resultado);
                resultado.setRespuestaString(string_xml_resultado);
                dao.insertarRespuesta(resultado);
                bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAPXP, request.getRemoteAddr());

                request.setAttribute("mensaje", helper.mensajeDeExito("Respuesta registrada correctamente."));
                this.getIndex(request, response);

            } catch (SQLException | ParserConfigurationException | SAXException | IOException | DOMException | IllegalArgumentException | TransformerException ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
            }
        } else {
            request.setAttribute("mensaje", helper.mensajeDeExito("No se puede registrar respuesta ya que no está habilitada."));
            this.getIndex(request, response);
        }

    }

    protected void postRepetir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(661, request);
        int id_respuesta = Integer.parseInt(this.obtenerParametro("id_respuesta"));
        Respuesta_pxp resultado = dao.obtenerRespuesta(id_respuesta);
        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setId_usuario(id_usuario);
        resultado.setUsuario_realizar(u);
        String redireccion = "Lote/index.jsp";
        if (resultado.getEstado() == 5) {
            //Se crea el Path en la carpeta del Proyecto
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Imagenes" + File.separatorChar + "Realizar Lote" + File.separatorChar + resultado.getLote().getNombre();
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            //parametros = upload.parseRequest(request);
            try {
                String string_xml_resultado = parseXML(resultado, ubicacion);
                resultado.setRespuestaString(string_xml_resultado);
                int version = dao.obtenerUltimaVersionRespuesta(id_respuesta);
                dao.editarRespuesta(resultado, version + 1);
                bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_REPETIR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESPUESTAPXP, request.getRemoteAddr());

                request.setAttribute("mensaje", helper.mensajeDeExito("Respuesta registrada correctamente."));
                request.setAttribute("id_lote", resultado.getLote().getId_lote());
                this.getVer(request, response, resultado.getLote().getId_lote());

            } catch (SQLException | ParserConfigurationException | SAXException | IOException | DOMException | IllegalArgumentException | TransformerException ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
            }
        } else {
            request.setAttribute("mensaje", helper.mensajeDeExito("No se puede repetir respuesta."));
            this.getIndex(request, response);
        }
    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Lote construirObjeto(HttpServletRequest request) {
        Lote l = new Lote();
        l.setNombre(request.getParameter("nombre"));

        return l;
    }

    private String parseXML(Respuesta_pxp resultado, String ubicacion) throws ServletException, IOException, TransformerException, SQLException, ParserConfigurationException, SAXException, SIGIPROException, Exception {
        String string_xml_resultado = null;

        InputStream binary_stream = resultado.getPaso().getEstructura().getBinaryStream();

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
                        System.out.println(lista_opciones);
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
                        //Obtengo los usuarios agregados, y los meto en una lista
                        String[] usuarios = this.obtenerParametros(nombre_campo_resultado);
                        List<String> lista_usuarios = new ArrayList<String>();
                        lista_usuarios.addAll(Arrays.asList(usuarios));
                        //Obtengo la seccion escogida, y cargo una lista de los usuarios de dicha seccion
                        nodo_valor = elemento.getElementsByTagName("seccion").item(0);
                        int seccion = Integer.parseInt(nodo_valor.getTextContent());
                        List<Usuario> usuarios_seccion = usuariodao.obtenerUsuariosProduccion(seccion);

                        List<Integer> id_usuarios = new ArrayList<>();

                        for (String id : lista_usuarios) {
                            id_usuarios.add(Integer.parseInt(id));
                        }
                        nodo_valor = elemento.getElementsByTagName("valor").item(0);
                        for (Usuario usuario : usuarios_seccion) {
                            if (id_usuarios.contains(usuario.getId_usuario())) {
                                Element e = documento_resultado.createElement("usuario");

                                Element id_usuario = documento_resultado.createElement("id");
                                id_usuario.appendChild(documento_resultado.createTextNode("" + usuario.getId_usuario()));
                                e.appendChild(id_usuario);

                                Element nombre = documento_resultado.createElement("nombre");
                                nombre.appendChild(documento_resultado.createTextNode("" + usuario.getNombre_completo()));
                                e.appendChild(nombre);

                                nodo_valor.appendChild(e);
                            }
                        }
                        break;
                    case ("subbodega"):
                        nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                        //Obtengo los productos seleccionados
                        String[] productos = this.obtenerParametros(nombre_campo_resultado);
                        List<String> lista_productos = new ArrayList<String>();
                        lista_productos.addAll(Arrays.asList(productos));
                        //Parseo los id's en String, a Int
                        List<Integer> lista_id_productos = new ArrayList<>();
                        for (String producto : lista_productos) {
                            lista_id_productos.add(Integer.parseInt(producto));
                        }
                        //Obtengo los productos para poder almacenar los nombres de los productos
                        int id_sub_bodega = Integer.parseInt(elemento.getElementsByTagName("subbodega").item(0).getTextContent());
                        SubBodega subbodega = subbodegadao.buscarSubBodegaEInventariosProduccion(id_sub_bodega);
                        List<InventarioSubBodega> inventario = subbodega.getInventarios();
                        HashMap<Integer, String> nombre_productos = new HashMap<>();
                        for (InventarioSubBodega inv : inventario) {
                            if (lista_id_productos.contains(inv.getProducto().getId_producto())) {
                                nombre_productos.put(inv.getProducto().getId_producto(), inv.getProducto().getNombre());
                            }
                        }
                        //Ingreso los valores dentro del XML
                        nodo_valor = elemento.getElementsByTagName("valor").item(0);
                        for (Integer id : lista_id_productos) {
                            Element e = documento_resultado.createElement("producto");

                            Element id_producto = documento_resultado.createElement("id");
                            id_producto.appendChild(documento_resultado.createTextNode("" + id));
                            e.appendChild(id_producto);

                            Element nombre_producto = documento_resultado.createElement("nombre");
                            String nombre = nombre_productos.get(id);
                            nombre_producto.appendChild(documento_resultado.createTextNode(nombre));
                            e.appendChild(nombre_producto);

                            Element cantidad_producto = documento_resultado.createElement("cantidad");
                            String cantidad = this.obtenerParametro(nombre_campo_resultado + "_" + id);
                            cantidad_producto.appendChild(documento_resultado.createTextNode(cantidad));
                            e.appendChild(cantidad_producto);

                            nodo_valor.appendChild(e);

                        }
                        break;
                    case ("sangria"):
                        nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                        //Obtengo los productos seleccionados
                        String[] sangrias = this.obtenerParametros(nombre_campo_resultado);
                        List<String> lista_sangrias = new ArrayList<String>();
                        lista_sangrias.addAll(Arrays.asList(sangrias));
                        //Parseo los id's en String, a Int
                        List<Integer> lista_id_sangrias = new ArrayList<>();
                        for (String sangria : lista_sangrias) {
                            lista_id_sangrias.add(Integer.parseInt(sangria));
                        }
                        //Ingreso los valores dentro del XML
                        nodo_valor = elemento.getElementsByTagName("valor").item(0);
                        for (Integer id : lista_id_sangrias) {
                            Element e = documento_resultado.createElement("sangria");

                            Element id_sangria = documento_resultado.createElement("id");
                            id_sangria.appendChild(documento_resultado.createTextNode("" + id));
                            e.appendChild(id_sangria);

                            nodo_valor.appendChild(e);
                        }
                        break;
                    case ("aa"):
                        nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                        valor = this.obtenerParametro(nombre_campo_resultado);
                        nodo_valor = elemento.getElementsByTagName("valor").item(0);
                        nodo_valor.setTextContent(valor);
                        break;
                    case ("imagen"):
                        nombre_campo_resultado = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                        valor = this.obtenerImagen(nombre_campo_resultado, ubicacion);
                        if (valor != null) {
                            nodo_valor = elemento.getElementsByTagName("valor").item(0);
                            nodo_valor.setTextContent(valor);
                        } else {
                            String actual = this.obtenerParametro(nombre_campo_resultado + "_actual");
                            if (!actual.equals("")) {
                                nodo_valor = elemento.getElementsByTagName("valor").item(0);
                                nodo_valor.setTextContent(actual);
                            }
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
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(documento_resultado), new StreamResult(writer));
        string_xml_resultado = writer.getBuffer().toString().replaceAll("\n|\r", "");

        System.out.println(string_xml_resultado);

        return string_xml_resultado;
    }

    private boolean crearDirectorio(String path) {
        boolean resultado = false;
        File directorio = new File(path);
        if (!directorio.exists()) {
            System.out.println("Creando directorio: " + path);
            resultado = false;
            try {
                directorio.mkdirs();
                resultado = true;
            } catch (SecurityException se) {
                se.printStackTrace();
            }
            if (resultado) {
                System.out.println("Directorio Creado");
            }
        } else {
            resultado = true;
        }
        return resultado;
    }

    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
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
            Method metodo = clase.getDeclaredMethod(nombreMetodo, HttpServletRequest.class, HttpServletResponse.class
            );
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

    private void obtenerParametros(HttpServletRequest request) {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            parametros = upload.parseRequest(request);
        } catch (FileUploadException ex) {
            ex.printStackTrace();
        }
    }

    // </editor-fold>
}
