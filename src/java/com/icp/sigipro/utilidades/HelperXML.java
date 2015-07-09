/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import java.io.StringWriter;
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

/**
 *
 * @author ld.conejo
 */
public class HelperXML {

    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
    Element root;

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

    public Attr definirAtributo(String nombre, String valor){
        Attr attrType = doc.createAttribute(nombre);
        attrType.setValue(valor);
        return attrType;
    }
    
    public void agregarAtributo(Element elemento, Attr atributo){
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
