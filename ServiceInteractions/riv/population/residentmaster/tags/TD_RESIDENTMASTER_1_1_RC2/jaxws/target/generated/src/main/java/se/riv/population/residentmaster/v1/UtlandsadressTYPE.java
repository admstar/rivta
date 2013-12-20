
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
 * <p>Java class for UtlandsadressTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UtlandsadressTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Utdelningsadress1" type="{urn:riv:population:residentmaster:1}Strang35TYPE" minOccurs="0"/>
 *         &lt;element name="Utdelningsadress2" type="{urn:riv:population:residentmaster:1}Strang35TYPE" minOccurs="0"/>
 *         &lt;element name="Utdelningsadress3" type="{urn:riv:population:residentmaster:1}Strang35TYPE" minOccurs="0"/>
 *         &lt;element name="Land" type="{urn:riv:population:residentmaster:1}Strang35TYPE" minOccurs="0"/>
 *         &lt;element name="Utlandsadressdatum" type="{urn:riv:population:residentmaster:1}OfullstandigtDatumTYPE" minOccurs="0"/>
 *         &lt;element name="Rostrattsdatum" type="{urn:riv:population:residentmaster:1}OfullstandigtDatumTYPE" minOccurs="0"/>
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
@XmlType(name = "UtlandsadressTYPE", propOrder = {
    "utdelningsadress1",
    "utdelningsadress2",
    "utdelningsadress3",
    "land",
    "utlandsadressdatum",
    "rostrattsdatum",
    "any"
})
public class UtlandsadressTYPE {

    @XmlElement(name = "Utdelningsadress1")
    protected String utdelningsadress1;
    @XmlElement(name = "Utdelningsadress2")
    protected String utdelningsadress2;
    @XmlElement(name = "Utdelningsadress3")
    protected String utdelningsadress3;
    @XmlElement(name = "Land")
    protected String land;
    @XmlElement(name = "Utlandsadressdatum")
    protected String utlandsadressdatum;
    @XmlElement(name = "Rostrattsdatum")
    protected String rostrattsdatum;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the utdelningsadress1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtdelningsadress1() {
        return utdelningsadress1;
    }

    /**
     * Sets the value of the utdelningsadress1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtdelningsadress1(String value) {
        this.utdelningsadress1 = value;
    }

    /**
     * Gets the value of the utdelningsadress2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtdelningsadress2() {
        return utdelningsadress2;
    }

    /**
     * Sets the value of the utdelningsadress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtdelningsadress2(String value) {
        this.utdelningsadress2 = value;
    }

    /**
     * Gets the value of the utdelningsadress3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtdelningsadress3() {
        return utdelningsadress3;
    }

    /**
     * Sets the value of the utdelningsadress3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtdelningsadress3(String value) {
        this.utdelningsadress3 = value;
    }

    /**
     * Gets the value of the land property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLand() {
        return land;
    }

    /**
     * Sets the value of the land property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLand(String value) {
        this.land = value;
    }

    /**
     * Gets the value of the utlandsadressdatum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtlandsadressdatum() {
        return utlandsadressdatum;
    }

    /**
     * Sets the value of the utlandsadressdatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtlandsadressdatum(String value) {
        this.utlandsadressdatum = value;
    }

    /**
     * Gets the value of the rostrattsdatum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRostrattsdatum() {
        return rostrattsdatum;
    }

    /**
     * Sets the value of the rostrattsdatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRostrattsdatum(String value) {
        this.rostrattsdatum = value;
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
