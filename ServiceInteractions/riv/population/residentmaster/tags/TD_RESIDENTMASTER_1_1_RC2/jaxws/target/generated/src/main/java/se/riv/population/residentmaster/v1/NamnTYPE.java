
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
 * <p>Java class for NamnTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NamnTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tilltalsnamnsmarkering" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *               &lt;minInclusive value="10"/>
 *               &lt;maxInclusive value="99"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Fornamn" type="{urn:riv:population:residentmaster:1}Strang80TYPE" minOccurs="0"/>
 *         &lt;element name="Mellannamn" type="{urn:riv:population:residentmaster:1}Strang40TYPE" minOccurs="0"/>
 *         &lt;element name="Efternamn" type="{urn:riv:population:residentmaster:1}Strang60TYPE" minOccurs="0"/>
 *         &lt;element name="Aviseringsnamn" type="{urn:riv:population:residentmaster:1}Strang36TYPE" minOccurs="0"/>
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
@XmlType(name = "NamnTYPE", propOrder = {
    "tilltalsnamnsmarkering",
    "fornamn",
    "mellannamn",
    "efternamn",
    "aviseringsnamn",
    "any"
})
public class NamnTYPE {

    @XmlElement(name = "Tilltalsnamnsmarkering")
    protected Integer tilltalsnamnsmarkering;
    @XmlElement(name = "Fornamn")
    protected String fornamn;
    @XmlElement(name = "Mellannamn")
    protected String mellannamn;
    @XmlElement(name = "Efternamn")
    protected String efternamn;
    @XmlElement(name = "Aviseringsnamn")
    protected String aviseringsnamn;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the tilltalsnamnsmarkering property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTilltalsnamnsmarkering() {
        return tilltalsnamnsmarkering;
    }

    /**
     * Sets the value of the tilltalsnamnsmarkering property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTilltalsnamnsmarkering(Integer value) {
        this.tilltalsnamnsmarkering = value;
    }

    /**
     * Gets the value of the fornamn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFornamn() {
        return fornamn;
    }

    /**
     * Sets the value of the fornamn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFornamn(String value) {
        this.fornamn = value;
    }

    /**
     * Gets the value of the mellannamn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMellannamn() {
        return mellannamn;
    }

    /**
     * Sets the value of the mellannamn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMellannamn(String value) {
        this.mellannamn = value;
    }

    /**
     * Gets the value of the efternamn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEfternamn() {
        return efternamn;
    }

    /**
     * Sets the value of the efternamn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEfternamn(String value) {
        this.efternamn = value;
    }

    /**
     * Gets the value of the aviseringsnamn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAviseringsnamn() {
        return aviseringsnamn;
    }

    /**
     * Sets the value of the aviseringsnamn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAviseringsnamn(String value) {
        this.aviseringsnamn = value;
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
