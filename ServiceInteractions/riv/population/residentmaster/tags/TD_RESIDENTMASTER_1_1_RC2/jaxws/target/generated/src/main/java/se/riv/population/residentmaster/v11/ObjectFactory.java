
package se.riv.population.residentmaster.v11;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import se.riv.population.residentmaster.v1.AvregistreringsorsakKodKomplettTYPE;
import se.riv.population.residentmaster.v1.CivilstandTYPE;
import se.riv.population.residentmaster.v1.FodelseTYPE;
import se.riv.population.residentmaster.v1.InvandringTYPE;
import se.riv.population.residentmaster.v1.RelationerTYPE;
import se.riv.population.residentmaster.v1.UtlandsadressTYPE;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the se.riv.population.residentmaster.v11 package. 
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

    private final static QName _Utlandsadress_QNAME = new QName("urn:riv:population:residentmaster:1.1", "Utlandsadress");
    private final static QName _AvregistreringsorsakKodKomplett_QNAME = new QName("urn:riv:population:residentmaster:1.1", "AvregistreringsorsakKodKomplett");
    private final static QName _HanvisningsPersonNr_QNAME = new QName("urn:riv:population:residentmaster:1.1", "HanvisningsPersonNr");
    private final static QName _Civilstand_QNAME = new QName("urn:riv:population:residentmaster:1.1", "Civilstand");
    private final static QName _Fodelse_QNAME = new QName("urn:riv:population:residentmaster:1.1", "Fodelse");
    private final static QName _Folkbokforingsdatum_QNAME = new QName("urn:riv:population:residentmaster:1.1", "Folkbokforingsdatum");
    private final static QName _Invandring_QNAME = new QName("urn:riv:population:residentmaster:1.1", "Invandring");
    private final static QName _Relationer_QNAME = new QName("urn:riv:population:residentmaster:1.1", "Relationer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: se.riv.population.residentmaster.v11
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UtlandsadressTYPE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:1.1", name = "Utlandsadress")
    public JAXBElement<UtlandsadressTYPE> createUtlandsadress(UtlandsadressTYPE value) {
        return new JAXBElement<UtlandsadressTYPE>(_Utlandsadress_QNAME, UtlandsadressTYPE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AvregistreringsorsakKodKomplettTYPE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:1.1", name = "AvregistreringsorsakKodKomplett")
    public JAXBElement<AvregistreringsorsakKodKomplettTYPE> createAvregistreringsorsakKodKomplett(AvregistreringsorsakKodKomplettTYPE value) {
        return new JAXBElement<AvregistreringsorsakKodKomplettTYPE>(_AvregistreringsorsakKodKomplett_QNAME, AvregistreringsorsakKodKomplettTYPE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:1.1", name = "HanvisningsPersonNr")
    public JAXBElement<String> createHanvisningsPersonNr(String value) {
        return new JAXBElement<String>(_HanvisningsPersonNr_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CivilstandTYPE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:1.1", name = "Civilstand")
    public JAXBElement<CivilstandTYPE> createCivilstand(CivilstandTYPE value) {
        return new JAXBElement<CivilstandTYPE>(_Civilstand_QNAME, CivilstandTYPE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FodelseTYPE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:1.1", name = "Fodelse")
    public JAXBElement<FodelseTYPE> createFodelse(FodelseTYPE value) {
        return new JAXBElement<FodelseTYPE>(_Fodelse_QNAME, FodelseTYPE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:1.1", name = "Folkbokforingsdatum")
    public JAXBElement<String> createFolkbokforingsdatum(String value) {
        return new JAXBElement<String>(_Folkbokforingsdatum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvandringTYPE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:1.1", name = "Invandring")
    public JAXBElement<InvandringTYPE> createInvandring(InvandringTYPE value) {
        return new JAXBElement<InvandringTYPE>(_Invandring_QNAME, InvandringTYPE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelationerTYPE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:riv:population:residentmaster:1.1", name = "Relationer")
    public JAXBElement<RelationerTYPE> createRelationer(RelationerTYPE value) {
        return new JAXBElement<RelationerTYPE>(_Relationer_QNAME, RelationerTYPE.class, null, value);
    }

}
