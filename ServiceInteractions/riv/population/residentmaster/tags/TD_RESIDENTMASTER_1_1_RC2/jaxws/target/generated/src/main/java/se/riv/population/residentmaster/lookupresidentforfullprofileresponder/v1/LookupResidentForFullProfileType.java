
package se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for LookupResidentForFullProfileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LookupResidentForFullProfileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="personId" type="{urn:riv:population:residentmaster:1}PersonIdTYPE" maxOccurs="unbounded"/>
 *         &lt;element name="lookUpSpecification" type="{urn:riv:population:residentmaster:LookupResidentForFullProfileResponder:1}LookUpSpecificationType"/>
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
@XmlType(name = "LookupResidentForFullProfileType", propOrder = {
    "personId",
    "lookUpSpecification",
    "any"
})
public class LookupResidentForFullProfileType {

    @XmlElement(required = true)
    protected List<String> personId;
    @XmlElement(required = true)
    protected LookUpSpecificationType lookUpSpecification;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the personId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPersonId() {
        if (personId == null) {
            personId = new ArrayList<String>();
        }
        return this.personId;
    }

    /**
     * Gets the value of the lookUpSpecification property.
     * 
     * @return
     *     possible object is
     *     {@link LookUpSpecificationType }
     *     
     */
    public LookUpSpecificationType getLookUpSpecification() {
        return lookUpSpecification;
    }

    /**
     * Sets the value of the lookUpSpecification property.
     * 
     * @param value
     *     allowed object is
     *     {@link LookUpSpecificationType }
     *     
     */
    public void setLookUpSpecification(LookUpSpecificationType value) {
        this.lookUpSpecification = value;
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
