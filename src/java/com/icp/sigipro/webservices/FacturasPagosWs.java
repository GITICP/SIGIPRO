
package com.icp.sigipro.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacturasPagosWs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacturasPagosWs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroFactura" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="estadoFactura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listPagos" type="{https://fundacionucr.ac.cr/}ArrayOfPagosWs" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacturasPagosWs", propOrder = {
    "numeroFactura",
    "estadoFactura",
    "listPagos"
})
public class FacturasPagosWs {

    protected int numeroFactura;
    protected String estadoFactura;
    protected ArrayOfPagosWs listPagos;

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
     * Gets the value of the estadoFactura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoFactura() {
        return estadoFactura;
    }

    /**
     * Sets the value of the estadoFactura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoFactura(String value) {
        this.estadoFactura = value;
    }

    /**
     * Gets the value of the listPagos property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPagosWs }
     *     
     */
    public ArrayOfPagosWs getListPagos() {
        return listPagos;
    }

    /**
     * Sets the value of the listPagos property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPagosWs }
     *     
     */
    public void setListPagos(ArrayOfPagosWs value) {
        this.listPagos = value;
    }

}
