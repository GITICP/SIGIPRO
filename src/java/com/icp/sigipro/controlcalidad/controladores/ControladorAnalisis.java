/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.controlcalidad.controladores;

import com.icp.sigipro.bitacora.dao.BitacoraDAO;
import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.controlcalidad.dao.AnalisisDAO;
import com.icp.sigipro.controlcalidad.dao.TipoEquipoDAO;
import com.icp.sigipro.controlcalidad.dao.TipoReactivoDAO;
import com.icp.sigipro.controlcalidad.modelos.Analisis;
import com.icp.sigipro.controlcalidad.modelos.TipoEquipo;
import com.icp.sigipro.controlcalidad.modelos.TipoReactivo;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.utilidades.HelpersHTML;
import com.icp.sigipro.utilidades.HelperXML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.SQLXML;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorAnalisis", urlPatterns = {"/ControlCalidad/Analisis"})
public class ControladorAnalisis extends SIGIPROServlet {

    //Falta implementar
    private final int[] permisos = {1, 540};
    //-----------------
    private AnalisisDAO dao = new AnalisisDAO();
    private TipoEquipoDAO tipoequipodao = new TipoEquipoDAO();
    private TipoReactivoDAO tiporeactivodao = new TipoReactivoDAO();

    HelpersHTML helper = HelpersHTML.getSingletonHelpersHTML();
    BitacoraDAO bitacora = new BitacoraDAO();

    protected final Class clase = ControladorAnalisis.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("archivo");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregareditar");
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
        request.setAttribute("analisis", a);
        request.setAttribute("tipoequipos", tipoequipo);
        request.setAttribute("tiporeactivos", tiporeactivo);
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
        try {
            Analisis a = dao.obtenerAnalisis(id_analisis);
            request.setAttribute("analisis", a);
            redireccionar(request, response, redireccion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                System.out.println(a.getEstructuraString());
                if (resultado) {
                    dao.insertarTipoEquipo(a.getTipos_equipos_analisis(), a.getId_analisis());
                    dao.insertarTipoReactivo(a.getTipos_reactivos_analisis(), a.getId_analisis());
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
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Analisis construirObjeto(List<FileItem> items, HttpServletRequest request, String ubicacion) {
        Analisis a = new Analisis();
        a.setTipos_equipos_analisis(new ArrayList<TipoEquipo>());
        a.setTipos_reactivos_analisis(new ArrayList<TipoReactivo>());
        HashMap<Integer, HashMap> dictionary = new HashMap<Integer, HashMap>();
        String orden = "";
        //Contadores para clasificar las columnas y filas
        HashMap<Integer, HashMap> columnasfilas = new HashMap<Integer, HashMap>();
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                String fieldName = item.getFieldName();
                String fieldValue;
                try {
                    fieldValue = item.getString("UTF-8").trim();
                } catch (UnsupportedEncodingException ex) {
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
                    case "orden":
                        orden = fieldValue;
                        break;
                    default:
                        //Se crea un diccionario con los elementos del Formulario
                        String[] values = fieldName.split("_");
                        if (values.length > 1) {
                            int id = Integer.parseInt(values[2]);
                            if (!dictionary.containsKey(id)) {
                                HashMap<String, String> llaves = new HashMap<String, String>();
                                if (values[0].equals("c")) {
                                    llaves.put("tipo", "campo");
                                } else {
                                    llaves.put("tipo", "tabla");
                                }
                                dictionary.put(id, llaves);
                            }
                            if (!columnasfilas.containsKey(id)) {
                                HashMap<String, Integer> columnafila = new HashMap<String, Integer>();
                                columnafila.put("columnas", 1);
                                columnafila.put("filas", 1);
                                columnasfilas.put(id, columnafila);
                            }
                            switch (values[1]) {
                                case "nombrecolumna":
                                    int cantidadColumnas1 = (int) columnasfilas.get(id).get("columnas");
                                    dictionary.get(id).put(values[1] + "_" + cantidadColumnas1, fieldValue);
                                    break;
                                case "tipocampocolumna":
                                    int cantidadColumnas2 = (int) columnasfilas.get(id).get("columnas");
                                    dictionary.get(id).put(values[1] + "_" + cantidadColumnas2, fieldValue);
                                    cantidadColumnas2++;
                                    columnasfilas.get(id).put("columnas", cantidadColumnas2);
                                    break;
                                case "nombrefilaespecial":
                                    int cantidadFilas1 = (int) columnasfilas.get(id).get("filas");
                                    dictionary.get(id).put(values[1] + "_" + cantidadFilas1, fieldValue);
                                    break;
                                case "tipocampofilaespecial":
                                    int cantidadFilas2 = (int) columnasfilas.get(id).get("filas");
                                    dictionary.get(id).put(values[1] + "_" + cantidadFilas2, fieldValue);
                                    cantidadFilas2++;
                                    columnasfilas.get(id).put("filas", cantidadFilas2);
                                    break;
                                default:
                                    dictionary.get(id).put(values[1], fieldValue);
                                    break;
                            }
                            break;
                        }
                }
            } else {
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
        if (!dictionary.isEmpty()) {
            String xml = this.parseDictXML(dictionary, orden, columnasfilas);
            a.setEstructuraString(xml);
        }
        return a;
    }

    private String parseDictXML(HashMap<Integer, HashMap> dictionary, String orden, HashMap<Integer, HashMap> columnasfilas) {

        String[] keys = orden.split(",");
        int contadorTablas = 0;

        HelperXML xml = new HelperXML();

        for (String i : keys) {
            int key = Integer.parseInt(i);
            Element campo = xml.agregarElemento("campo");
            HashMap<String, String> hash = dictionary.get(key);
            if (hash.get("tipo").equals("campo")) {
                this.crearCampo(xml, hash, campo);
            } else {
                xml.agregarSubelemento("tipo", "table", campo);
                if (hash.containsKey("tablavisible")) {
                    xml.agregarSubelemento("visible", "True", campo);
                } else {
                    xml.agregarSubelemento("visible", "False", campo);
                }
                Element columnas = xml.agregarElemento("columnas", campo);
                Element filas = xml.agregarElemento("filas", campo);

                List<String> tiposcolumnas = new ArrayList<String>();
                //Columnas
                Element primeraColumna = xml.agregarElemento("columna", columnas);
                xml.agregarSubelemento("nombre", hash.get("nombrefilacolumna"), primeraColumna);
                int cantidadColumnas = (int) columnasfilas.get(key).get("columnas") - 1;
                for (int it = 0; it < cantidadColumnas; it++) {
                    int col = it + 1;
                    String keycol = "nombrecolumna_" + col;
                    String valorCol = hash.get(keycol);
                    String keytipo = "tipocampocolumna_" + col;
                    String valorTipo = hash.get(keytipo);
                    tiposcolumnas.add(valorTipo);
                    Element columna = xml.agregarElemento("columna", columnas);
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
                        String nombre_celda = "Tabla_" + contadorTablas + "_Celda_" + it + "_" + jt;
                        xml.agregarSubelemento("nombre-campo", nombre_celda, campo_fila);
                        xml.agregarSubelemento("valor", "", campo_fila);
                    }
                }
                //Filas especiales
                int cantidadFilasEspeciales = (int) columnasfilas.get(key).get("filas") - 1;
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
                        String nombre_celda = "Tabla_" + contadorTablas + "_" + valorTipo + "_" + jt;
                        xml.agregarSubelemento("nombre-campo", nombre_celda, campo_fila);
                        xml.agregarSubelemento("valor", "", campo_fila);
                    }
                }
                //------------
                contadorTablas++;
            }
        }
        return xml.imprimirXML();
    }

    private void crearCampo(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("nombre-campo", hash.get("nombre"), campo);
        xml.agregarSubelemento("etiqueta", hash.get("nombre"), campo);
        xml.agregarSubelemento("valor", "", campo);
        if (hash.containsKey("manual")) {
            xml.agregarSubelemento("tipo", "Excel", campo);
            xml.agregarSubelemento("celda", hash.get("celda"), campo);
        } else {
            xml.agregarSubelemento("tipo", hash.get("tipocampo"), campo);
        }
        if (hash.containsKey("visible")) {
            xml.agregarSubelemento("visible", "True", campo);
        } else {
            xml.agregarSubelemento("visible", "False", campo);
        }
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

  // </editor-fold>
}
