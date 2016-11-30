
package com.icp.sigipro.webservices;

import java.math.BigDecimal;
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
 *         &lt;element name="codigoOrden" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="usuarioEjecuta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUsuarioEjecuta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nombreFactura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="proyecto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="moneda" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="plazo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="porcentajeDescuento" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="subtotalFactura" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="totalFactura" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="correoEnviar" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="notas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productosJson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="llave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "codigoOrden",
    "usuarioEjecuta",
    "codUsuarioEjecuta",
    "nombreFactura",
    "proyecto",
    "moneda",
    "plazo",
    "porcentajeDescuento",
    "subtotalFactura",
    "totalFactura",
    "correoEnviar",
    "notas",
    "productosJson",
    "llave"
})
@XmlRootElement(name = "LineasProductos")
public class LineasProductos {

    protected int codigoOrden;
    protected String usuarioEjecuta;
    protected int codUsuarioEjecuta;
    protected String nombreFactura;
    protected int proyecto;
    protected int moneda;
    protected int plazo;
    @XmlElement(required = true)
    protected BigDecimal porcentajeDescuento;
    @XmlElement(required = true)
    protected BigDecimal subtotalFactura;
    @XmlElement(required = true)
    protected BigDecimal totalFactura;
    protected String correoEnviar;
    protected String notas;
    protected String productosJson;
    protected String llave;

    /**
     * Gets the value of the codigoOrden property.
     * 
     */
    public int getCodigoOrden() {
        return codigoOrden;
    }

    /**
     * Sets the value of the codigoOrden property.
     * 
     */
    public void setCodigoOrden(int value) {
        this.codigoOrden = value;
    }

    /**
     * Gets the value of the usuarioEjecuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuarioEjecuta() {
        return usuarioEjecuta;
    }

    /**
     * Sets the value of the usuarioEjecuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioEjecuta(String value) {
        this.usuarioEjecuta = value;
    }

    /**
     * Gets the value of the codUsuarioEjecuta property.
     * 
     */
    public int getCodUsuarioEjecuta() {
        return codUsuarioEjecuta;
    }

    /**
     * Sets the value of the codUsuarioEjecuta property.
     * 
     */
    public void setCodUsuarioEjecuta(int value) {
        this.codUsuarioEjecuta = value;
    }

    /**
     * Gets the value of the nombreFactura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreFactura() {
        return nombreFactura;
    }

    /**
     * Sets the value of the nombreFactura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreFactura(String value) {
        this.nombreFactura = value;
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

    /**
     * Gets the value of the moneda property.
     * 
     */
    public int getMoneda() {
        return moneda;
    }

    /**
     * Sets the value of the moneda property.
     * 
     */
    public void setMoneda(int value) {
        this.moneda = value;
    }

    /**
     * Gets the value of the plazo property.
     * 
     */
    public int getPlazo() {
        return plazo;
    }

    /**
     * Sets the value of the plazo property.
     * 
     */
    public void setPlazo(int value) {
        this.plazo = value;
    }

    /**
     * Gets the value of the porcentajeDescuento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    /**
     * Sets the value of the porcentajeDescuento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPorcentajeDescuento(BigDecimal value) {
        this.porcentajeDescuento = value;
    }

    /**
     * Gets the value of the subtotalFactura property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSubtotalFactura() {
        return subtotalFactura;
    }

    /**
     * Sets the value of the subtotalFactura property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSubtotalFactura(BigDecimal value) {
        this.subtotalFactura = value;
    }

    /**
     * Gets the value of the totalFactura property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    /**
     * Sets the value of the totalFactura property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalFactura(BigDecimal value) {
        this.totalFactura = value;
    }

    /**
     * Gets the value of the correoEnviar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorreoEnviar() {
        return correoEnviar;
    }

    /**
     * Sets the value of the correoEnviar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorreoEnviar(String value) {
        this.correoEnviar = value;
    }

    /**
     * Gets the value of the notas property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotas() {
        return notas;
    }

    /**
     * Sets the value of the notas property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotas(String value) {
        this.notas = value;
    }

    /**
     * Gets the value of the productosJson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductosJson() {
        return productosJson;
    }

    /**
     * Sets the value of the productosJson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductosJson(String value) {
        this.productosJson = value;
    }

    /**
     * Gets the value of the llave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLlave() {
        return llave;
    }

    /**
     * Sets the value of the llave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLlave(String value) {
        this.llave = value;
    }

}
