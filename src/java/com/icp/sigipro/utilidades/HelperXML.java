/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author ld.conejo
 */
public class HelperXML {

    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
    Element root;
    int contador;
    //int columnas;
    //int filasespeciales;
    //int filas;
    HashMap<Integer, HashMap> dictionary;

    public HelperXML() {
        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();
            // root element
            root = doc.createElement("analisis");

            doc.appendChild(root);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }

    }

    public HelperXML(SQLXML xml, String tipo) throws SQLException, ParserConfigurationException, SAXException, IOException {
        if (xml != null) {
            InputStream binaryStream = xml.getBinaryStream();
            DocumentBuilder parser
                    = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = parser.parse(binaryStream);

            dictionary = new HashMap<Integer, HashMap>();
            contador = 0;
            //columnas = 0;
            //filasespeciales = 0;
            //filas = 0;
            if (tipo.equals("control")) {
                leerXML(doc.getDocumentElement().getFirstChild());
            } else {
                leerXMLProduccion(doc.getDocumentElement().getFirstChild());
            }

            System.out.println(dictionary);
        }

    }

    public void leerXML(Node node) {
        String name = node.getNodeName();
        String text = node.getTextContent();

        NodeList nodosTipo = node.getChildNodes();

        boolean isCampo = true;
        for (int i = 0, len = nodosTipo.getLength(); i < len; i++) {
            Node nodo = nodosTipo.item(i);
            if (nodo.getNodeName().equals("tipo") && nodo.getTextContent().equals("table")) {
                isCampo = false;
                break;
            }
        }
        if (isCampo) {
            parseCampos(node.getChildNodes());
        } else {
            parseTabla(node.getChildNodes());
        }

        Node nextNode = node.getNextSibling();
        if (nextNode != null) {
            leerXML(nextNode);
        }

    }

    public void leerXMLProduccion(Node node) {
        String name = node.getNodeName();
        String text = node.getTextContent();

        NodeList nodosTipo = node.getChildNodes();

        String campo = "";
        for (int i = 0, len = nodosTipo.getLength(); i < len; i++) {
            Node nodo = nodosTipo.item(i);
            System.out.println(nodo.getNodeName());
            System.out.println(nodo.getTextContent());
            if (nodo.getNodeName().equals("tipo")) {
                switch (nodo.getTextContent()) {
                    case ("seleccion"):
                        parseCheckbox(node.getChildNodes());
                        break;
                    case ("usuario"):
                        parseUsuarios(node.getChildNodes());
                        break;
                    case ("subbodega"):
                        parseSubbodegas(node.getChildNodes());
                        break;
                    default:
                        parseCampos(node.getChildNodes());
                        break;
                }
            }
        }

        Node nextNode = node.getNextSibling();
        if (nextNode != null) {
            leerXMLProduccion(nextNode);
        }

    }

    private void parseCampos(NodeList nodos) {
        contador++;
        HashMap<String, Object> hash = new HashMap<String, Object>();
        dictionary.put(contador, hash);
        dictionary.get(contador).put("tipocampo", "campo");

        for (int i = 0, len = nodos.getLength(); i < len; i++) {
            Node currentNode = nodos.item(i);
            String name = currentNode.getNodeName();
            String text = currentNode.getTextContent();
            switch (name) {
                case "tipo":
                    dictionary.get(contador).put(name, text);
                    if (text.equals("Excel")) {
                        dictionary.get(contador).put("manual", "True");
                    } else {
                        dictionary.get(contador).put("manual", "False");
                    }
                    break;
                case "resultado":
                    dictionary.get(contador).put(name, text);
                    break;
                case "etiqueta":
                    dictionary.get(contador).put(name, text);
                    break;
                case "celda":
                    String patron = "([A-Z]+)(\\d+)";
                    String primera = text.replaceFirst(patron, "$1");
                    String segunda = text.replaceFirst(patron, "$2");
                    String celda = primera + "-" + segunda;
                    dictionary.get(contador).put(name, celda);
                    break;

            }
        }
    }

    private void parseUsuarios(NodeList nodos) {
        contador++;
        HashMap<String, Object> hash = new HashMap<String, Object>();
        dictionary.put(contador, hash);
        dictionary.get(contador).put("tipocampo", "usuario");

        for (int i = 0, len = nodos.getLength(); i < len; i++) {
            Node currentNode = nodos.item(i);
            String name = currentNode.getNodeName();
            String text = currentNode.getTextContent();
            switch (name) {
                case "tipo":
                    dictionary.get(contador).put(name, text);
                    break;
                case "etiqueta":
                    dictionary.get(contador).put("nombre", text);
                    break;
                case "seccion":
                    dictionary.get(contador).put("seccion", text);
                    break;
                case "nombre-seccion":
                    dictionary.get(contador).put("nombreseccion", text);
                    break;
            }
        }
    }

    private void parseSubbodegas(NodeList nodos) {
        contador++;
        HashMap<String, Object> hash = new HashMap<String, Object>();
        dictionary.put(contador, hash);
        dictionary.get(contador).put("tipocampo", "subbodega");

        for (int i = 0, len = nodos.getLength(); i < len; i++) {
            Node currentNode = nodos.item(i);
            String name = currentNode.getNodeName();
            String text = currentNode.getTextContent();
            switch (name) {
                case "tipo":
                    dictionary.get(contador).put(name, text);
                    break;
                case "etiqueta":
                    dictionary.get(contador).put("nombre", text);
                    break;
                case "subbodega":
                    dictionary.get(contador).put("subbodega", text);
                    break;
                case "nombre-subbodega":
                    dictionary.get(contador).put("nombresubbodega", text);
                    break;
                case "cantidad":
                    if (text.equals("true")) {
                        dictionary.get(contador).put("cantidad", text);
                    }
                    break;
            }
        }
    }

    public void parseCheckbox(NodeList nodos) {
        contador++;
        HashMap<String, Object> hash = new HashMap<String, Object>();
        dictionary.put(contador, hash);
        dictionary.get(contador).put("tipocampo", "checkbox");

        for (int i = 0, len = nodos.getLength(); i < len; i++) {
            Node currentNode = nodos.item(i);
            String name = currentNode.getNodeName();
            String text = currentNode.getTextContent();
            switch (name) {
                case "etiqueta":
                    dictionary.get(contador).put("nombre", text);
                    break;
                case "opciones":
                    NodeList nodosOpciones = currentNode.getChildNodes();
                    parseOpciones(nodosOpciones);
                    break;
            }
        }

    }

    public void parseTabla(NodeList nodos) {
        contador++;
        HashMap<String, Object> hash = new HashMap<String, Object>();
        dictionary.put(contador, hash);
        dictionary.get(contador).put("tipocampo", "tabla");

        for (int i = 0, len = nodos.getLength(); i < len; i++) {
            Node currentNode = nodos.item(i);
            String name = currentNode.getNodeName();
            String text = currentNode.getTextContent();
            switch (name) {
                case "visible":
                    dictionary.get(contador).put(name, text);
                    break;
                case "nombre":
                    dictionary.get(contador).put(name, text);
                    break;
                case "columnas":
                    NodeList nodosColumnas = currentNode.getChildNodes();
                    parseColumnas(nodosColumnas);
                    break;
                case "filas":
                    NodeList nodosFilas = currentNode.getChildNodes();
                    parseFilas(nodosFilas);
                    break;
            }
        }

    }

    private void parseColumnas(NodeList nodosColumnas) {

        List<String> columnas = new ArrayList<String>();
        for (int j = 0, lenColumnas = nodosColumnas.getLength(); j < lenColumnas; j++) {
            Node columna = nodosColumnas.item(j);
            Node nombre = columna.getFirstChild();
            if (j == 0) {
                dictionary.get(contador).put("nombrefilacolumna", nombre.getTextContent());
            } else {
                columnas.add(nombre.getTextContent());
            }
        }
        dictionary.get(contador).put("columnas", columnas);

    }

    private void parseOpciones(NodeList nodosOpciones) {

        List<String> opciones = new ArrayList<String>();
        int pivot = 1;
        for (int j = 0, lenOpciones = nodosOpciones.getLength(); j < lenOpciones; j++) {
            Node opcion = nodosOpciones.item(j);
            NodeList nodosOpcion = opcion.getChildNodes();

            for (int i = 0, len = nodosOpcion.getLength(); i < len; i++) {
                Node currentNode = nodosOpcion.item(i);
                String name = currentNode.getNodeName();
                String text = currentNode.getTextContent();
                System.out.println(pivot);
                switch (name) {
                    case "etiqueta":
                        dictionary.get(contador).put("opcion" + pivot, text);
                        pivot++;
                        break;
                }
            }
        }
        dictionary.get(contador).put("cantidad", pivot - 1);

    }

    private void parseFilas(NodeList nodosFilas) {
        List<String> filasespeciales = new ArrayList<String>();
        List<String> tipofilasespeciales = new ArrayList<String>();
        List<String> nombrefilas = new ArrayList<String>();
        List<String> tipocolumnas = new ArrayList<String>();
        List<String> celdacolumna = new ArrayList<String>();
        boolean isNombreFilas = true;
        int cantidadFilas = nodosFilas.getLength();
        for (int j = 0, lenColumnas = nodosFilas.getLength(); j < lenColumnas; j++) {
            Node fila = nodosFilas.item(j);
            NamedNodeMap atributos = fila.getAttributes();
            if (atributos.getLength() != 0) {
                cantidadFilas = cantidadFilas - 1;
                Node nombre = fila.getFirstChild().getFirstChild();
                tipofilasespeciales.add(atributos.getNamedItem("funcion").getTextContent());
                filasespeciales.add(nombre.getTextContent());
            } else {
                NodeList nodosFila = fila.getChildNodes();
                Node celdas = nodosFila.item(0);
                NodeList nodosCeldas = celdas.getChildNodes();
                for (int x = 0, lenCelda = nodosCeldas.getLength(); x < lenCelda; x++) {
                    Node celda = nodosCeldas.item(x);
                    Node etiqueta = celda.getFirstChild();
                    String nameCelda = etiqueta.getNodeName();
                    if (nameCelda.equals("celda-nombre")) {
                        Node nombre = etiqueta.getFirstChild();
                        String nombrefila = nombre.getTextContent();
                        if (nombrefila.equals("")) {
                            isNombreFilas = false;
                        }
                        nombrefilas.add(nombre.getTextContent());
                    } else {
                        NodeList nodosCampos = etiqueta.getChildNodes();
                        if (nodosCampos.getLength() == 3) {
                            celdacolumna.add("");
                        }
                        for (int y = 0, lenCampos = nodosCampos.getLength(); y < lenCampos; y++) {
                            Node campo = nodosCampos.item(y);
                            switch (campo.getNodeName()) {
                                case "tipo":
                                    String tipocolumna = campo.getTextContent().split("_")[0];
                                    tipocolumnas.add(tipocolumna);
                                    break;
                                case "celda":
                                    String patron = "([A-Z]+)(\\d+)";
                                    String prueba = campo.getTextContent();
                                    String primera = prueba.replaceFirst(patron, "$1");
                                    String segunda = prueba.replaceFirst(patron, "$2");
                                    celdacolumna.add(primera + "-" + segunda);
                                    break;
                            }

                        }
                    }
                }
            }
        }
        dictionary.get(contador).put("cantidadfilas", cantidadFilas);
        dictionary.get(contador).put("filasespeciales", filasespeciales);
        dictionary.get(contador).put("tipofilasespeciales", tipofilasespeciales);
        dictionary.get(contador).put("tipocolumnas", tipocolumnas);
        dictionary.get(contador).put("celdacolumna", celdacolumna);
        if (isNombreFilas) {
            dictionary.get(contador).put("nombrefilas", nombrefilas);
        } else {
            dictionary.get(contador).put("nombrefilas", null);
        }
    }

    public HashMap<Integer, HashMap> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<Integer, HashMap> dictionary) {
        this.dictionary = dictionary;
    }

    public Element agregarElemento(String nombre) {
        Element elemento = doc.createElement(nombre);
        root.appendChild(elemento);
        return elemento;
    }

    public Element agregarElemento(String nombre, Element primario) {
        Element elemento = doc.createElement(nombre);
        primario.appendChild(elemento);
        return elemento;
    }

    public Attr definirAtributo(String nombre, String valor) {
        Attr attrType = doc.createAttribute(nombre);
        attrType.setValue(valor);
        return attrType;
    }

    public void agregarAtributo(Element elemento, Attr atributo) {
        elemento.setAttributeNode(atributo);
    }

    public void agregarSubelemento(String nombre, String valor, Element elemento) {
        Element sub = doc.createElement(nombre);
        sub.appendChild(doc.createTextNode(valor));
        elemento.appendChild(sub);
    }

    public String imprimirXML() {
        String output = "";
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            output = writer.getBuffer().toString().replaceAll("\n|\r", "");
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(HelperXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(HelperXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }
}
