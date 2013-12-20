
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
 * <p>Java class for OrtUtlandetTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrtUtlandetTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FodelseortUtland" type="{urn:riv:population:residentmaster:1}Strang80TYPE" minOccurs="0"/>
 *         &lt;element name="Styrkt" type="{urn:riv:population:residentmaster:1}JaNejTYPE" minOccurs="0"/>
 *         &lt;element name="Fodelseland" type="{urn:riv:population:residentmaster:1}Strang40TYPE" minOccurs="0"/>
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
@XmlType(name = "OrtUtlandetTYPE", propOrder = {
    "fodelseortUtland",
    "styrkt",
    "fodelseland",
    "any"
})
public class OrtUtlandetTYPE {

    @XmlElement(name = "FodelseortUtland")
    protected String fodelseortUtland;
    @XmlElement(name = "Styrkt")
    protected JaNejTYPE styrkt;
    @XmlElement(name = "Fodelseland")
    protected String fodelseland;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the fodelseortUtland property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFodelseortUtland() {
        return fodelseortUtland;
    }

    /**
     * Sets the value of the fodelseortUtland property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFodelseortUtland(String value) {
        this.fodelseortUtland = value;
    }

    /**
     * Gets the value of the styrkt property.
     * 
     * @return
     *     possible object is
     *     {@link JaNejTYPE }
     *     
     */
    public JaNejTYPE getStyrkt() {
        return styrkt;
    }

    /**
     * Sets the value of the styrkt property.
     * 
     * @param value
     *     allowed object is
     *     {@link JaNejTYPE }
     *     
     */
    public void setStyrkt(JaNejTYPE value) {
        this.styrkt = value;
    }

    /**
     * Gets the value of the fodelseland property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFodelseland() {
        return fodelseland;
    }

    /**
     * Sets the value of the fodelseland property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFodelseland(String value) {
        this.fodelseland = value;
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
