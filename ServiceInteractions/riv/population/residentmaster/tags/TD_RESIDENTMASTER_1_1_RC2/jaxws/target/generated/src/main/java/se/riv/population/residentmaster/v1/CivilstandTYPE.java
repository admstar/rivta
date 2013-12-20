
package se.riv.population.residentmaster.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for CivilstandTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CivilstandTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CivilstandKod" type="{urn:riv:population:residentmaster:1}CivilstandKodTYPE" minOccurs="0"/>
 *         &lt;element name="Civilstandsdatum" type="{urn:riv:population:residentmaster:1}OfullstandigtDatumTYPE" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CivilstandTYPE", propOrder = {
    "civilstandKod",
    "civilstandsdatum",
    "any"
})
public class CivilstandTYPE {

    @XmlElement(name = "CivilstandKod")
    protected CivilstandKodTYPE civilstandKod;
    @XmlElement(name = "Civilstandsdatum")
    protected String civilstandsdatum;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the civilstandKod property.
     * 
     * @return
     *     possible object is
     *     {@link CivilstandKodTYPE }
     *     
     */
    public CivilstandKodTYPE getCivilstandKod() {
        return civilstandKod;
    }

    /**
     * Sets the value of the civilstandKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CivilstandKodTYPE }
     *     
     */
    public void setCivilstandKod(CivilstandKodTYPE value) {
        this.civilstandKod = value;
    }

    /**
     * Gets the value of the civilstandsdatum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivilstandsdatum() {
        return civilstandsdatum;
    }

    /**
     * Sets the value of the civilstandsdatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivilstandsdatum(String value) {
        this.civilstandsdatum = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

}
