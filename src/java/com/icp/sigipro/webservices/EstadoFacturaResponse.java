
package com.icp.sigipro.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="EstadoFacturaResult" type="{https://fundacionucr.ac.cr/}FacturasPagosWs" minOccurs="0"/>
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
    "estadoFacturaResult"
})
@XmlRootElement(name = "EstadoFacturaResponse")
public class EstadoFacturaResponse {

    @XmlElement(name = "EstadoFacturaResult")
    protected FacturasPagosWs estadoFacturaResult;

    /**
     * Gets the value of the estadoFacturaResult property.
     * 
     * @return
     *     possible object is
     *     {@link FacturasPagosWs }
     *     
     */
    public FacturasPagosWs getEstadoFacturaResult() {
        return estadoFacturaResult;
    }

    /**
     * Sets the value of the estadoFacturaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacturasPagosWs }
     *     
     */
    public void setEstadoFacturaResult(FacturasPagosWs value) {
        this.estadoFacturaResult = value;
    }

}
