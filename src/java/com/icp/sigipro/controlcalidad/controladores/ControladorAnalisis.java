/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.AnalisisDAO;
import com.icp.sigipro.controlcalidad.dao.EquipoDAO;
import com.icp.sigipro.controlcalidad.dao.ReactivoDAO;
import com.icp.sigipro.controlcalidad.dao.ResultadoDAO;
import com.icp.sigipro.controlcalidad.dao.SolicitudDAO;
import com.icp.sigipro.controlcalidad.dao.TipoEquipoDAO;
import com.icp.sigipro.controlcalidad.dao.TipoMuestraDAO;
import com.icp.sigipro.controlcalidad.dao.TipoReactivoDAO;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Equipo;
import com.icp.sigipro.controlcalidad.modelos.Reactivo;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
import com.icp.sigipro.controlcalidad.modelos.TipoMuestra;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.core.formulariosdinamicos.ControlXSLT;
import com.icp.sigipro.core.formulariosdinamicos.ControlXSLTDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperExcel;
import com.icp.sigipro.utilidades.HelperXML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
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
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ld.conejo, boga
 */
@WebServlet(name = "ControladorAnalisis", urlPatterns = {"/ControlCalidad/Analisis"})
public class ControladorAnalisis extends SIGIPROServlet {

    //Administrar, Realizar
    private final int[] permisos = {1, 540, 541};
    //-----------------
    private final AnalisisDAO dao = new AnalisisDAO();
    private final UsuarioDAO usuariodao = new UsuarioDAO();
    private final TipoEquipoDAO tipoequipodao = new TipoEquipoDAO();
    private final TipoReactivoDAO tiporeactivodao = new TipoReactivoDAO();
    private final TipoMuestraDAO tipomuestradao = new TipoMuestraDAO();
    private final ControlXSLTDAO controlxsltdao = new ControlXSLTDAO();
    private final EquipoDAO equipodao = new EquipoDAO();
    private final ReactivoDAO reactivodao = new ReactivoDAO();
    private final ResultadoDAO resultadodao = new ResultadoDAO();
    private final SolicitudDAO solicituddao = new SolicitudDAO();
    private String ubicacion;

    private int nombre_campo;

    protected final Class clase = ControladorAnalisis.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("archivo");
            add("realizar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregareditar");
            add("realizar");
            add("aprobar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);

        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        Analisis analisis = dao.obtenerAnalisis(id_analisis);

        String filename = analisis.getMachote();
        File file = new File(filename);

        if (file.exists()) {
            ServletContext ctx = getServletContext();
            InputStream fis = new FileInputStream(file);
            String mimeType = ctx.getMimeType(file.getAbsolutePath());

            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
            response.setContentLength((int) file.length());
            String nombre = "machote-" + analisis + "." + this.getFileExtension(filename);
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

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(540, listaPermisos);

        String redireccion = "Analisis/Agregar.jsp";
        Analisis a = new Analisis();
        List<TipoEquipo> tipoequipo = tipoequipodao.obtenerTipoEquipos();
        List<TipoReactivo> tiporeactivo = tiporeactivodao.obtenerTipoReactivos();
        List<TipoMuestra> tiposmuestra = new ArrayList<TipoMuestra>();
        try {
            tiposmuestra = tipomuestradao.obtenerTiposDeMuestra();
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", sig_ex.getMessage());
        }

        request.setAttribute("analisis", a);
        request.setAttribute("tipoequipos", tipoequipo);
        request.setAttribute("tiporeactivos", tiporeactivo);
        request.setAttribute("tiposmuestra", tiposmuestra);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Analisis/index.jsp";
        List<Analisis> analisis = dao.obtenerAnalisis();
        request.setAttribute("listaAnalisis", analisis);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Analisis/Ver.jsp";
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        ControlXSLT xslt;
        Analisis analisis;

        try {
            analisis = dao.obtenerAnalisis(id_analisis);
            xslt = controlxsltdao.obtenerControlXSLTVerFormulario();
            if (analisis.getEstructura() != null) {
                TransformerFactory tff = TransformerFactory.newInstance();
                InputStream streamXSLT = xslt.getEstructura().getBinaryStream();
                InputStream streamXML = analisis.getEstructura().getBinaryStream();
                Transformer transformador = tff.newTransformer(new StreamSource(streamXSLT));
                StreamSource stream_source = new StreamSource(streamXML);
                StreamResult stream_result = new StreamResult(new StringWriter());
                transformador.transform(stream_source, stream_result);

                String formulario = stream_result.getWriter().toString();

                request.setAttribute("cuerpo_datos", formulario);
            } else {
                request.setAttribute("cuerpo_datos", null);
            }
            request.setAttribute("analisis", analisis);
            redireccionar(request, response, redireccion);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParserConfigurationException, SAXException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(540, listaPermisos);
        String redireccion = "Analisis/Editar.jsp";
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        Analisis a = dao.obtenerAnalisis(id_analisis);

        HelperXML xml = new HelperXML(a.getEstructura());

        HashMap<Integer, HashMap> diccionario_formulario = xml.getDictionary();

        System.out.println(diccionario_formulario.get(2).get("nombrefilas"));

        Set<Integer> it = diccionario_formulario.keySet();
        List<Integer> lista = new ArrayList<Integer>();
        lista.addAll(it);

        List<TipoEquipo> tipoequipo = tipoequipodao.obtenerTipoEquipos();

        List<TipoReactivo> tiporeactivo = tiporeactivodao.obtenerTipoReactivos();
        request.setAttribute("analisis", a);
        request.setAttribute("tipoequipos", tipoequipo);
        request.setAttribute("tiporeactivos", tiporeactivo);
        request.setAttribute("lista", lista);
        request.setAttribute("diccionario", diccionario_formulario);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(540, listaPermisos);
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        boolean resultado = false;
        try {
            resultado = dao.eliminarAnalisis(id_analisis);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_analisis, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ANALISIS, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Analisis eliminado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser eliminado ya que tiene otras asociaciones."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser eliminado ya que tiene otras asociaciones."));
            this.getIndex(request, response);
        }

    }

    protected void getRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(541, listaPermisos);
        String redireccion = "Analisis/Realizar.jsp";

        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        request.setAttribute("id_analisis", id_analisis);
        request.setAttribute("id_ags", request.getParameter("id_ags"));

        ControlXSLT xslt;
        Analisis analisis;

        try {
            xslt = controlxsltdao.obtenerControlXSLTFormulario();
            analisis = dao.obtenerAnalisis(id_analisis);

            TransformerFactory tff = TransformerFactory.newInstance();
            InputStream streamXSLT = xslt.getEstructura().getBinaryStream();
            InputStream streamXML = analisis.getEstructura().getBinaryStream();
            Transformer transformador = tff.newTransformer(new StreamSource(streamXSLT));
            StreamSource stream_source = new StreamSource(streamXML);
            StreamResult stream_result = new StreamResult(new StringWriter());
            transformador.transform(stream_source, stream_result);

            String formulario = stream_result.getWriter().toString();

            request.setAttribute("cuerpo_formulario", formulario);
            List<Equipo> equipos = (analisis.tiene_equipos()) ? equipodao.obtenerEquiposTipo(analisis.pasar_ids_tipos("equipos")) : new ArrayList<Equipo>();
            List<Reactivo> reactivos = (analisis.tiene_reactivos()) ? reactivodao.obtenerReactivosTipo(analisis.pasar_ids_tipos("reactivos")) : new ArrayList<Reactivo>();

            request.setAttribute("equipos", equipos);
            request.setAttribute("reactivos", reactivos);
            request.setAttribute("analisis", analisis);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

        redireccionar(request, response, redireccion);
    }

    protected void getResultado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(541, listaPermisos);
        String redireccion = "VerResultado.jsp";

        int id_resultado = Integer.parseInt(request.getParameter("id_resultado"));

        ControlXSLT xslt;
        Resultado resultado;

        try {
            xslt = controlxsltdao.obtenerControlXSLTResultado();
            resultado = resultadodao.obtenerResultado(id_resultado);

            TransformerFactory tff = TransformerFactory.newInstance();
            InputStream streamXSLT = xslt.getEstructura().getBinaryStream();
            InputStream streamXML = resultado.getDatos().getBinaryStream();
            Transformer transformador = tff.newTransformer(new StreamSource(streamXSLT));
            StreamSource stream_source = new StreamSource(streamXML);
            StreamResult stream_result = new StreamResult(new StringWriter());
            transformador.transform(stream_source, stream_result);

            String formulario = stream_result.getWriter().toString();

            request.setAttribute("resultado", formulario);
            request.setAttribute("cuerpo_datos", formulario);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParserConfigurationException, SAXException {
        boolean resultado = false;

        Analisis a = construirObjeto(parametros, request, ubicacion);

        if (a.getId_analisis() == 0) {
            resultado = dao.insertarAnalisis(a);
            if (resultado) {
                dao.insertarTipoEquipo(a.getTipos_equipos_analisis(), a.getId_analisis());
                dao.insertarTipoReactivo(a.getTipos_reactivos_analisis(), a.getId_analisis());
                dao.insertarTipoMuestra(a.getTipos_muestras_analisis(), a.getId_analisis());
                request.setAttribute("mensaje", helper.mensajeDeExito("Analisis agregado correctamente"));
                //Funcion que genera la bitacora
                bitacora.setBitacora(a.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ANALISIS, request.getRemoteAddr());
                //*----------------------------*
                this.getIndex(request, response);
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser agregado. Inténtelo de nuevo."));
                this.getAgregar(request, response);
            }
        } else {
            resultado = dao.editarAnalisis(a);
            if (resultado) {
                //Se vacian por si hubo cambios
                dao.eliminarTiposEquiposAnalisis(a.getId_analisis());
                dao.eliminarTiposReactivosAnalisis(a.getId_analisis());
                //------------------------------
                dao.insertarTipoEquipo(a.getTipos_equipos_analisis(), a.getId_analisis());
                dao.insertarTipoReactivo(a.getTipos_reactivos_analisis(), a.getId_analisis());
                //Funcion que genera la bitacora
                bitacora.setBitacora(a.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ANALISIS, request.getRemoteAddr());
                //*----------------------------*
                request.setAttribute("mensaje", helper.mensajeDeExito("Analisis editado correctamente"));
                this.getIndex(request, response);
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser editado. Inténtelo de nuevo."));
                request.setAttribute("id_analisis", a.getId_analisis());
                this.getEditar(request, response);
            }
        }

    }

    protected void postAprobar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean resultado = false;

        String usuario = request.getParameter("usuario_aprobacion");
        String contrasenna = request.getParameter("passw");

        int id_analisis = Integer.parseInt(request.getParameter("id_analisis_aprobar"));

        boolean autenticacion = usuariodao.autorizarRecibo(usuario, contrasenna);
        if (autenticacion) {
            resultado = dao.aprobarAnalisis(id_analisis);
            if (resultado) {
                Analisis a = dao.obtenerAnalisis(id_analisis);
                request.setAttribute("mensaje", helper.mensajeDeExito("Analisis aprobado correctamente"));
                //Funcion que genera la bitacora
                bitacora.setBitacora(a.parseJSON(), Bitacora.ACCION_APROBAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ANALISIS, request.getRemoteAddr());
                //*----------------------------*
                this.getIndex(request, response);
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser aprobado. Inténtelo de nuevo."));
                this.getIndex(request, response);
            }
        } else {
            request.setAttribute("mensaje", helper.mensajeDeError("Usuario o contraseña incorrectos."));
            this.getIndex(request, response);
        }
    }

    protected void postRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_analisis = Integer.parseInt(this.obtenerParametro("id_analisis"));
        int id_ags = Integer.parseInt(this.obtenerParametro("id_ags"));

        Analisis analisis = dao.obtenerAnalisis(id_analisis);

        Resultado resultado = new Resultado();
        AnalisisGrupoSolicitud ags = new AnalisisGrupoSolicitud();
        ags.setId_analisis_grupo_solicitud(id_ags);
        resultado.setAgs(ags);

        Usuario u = new Usuario();
        int id_usuario = (int) request.getSession().getAttribute("idusuario");
        u.setIdUsuario(id_usuario);

        resultado.setUsuario(u);

        String redireccion = "Analisis/index.jsp";

        String[] equipos_utilizados = this.obtenerParametros("equipos");
        String[] reactivos_utilizados = this.obtenerParametros("reactivos");

        HelperExcel excel = this.guardarArchivoResultado(resultado, analisis, ubicacion);

        try {
            InputStream binary_stream = analisis.getEstructura().getBinaryStream();

            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documento_resultado = parser.parse(binary_stream);
            Element elemento_resultado = documento_resultado.getDocumentElement();

            NodeList lista_nodos = elemento_resultado.getElementsByTagName("campo");

            for (int i = 0; i < lista_nodos.getLength(); i++) {
                Node nodo = lista_nodos.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {

                    Element elemento = (Element) nodo;
                    String tipo_campo = elemento.getElementsByTagName("tipo").item(0).getTextContent();

                    if (!tipo_campo.equals("table")) {
                        String valor;
                        String nombre_campo = elemento.getElementsByTagName("nombre-campo").item(0).getTextContent();
                        Node nodo_valor = elemento.getElementsByTagName("valor").item(0);
                        if (tipo_campo.equalsIgnoreCase("excel") || tipo_campo.equalsIgnoreCase("excel_tabla")) {
                            Node nodo_celda = elemento.getElementsByTagName("celda").item(0);
                            String celda = nodo_celda.getTextContent();
                            valor = excel.obtenerCelda(celda);
                        } else {
                            valor = this.obtenerParametro(nombre_campo);
                        }
                        nodo_valor.setTextContent(valor);

                        Node nodo_resultado = elemento.getElementsByTagName("resultado").item(0);
                        if (nodo_resultado != null) {
                            String texto = nodo_resultado.getTextContent();
                            if (texto.equalsIgnoreCase("true")) {
                                resultado.setResultado(valor);
                            }
                        }
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

            resultado.setDatos_string(string_xml_resultado);

            resultado.setEquipos(equipos_utilizados);
            resultado.setReactivos(reactivos_utilizados);

            resultadodao.insertarResultado(resultado);

            redireccion = "/ControlCalidad/Solicitud/Ver.jsp";
            try {
                SolicitudCC s = solicituddao.obtenerSolicitud(resultado.getAgs().getGrupo().getSolicitud().getId_solicitud());
                request.setAttribute("solicitud", s);

            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("No se pudo obtener la solicitud. Notifique al administrador del sistema."));
            }

            request.setAttribute("mensaje", helper.mensajeDeExito("Resultado registrado correctamente."));

        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", helper.mensajeDeError(sig_ex.getMessage()));
        } catch (SQLException | ParserConfigurationException | SAXException | IOException | DOMException | IllegalArgumentException | TransformerException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Contacte al administrador del sistema."));
        }

        this.redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Analisis construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) {
        Analisis a = new Analisis();
        a.setTipos_equipos_analisis(new ArrayList<TipoEquipo>());
        a.setTipos_reactivos_analisis(new ArrayList<TipoReactivo>());
        //Se crea un diccionario con los elementos del Formulario Dinamico
        HashMap<Integer, HashMap> diccionario_formulario = new HashMap<Integer, HashMap>();
        //Variable donde se define el ID actual del campo o tabla del formulario
        int id_actual = 0;
        //Variable donde se agrega el orden en el que el Formulario se agrego
        String orden = "";
        //Diccionario con todos los valores de las columnas y filas del Formulario
        HashMap<Integer, HashMap> columnas_filas = new HashMap<Integer, HashMap>();
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
                        a.setNombre(fieldValue);
                        break;
                    case "id_analisis":
                        int id_analisis = Integer.parseInt(fieldValue);
                        a.setId_analisis(id_analisis);
                        break;
                    case "id_analisis_aprobar":
                        int id_analisis_aprobar = Integer.parseInt(fieldValue);
                        a.setId_analisis(id_analisis_aprobar);
                        break;
                    case "tipoequipos":
                        TipoEquipo tipoequipo = new TipoEquipo();
                        tipoequipo.setId_tipo_equipo(Integer.parseInt(fieldValue));
                        a.getTipos_equipos_analisis().add(tipoequipo);
                        break;
                    case "tiporeactivos":
                        TipoReactivo tiporeactivo = new TipoReactivo();
                        tiporeactivo.setId_tipo_reactivo(Integer.parseInt(fieldValue));
                        a.getTipos_reactivos_analisis().add(tiporeactivo);
                        break;
                    case "tipos-muestra":
                        TipoMuestra tipo_muestra = new TipoMuestra();
                        tipo_muestra.setId_tipo_muestra(Integer.parseInt(fieldValue));
                        a.getTipos_muestras_analisis().add(tipo_muestra);
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
                                if (values[0].equals("c")) {
                                    llaves.put("tipo", "campo");
                                } else {
                                    llaves.put("tipo", "tabla");
                                }
                                diccionario_formulario.put(id, llaves);
                            }
                            //Se crea el Hash en el diccionario en caso de que fuera una tabla
                            if (!columnas_filas.containsKey(id)) {
                                HashMap<String, Integer> columna_fila = new HashMap<String, Integer>();
                                columna_fila.put("columnas", 1);
                                columna_fila.put("filas", 1);
                                columnas_filas.put(id, columna_fila);
                            }
                            switch (values[1]) {
                                case "nombrecolumna":
                                    int cantidad_columnas1 = (int) columnas_filas.get(id).get("columnas");
                                    diccionario_formulario.get(id).put(values[1] + "_" + cantidad_columnas1, fieldValue);
                                    break;
                                case "tipocampocolumna":
                                    int cantidad_columnas2 = (int) columnas_filas.get(id).get("columnas");
                                    diccionario_formulario.get(id).put(values[1] + "_" + cantidad_columnas2, fieldValue);
                                    cantidad_columnas2++;
                                    columnas_filas.get(id).put("columnas", cantidad_columnas2);
                                    break;
                                case "columnacelda":
                                    int cantidad_columnas3 = (int) columnas_filas.get(id).get("columnas") - 1;
                                    diccionario_formulario.get(id).put(values[1] + "_" + cantidad_columnas3, fieldValue);
                                    break;
                                case "nombrefilaespecial":
                                    int cantidadFilas1 = (int) columnas_filas.get(id).get("filas");
                                    diccionario_formulario.get(id).put(values[1] + "_" + cantidadFilas1, fieldValue);
                                    break;
                                case "tipocampofilaespecial":
                                    int cantidadFilas2 = (int) columnas_filas.get(id).get("filas");
                                    diccionario_formulario.get(id).put(values[1] + "_" + cantidadFilas2, fieldValue);
                                    cantidadFilas2++;
                                    columnas_filas.get(id).put("filas", cantidadFilas2);
                                    break;
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
                        ubicacion += File.separatorChar + "Machotes";
                        this.crearDirectorio(ubicacion);
                        //Creacion del nombre
                        Date dNow = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
                        String fecha = ft.format(dNow);
                        String extension = this.getFileExtension(item.getName());
                        String nombre = a.getNombre() + "-" + fecha + "." + extension;
                        //---------------------
                        File archivo = new File(ubicacion, nombre);
                        item.write(archivo);
                        a.setMachote(archivo.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        if (!diccionario_formulario.isEmpty()) {
            //Se transforma el diccionario en un XML
            String xml = this.parseDictXML(diccionario_formulario, orden, columnas_filas);
            a.setEstructuraString(xml);
        }
        return a;
    }

    private HelperExcel guardarArchivoResultado(Resultado resultado, Analisis analisis, String ubicacion) {

        HelperExcel excel = null;

        FileItem item = this.obtenerParametroFileItem("resultado");

        try {
            if (item.getSize() != 0) {
                ubicacion += File.separatorChar + "Resultados";
                this.crearDirectorio(ubicacion);
                //Creacion del nombre
                Date dNow = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmm");
                String fecha = ft.format(dNow);
                String extension = this.getFileExtension(item.getName());
                String nombre = analisis.getNombre() + "-" + fecha + "." + extension;
                //---------------------
                File archivo = new File(ubicacion, nombre);
                item.write(archivo);
                resultado.setPath(archivo.getAbsolutePath());

                excel = new HelperExcel(resultado.getPath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return excel;
    }

    private String parseDictXML(HashMap<Integer, HashMap> diccionario_formulario, String orden, HashMap<Integer, HashMap> columnas_filas) {
        this.nombre_campo = 1;
        //Se obtiene el orden de los campos
        String[] orden_formulario = orden.split(",");
        int contador_tablas = 0;
        HelperXML xml = new HelperXML();
        //Se itera sobre los IDS del orden de los campos
        for (String i : orden_formulario) {
            if (!i.equals("")) {
                int key = Integer.parseInt(i);
                Element campo = xml.agregarElemento("campo");
                HashMap<String, String> hash = diccionario_formulario.get(key);
                if (hash.get("tipo").equals("campo")) {
                    this.crearCampo(xml, hash, campo);
                } else {
                    xml.agregarSubelemento("tipo", "table", campo);
                    //if (hash.containsKey("tablavisible")) {
                    //    xml.agregarSubelemento("visible", "True", campo);
                    //} else {
                    //    xml.agregarSubelemento("visible", "False", campo);
                    //}
                    String nombretabla = hash.get("nombretabla");

                    xml.agregarSubelemento("nombre", nombretabla, campo);

                    Element columnas = xml.agregarElemento("columnas", campo);
                    Element filas = xml.agregarElemento("filas", campo);

                    List<String> tiposcolumnas = new ArrayList<String>();
                    List<String> columnacelda = new ArrayList<String>();
                    //Columnas
                    Element primeraColumna = xml.agregarElemento("columna", columnas);
                    xml.agregarSubelemento("nombre", hash.get("nombrefilacolumna"), primeraColumna);
                    int cantidad_columnas = (int) columnas_filas.get(key).get("columnas") - 1;
                    for (int it = 0; it < cantidad_columnas; it++) {
                        int col = it + 1;
                        String keycol = "nombrecolumna_" + col;
                        String valorCol = hash.get(keycol);
                        String keytipo = "tipocampocolumna_" + col;
                        String valorTipo = hash.get(keytipo);
                        String keycelda = "columnacelda_" + col;
                        String valorCelda;
                        if (hash.containsKey(keycelda)) {
                            valorCelda = hash.get(keycelda);
                        } else {
                            valorCelda = "";
                        }
                        tiposcolumnas.add(valorTipo);
                        columnacelda.add(valorCelda);
                        Element columna = xml.agregarElemento("columna", columnas);
                        if (valorTipo.equals("excel_tabla")) {
                            Attr tipo = xml.definirAtributo("tipo", "excel_tabla");
                            xml.agregarAtributo(columna, tipo);
                        }
                        xml.agregarSubelemento("nombre", valorCol, columna);
                    }
                    //------------
                    //Filas
                    String[] nombresfilas = "".split("");
                    if (hash.containsKey("connombre")) {
                        nombresfilas = hash.get("nombresfilas").split(",");
                    }
                    int cantidadFilas = Integer.parseInt(hash.get("cantidadfilas"));
                    for (int it = 0; it < cantidadFilas; it++) {
                        Element fila = xml.agregarElemento("fila", filas);
                        Element celdas = xml.agregarElemento("celdas", fila);
                        if (nombresfilas.length > 1) {
                            Element celda = xml.agregarElemento("celda", celdas);
                            Element fila_nombre = xml.agregarElemento("celda-nombre", celda);
                            xml.agregarSubelemento("nombre", nombresfilas[it], fila_nombre);
                        } else {
                            Element celda = xml.agregarElemento("celda", celdas);
                            Element fila_nombre = xml.agregarElemento("celda-nombre", celda);
                            xml.agregarSubelemento("nombre", "", fila_nombre);
                        }
                        for (int jt = 0; jt < tiposcolumnas.size(); jt++) {
                            Element celda = xml.agregarElemento("celda", celdas);
                            Element campo_fila = xml.agregarElemento("campo", celda);
                            xml.agregarSubelemento("tipo", tiposcolumnas.get(jt), campo_fila);
                            String nombre_celda = "Tabla_" + contador_tablas + "_Celda_" + it + "_" + jt;
                            xml.agregarSubelemento("nombre-campo", nombre_celda, campo_fila);
                            String celdaColumna = columnacelda.get(jt);
                            if (!celdaColumna.equals("")) {
                                celdaColumna = celdaColumna.replace("-", "");
                                xml.agregarSubelemento("celda", celdaColumna, campo_fila);
                                String[] listaCelda = columnacelda.get(jt).split("-");
                                if (listaCelda.length > 0) {
                                    int numeroCelda = Integer.parseInt(listaCelda[1]);
                                    numeroCelda++;
                                    String nuevaCelda = listaCelda[0] + "-" + numeroCelda;
                                    columnacelda.remove(jt);
                                    columnacelda.add(jt, nuevaCelda);
                                }
                            }
                            xml.agregarSubelemento("valor", "", campo_fila);
                        }
                    }
                    //Filas especiales
                    int cantidadFilasEspeciales = (int) columnas_filas.get(key).get("filas") - 1;
                    for (int it = 0; it < cantidadFilasEspeciales; it++) {
                        int fil = it + 1;
                        String keycol = "nombrefilaespecial_" + fil;
                        String valorFil = hash.get(keycol);
                        String keytipo = "tipocampofilaespecial_" + fil;
                        String valorTipo = hash.get(keytipo);
                        Element fila = xml.agregarElemento("fila", filas);
                        Attr tipo = xml.definirAtributo("tipo", "especial");
                        Attr funcion = xml.definirAtributo("funcion", valorTipo);
                        xml.agregarAtributo(fila, tipo);
                        xml.agregarAtributo(fila, funcion);
                        Element celdas = xml.agregarElemento("celdas", fila);
                        Element celda_primera = xml.agregarElemento("celda", celdas);
                        Element celda_nombre = xml.agregarElemento("celda-nombre", celda_primera);
                        xml.agregarSubelemento("nombre", valorFil, celda_nombre);
                        for (int jt = 0; jt < tiposcolumnas.size(); jt++) {
                            Element celda = xml.agregarElemento("celda", celdas);
                            Element campo_fila = xml.agregarElemento("campo", celda);
                            xml.agregarSubelemento("tipo", tiposcolumnas.get(jt), campo_fila);
                            String nombre_celda = "Tabla_" + contador_tablas + "_" + valorTipo + "_" + jt;
                            xml.agregarSubelemento("nombre-campo", nombre_celda, campo_fila);
                            String celdaColumna = columnacelda.get(jt);
                            if (!celdaColumna.equals("")) {
                                celdaColumna = celdaColumna.replace("-", "");
                                xml.agregarSubelemento("celda", celdaColumna, campo_fila);
                                String[] listaCelda = columnacelda.get(jt).split("-");
                                if (listaCelda.length > 0) {
                                    int numeroCelda = Integer.parseInt(listaCelda[1]);
                                    numeroCelda++;
                                    String nuevaCelda = listaCelda[0] + "-" + numeroCelda;
                                    columnacelda.remove(jt);
                                    columnacelda.add(jt, nuevaCelda);
                                }
                            }
                            xml.agregarSubelemento("valor", "", campo_fila);
                        }
                    }
                    //------------
                    contador_tablas++;
                }
            }
        }

        return xml.imprimirXML();
    }

    private void crearCampo(HelperXML xml, HashMap<String, String> hash, Element campo) {
        System.out.println(hash.get("nombre") + "_" + this.nombre_campo);
        xml.agregarSubelemento("nombre-campo", hash.get("nombre") + "_" + this.nombre_campo, campo);
        this.nombre_campo++;
        xml.agregarSubelemento("etiqueta", hash.get("nombre"), campo);
        xml.agregarSubelemento("valor", "", campo);
        if (hash.containsKey("manual")) {
            String celda = hash.get("celda");
            celda = celda.replace("-", "");
            xml.agregarSubelemento("tipo", "Excel", campo);
            xml.agregarSubelemento("celda", celda, campo);
        } else {
            xml.agregarSubelemento("tipo", hash.get("tipocampo"), campo);
        }
        if (hash.containsKey("camporesultado")) {
            xml.agregarSubelemento("resultado", "True", campo);
        } else {
            xml.agregarSubelemento("resultado", "False", campo);
        }
    }

    private boolean crearDirectorio(String path) {
        boolean resultado = false;
        File directorio = new File(path);
        if (!directorio.exists()) {
            resultado = false;
            try {
                directorio.mkdirs();
                resultado = true;
            } catch (SecurityException se) {
                se.printStackTrace();
            }
            if (resultado) {
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
                if (this.obtenerParametro("accion").equals("realizar")) {
                    accion = "realizar";
                }
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
            //Se crea el Path en la carpeta del Proyecto
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String pathArr[] = fullPath.split("/WEB-INF/classes/");
            fullPath = pathArr[0];
            this.ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Analisis";
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            parametros = upload.parseRequest(request);
        } catch (FileUploadException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }

    // </editor-fold>
}
