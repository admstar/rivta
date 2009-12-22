
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
 * <p>Java class for GetBookingDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetBookingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="healthcare_facility" type="{urn:riv:crm:scheduling:1}HsaIdType"/>
 *         &lt;element name="bookingId" type="{urn:riv:crm:scheduling:1}BookingIdType"/>
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
@XmlType(name = "GetBookingDetailsType", namespace = "urn:riv:crm:scheduling:GetBookingDetailsResponder:1", propOrder = {
    "healthcareFacility",
    "bookingId",
    "any"
})
public class GetBookingDetailsType {

    @XmlElement(name = "healthcare_facility", required = true)
    protected String healthcareFacility;
    @XmlElement(required = true)
    protected String bookingId;
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
