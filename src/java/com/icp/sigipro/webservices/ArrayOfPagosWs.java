
package com.icp.sigipro.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfPagosWs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPagosWs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PagosWs" type="{https://fundacionucr.ac.cr/}PagosWs" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPagosWs", propOrder = {
    "pagosWs"
})
public class ArrayOfPagosWs {

    @XmlElement(name = "PagosWs", nillable = true)
    protected List<PagosWs> pagosWs;

    /**
     * Gets the value of the pagosWs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pagosWs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPagosWs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PagosWs }
     * 
     * 
     */
    public List<PagosWs> getPagosWs() {
        if (pagosWs == null) {
            pagosWs = new ArrayList<PagosWs>();
        }
        return this.pagosWs;
    }

}
