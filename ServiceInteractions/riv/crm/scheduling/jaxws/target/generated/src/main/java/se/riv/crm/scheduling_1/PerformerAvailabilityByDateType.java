
package se.riv.crm.scheduling_1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import org.w3c.dom.Element;


/**
 * <p>Java class for PerformerAvailabilityByDateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerformerAvailabilityByDateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="healthcare_facility" type="{urn:riv:crm:scheduling:1}HsaIdType"/>
 *         &lt;element name="performer" type="{urn:riv:crm:scheduling:1}HsaIdType" minOccurs="0"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="resourceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resourceID" type="{urn:riv:crm:scheduling:1}ResourceIDType" minOccurs="0"/>
 *         &lt;element name="timeTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timeTypeID" type="{urn:riv:crm:scheduling:1}TimeTypeIDType" minOccurs="0"/>
 *         &lt;element name="careTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="careTypeID" type="{urn:riv:crm:scheduling:1}CareTypeIDType" minOccurs="0"/>
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
@XmlType(name = "PerformerAvailabilityByDateType", propOrder = {
    "healthcareFacility",
    "performer",
    "date",
    "resourceName",
    "resourceID",
    "timeTypeName",
    "timeTypeID",
    "careTypeName",
    "careTypeID",
    "any"
})
public class PerformerAvailabilityByDateType {

    @XmlElement(name = "healthcare_facility", required = true)
    protected String healthcareFacility;
    protected String performer;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    protected String resourceName;
    protected String resourceID;
    protected String timeTypeName;
    protected String timeTypeID;
    protected String careTypeName;
    protected String careTypeID;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the healthcareFacility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHealthcareFacility() {
        return healthcareFacility;
    }

    /**
     * Sets the value of the healthcareFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealthcareFacility(String value) {
        this.healthcareFacility = value;
    }

    /**
     * Gets the value of the performer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformer() {
        return performer;
    }

    /**
     * Sets the value of the performer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformer(String value) {
        this.performer = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the resourceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Sets the value of the resourceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceName(String value) {
        this.resourceName = value;
    }

    /**
     * Gets the value of the resourceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceID() {
        return resourceID;
    }

    /**
     * Sets the value of the resourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceID(String value) {
        this.resourceID = value;
    }

    /**
     * Gets the value of the timeTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeTypeName() {
        return timeTypeName;
    }

    /**
     * Sets the value of the timeTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeTypeName(String value) {
        this.timeTypeName = value;
    }

    /**
     * Gets the value of the timeTypeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeTypeID() {
        return timeTypeID;
    }

    /**
     * Sets the value of the timeTypeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeTypeID(String value) {
        this.timeTypeID = value;
    }

    /**
     * Gets the value of the careTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCareTypeName() {
        return careTypeName;
    }

    /**
     * Sets the value of the careTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCareTypeName(String value) {
        this.careTypeName = value;
    }

    /**
     * Gets the value of the careTypeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCareTypeID() {
        return careTypeID;
    }

    /**
     * Sets the value of the careTypeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCareTypeID(String value) {
        this.careTypeID = value;
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
