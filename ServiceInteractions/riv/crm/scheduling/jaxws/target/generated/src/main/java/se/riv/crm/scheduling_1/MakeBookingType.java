
package se.riv.crm.scheduling_1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for MakeBookingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MakeBookingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="healthcare_facility_med" type="{urn:riv:crm:scheduling:1}HsaIdType"/>
 *         &lt;element name="requestedTimeslot" type="{urn:riv:crm:scheduling:1}TimeslotType"/>
 *         &lt;element name="subject_of_care_info" type="{urn:riv:crm:scheduling:1}SubjectOfCareType" minOccurs="0"/>
 *         &lt;element name="notification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "MakeBookingType", namespace = "urn:riv:crm:scheduling:MakeBookingResponder:1", propOrder = {
    "healthcareFacilityMed",
    "requestedTimeslot",
    "subjectOfCareInfo",
    "notification",
    "any"
})
public class MakeBookingType {

    @XmlElement(name = "healthcare_facility_med", required = true)
    protected String healthcareFacilityMed;
    @XmlElement(required = true)
    protected TimeslotType requestedTimeslot;
    @XmlElement(name = "subject_of_care_info")
    protected SubjectOfCareType subjectOfCareInfo;
    protected String notification;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the healthcareFacilityMed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHealthcareFacilityMed() {
        return healthcareFacilityMed;
    }

    /**
     * Sets the value of the healthcareFacilityMed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealthcareFacilityMed(String value) {
        this.healthcareFacilityMed = value;
    }

    /**
     * Gets the value of the requestedTimeslot property.
     * 
     * @return
     *     possible object is
     *     {@link TimeslotType }
     *     
     */
    public TimeslotType getRequestedTimeslot() {
        return requestedTimeslot;
    }

    /**
     * Sets the value of the requestedTimeslot property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeslotType }
     *     
     */
    public void setRequestedTimeslot(TimeslotType value) {
        this.requestedTimeslot = value;
    }

    /**
     * Gets the value of the subjectOfCareInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SubjectOfCareType }
     *     
     */
    public SubjectOfCareType getSubjectOfCareInfo() {
        return subjectOfCareInfo;
    }

    /**
     * Sets the value of the subjectOfCareInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjectOfCareType }
     *     
     */
    public void setSubjectOfCareInfo(SubjectOfCareType value) {
        this.subjectOfCareInfo = value;
    }

    /**
     * Gets the value of the notification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotification() {
        return notification;
    }

    /**
     * Sets the value of the notification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotification(String value) {
        this.notification = value;
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
