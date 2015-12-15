/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.google.gson.Gson;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.core.formulariosdinamicos.ProduccionXSLT;
import com.icp.sigipro.core.formulariosdinamicos.ProduccionXSLTDAO;
import com.icp.sigipro.produccion.dao.LoteDAO;
import com.icp.sigipro.produccion.dao.PasoDAO;
import com.icp.sigipro.produccion.modelos.Lote;
import com.icp.sigipro.produccion.modelos.Paso;
import com.icp.sigipro.produccion.modelos.Protocolo;
import com.icp.sigipro.produccion.modelos.Respuesta_pxp;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperExcel;
import com.icp.sigipro.utilidades.HelperTransformaciones;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
@WebServlet(name = "ControladorLote", urlPatterns = {"/Produccion/Lote"})
public class ControladorLote extends SIGIPROServlet {

    //CRUD, Realizar, Aprobar, Activar Respuesta
    private final int[] permisos = {660, 661, 662, 663};
    //-----------------
    private final LoteDAO dao = new LoteDAO();
    private final ProduccionXSLTDAO produccionxsltdao = new ProduccionXSLTDAO();
    private final PasoDAO pasodao = new PasoDAO();
    private final UsuarioDAO usuariodao = new UsuarioDAO();

    private final HelperTransformaciones helper_transformaciones = HelperTransformaciones.getHelperTransformaciones();

    protected final Class clase = ControladorLote.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("eliminar");
            add("realizar");
            add("usuariosajax");
            add("verrespuesta");
            add("historial");
            add("verhistorial");
            add("repetir");
            add("activar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregar");
            add("realizar");
            add("aprobar");
            add("repetir");
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

        int id_lote = Integer.parseInt(request.getParameter("id_lote"));
        Lote lote = dao.obtenerLote(id_lote);
        if (!lote.isAprobacion()) {
            request.setAttribute("id_lote", id_lote);

            ProduccionXSLT xslt;
            Paso paso;

            try {
                xslt = produccionxsltdao.obtenerProduccionXSLTFormulario();

                paso = dao.obtenerPasoActual(id_lote);

                System.out.println(paso.getEstructura().getString());

                String formulario = helper_transformaciones.transformar(xslt, paso.getEstructura());

                request.setAttribute("cuerpo_formulario", formulario);

                request.setAttribute("paso", paso);

                request.setAttribute("lote", lote);

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

        validarPermiso(661, request);
        String redireccion = "Lote/Repetir.jsp";

        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta"));
        Respuesta_pxp respuesta = dao.obtenerRespuesta(id_respuesta);
        if (!respuesta.getLote().isAprobacion()) {
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
            request.setAttribute("mensaje", helper.mensajeDeError("No se ha aprobado el paso."));
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

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(662, request);
        int id_lote = Integer.parseInt(request.getParameter("id_lote"));
        int id_respuesta = Integer.parseInt(request.getParameter("id_respuesta_actual"));
        int posicion_actual = Integer.parseInt(request.getParameter("posicion_actual"));
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        boolean resultado = false;
        try {
            resultado = dao.aprobarPasoActual(id_lote, id_respuesta, id_usuario, posicion_actual);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_lote, Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_LOTEPRODUCCION, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Paso de Protocolo aprobado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser aprobado."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser aprobado."));
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

    protected void postRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_lote = Integer.parseInt(this.obtenerParametro("id_lote"));

        Lote lote = dao.obtenerLote(id_lote);

        Respuesta_pxp resultado = new Respuesta_pxp();
        resultado.setLote(lote);
        resultado.setPaso(lote.getPaso_actual());

        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setId_usuario(id_usuario);

        resultado.setUsuario_realizar(u);

        String redireccion = "Lote/index.jsp";

        try {
            InputStream binary_stream = lote.getPaso_actual().getEstructura().getBinaryStream();

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

    }

    protected void postRepetir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_respuesta = Integer.parseInt(this.obtenerParametro("id_respuesta"));
        Respuesta_pxp resultado = dao.obtenerRespuesta(id_respuesta);
        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setId_usuario(id_usuario);
        resultado.setUsuario_realizar(u);
        String redireccion = "Lote/index.jsp";
        try {
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

    }

    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Lote construirObjeto(HttpServletRequest request) {
        Lote l = new Lote();
        l.setNombre(request.getParameter("nombre"));

        return l;
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
