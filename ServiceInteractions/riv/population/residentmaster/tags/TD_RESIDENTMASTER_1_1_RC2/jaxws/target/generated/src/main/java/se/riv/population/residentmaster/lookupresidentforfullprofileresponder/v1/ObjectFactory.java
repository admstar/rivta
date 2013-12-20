
package se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1 package. 
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

    private final static QName _LookupResidentForFullProfile_QNAME = new QName("urn:riv:population:residentmaster:LookupResidentForFullProfileResponder:1", "LookupResidentForFullProfile");
    private final static QName _LookupResidentForFullProfileResponse_QNAME = new QName("urn:riv:population:residentmaster:LookupResidentForFullProfileResponder:1", "LookupResidentForFullProfileResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.riv.population.residentmaster.lookupresidentforfullprofileresponder.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LookupResidentForFullProfileResponseType }
     * 
     */
    public LookupResidentForFullProfileResponseType createLookupResidentForFullProfileResponseType() {
        return new LookupResidentForFullProfileResponseType();
    }

    /**
     * Create an instance of {@link LookUpSpecificationType }
     * 
     */
    public LookUpSpecificationType createLookUpSpecificationType() {
        return new LookUpSpecificationType();
    }

    /**
     * Create an instance of {@link LookupResidentForFullProfileType }
     * 
     */
    public LookupResidentForFullProfileType createLookupResidentForFullProfileType() {
        return new LookupResidentForFullProfileType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupResidentForFullProfileType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:LookupResidentForFullProfileResponder:1", name = "LookupResidentForFullProfile")
    public JAXBElement<LookupResidentForFullProfileType> createLookupResidentForFullProfile(LookupResidentForFullProfileType value) {
        return new JAXBElement<LookupResidentForFullProfileType>(_LookupResidentForFullProfile_QNAME, LookupResidentForFullProfileType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupResidentForFullProfileResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:LookupResidentForFullProfileResponder:1", name = "LookupResidentForFullProfileResponse")
    public JAXBElement<LookupResidentForFullProfileResponseType> createLookupResidentForFullProfileResponse(LookupResidentForFullProfileResponseType value) {
        return new JAXBElement<LookupResidentForFullProfileResponseType>(_LookupResidentForFullProfileResponse_QNAME, LookupResidentForFullProfileResponseType.class, null, value);
    }

}
