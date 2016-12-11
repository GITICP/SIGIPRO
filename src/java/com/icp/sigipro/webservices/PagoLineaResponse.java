
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
 *         &lt;element name="PagoLineaResult" type="{https://fundacionucr.ac.cr/}FacturasWs" minOccurs="0"/>
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
    "pagoLineaResult"
})
@XmlRootElement(name = "PagoLineaResponse")
public class PagoLineaResponse {

    @XmlElement(name = "PagoLineaResult")
    protected FacturasWs pagoLineaResult;

    /**
     * Gets the value of the pagoLineaResult property.
     * 
     * @return
     *     possible object is
     *     {@link FacturasWs }
     *     
     */
    public FacturasWs getPagoLineaResult() {
        return pagoLineaResult;
    }

    /**
     * Sets the value of the pagoLineaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacturasWs }
     *     
     */
    public void setPagoLineaResult(FacturasWs value) {
        this.pagoLineaResult = value;
    }

}
