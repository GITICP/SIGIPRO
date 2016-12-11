
package com.icp.sigipro.webservices;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PagosWs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PagosWs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="nota" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="consecutive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="moneda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoRemision" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="consecutiveRemision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaRemision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagosWs", propOrder = {
    "codigo",
    "monto",
    "nota",
    "fecha",
    "consecutive",
    "moneda",
    "codigoRemision",
    "consecutiveRemision",
    "fechaRemision"
})
public class PagosWs {

    protected int codigo;
    @XmlElement(required = true)
    protected BigDecimal monto;
    protected String nota;
    protected String fecha;
    protected String consecutive;
    protected String moneda;
    protected int codigoRemision;
    protected String consecutiveRemision;
    protected String fechaRemision;

    /**
     * Gets the value of the codigo property.
     * 
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     */
    public void setCodigo(int value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the monto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Sets the value of the monto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonto(BigDecimal value) {
        this.monto = value;
    }

    /**
     * Gets the value of the nota property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNota() {
        return nota;
    }

    /**
     * Sets the value of the nota property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNota(String value) {
        this.nota = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecha(String value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the consecutive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsecutive() {
        return consecutive;
    }

    /**
     * Sets the value of the consecutive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsecutive(String value) {
        this.consecutive = value;
    }

    /**
     * Gets the value of the moneda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * Sets the value of the moneda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoneda(String value) {
        this.moneda = value;
    }

    /**
     * Gets the value of the codigoRemision property.
     * 
     */
    public int getCodigoRemision() {
        return codigoRemision;
    }

    /**
     * Sets the value of the codigoRemision property.
     * 
     */
    public void setCodigoRemision(int value) {
        this.codigoRemision = value;
    }

    /**
     * Gets the value of the consecutiveRemision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsecutiveRemision() {
        return consecutiveRemision;
    }

    /**
     * Sets the value of the consecutiveRemision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsecutiveRemision(String value) {
        this.consecutiveRemision = value;
    }

    /**
     * Gets the value of the fechaRemision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechaRemision() {
        return fechaRemision;
    }

    /**
     * Sets the value of the fechaRemision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechaRemision(String value) {
        this.fechaRemision = value;
    }

}
