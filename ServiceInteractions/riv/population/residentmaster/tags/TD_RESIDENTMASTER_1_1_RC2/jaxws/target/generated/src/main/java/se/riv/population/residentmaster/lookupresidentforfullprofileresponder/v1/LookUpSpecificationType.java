
package se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;
import se.riv.population.residentmaster.v1.JaNejTYPE;


/**
 * <p>Java class for LookUpSpecificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LookUpSpecificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Avregistreringsorsak" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Sekretessmarkering" type="{urn:riv:population:residentmaster:1}JaNejTYPE" minOccurs="0"/>
 *         &lt;element name="SenasteAndringFolkbokforing" type="{urn:riv:population:residentmaster:1}DT" minOccurs="0"/>
 *         &lt;element name="historiskTidpunkt" type="{urn:riv:population:residentmaster:1}TS" minOccurs="0"/>
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
@XmlType(name = "LookUpSpecificationType", propOrder = {
    "avregistreringsorsak",
    "sekretessmarkering",
    "senasteAndringFolkbokforing",
    "historiskTidpunkt",
    "any"
})
public class LookUpSpecificationType {

    @XmlElement(name = "Avregistreringsorsak")
    protected List<String> avregistreringsorsak;
    @XmlElement(name = "Sekretessmarkering")
    protected JaNejTYPE sekretessmarkering;
    @XmlElement(name = "SenasteAndringFolkbokforing")
    protected String senasteAndringFolkbokforing;
    protected String historiskTidpunkt;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the avregistreringsorsak property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the avregistreringsorsak property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvregistreringsorsak().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAvregistreringsorsak() {
        if (avregistreringsorsak == null) {
            avregistreringsorsak = new ArrayList<String>();
        }
        return this.avregistreringsorsak;
    }

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
     * Gets the value of the historiskTidpunkt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoriskTidpunkt() {
        return historiskTidpunkt;
    }

    /**
     * Sets the value of the historiskTidpunkt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoriskTidpunkt(String value) {
        this.historiskTidpunkt = value;
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
