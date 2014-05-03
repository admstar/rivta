
package se.riv.ehr.accesscontrol.assertcareengagementresponder.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.riv.ehr.accesscontrol.assertcareengagementresponder.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AssertCareEngagementResponse_QNAME = new QName("urn:riv:ehr:accesscontrol:AssertCareEngagementResponder:1", "AssertCareEngagementResponse");
    private final static QName _AssertCareEngagement_QNAME = new QName("urn:riv:ehr:accesscontrol:AssertCareEngagementResponder:1", "AssertCareEngagement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.riv.ehr.accesscontrol.assertcareengagementresponder.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AssertCareEngagementType }
     * 
     */
    public AssertCareEngagementType createAssertCareEngagementType() {
        return new AssertCareEngagementType();
    }

    /**
     * Create an instance of {@link AssertCareEngagementResponseType }
     * 
     */
    public AssertCareEngagementResponseType createAssertCareEngagementResponseType() {
        return new AssertCareEngagementResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssertCareEngagementResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:ehr:accesscontrol:AssertCareEngagementResponder:1", name = "AssertCareEngagementResponse")
    public JAXBElement<AssertCareEngagementResponseType> createAssertCareEngagementResponse(AssertCareEngagementResponseType value) {
        return new JAXBElement<AssertCareEngagementResponseType>(_AssertCareEngagementResponse_QNAME, AssertCareEngagementResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssertCareEngagementType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:ehr:accesscontrol:AssertCareEngagementResponder:1", name = "AssertCareEngagement")
    public JAXBElement<AssertCareEngagementType> createAssertCareEngagement(AssertCareEngagementType value) {
        return new JAXBElement<AssertCareEngagementType>(_AssertCareEngagement_QNAME, AssertCareEngagementType.class, null, value);
    }

}
