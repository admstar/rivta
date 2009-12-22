
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
 * <p>Java class for TimeslotType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimeslotType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startTimeInclusive" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="endTimeExclusive" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="healthcare_facility" type="{urn:riv:crm:scheduling:1}HsaIdType"/>
 *         &lt;element name="performer" type="{urn:riv:crm:scheduling:1}HsaIdType" minOccurs="0"/>
 *         &lt;element name="bookingId" type="{urn:riv:crm:scheduling:1}BookingIdType" minOccurs="0"/>
 *         &lt;element name="subject_of_care" type="{urn:riv:crm:scheduling:1}SubjectOfCareIdType"/>
 *         &lt;element name="purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resourceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="healthcare_facility_name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="performerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resourceID" type="{urn:riv:crm:scheduling:1}ResourceIDType" minOccurs="0"/>
 *         &lt;element name="timeTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="timeTypeID" type="{urn:riv:crm:scheduling:1}TimeTypeIDType" minOccurs="0"/>
 *         &lt;element name="careTypeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="careTypeID" type="{urn:riv:crm:scheduling:1}CareTypeIDType" minOccurs="0"/>
 *         &lt;element name="cancel_booking_allowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="rebooking_allowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="message_allowed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "TimeslotType", namespace = "urn:riv:crm:scheduling:1", propOrder = {
    "startTimeInclusive",
    "endTimeExclusive",
    "healthcareFacility",
    "performer",
    "bookingId",
    "subjectOfCare",
    "purpose",
    "reason",
    "resourceName",
    "healthcareFacilityName",
    "performerName",
    "resourceID",
    "timeTypeName",
    "timeTypeID",
    "careTypeName",
    "careTypeID",
    "cancelBookingAllowed",
    "rebookingAllowed",
    "messageAllowed",
    "any"
})
public class TimeslotType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTimeInclusive;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTimeExclusive;
    @XmlElement(name = "healthcare_facility", required = true)
    protected String healthcareFacility;
    protected String performer;
    protected String bookingId;
    @XmlElement(name = "subject_of_care", required = true)
    protected String subjectOfCare;
    protected String purpose;
    protected String reason;
    protected String resourceName;
    @XmlElement(name = "healthcare_facility_name")
    protected String healthcareFacilityName;
    protected String performerName;
    protected String resourceID;
    protected String timeTypeName;
    protected String timeTypeID;
    protected String careTypeName;
    protected String careTypeID;
    @XmlElement(name = "cancel_booking_allowed")
    protected boolean cancelBookingAllowed;
    @XmlElement(name = "rebooking_allowed")
    protected boolean rebookingAllowed;
    @XmlElement(name = "message_allowed")
    protected boolean messageAllowed;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the startTimeInclusive property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTimeInclusive() {
        return startTimeInclusive;
    }

    /**
     * Sets the value of the startTimeInclusive property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTimeInclusive(XMLGregorianCalendar value) {
        this.startTimeInclusive = value;
    }

    /**
     * Gets the value of the endTimeExclusive property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTimeExclusive() {
        return endTimeExclusive;
    }

    /**
     * Sets the value of the endTimeExclusive property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTimeExclusive(XMLGregorianCalendar value) {
        this.endTimeExclusive = value;
    }

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
     * Gets the value of the bookingId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Sets the value of the bookingId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingId(String value) {
        this.bookingId = value;
    }

    /**
     * Gets the value of the subjectOfCare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectOfCare() {
        return subjectOfCare;
    }

    /**
     * Sets the value of the subjectOfCare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectOfCare(String value) {
        this.subjectOfCare = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
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
     * Gets the value of the healthcareFacilityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHealthcareFacilityName() {
        return healthcareFacilityName;
    }

    /**
     * Sets the value of the healthcareFacilityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHealthcareFacilityName(String value) {
        this.healthcareFacilityName = value;
    }

    /**
     * Gets the value of the performerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerformerName() {
        return performerName;
    }

    /**
     * Sets the value of the performerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformerName(String value) {
        this.performerName = value;
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
     * Gets the value of the cancelBookingAllowed property.
     * 
     */
    public boolean isCancelBookingAllowed() {
        return cancelBookingAllowed;
    }

    /**
     * Sets the value of the cancelBookingAllowed property.
     * 
     */
    public void setCancelBookingAllowed(boolean value) {
        this.cancelBookingAllowed = value;
    }

    /**
     * Gets the value of the rebookingAllowed property.
     * 
     */
    public boolean isRebookingAllowed() {
        return rebookingAllowed;
    }

    /**
     * Sets the value of the rebookingAllowed property.
     * 
     */
    public void setRebookingAllowed(boolean value) {
        this.rebookingAllowed = value;
    }

    /**
     * Gets the value of the messageAllowed property.
     * 
     */
    public boolean isMessageAllowed() {
        return messageAllowed;
    }

    /**
     * Sets the value of the messageAllowed property.
     * 
     */
    public void setMessageAllowed(boolean value) {
        this.messageAllowed = value;
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
