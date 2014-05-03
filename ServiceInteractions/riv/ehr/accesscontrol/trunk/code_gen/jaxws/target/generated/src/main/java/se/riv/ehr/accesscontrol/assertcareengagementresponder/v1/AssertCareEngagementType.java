
package se.riv.ehr.accesscontrol.assertcareengagementresponder.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * Request parameters of the AssertCareEngagement service
 * 
 * <p>Java class for AssertCareEngagementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AssertCareEngagementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="performer" type="{urn:riv:ehr:accesscontrol:1}HsaIdType"/>
 *         &lt;element name="subjectOfCareId" type="{urn:riv:ehr:accesscontrol:1}SubjectOfCareIdType"/>
 *         &lt;element name="careUnitHsaIdentity" type="{urn:riv:ehr:accesscontrol:1}HsaIdType"/>
 *         &lt;element name="careGiverHsaIdentity" type="{urn:riv:ehr:accesscontrol:1}HsaIdType"/>
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
@XmlType(name = "AssertCareEngagementType", propOrder = {
    "performer",
    "subjectOfCareId",
    "careUnitHsaIdentity",
    "careGiverHsaIdentity",
    "any"
})
public class AssertCareEngagementType {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String performer;
    @XmlElement(required = true)
    protected String subjectOfCareId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String careUnitHsaIdentity;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String careGiverHsaIdentity;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

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
     * Gets the value of the subjectOfCareId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectOfCareId() {
        return subjectOfCareId;
    }

    /**
     * Sets the value of the subjectOfCareId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectOfCareId(String value) {
        this.subjectOfCareId = value;
    }

    /**
     * Gets the value of the careUnitHsaIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCareUnitHsaIdentity() {
        return careUnitHsaIdentity;
    }

    /**
     * Sets the value of the careUnitHsaIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCareUnitHsaIdentity(String value) {
        this.careUnitHsaIdentity = value;
    }

    /**
     * Gets the value of the careGiverHsaIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCareGiverHsaIdentity() {
        return careGiverHsaIdentity;
    }

    /**
     * Sets the value of the careGiverHsaIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCareGiverHsaIdentity(String value) {
        this.careGiverHsaIdentity = value;
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
