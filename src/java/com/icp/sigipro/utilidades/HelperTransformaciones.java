/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import com.icp.sigipro.core.formulariosdinamicos.ControlXSLT;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.SQLXML;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Boga
 */
public class HelperTransformaciones
{

    private static HelperTransformaciones theSingleton = null;

    private HelperTransformaciones() {

    }

    public static HelperTransformaciones getHelperTransformaciones() {
        if (theSingleton == null) {
            theSingleton = new HelperTransformaciones();
        }
        return theSingleton;
    }

    public String transformar(ControlXSLT xslt, SQLXML estructura) throws TransformerException, SQLException {
        TransformerFactory tff = TransformerFactory.newInstance();
        InputStream streamXSLT = xslt.getEstructura().getBinaryStream();
        InputStream streamXML = estructura.getBinaryStream();
        Transformer transformador = tff.newTransformer(new StreamSource(streamXSLT));
        StreamSource stream_source = new StreamSource(streamXML);
        StreamResult stream_result = new StreamResult(new StringWriter());
        transformador.transform(stream_source, stream_result);
        return stream_result.getWriter().toString();
    }
}
