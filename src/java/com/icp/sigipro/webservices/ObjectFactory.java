
package com.icp.sigipro.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.icp.sigipro.webservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _FacturasWs_QNAME = new QName("https://fundacionucr.ac.cr/", "FacturasWs");
    private final static QName _FacturasPagosWs_QNAME = new QName("https://fundacionucr.ac.cr/", "FacturasPagosWs");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.icp.sigipro.webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DetalleServicio }
     * 
     */
    public DetalleServicio createDetalleServicio() {
        return new DetalleServicio();
    }

    /**
     * Create an instance of {@link FacturasPagosWs }
     * 
     */
    public FacturasPagosWs createFacturasPagosWs() {
        return new FacturasPagosWs();
    }

    /**
     * Create an instance of {@link FacturasWs }
     * 
     */
    public FacturasWs createFacturasWs() {
        return new FacturasWs();
    }

    /**
     * Create an instance of {@link PagoLinea }
     * 
     */
    public PagoLinea createPagoLinea() {
        return new PagoLinea();
    }

    /**
     * Create an instance of {@link LineasProductosResponse }
     * 
     */
    public LineasProductosResponse createLineasProductosResponse() {
        return new LineasProductosResponse();
    }

    /**
     * Create an instance of {@link EstadoFactura }
     * 
     */
    public EstadoFactura createEstadoFactura() {
        return new EstadoFactura();
    }

    /**
     * Create an instance of {@link PagoLineaResponse }
     * 
     */
    public PagoLineaResponse createPagoLineaResponse() {
        return new PagoLineaResponse();
    }

    /**
     * Create an instance of {@link LineasProductos }
     * 
     */
    public LineasProductos createLineasProductos() {
        return new LineasProductos();
    }

    /**
     * Create an instance of {@link EstadoFacturaResponse }
     * 
     */
    public EstadoFacturaResponse createEstadoFacturaResponse() {
        return new EstadoFacturaResponse();
    }

    /**
     * Create an instance of {@link DetalleServicioResponse }
     * 
     */
    public DetalleServicioResponse createDetalleServicioResponse() {
        return new DetalleServicioResponse();
    }

    /**
     * Create an instance of {@link ArrayOfPagosWs }
     * 
     */
    public ArrayOfPagosWs createArrayOfPagosWs() {
        return new ArrayOfPagosWs();
    }

    /**
     * Create an instance of {@link PagosWs }
     * 
     */
    public PagosWs createPagosWs() {
        return new PagosWs();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FacturasWs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://fundacionucr.ac.cr/", name = "FacturasWs")
    public JAXBElement<FacturasWs> createFacturasWs(FacturasWs value) {
        return new JAXBElement<FacturasWs>(_FacturasWs_QNAME, FacturasWs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FacturasPagosWs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "https://fundacionucr.ac.cr/", name = "FacturasPagosWs")
    public JAXBElement<FacturasPagosWs> createFacturasPagosWs(FacturasPagosWs value) {
        return new JAXBElement<FacturasPagosWs>(_FacturasPagosWs_QNAME, FacturasPagosWs.class, null, value);
    }

}
