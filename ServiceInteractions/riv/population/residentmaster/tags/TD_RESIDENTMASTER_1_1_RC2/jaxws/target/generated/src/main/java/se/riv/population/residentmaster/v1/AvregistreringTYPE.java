
package se.riv.population.residentmaster.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AvregistreringTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AvregistreringTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AvregistreringsorsakKod" type="{urn:riv:population:residentmaster:1}AvregistreringsorsakKodTYPE" minOccurs="0"/>
 *         &lt;element name="Avregistreringsdatum" type="{urn:riv:population:residentmaster:1}OfullstandigtDatumTYPE" minOccurs="0"/>
 *         &lt;element ref="{urn:riv:population:residentmaster:1.1}AvregistreringsorsakKodKomplett" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AvregistreringTYPE", propOrder = {
    "avregistreringsorsakKod",
    "avregistreringsdatum",
    "avregistreringsorsakKodKomplett"
})
public class AvregistreringTYPE {

    @XmlElement(name = "AvregistreringsorsakKod")
    protected AvregistreringsorsakKodTYPE avregistreringsorsakKod;
    @XmlElement(name = "Avregistreringsdatum")
    protected String avregistreringsdatum;
    @XmlElement(name = "AvregistreringsorsakKodKomplett", namespace = "urn:riv:population:residentmaster:1.1")
    protected AvregistreringsorsakKodKomplettTYPE avregistreringsorsakKodKomplett;

    /**
     * Gets the value of the avregistreringsorsakKod property.
     * 
     * @return
     *     possible object is
     *     {@link AvregistreringsorsakKodTYPE }
     *     
     */
    public AvregistreringsorsakKodTYPE getAvregistreringsorsakKod() {
        return avregistreringsorsakKod;
    }

    /**
     * Sets the value of the avregistreringsorsakKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvregistreringsorsakKodTYPE }
     *     
     */
    public void setAvregistreringsorsakKod(AvregistreringsorsakKodTYPE value) {
        this.avregistreringsorsakKod = value;
    }

    /**
     * Gets the value of the avregistreringsdatum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvregistreringsdatum() {
        return avregistreringsdatum;
    }

    /**
     * Sets the value of the avregistreringsdatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvregistreringsdatum(String value) {
        this.avregistreringsdatum = value;
    }

    /**
     * Gets the value of the avregistreringsorsakKodKomplett property.
     * 
     * @return
     *     possible object is
     *     {@link AvregistreringsorsakKodKomplettTYPE }
     *     
     */
    public AvregistreringsorsakKodKomplettTYPE getAvregistreringsorsakKodKomplett() {
        return avregistreringsorsakKodKomplett;
    }

    /**
     * Sets the value of the avregistreringsorsakKodKomplett property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvregistreringsorsakKodKomplettTYPE }
     *     
     */
    public void setAvregistreringsorsakKodKomplett(AvregistreringsorsakKodKomplettTYPE value) {
        this.avregistreringsorsakKodKomplett = value;
    }

}
