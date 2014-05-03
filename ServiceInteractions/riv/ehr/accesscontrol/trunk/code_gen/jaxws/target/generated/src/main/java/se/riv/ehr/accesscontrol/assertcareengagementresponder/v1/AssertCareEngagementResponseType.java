
package se.riv.ehr.accesscontrol.assertcareengagementresponder.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for AssertCareEngagementResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssertCareEngagementResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HasCareEngagement" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "AssertCareEngagementResponseType", propOrder = {
    "hasCareEngagement",
    "any"
})
public class AssertCareEngagementResponseType {

    @XmlElement(name = "HasCareEngagement")
    protected boolean hasCareEngagement;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the hasCareEngagement property.
     * 
     */
    public boolean isHasCareEngagement() {
        return hasCareEngagement;
    }

    /**
     * Sets the value of the hasCareEngagement property.
     * 
     */
    public void setHasCareEngagement(boolean value) {
        this.hasCareEngagement = value;
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
     * {@link Object }
     * {@link Element }
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
