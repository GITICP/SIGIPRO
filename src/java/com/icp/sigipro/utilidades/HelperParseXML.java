/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import com.icp.sigipro.bodegas.dao.SubBodegaDAO;
import com.icp.sigipro.bodegas.modelos.InventarioSubBodega;
import com.icp.sigipro.bodegas.modelos.SubBodega;
import com.icp.sigipro.core.SIGIPROException;
import com.icp.sigipro.core.SIGIPROServlet;
import com.icp.sigipro.core.formulariosdinamicos.ProduccionXSLTDAO;
import com.icp.sigipro.produccion.dao.Actividad_ApoyoDAO;
import com.icp.sigipro.produccion.dao.Categoria_AADAO;
import com.icp.sigipro.produccion.modelos.Actividad_Apoyo;
import com.icp.sigipro.produccion.modelos.Categoria_AA;
import com.icp.sigipro.produccion.modelos.Paso;
import com.icp.sigipro.produccion.modelos.Respuesta_AA;
import com.icp.sigipro.produccion.modelos.Respuesta_pxp;
import com.icp.sigipro.seguridad.dao.SeccionDAO;
import com.icp.sigipro.seguridad.dao.UsuarioDAO;
import com.icp.sigipro.seguridad.modelos.Seccion;
import com.icp.sigipro.seguridad.modelos.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Luis Diego
 */
public class HelperParseXML extends SIGIPROServlet {

    private final SubBodegaDAO subbodegadao = new SubBodegaDAO();
    private final Actividad_ApoyoDAO dao = new Actividad_ApoyoDAO();
    private final ProduccionXSLTDAO produccionxsltdao = new ProduccionXSLTDAO();
    private final SeccionDAO secciondao = new SeccionDAO();
    private final Categoria_AADAO categoriadao = new Categoria_AADAO();
    private final UsuarioDAO usuariodao = new UsuarioDAO();

    private int nombre_campo;

    public HelperParseXML() {

    }
    
    public HelperParseXML(List<FileItem> parametros) {
        this.parametros = parametros;
    }

    public HashMap<Integer, HashMap> parseFormularioXML(List<FileItem> items, Paso p, Actividad_Apoyo aa) {
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
                        if (aa != null) {
                            aa.setNombre(fieldValue);
                        } else {
                            p.setNombre(fieldValue);
                        }
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
                    case "requiere_ap":
                        boolean requiere_ap = true;
                        aa.setRequiere_ap(requiere_ap);
                        break;
                    case "version":
                        int version = Integer.parseInt(fieldValue);
                        if (aa != null) {
                            aa.setVersion(version);
                        } else {
                            p.setVersion(version);
                        }
                        break;
                    case "id_paso":
                        int id_paso = Integer.parseInt(fieldValue);
                        p.setId_paso(id_paso);
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
        return diccionario_formulario;

    }

    public String parseRespuestaXML(Respuesta_AA respuesta_aa, Respuesta_pxp respuesta_pxp, String ubicacion) throws ServletException, IOException, TransformerException, SQLException, ParserConfigurationException, SAXException, SIGIPROException, Exception {
        String string_xml_resultado = null;
        InputStream binary_stream = null;
        if (respuesta_aa != null) {
            binary_stream = respuesta_aa.getActividad().getEstructura().getBinaryStream();
        } else {
            binary_stream = respuesta_pxp.getPaso().getEstructura().getBinaryStream();
        }

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

    public String parseJSONXML(HashMap<Integer, HashMap> diccionario_formulario, String orden, String tipo) {
        this.nombre_campo = 1;
        //Se obtiene el orden de los campos
        String[] orden_formulario = orden.split(",");
        //Tipo puede ser paso o actividad
        HelperXML xml = new HelperXML(tipo);
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
            actividad += "\"[" + aa.getCategoria().getNombre() + "] " + aa.getNombre() + "\"]";
            respuesta.add(actividad);
        }
        System.out.println(respuesta.toString());
        return respuesta;
    }
    
    public boolean crearDirectorio(String path) {
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

    @Override
    protected int getPermiso() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
