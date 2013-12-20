
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
 * <p>Java class for FodelseTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FodelseTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HemortSverige" type="{urn:riv:population:residentmaster:1}HemortSverigeTYPE" minOccurs="0"/>
 *         &lt;element name="OrtUtlandet" type="{urn:riv:population:residentmaster:1}OrtUtlandetTYPE" minOccurs="0"/>
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
@XmlType(name = "FodelseTYPE", propOrder = {
    "hemortSverige",
    "ortUtlandet",
    "any"
})
public class FodelseTYPE {

    @XmlElement(name = "HemortSverige")
    protected HemortSverigeTYPE hemortSverige;
    @XmlElement(name = "OrtUtlandet")
    protected OrtUtlandetTYPE ortUtlandet;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the hemortSverige property.
     * 
     * @return
     *     possible object is
     *     {@link HemortSverigeTYPE }
     *     
     */
    public HemortSverigeTYPE getHemortSverige() {
        return hemortSverige;
    }

    /**
     * Sets the value of the hemortSverige property.
     * 
     * @param value
     *     allowed object is
     *     {@link HemortSverigeTYPE }
     *     
     */
    public void setHemortSverige(HemortSverigeTYPE value) {
        this.hemortSverige = value;
    }

    /**
     * Gets the value of the ortUtlandet property.
     * 
     * @return
     *     possible object is
     *     {@link OrtUtlandetTYPE }
     *     
     */
    public OrtUtlandetTYPE getOrtUtlandet() {
        return ortUtlandet;
    }

    /**
     * Sets the value of the ortUtlandet property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrtUtlandetTYPE }
     *     
     */
    public void setOrtUtlandet(OrtUtlandetTYPE value) {
        this.ortUtlandet = value;
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
