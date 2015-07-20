/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas_xslt;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Boga
 */
public class PruebasXSLT
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer(new StreamSource(new File(
                    "Archivos Importantes\\XSLT\\Generadores\\GeneradorFormulariosCalidad.xsl"
            )));
            StreamSource ss = new StreamSource(new File("Archivos Importantes\\XSLT\\Archivos de Prueba\\Formulario 2.xml"));
            StreamResult sr = new StreamResult(new File("Archivos Importantes\\XSLT\\Archivos Generados\\PruebaVerFormulario.html"));
            tf.transform(ss, sr);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
}
