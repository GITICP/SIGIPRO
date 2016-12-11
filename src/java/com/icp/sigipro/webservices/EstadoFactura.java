
package com.icp.sigipro.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroFactura" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="proyecto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "numeroFactura",
    "proyecto"
})
@XmlRootElement(name = "EstadoFactura")
public class EstadoFactura {

    protected int numeroFactura;
    protected int proyecto;

    /**
     * Gets the value of the numeroFactura property.
     * 
     */
    public int getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * Sets the value of the numeroFactura property.
     * 
     */
    public void setNumeroFactura(int value) {
        this.numeroFactura = value;
    }

    /**
     * Gets the value of the proyecto property.
     * 
     */
    public int getProyecto() {
        return proyecto;
    }

    /**
     * Sets the value of the proyecto property.
     * 
     */
    public void setProyecto(int value) {
        this.proyecto = value;
    }

}
