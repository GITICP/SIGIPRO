/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.AnalisisDAO;
import com.icp.sigipro.controlcalidad.dao.EquipoDAO;
import com.icp.sigipro.controlcalidad.dao.PatronDAO;
import com.icp.sigipro.controlcalidad.dao.ReactivoDAO;
import com.icp.sigipro.controlcalidad.dao.ResultadoDAO;
import com.icp.sigipro.controlcalidad.dao.ResultadoSangriaPruebaDAO;
import com.icp.sigipro.controlcalidad.dao.SolicitudDAO;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.AnalisisGrupoSolicitud;
import com.icp.sigipro.controlcalidad.modelos.Equipo;
import com.icp.sigipro.controlcalidad.modelos.Patron;
import com.icp.sigipro.controlcalidad.modelos.Reactivo;
import com.icp.sigipro.controlcalidad.modelos.Resultado;
import com.icp.sigipro.controlcalidad.modelos.ResultadoSangriaPrueba;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private final ResultadoSangriaPruebaDAO dao_sp = new ResultadoSangriaPruebaDAO();
    private final EquipoDAO equipodao = new EquipoDAO();
    private final ReactivoDAO reactivodao = new ReactivoDAO();
    private final SolicitudDAO solicituddao = new SolicitudDAO();
    private final PatronDAO patrondao = new PatronDAO();
    private final ResultadoSangriaPruebaDAO sangriapruebadao = new ResultadoSangriaPruebaDAO();
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
            add("editar_sp");
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
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));

        ControlXSLT xslt;
        Resultado resultado;
        try {

            if (id_analisis != Integer.MAX_VALUE) {
                xslt = controlxsltdao.obtenerControlXSLTResultado();
                resultado = dao.obtenerResultado(id_resultado);

                String formulario = helper_transformaciones.transformar(xslt, resultado.getDatos());
                request.setAttribute("cuerpo_datos", formulario);
            } else {
                resultado = dao_sp.obtenerResultadoSangriaPrueba(id_resultado);
            }
            
            Analisis analisis = resultado.getAgs().getAnalisis();
            SolicitudCC solicitud = resultado.getAgs().getGrupo().getSolicitud();

            request.setAttribute("resultado", resultado);
            request.setAttribute("analisis", analisis);
            request.setAttribute("solicitud", solicitud);
            
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
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));

        ControlXSLT xslt;
        Resultado resultado;
        Analisis analisis;

        try {
            List<Equipo> equipos;
            List<Reactivo> reactivos;
            String formulario = null;
            SolicitudCC solicitud = null;
            
            if (id_analisis != Integer.MAX_VALUE) {
                xslt = controlxsltdao.obtenerControlXSLTFormulario();
                resultado = dao.obtenerResultado(id_resultado);
                analisis = analisisdao.obtenerAnalisis(resultado.getAgs().getAnalisis().getId_analisis());
                solicitud = resultado.getAgs().getGrupo().getSolicitud();
                formulario = helper_transformaciones.transformar(xslt, resultado.getDatos());
            } else {
                request.setAttribute("accion", "Editar");
                resultado = sangriapruebadao.obtenerResultadoSangriaPrueba(id_resultado);
                analisis = analisisdao.obtenerAnalisis(id_analisis);
                redireccion = "/ControlCalidad/Analisis/FormularioSangriaPrueba.jsp";
            }
            
            List<List<Patron>> patrones_controles = patrondao.obtenerPatronesRealizarAnalisis();
            equipos = (analisis.tiene_equipos()) ? equipodao.obtenerEquiposTipo(analisis.pasar_ids_tipos("equipos")) : new ArrayList<Equipo>();
            reactivos = (analisis.tiene_reactivos()) ? reactivodao.obtenerReactivosTipo(analisis.pasar_ids_tipos("reactivos")) : new ArrayList<Reactivo>();

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
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));

        Analisis a = analisisdao.obtenerAnalisis(id_analisis);

        request.setAttribute("id_solicitud", id_solicitud);
        request.setAttribute("numero_solicitud", numero_solicitud);
        request.setAttribute("analisis", a);

        try {
            AnalisisGrupoSolicitud ags = dao.obtenerAGS(id_ags, id_analisis == Integer.MAX_VALUE);
            request.setAttribute("ags", ags);
            request.setAttribute("resultados", ags.getResultados());
            request.setAttribute("id_analisis", id_analisis);
        } catch (SIGIPROException sig_ex) {
            request.setAttribute("mensaje", this.helper.mensajeDeError(sig_ex.getMessage()));
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
            bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESULTADO, request.getRemoteAddr());

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
    
    protected void postEditar_sp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        validarPermiso(547, request);

        String redireccion = "/ControlCalidad/Analisis/FormularioSangriaPrueba.jsp";

        try {
            ResultadoSangriaPrueba resultado = construirObjectoSangriaPrueba(request);            

            sangriapruebadao.editarResultadoSangriaPrueba(resultado);
            bitacora.setBitacora(resultado.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_RESULTADO_SP, request.getRemoteAddr());

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
    
    private ResultadoSangriaPrueba construirObjectoSangriaPrueba(HttpServletRequest request) {
        ResultadoSangriaPrueba resultado = new ResultadoSangriaPrueba();

        try {
            Usuario u = new Usuario();
            int id_usuario = (int) request.getSession().getAttribute("idusuario");
            u.setIdUsuario(id_usuario);
            
            resultado.setId_resultado(Integer.parseInt(this.obtenerParametro("id_resultado")));
            resultado.setUsuario(u);
            resultado.setHematocrito(Float.parseFloat(this.obtenerParametro("hematocrito")));
            resultado.setHemoglobina(Float.parseFloat(this.obtenerParametro("hemoglobina")));
            resultado.setRbc(Float.parseFloat(this.obtenerParametro("rbc")));
            resultado.setWbc(Float.parseFloat(this.obtenerParametro("wbc")));
            resultado.setMch(Float.parseFloat(this.obtenerParametro("mch")));
            resultado.setMchc(Float.parseFloat(this.obtenerParametro("mchc")));
            resultado.setLym(Float.parseFloat(this.obtenerParametro("lym")));
            resultado.setLinfocitos(Float.parseFloat(this.obtenerParametro("linfocitos")));
            resultado.setNum_otros(Float.parseFloat(this.obtenerParametro("num_otros")));
            resultado.setPlt(Float.parseFloat(this.obtenerParametro("plt")));
            resultado.setMcv(Float.parseFloat(this.obtenerParametro("mcv")));
            resultado.setOtros(Float.parseFloat(this.obtenerParametro("otros")));
            resultado.setFecha(helper_fechas.getFecha_hoy());
            AnalisisGrupoSolicitud ags = new AnalisisGrupoSolicitud();
            ags.setId_analisis_grupo_solicitud(Integer.parseInt(this.obtenerParametro("id_ags")));
            resultado.setAgs(ags);
            resultado.setUsuario(u);

            String[] equipos_utilizados = this.obtenerParametros("equipos");
            String[] reactivos_utilizados = this.obtenerParametros("reactivos");
            String[] controles_utilizados = this.obtenerParametros("controles");
            String[] patrones_utilizados = this.obtenerParametros("patrones");
            
            resultado.setEquipos(equipos_utilizados);
            resultado.setReactivos(reactivos_utilizados);
            resultado.setPatrones(patrones_utilizados);
            resultado.setControles(controles_utilizados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resultado;
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
            String fullPath = helper_archivos.obtenerDireccionArchivos();
            this.ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Analisis" + File.separatorChar + "Resultados";
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            parametros = upload.parseRequest(request);
        } catch (FileUploadException ex) {
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
                String nombre_arreglado = analisis.getNombre().replaceAll("[/\\:*?<>|\"]","_");
                String nombre = nombre_arreglado + "-" + fecha + "." + extension;
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
