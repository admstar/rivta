
package se.riv.crm.scheduling_1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.riv.crm.scheduling_1 package. 
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

    private final static QName _GetAvailableDates_QNAME = new QName("urn:riv:crm:scheduling:GetAvailableDatesResponder:1", "GetAvailableDates");
    private final static QName _GetAvailableDatesResponse_QNAME = new QName("urn:riv:crm:scheduling:GetAvailableDatesResponder:1", "GetAvailableDatesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.riv.crm.scheduling_1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SubjectOfCareType }
     * 
     */
    public SubjectOfCareType createSubjectOfCareType() {
        return new SubjectOfCareType();
    }

    /**
     * Create an instance of {@link GetAvailableDatesType }
     * 
     */
    public GetAvailableDatesType createGetAvailableDatesType() {
        return new GetAvailableDatesType();
    }

    /**
     * Create an instance of {@link TimeslotType }
     * 
     */
    public TimeslotType createTimeslotType() {
        return new TimeslotType();
    }

    /**
     * Create an instance of {@link GetAvailableDatesResponseType }
     * 
     */
    public GetAvailableDatesResponseType createGetAvailableDatesResponseType() {
        return new GetAvailableDatesResponseType();
    }

    /**
     * Create an instance of {@link PerformerAvailabilityByDateType }
     * 
     */
    public PerformerAvailabilityByDateType createPerformerAvailabilityByDateType() {
        return new PerformerAvailabilityByDateType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableDatesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:crm:scheduling:GetAvailableDatesResponder:1", name = "GetAvailableDates")
    public JAXBElement<GetAvailableDatesType> createGetAvailableDates(GetAvailableDatesType value) {
        return new JAXBElement<GetAvailableDatesType>(_GetAvailableDates_QNAME, GetAvailableDatesType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAvailableDatesResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:crm:scheduling:GetAvailableDatesResponder:1", name = "GetAvailableDatesResponse")
    public JAXBElement<GetAvailableDatesResponseType> createGetAvailableDatesResponse(GetAvailableDatesResponseType value) {
        return new JAXBElement<GetAvailableDatesResponseType>(_GetAvailableDatesResponse_QNAME, GetAvailableDatesResponseType.class, null, value);
    }

}
