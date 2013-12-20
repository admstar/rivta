
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
 * <p>Java class for ResidentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResidentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sekretessmarkering" type="{urn:riv:population:residentmaster:1}JaNejTYPE"/>
 *         &lt;element name="SenasteAndringFolkbokforing" type="{urn:riv:population:residentmaster:1}DT" minOccurs="0"/>
 *         &lt;element name="Personpost" type="{urn:riv:population:residentmaster:1}PersonpostTYPE"/>
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
@XmlType(name = "ResidentType", propOrder = {
    "sekretessmarkering",
    "senasteAndringFolkbokforing",
    "personpost",
    "any"
})
public class ResidentType {

    @XmlElement(name = "Sekretessmarkering", required = true)
    protected JaNejTYPE sekretessmarkering;
    @XmlElement(name = "SenasteAndringFolkbokforing")
    protected String senasteAndringFolkbokforing;
    @XmlElement(name = "Personpost", required = true)
    protected PersonpostTYPE personpost;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the sekretessmarkering property.
     * 
     * @return
     *     possible object is
     *     {@link JaNejTYPE }
     *     
     */
    public JaNejTYPE getSekretessmarkering() {
        return sekretessmarkering;
    }

    /**
     * Sets the value of the sekretessmarkering property.
     * 
     * @param value
     *     allowed object is
     *     {@link JaNejTYPE }
     *     
     */
    public void setSekretessmarkering(JaNejTYPE value) {
        this.sekretessmarkering = value;
    }

    /**
     * Gets the value of the senasteAndringFolkbokforing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenasteAndringFolkbokforing() {
        return senasteAndringFolkbokforing;
    }

    /**
     * Sets the value of the senasteAndringFolkbokforing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenasteAndringFolkbokforing(String value) {
        this.senasteAndringFolkbokforing = value;
    }

    /**
     * Gets the value of the personpost property.
     * 
     * @return
     *     possible object is
     *     {@link PersonpostTYPE }
     *     
     */
    public PersonpostTYPE getPersonpost() {
        return personpost;
    }

    /**
     * Sets the value of the personpost property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonpostTYPE }
     *     
     */
    public void setPersonpost(PersonpostTYPE value) {
        this.personpost = value;
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
