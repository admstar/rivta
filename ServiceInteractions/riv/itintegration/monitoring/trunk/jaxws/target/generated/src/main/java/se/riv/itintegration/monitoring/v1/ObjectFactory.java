
package se.riv.itintegration.monitoring.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.riv.itintegration.monitoring.v1 package. 
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

    private final static QName _PingForConfiguration_QNAME = new QName("urn:riv:itintegration:monitoring:PingForConfigurationResponder:1", "PingForConfiguration");
    private final static QName _PingForConfigurationResponse_QNAME = new QName("urn:riv:itintegration:monitoring:PingForConfigurationResponder:1", "PingForConfigurationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.riv.itintegration.monitoring.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConfigurationType }
     * 
     */
    public ConfigurationType createConfigurationType() {
        return new ConfigurationType();
    }

    /**
     * Create an instance of {@link PingForConfigurationType }
     * 
     */
    public PingForConfigurationType createPingForConfigurationType() {
        return new PingForConfigurationType();
    }

    /**
     * Create an instance of {@link PingForConfigurationResponseType }
     * 
     */
    public PingForConfigurationResponseType createPingForConfigurationResponseType() {
        return new PingForConfigurationResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PingForConfigurationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:itintegration:monitoring:PingForConfigurationResponder:1", name = "PingForConfiguration")
    public JAXBElement<PingForConfigurationType> createPingForConfiguration(PingForConfigurationType value) {
        return new JAXBElement<PingForConfigurationType>(_PingForConfiguration_QNAME, PingForConfigurationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PingForConfigurationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:itintegration:monitoring:PingForConfigurationResponder:1", name = "PingForConfigurationResponse")
    public JAXBElement<PingForConfigurationResponseType> createPingForConfigurationResponse(PingForConfigurationResponseType value) {
        return new JAXBElement<PingForConfigurationResponseType>(_PingForConfigurationResponse_QNAME, PingForConfigurationResponseType.class, null, value);
    }

}
