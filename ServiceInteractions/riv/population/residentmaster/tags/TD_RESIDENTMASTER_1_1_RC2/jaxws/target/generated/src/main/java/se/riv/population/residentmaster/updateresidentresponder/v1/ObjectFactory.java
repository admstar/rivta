
package se.riv.population.residentmaster.updateresidentresponder.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.riv.population.residentmaster.updateresidentresponder.v1 package. 
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

    private final static QName _UpdateResidentResponse_QNAME = new QName("urn:riv:population:residentmaster:UpdateResidentResponder:1", "UpdateResidentResponse");
    private final static QName _UpdateResident_QNAME = new QName("urn:riv:population:residentmaster:UpdateResidentResponder:1", "UpdateResident");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.riv.population.residentmaster.updateresidentresponder.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateResidentType }
     * 
     */
    public UpdateResidentType createUpdateResidentType() {
        return new UpdateResidentType();
    }

    /**
     * Create an instance of {@link UpdateResidentResponseType }
     * 
     */
    public UpdateResidentResponseType createUpdateResidentResponseType() {
        return new UpdateResidentResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResidentResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:UpdateResidentResponder:1", name = "UpdateResidentResponse")
    public JAXBElement<UpdateResidentResponseType> createUpdateResidentResponse(UpdateResidentResponseType value) {
        return new JAXBElement<UpdateResidentResponseType>(_UpdateResidentResponse_QNAME, UpdateResidentResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResidentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:UpdateResidentResponder:1", name = "UpdateResident")
    public JAXBElement<UpdateResidentType> createUpdateResident(UpdateResidentType value) {
        return new JAXBElement<UpdateResidentType>(_UpdateResident_QNAME, UpdateResidentType.class, null, value);
    }

}
