//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.16 at 10:56:06 PM CEST 
//


package bezbednost.poslovna.xml.ws.izvod;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IzvodRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IzvodRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Zahtev" type="{http://xml.poslovna.bezbednost/ws/Izvod}TZahtev"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="IzvodRequest", namespace="http://izvod.ws.xml.poslovna.bezbednost/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IzvodRequest", propOrder = {
    "zahtev"
}, namespace="http://izvod.ws.xml.poslovna.bezbednost/")
public class IzvodRequest {

    @XmlElement(name = "Zahtev", required = true)
    protected TZahtev zahtev;

    /**
     * Gets the value of the zahtev property.
     * 
     * @return
     *     possible object is
     *     {@link TZahtev }
     *     
     */
    public TZahtev getZahtev() {
        return zahtev;
    }

    /**
     * Sets the value of the zahtev property.
     * 
     * @param value
     *     allowed object is
     *     {@link TZahtev }
     *     
     */
    public void setZahtev(TZahtev value) {
        this.zahtev = value;
    }

}
