/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.AnalisisDAO;
import com.icp.sigipro.controlcalidad.dao.TipoEquipoDAO;
import com.icp.sigipro.controlcalidad.dao.TipoReactivoDAO;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.core.formulariosdinamicos.ControlXSLT;
import com.icp.sigipro.core.formulariosdinamicos.ControlXSLTDAO;
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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorAnalisis", urlPatterns = {"/ControlCalidad/Analisis"})
public class ControladorAnalisis extends SIGIPROServlet
{

    //Falta implementar
    private final int[] permisos = {1, 540};
    //-----------------
    private final AnalisisDAO dao = new AnalisisDAO();
    private final TipoEquipoDAO tipoequipodao = new TipoEquipoDAO();
    private final TipoReactivoDAO tiporeactivodao = new TipoReactivoDAO();
    private final ControlXSLTDAO controlxsltdao = new ControlXSLTDAO();

    protected final Class clase = ControladorAnalisis.class;
    protected final List<String> accionesGet = new ArrayList<String>()
    {
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
    protected final List<String> accionesPost = new ArrayList<String>()
    {
        {
            add("agregareditar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getArchivo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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

    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(540, listaPermisos);

        String redireccion = "Analisis/Agregar.jsp";
        Analisis a = new Analisis();
        List<TipoEquipo> tipoequipo = tipoequipodao.obtenerTipoEquipos();
        List<TipoReactivo> tiporeactivo = tiporeactivodao.obtenerTipoReactivos();
        request.setAttribute("analisis", a);
        request.setAttribute("tipoequipos", tipoequipo);
        request.setAttribute("tiporeactivos", tiporeactivo);
        request.setAttribute("accion", "Agregar");
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Analisis/index.jsp";
        List<Analisis> analisis = dao.obtenerAnalisis();
        request.setAttribute("listaAnalisis", analisis);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermisos(permisos, listaPermisos);
        String redireccion = "Analisis/Ver.jsp";
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        try {
            Analisis a = dao.obtenerAnalisis(id_analisis);
            request.setAttribute("analisis", a);
            redireccionar(request, response, redireccion);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(540, listaPermisos);
        String redireccion = "Analisis/Editar.jsp";
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        Analisis a = dao.obtenerAnalisis(id_analisis);
        List<TipoEquipo> tipoequipo = tipoequipodao.obtenerTipoEquipos();
        List<TipoReactivo> tiporeactivo = tiporeactivodao.obtenerTipoReactivos();
        request.setAttribute("analisis", a);
        request.setAttribute("tipoequipo", tipoequipo);
        request.setAttribute("tiporeactivo", tiporeactivo);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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
            }
            else {
                request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser eliminado ya que tiene otras asociaciones."));
            }
            this.getIndex(request, response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser eliminado ya que tiene otras asociaciones."));
            this.getIndex(request, response);
        }

    }

    protected void getRealizar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Integer> listaPermisos = getPermisosUsuario(request);
        validarPermiso(540, listaPermisos);
        String redireccion = "Analisis/Realizar.jsp";
        
        int id_analisis = Integer.parseInt(request.getParameter("id_analisis"));
        
        ControlXSLT xslt;
        Analisis analisis;
        
        try {
            xslt = controlxsltdao.obtenerControlXSLT();
            analisis = dao.obtenerAnalisis(id_analisis);
            
            TransformerFactory tff = TransformerFactory.newInstance();
            InputStream streamXSLT = xslt.getEstructura().getBinaryStream();
            InputStream streamXML = analisis.getEstructura().getBinaryStream();
            Transformer transformador = tff.newTransformer(new StreamSource(streamXSLT));
            StreamSource stream_source = new StreamSource(streamXML);
            StreamResult stream_result = new StreamResult(new StringWriter());
            transformador.transform(stream_source, stream_result);
            
            String formulario = stream_result.getWriter().toString();
            
            request.setAttribute("formulario", formulario);
        } catch (TransformerException | SIGIPROException | SQLException ex ) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

        redireccionar(request, response, redireccion);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        boolean resultado = false;
        try {
            //Se crea el Path en la carpeta del Proyecto
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String pathArr[] = fullPath.split("/WEB-INF/classes/");
            fullPath = pathArr[0];
            String ubicacion = new File(fullPath).getPath() + File.separatorChar + "Documentos" + File.separatorChar + "Analisis" + File.separatorChar + "Machotes";
            //-------------------------------------------
            //Crea los directorios si no estan creados aun
            this.crearDirectorio(ubicacion);
            //--------------------------------------------
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Analisis a = construirObjeto(items, request, ubicacion);

            if (a.getId_analisis() == 0) {
                resultado = dao.insertarAnalisis(a);
                if (resultado) {
                    dao.insertarTipoEquipo(a.getTipos_equipos_analisis(), a.getId_analisis());
                    dao.insertarTipoReactivo(a.getTipos_reactivos_analisis(), a.getId_analisis());
                    request.setAttribute("mensaje", helper.mensajeDeExito("Analisis agregado correctamente"));
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(a.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ANALISIS, request.getRemoteAddr());
                    //*----------------------------*
                    this.getIndex(request, response);
                }
                else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser agregado. Inténtelo de nuevo."));
                    this.getAgregar(request, response);
                }
            }
            else {
                resultado = dao.editarAnalisis(a);
                if (resultado) {
                    //Funcion que genera la bitacora
                    bitacora.setBitacora(a.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_ANALISIS, request.getRemoteAddr());
                    //*----------------------------*
                    request.setAttribute("mensaje", helper.mensajeDeExito("Analisis editado correctamente"));
                    this.getIndex(request, response);
                }
                else {
                    request.setAttribute("mensaje", helper.mensajeDeError("Analisis no pudo ser editado. Inténtelo de nuevo."));
                    request.setAttribute("id_analisis", a.getId_analisis());
                    this.getEditar(request, response);
                }
            }
        }
        catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Analisis construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion)
    {
        Analisis a = new Analisis();
        a.setTipos_equipos_analisis(new ArrayList<TipoEquipo>());
        a.setTipos_reactivos_analisis(new ArrayList<TipoReactivo>());
        HashMap<Integer, HashMap> dictionary = new HashMap<Integer, HashMap>();
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                String fieldName = item.getFieldName();
                String fieldValue;
                try {
                    fieldValue = item.getString("UTF-8").trim();
                }
                catch (UnsupportedEncodingException ex) {
                    fieldValue = item.getString();
                }
                //Todavia falta la estructura
                switch (fieldName) {
                    case "nombre":
                        a.setNombre(fieldValue);
                        break;
                    case "id_analisis":
                        int id_analisis = Integer.parseInt(fieldValue);
                        a.setId_analisis(id_analisis);
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
                    default:
                        String[] values = fieldName.split("_");
                        if (values.length > 1) {
                            int id = Integer.parseInt(values[2]);
                            if (!dictionary.containsKey(id)) {
                                HashMap<String, String> llaves = new HashMap<String, String>();
                                dictionary.put(id, llaves);
                            }
                            switch (values[1]) {
                                case "tipocampo":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "nombre":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "campovisible":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "celda":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "manual":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "tablavisible":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "nombrecolumna":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "tipocampocolumna":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "cantidadfilas":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "nombrefilacolumna":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "connombre":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "nombresfilas":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "nombrefilaespecial":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                                case "tipocampofilaespecial":
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                            }

                            break;
                        }
                }
            }
            else {
                try {
                    if (item.getSize() != 0) {
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
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        System.out.println(dictionary);
        return a;
    }

    private boolean crearDirectorio(String path)
    {
        boolean resultado = false;
        File directorio = new File(path);
        if (!directorio.exists()) {
            System.out.println("Creando directorio: " + path);
            resultado = false;
            try {
                directorio.mkdirs();
                resultado = true;
            }
            catch (SecurityException se) {
                se.printStackTrace();
            }
            if (resultado) {
                System.out.println("Directorio Creado");
            }
        }
        else {
            resultado = true;
        }
        return resultado;
    }

    private String getFileExtension(String fileName)
    {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        else {
            return "";
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos abstractos sobreescritos">
    @Override
    protected void ejecutarAccion(HttpServletRequest request, HttpServletResponse response, String accion, String accionHTTP) throws ServletException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        List<String> lista_acciones;
        if (accionHTTP.equals("get")) {
            lista_acciones = accionesGet;
        }
        else {
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
