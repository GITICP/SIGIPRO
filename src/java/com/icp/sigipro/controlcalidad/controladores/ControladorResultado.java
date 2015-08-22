/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.controlcalidad.dao.AnalisisDAO;
import com.icp.sigipro.controlcalidad.dao.EquipoDAO;
import com.icp.sigipro.controlcalidad.dao.PatronDAO;
import com.icp.sigipro.controlcalidad.dao.ReactivoDAO;
import com.icp.sigipro.controlcalidad.dao.ResultadoDAO;
import com.icp.sigipro.controlcalidad.dao.SolicitudDAO;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Equipo;
import com.icp.sigipro.controlcalidad.modelos.Patron;
import com.icp.sigipro.controlcalidad.modelos.Reactivo;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.SolicitudCC;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.core.formulariosdinamicos.ControlXSLT;
import com.icp.sigipro.core.formulariosdinamicos.ControlXSLTDAO;
import com.icp.sigipro.seguridad.modelos.Usuario;
import com.icp.sigipro.utilidades.HelperExcel;
import com.icp.sigipro.utilidades.HelperTransformaciones;
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
 * @author ld.conejo, boga
 */
@WebServlet(name = "ControladorResultado", urlPatterns = {"/ControlCalidad/Resultado"})
public class ControladorResultado extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {541, 546, 547};
    //-----------------
    private final AnalisisDAO analisisdao = new AnalisisDAO();
    private final ControlXSLTDAO controlxsltdao = new ControlXSLTDAO();
    private final ResultadoDAO dao = new ResultadoDAO();
    private final EquipoDAO equipodao = new EquipoDAO();
    private final ReactivoDAO reactivodao = new ReactivoDAO();
    private final SolicitudDAO solicituddao = new SolicitudDAO();
    private final PatronDAO patrondao = new PatronDAO();
    private final HelperTransformaciones helper_transformaciones = HelperTransformaciones.getHelperTransformaciones();
    private String ubicacion;

    protected final Class clase = ControladorResultado.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("ver");
            add("editar");
            add("vermultiple");
            add("verprueba");
            add("archivo");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("editar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);

        int id_resultado = Integer.parseInt(request.getParameter("id_resultado"));

        try {
            Resultado resultado = dao.obtenerResultado(id_resultado);
            String filename = resultado.getPath();
            File file = new File(filename);

            if (file.exists()) {
                ServletContext ctx = getServletContext();
                InputStream fis = new FileInputStream(file);
                String mimeType = ctx.getMimeType(file.getAbsolutePath());

                response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
                response.setContentLength((int) file.length());
                String nombre = "resultado-" + resultado + "." + this.getFileExtension(filename);
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
        } catch (SIGIPROException sig_ex) {
            sig_ex.printStackTrace();
        }
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermisosMultiple(permisos, request);
        String redireccion = "Resultado/Ver.jsp";

        int id_resultado = Integer.parseInt(request.getParameter("id_resultado"));

        ControlXSLT xslt;
        Resultado resultado;

        try {
            xslt = controlxsltdao.obtenerControlXSLTResultado();
            resultado = dao.obtenerResultado(id_resultado);

            String formulario = helper_transformaciones.transformar(xslt, resultado.getDatos());
            Analisis analisis = resultado.getAgs().getAnalisis();
            SolicitudCC solicitud = resultado.getAgs().getGrupo().getSolicitud();

            request.setAttribute("resultado", resultado);
            request.setAttribute("analisis", analisis);
            request.setAttribute("solicitud", solicitud);
            request.setAttribute("cuerpo_datos", formulario);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

        redireccionar(request, response, redireccion);
    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(547, request);

        String redireccion = "Resultado/Editar.jsp";

        int id_resultado = Integer.parseInt(request.getParameter("id_resultado"));

        ControlXSLT xslt;
        Resultado resultado;
        Analisis analisis;

        try {
            xslt = controlxsltdao.obtenerControlXSLTFormulario();
            resultado = dao.obtenerResultado(id_resultado);
            analisis = analisisdao.obtenerAnalisis(resultado.getAgs().getAnalisis().getId_analisis());

            SolicitudCC solicitud = resultado.getAgs().getGrupo().getSolicitud();

            String formulario = helper_transformaciones.transformar(xslt, resultado.getDatos());

            List<Equipo> equipos = (analisis.tiene_equipos()) ? equipodao.obtenerEquiposTipo(analisis.pasar_ids_tipos("equipos")) : new ArrayList<Equipo>();
            List<Reactivo> reactivos = (analisis.tiene_reactivos()) ? reactivodao.obtenerReactivosTipo(analisis.pasar_ids_tipos("reactivos")) : new ArrayList<Reactivo>();
            List<List<Patron>> patrones_controles = patrondao.obtenerPatronesRealizarAnalisis();

            request.setAttribute("patrones", patrones_controles.get(0));
            request.setAttribute("controles", patrones_controles.get(1));
            request.setAttribute("resultado", resultado);
            request.setAttribute("analisis", analisis);
            request.setAttribute("solicitud", solicitud);
            request.setAttribute("cuerpo_formulario", formulario);
            request.setAttribute("id_ags", resultado.getAgs().getId_analisis_grupo_solicitud());
            request.setAttribute("id_analisis", resultado.getAgs().getAnalisis().getId_analisis());
            request.setAttribute("equipos", equipos);
            request.setAttribute("reactivos", reactivos);

        } catch (TransformerException | SIGIPROException | SQLException ex) {
            request.setAttribute("mensaje", ex.getMessage());
        }

        redireccionar(request, response, redireccion);
    }

    protected void getVermultiple(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(546, request);

        String redireccion = "Resultado/VerMultiple.jsp";

        int id_ags = Integer.parseInt(request.getParameter("id_ags"));
        String id_solicitud = request.getParameter("id_solicitud");
        String numero_solicitud = request.getParameter("numero_solicitud");
        String id_analisis = request.getParameter("id_analisis");

        Analisis a = analisisdao.obtenerAnalisis(Integer.parseInt(id_analisis));

        request.setAttribute("id_solicitud", id_solicitud);
        request.setAttribute("numero_solicitud", numero_solicitud);
        request.setAttribute("analisis", a);

        try {
            AnalisisGrupoSolicitud ags = dao.obtenerAGS(id_ags);
            request.setAttribute("ags", ags);
            request.setAttribute("resultados", ags.getResultados());
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", sig_ex.getMessage());
        }

        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        validarPermiso(547, request);

        String redireccion = "Resultado/Editar.jsp";
        int id_resultado = Integer.parseInt(this.obtenerParametro("id_resultado"));
        int id_analisis = Integer.parseInt(this.obtenerParametro("id_analisis"));

        try {
            Resultado resultado = dao.obtenerResultado(id_resultado);
            Analisis analisis = analisisdao.obtenerAnalisis(id_analisis);

            Usuario u = new Usuario();
            int id_usuario = (int) request.getSession().getAttribute("idusuario");
            u.setIdUsuario(id_usuario);

            resultado.setUsuario(u);

            String[] equipos_utilizados = this.obtenerParametros("equipos");
            String[] reactivos_utilizados = this.obtenerParametros("reactivos");
            String[] controles_utilizados = this.obtenerParametros("controles");
            String[] patrones_utilizados = this.obtenerParametros("patrones");

            InputStream binary_stream = resultado.getDatos().getBinaryStream();

            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document documento_resultado = parser.parse(binary_stream);
            Element elemento_resultado = documento_resultado.getDocumentElement();

            NodeList lista_nodos = elemento_resultado.getElementsByTagName("campo");

            HelperExcel excel = this.guardarArchivoResultado(resultado, analisis, ubicacion);
            boolean cambio_excel = excel != null;

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
                            if (cambio_excel) {
                                Node nodo_celda = elemento.getElementsByTagName("celda").item(0);
                                String celda = nodo_celda.getTextContent();
                                valor = excel.obtenerCelda(celda);
                            } else {
                                valor = nodo_valor.getTextContent();
                            }
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
            resultado.setPatrones(patrones_utilizados);
            resultado.setControles(controles_utilizados);

            dao.editarResultado(resultado);

            redireccion = "/ControlCalidad/Solicitud/Ver.jsp";
            try {
                SolicitudCC s = solicituddao.obtenerSolicitud(resultado.getAgs().getGrupo().getSolicitud().getId_solicitud());
                request.setAttribute("solicitud", s);

            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("mensaje", helper.mensajeDeError("No se pudo obtener la solicitud. Notifique al administrador del sistema."));
            }

            request.setAttribute("mensaje", helper.mensajeDeExito("Resultado editado correctamente."));

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
            this.ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Analisis" + File.separatorChar + "Resultados";
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

    private HelperExcel guardarArchivoResultado(Resultado resultado, Analisis analisis, String ubicacion) {

        HelperExcel excel = null;

        FileItem item = this.obtenerParametroFileItem("resultado");

        try {
            if (item.getSize() != 0) {
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

                excel = abrirExcel(resultado.getPath());
            } else {
                excel = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return excel;
    }

    private HelperExcel abrirExcel(String ubicacion) {
        HelperExcel excel = null;
        try {
            excel = new HelperExcel(ubicacion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return excel;
    }

    // </editor-fold>
}
