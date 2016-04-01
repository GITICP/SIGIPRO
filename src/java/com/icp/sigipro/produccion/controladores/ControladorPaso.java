/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.controladores;

import com.icp.sigipro.bitacora.modelo.Bitacora;
import com.icp.sigipro.bodegas.dao.SubBodegaDAO;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.core.formulariosdinamicos.ProduccionXSLT;
import com.icp.sigipro.core.formulariosdinamicos.ProduccionXSLTDAO;
import com.icp.sigipro.produccion.dao.Actividad_ApoyoDAO;
import com.icp.sigipro.produccion.dao.PasoDAO;
import com.icp.sigipro.produccion.modelos.Actividad_Apoyo;
import com.icp.sigipro.produccion.modelos.Paso;
import com.icp.sigipro.seguridad.dao.SeccionDAO;
import com.icp.sigipro.seguridad.modelos.Seccion;
import com.icp.sigipro.utilidades.HelperTransformaciones;
import com.icp.sigipro.utilidades.HelperXML;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ld.conejo
 */
@WebServlet(name = "ControladorPaso", urlPatterns = {"/Produccion/Paso"})
public class ControladorPaso extends SIGIPROServlet {

    //CRUD, Activar
    private final int[] permisos = {650, 651};
    //-----------------
    private final PasoDAO dao = new PasoDAO();
    private final ProduccionXSLTDAO produccionxsltdao = new ProduccionXSLTDAO();
    private final SubBodegaDAO subbodegadao = new SubBodegaDAO();
    private final SeccionDAO secciondao = new SeccionDAO();
    private final Actividad_ApoyoDAO actividaddao = new Actividad_ApoyoDAO();

    private final HelperTransformaciones helper_transformaciones = HelperTransformaciones.getHelperTransformaciones();

    private int nombre_campo;

    protected final Class clase = ControladorPaso.class;
    protected final List<String> accionesGet = new ArrayList<String>() {
        {
            add("index");
            add("ver");
            add("agregar");
            add("eliminar");
            add("editar");
            add("verhistorial");
            add("activar");
        }
    };
    protected final List<String> accionesPost = new ArrayList<String>() {
        {
            add("agregareditar");
        }
    };

    // <editor-fold defaultstate="collapsed" desc="Métodos Get">
    protected void getAgregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SIGIPROException {
        validarPermiso(650, request);

        String redireccion = "Paso/Agregar.jsp";
        Paso p = new Paso();
        request.setAttribute("paso", p);
        request.setAttribute("accion", "Agregar");
        request.setAttribute("listaSubbodegas", this.parseListaSubbodegas(subbodegadao.obtenerSubBodegas()));
        request.setAttribute("listaSecciones", this.parseListaSecciones(secciondao.obtenerSecciones()));
        request.setAttribute("listaActividades", this.parseListaActividades(actividaddao.obtenerActividades_Apoyo()));
        request.setAttribute("orden", "");
        request.setAttribute("cantidad", 0);
        request.setAttribute("contador", 0);
        redireccionar(request, response, redireccion);
    }

    protected void getIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Paso/index.jsp";
        List<Paso> pasos = dao.obtenerPasos();
        request.setAttribute("listaPasos", pasos);
        redireccionar(request, response, redireccion);
    }

    protected void getVer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Paso/Ver.jsp";
        int id_paso = Integer.parseInt(request.getParameter("id_paso"));
        ProduccionXSLT xslt;
        Paso p;

        try {
            p = dao.obtenerPaso(id_paso);
            xslt = produccionxsltdao.obtenerProduccionXSLTVerFormulario();
            if (p.getEstructura() != null) {
                String formulario = helper_transformaciones.transformar(xslt, p.getEstructura());
                request.setAttribute("cuerpo_datos", formulario);
            } else {
                request.setAttribute("cuerpo_datos", null);
            }
            request.setAttribute("paso", p);
            redireccionar(request, response, redireccion);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

    }

    protected void getVerhistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermisosMultiple(permisos, request);
        String redireccion = "Paso/VerHistorial.jsp";
        int id_historial = Integer.parseInt(request.getParameter("id_historial"));
        ProduccionXSLT xslt;
        Paso p;

        try {
            p = dao.obtenerHistorial(id_historial);
            xslt = produccionxsltdao.obtenerProduccionXSLTVerFormulario();
            if (p.getEstructura() != null) {
                String formulario = helper_transformaciones.transformar(xslt, p.getEstructura());
                request.setAttribute("cuerpo_datos", formulario);
            } else {
                request.setAttribute("cuerpo_datos", null);
            }
            request.setAttribute("paso", p);
            redireccionar(request, response, redireccion);
        } catch (TransformerException | SIGIPROException | SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Ha ocurrido un error inesperado. Notifique al administrador del sistema."));
        }

    }

    protected void getActivar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(650, request);
        int id_historial = Integer.parseInt(request.getParameter("id_historial"));
        int id_paso = Integer.parseInt(request.getParameter("id_paso"));
        int version = dao.obtenerVersion(id_historial);
        boolean resultado = false;
        try {
            resultado = dao.activarVersion(version, id_paso);
            if (resultado) {
                //Funcion que genera la bitacora 
                Paso paso = new Paso();
                paso.setId_historial(id_historial);
                paso.setId_paso(id_paso);
                paso.setVersion(version);
                bitacora.setBitacora(paso.parseJSON(), Bitacora.ACCION_ACTIVAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PASO, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Versión de Paso activado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Versión de Paso no pudo ser activado."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Versión de paso no pudo ser activado."));
            this.getIndex(request, response);
        }

    }

    protected void getEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, SIGIPROException, IOException, SQLException, ParserConfigurationException, SAXException {
        validarPermiso(650, request);
        String redireccion = "Paso/Editar.jsp";
        int id_paso = Integer.parseInt(request.getParameter("id_paso"));
        Paso p = dao.obtenerPaso(id_paso);

        HelperXML xml = new HelperXML(p.getEstructura(), "produccion");

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
        List<Actividad_Apoyo> actividades = actividaddao.obtenerActividades_Apoyo();

        request.setAttribute("paso", p);
        request.setAttribute("lista", lista);
        request.setAttribute("contador", lista.size());
        request.setAttribute("listaSubbodegas", this.parseListaSubbodegas(subbodegas));
        request.setAttribute("listaSecciones", this.parseListaSecciones(secciones));
        request.setAttribute("listaActividades", this.parseListaActividades(actividades));
        request.setAttribute("subbodegas", subbodegas);
        request.setAttribute("secciones", secciones);
        request.setAttribute("actividades", actividades);
        request.setAttribute("cantidad", cantidad);
        request.setAttribute("diccionario", diccionario_formulario);
        request.setAttribute("accion", "Editar");
        redireccionar(request, response, redireccion);

    }

    protected void getEliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        validarPermiso(650, request);
        int id_paso = Integer.parseInt(request.getParameter("id_paso"));
        boolean resultado = false;
        try {
            resultado = dao.eliminarPaso(id_paso);
            if (resultado) {
                //Funcion que genera la bitacora 
                bitacora.setBitacora(id_paso, Bitacora.ACCION_ELIMINAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PASO, request.getRemoteAddr());
                //----------------------------
                request.setAttribute("mensaje", helper.mensajeDeExito("Paso de Protocolo eliminado correctamente"));
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser eliminado ya que tiene protocolos asociados."));
            }
            this.getIndex(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser eliminado ya que tiene protocolos asociados."));
            this.getIndex(request, response);
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Métodos Post">
    protected void postAgregareditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, SIGIPROException, IOException, SQLException, ParserConfigurationException, SAXException {
        boolean resultado = false;

        Paso p = construirObjeto(parametros, request);
        if (p.getId_paso() == 0) {
            resultado = dao.insertarPaso(p);
            if (resultado) {
                request.setAttribute("mensaje", helper.mensajeDeExito("Paso de Protocolo agregado correctamente"));
                //Funcion que genera la bitacora
                bitacora.setBitacora(p.parseJSON(), Bitacora.ACCION_AGREGAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PASO, request.getRemoteAddr());
                //*----------------------------*
                this.getIndex(request, response);
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser agregado. Inténtelo de nuevo."));
                this.getAgregar(request, response);
            }
        } else {
            int version = dao.obtenerUltimaVersion(p.getId_paso());
            resultado = dao.editarPaso(p,version+1);
            if (resultado) {
                //Funcion que genera la bitacora
                bitacora.setBitacora(p.parseJSON(), Bitacora.ACCION_EDITAR, request.getSession().getAttribute("usuario"), Bitacora.TABLA_PASO, request.getRemoteAddr());
                //*----------------------------*
                request.setAttribute("mensaje", helper.mensajeDeExito("Paso de Protocolo editado correctamente"));
                this.getIndex(request, response);
            } else {
                request.setAttribute("mensaje", helper.mensajeDeError("Paso de Protocolo no pudo ser editado. Inténtelo de nuevo."));
                request.setAttribute("id_paso", p.getId_paso());
                this.getIndex(request, response);
            }
        }
    }
  // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Modelo">
    private Paso construirObjeto(List<FileItem> items, HttpServletRequest request) {
        Paso p = new Paso();
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
                        p.setNombre(fieldValue);
                        break;
                    case "id_paso":
                        int id_paso = Integer.parseInt(fieldValue);
                        p.setId_paso(id_paso);
                        break;
                    case "version":
                        int version = Integer.parseInt(fieldValue);
                        p.setVersion(version);
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
                                    case ("aa"):
                                        llaves.put("tipo", "aa");
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
            p.setEstructuraString(xml);
        }
        return p;
    }

    private String parseDictXML(HashMap<Integer, HashMap> diccionario_formulario, String orden) {
        this.nombre_campo = 1;
        //Se obtiene el orden de los campos
        String[] orden_formulario = orden.split(",");
        HelperXML xml = new HelperXML("paso");
        //Se itera sobre los IDS del orden de los campos
        for (String i : orden_formulario) {
            System.out.println(i);
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
                    case ("aa"):
                        this.crearActividadApoyo(xml, hash, campo);
                        break;
                }
            }
        }

        return xml.imprimirXML();
    }

    private void crearCampo(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("nombre-campo", hash.get("nombre").replaceAll(" ", "").replaceAll("_", "") + "_" + this.nombre_campo, campo);
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
            case ("lote"):
                xml.agregarSubelemento("tipo", hash.get("tipocampo"), campo);
                xml.agregarSubelemento("especial", "true", campo);
                break;
            default:
                xml.agregarSubelemento("tipo", hash.get("tipocampo"), campo);
                break;
        }
    }

    private void crearSubbodega(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("nombre-campo", hash.get("nombre").replaceAll(" ", "").replaceAll("_", "") + "_" + this.nombre_campo, campo);
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

    private void crearActividadApoyo(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("nombre-campo", hash.get("nombre").replaceAll(" ", "").replaceAll("_", "") + "_" + this.nombre_campo, campo);
        this.nombre_campo++;
        xml.agregarSubelemento("tipo", "aa", campo);
        xml.agregarSubelemento("etiqueta", hash.get("nombre"), campo);
        xml.agregarSubelemento("valor", "", campo);
        xml.agregarSubelemento("nombre-actividad", hash.get("nombreactividad"), campo);
        xml.agregarSubelemento("actividad", hash.get("actividad"), campo);

    }

    private void crearUsuario(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("nombre-campo", hash.get("nombre").replaceAll(" ", "").replaceAll("_", "") + "_" + this.nombre_campo, campo);
        this.nombre_campo++;
        xml.agregarSubelemento("etiqueta", hash.get("nombre"), campo);
        xml.agregarSubelemento("tipo", "usuario", campo);
        xml.agregarSubelemento("valor", "", campo);
        xml.agregarSubelemento("nombre-seccion", hash.get("nombreseccion"), campo);
        xml.agregarSubelemento("seccion", hash.get("seccion"), campo);
    }

    private void crearSeleccion(HelperXML xml, HashMap<String, String> hash, Element campo) {
        xml.agregarSubelemento("tipo", "seleccion", campo);
        xml.agregarSubelemento("nombre-campo", hash.get("snombre").replaceAll(" ", "").replaceAll("_", "") + "_" + this.nombre_campo, campo);
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

    public List<String> parseListaActividades(List<Actividad_Apoyo> actividades) {
        List<String> respuesta = new ArrayList<String>();
        for (Actividad_Apoyo aa : actividades) {
            String actividad = "[";
            actividad += aa.getId_actividad() + ",";
            actividad += "\"["+aa.getCategoria().getNombre()+"] " + aa.getNombre() + "\"]";
            respuesta.add(actividad);
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
                if (this.obtenerParametro("accion").equals("realizar")) {
                    accion = "realizar";
                }
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
