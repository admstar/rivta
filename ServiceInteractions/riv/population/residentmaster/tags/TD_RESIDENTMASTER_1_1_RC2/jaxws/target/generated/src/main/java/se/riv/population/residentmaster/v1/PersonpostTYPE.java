
package se.riv.population.residentmaster.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for PersonpostTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonpostTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PersonId" type="{urn:riv:population:residentmaster:1}PersonIdTYPE"/>
 *         &lt;element name="Kon" type="{urn:riv:population:residentmaster:1}KonTYPE" minOccurs="0"/>
 *         &lt;element name="Fodelsetid" type="{urn:riv:population:residentmaster:1}DT" minOccurs="0"/>
 *         &lt;element name="Avregistrering" type="{urn:riv:population:residentmaster:1}AvregistreringTYPE" minOccurs="0"/>
 *         &lt;element name="Namn" type="{urn:riv:population:residentmaster:1}NamnTYPE" minOccurs="0"/>
 *         &lt;element name="Folkbokforingsadress" type="{urn:riv:population:residentmaster:1}SvenskAdressTYPE" minOccurs="0"/>
 *         &lt;element name="SarskildPostadress" type="{urn:riv:population:residentmaster:1}SvenskAdressTYPE" minOccurs="0"/>
 *         &lt;element ref="{urn:riv:population:residentmaster:1.1}HanvisningsPersonNr" minOccurs="0"/>
 *         &lt;element ref="{urn:riv:population:residentmaster:1.1}Utlandsadress" minOccurs="0"/>
 *         &lt;element ref="{urn:riv:population:residentmaster:1.1}Civilstand" minOccurs="0"/>
 *         &lt;element ref="{urn:riv:population:residentmaster:1.1}Fodelse" minOccurs="0"/>
 *         &lt;element ref="{urn:riv:population:residentmaster:1.1}Invandring" minOccurs="0"/>
 *         &lt;element ref="{urn:riv:population:residentmaster:1.1}Relationer"/>
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
@XmlType(name = "PersonpostTYPE", propOrder = {
    "personId",
    "kon",
    "fodelsetid",
    "avregistrering",
    "namn",
    "folkbokforingsadress",
    "sarskildPostadress",
    "hanvisningsPersonNr",
    "utlandsadress",
    "civilstand",
    "fodelse",
    "invandring",
    "relationer",
    "any"
})
public class PersonpostTYPE {

    @XmlElement(name = "PersonId", required = true)
    protected String personId;
    @XmlElement(name = "Kon")
    protected KonTYPE kon;
    @XmlElement(name = "Fodelsetid")
    protected String fodelsetid;
    @XmlElement(name = "Avregistrering")
    protected AvregistreringTYPE avregistrering;
    @XmlElement(name = "Namn")
    protected NamnTYPE namn;
    @XmlElement(name = "Folkbokforingsadress")
    protected SvenskAdressTYPE folkbokforingsadress;
    @XmlElement(name = "SarskildPostadress")
    protected SvenskAdressTYPE sarskildPostadress;
    @XmlElement(name = "HanvisningsPersonNr", namespace = "urn:riv:population:residentmaster:1.1")
    protected String hanvisningsPersonNr;
    @XmlElement(name = "Utlandsadress", namespace = "urn:riv:population:residentmaster:1.1")
    protected UtlandsadressTYPE utlandsadress;
    @XmlElement(name = "Civilstand", namespace = "urn:riv:population:residentmaster:1.1")
    protected CivilstandTYPE civilstand;
    @XmlElement(name = "Fodelse", namespace = "urn:riv:population:residentmaster:1.1")
    protected FodelseTYPE fodelse;
    @XmlElement(name = "Invandring", namespace = "urn:riv:population:residentmaster:1.1")
    protected InvandringTYPE invandring;
    @XmlElement(name = "Relationer", namespace = "urn:riv:population:residentmaster:1.1", required = true)
    protected RelationerTYPE relationer;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the personId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Sets the value of the personId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonId(String value) {
        this.personId = value;
    }

    /**
     * Gets the value of the kon property.
     * 
     * @return
     *     possible object is
     *     {@link KonTYPE }
     *     
     */
    public KonTYPE getKon() {
        return kon;
    }

    /**
     * Sets the value of the kon property.
     * 
     * @param value
     *     allowed object is
     *     {@link KonTYPE }
     *     
     */
    public void setKon(KonTYPE value) {
        this.kon = value;
    }

    /**
     * Gets the value of the fodelsetid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFodelsetid() {
        return fodelsetid;
    }

    /**
     * Sets the value of the fodelsetid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFodelsetid(String value) {
        this.fodelsetid = value;
    }

    /**
     * Gets the value of the avregistrering property.
     * 
     * @return
     *     possible object is
     *     {@link AvregistreringTYPE }
     *     
     */
    public AvregistreringTYPE getAvregistrering() {
        return avregistrering;
    }

    /**
     * Sets the value of the avregistrering property.
     * 
     * @param value
     *     allowed object is
     *     {@link AvregistreringTYPE }
     *     
     */
    public void setAvregistrering(AvregistreringTYPE value) {
        this.avregistrering = value;
    }

    /**
     * Gets the value of the namn property.
     * 
     * @return
     *     possible object is
     *     {@link NamnTYPE }
     *     
     */
    public NamnTYPE getNamn() {
        return namn;
    }

    /**
     * Sets the value of the namn property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamnTYPE }
     *     
     */
    public void setNamn(NamnTYPE value) {
        this.namn = value;
    }

    /**
     * Gets the value of the folkbokforingsadress property.
     * 
     * @return
     *     possible object is
     *     {@link SvenskAdressTYPE }
     *     
     */
    public SvenskAdressTYPE getFolkbokforingsadress() {
        return folkbokforingsadress;
    }

    /**
     * Sets the value of the folkbokforingsadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvenskAdressTYPE }
     *     
     */
    public void setFolkbokforingsadress(SvenskAdressTYPE value) {
        this.folkbokforingsadress = value;
    }

    /**
     * Gets the value of the sarskildPostadress property.
     * 
     * @return
     *     possible object is
     *     {@link SvenskAdressTYPE }
     *     
     */
    public SvenskAdressTYPE getSarskildPostadress() {
        return sarskildPostadress;
    }

    /**
     * Sets the value of the sarskildPostadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link SvenskAdressTYPE }
     *     
     */
    public void setSarskildPostadress(SvenskAdressTYPE value) {
        this.sarskildPostadress = value;
    }

    /**
     * Gets the value of the hanvisningsPersonNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHanvisningsPersonNr() {
        return hanvisningsPersonNr;
    }

    /**
     * Sets the value of the hanvisningsPersonNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHanvisningsPersonNr(String value) {
        this.hanvisningsPersonNr = value;
    }

    /**
     * Gets the value of the utlandsadress property.
     * 
     * @return
     *     possible object is
     *     {@link UtlandsadressTYPE }
     *     
     */
    public UtlandsadressTYPE getUtlandsadress() {
        return utlandsadress;
    }

    /**
     * Sets the value of the utlandsadress property.
     * 
     * @param value
     *     allowed object is
     *     {@link UtlandsadressTYPE }
     *     
     */
    public void setUtlandsadress(UtlandsadressTYPE value) {
        this.utlandsadress = value;
    }

    /**
     * Gets the value of the civilstand property.
     * 
     * @return
     *     possible object is
     *     {@link CivilstandTYPE }
     *     
     */
    public CivilstandTYPE getCivilstand() {
        return civilstand;
    }

    /**
     * Sets the value of the civilstand property.
     * 
     * @param value
     *     allowed object is
     *     {@link CivilstandTYPE }
     *     
     */
    public void setCivilstand(CivilstandTYPE value) {
        this.civilstand = value;
    }

    /**
     * Gets the value of the fodelse property.
     * 
     * @return
     *     possible object is
     *     {@link FodelseTYPE }
     *     
     */
    public FodelseTYPE getFodelse() {
        return fodelse;
    }

    /**
     * Sets the value of the fodelse property.
     * 
     * @param value
     *     allowed object is
     *     {@link FodelseTYPE }
     *     
     */
    public void setFodelse(FodelseTYPE value) {
        this.fodelse = value;
    }

    /**
     * Gets the value of the invandring property.
     * 
     * @return
     *     possible object is
     *     {@link InvandringTYPE }
     *     
     */
    public InvandringTYPE getInvandring() {
        return invandring;
    }

    /**
     * Sets the value of the invandring property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvandringTYPE }
     *     
     */
    public void setInvandring(InvandringTYPE value) {
        this.invandring = value;
    }

    /**
     * Gets the value of the relationer property.
     * 
     * @return
     *     possible object is
     *     {@link RelationerTYPE }
     *     
     */
    public RelationerTYPE getRelationer() {
        return relationer;
    }

    /**
     * Sets the value of the relationer property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationerTYPE }
     *     
     */
    public void setRelationer(RelationerTYPE value) {
        this.relationer = value;
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
