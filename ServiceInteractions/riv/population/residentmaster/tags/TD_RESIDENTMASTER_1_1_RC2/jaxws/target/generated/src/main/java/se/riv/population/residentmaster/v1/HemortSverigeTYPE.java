
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
 * <p>Java class for HemortSverigeTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HemortSverigeTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FodelselanKod" type="{urn:riv:population:residentmaster:1}Strang2TYPE" minOccurs="0"/>
 *         &lt;element name="Fodelseforsamling" type="{urn:riv:population:residentmaster:1}Strang2TYPE" minOccurs="0"/>
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
@XmlType(name = "HemortSverigeTYPE", propOrder = {
    "fodelselanKod",
    "fodelseforsamling",
    "any"
})
public class HemortSverigeTYPE {

    @XmlElement(name = "FodelselanKod")
    protected String fodelselanKod;
    @XmlElement(name = "Fodelseforsamling")
    protected String fodelseforsamling;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the fodelselanKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFodelselanKod() {
        return fodelselanKod;
    }

    /**
     * Sets the value of the fodelselanKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFodelselanKod(String value) {
        this.fodelselanKod = value;
    }

    /**
     * Gets the value of the fodelseforsamling property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFodelseforsamling() {
        return fodelseforsamling;
    }

    /**
     * Sets the value of the fodelseforsamling property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFodelseforsamling(String value) {
        this.fodelseforsamling = value;
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
