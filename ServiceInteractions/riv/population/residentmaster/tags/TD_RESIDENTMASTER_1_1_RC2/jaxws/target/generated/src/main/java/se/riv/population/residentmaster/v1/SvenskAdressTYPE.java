
package se.riv.population.residentmaster.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SvenskAdressTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SvenskAdressTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LanKod" type="{urn:riv:population:residentmaster:1}Strang2TYPE" minOccurs="0"/>
 *         &lt;element name="KommunKod" type="{urn:riv:population:residentmaster:1}Strang2TYPE" minOccurs="0"/>
 *         &lt;element name="ForsamlingKod" type="{urn:riv:population:residentmaster:1}Strang2TYPE" minOccurs="0"/>
 *         &lt;element name="Fastighetsbeteckning" type="{urn:riv:population:residentmaster:1}Strang40TYPE" minOccurs="0"/>
 *         &lt;element name="CareOf" type="{urn:riv:population:residentmaster:1}Strang35TYPE" minOccurs="0"/>
 *         &lt;element name="Utdelningsadress1" type="{urn:riv:population:residentmaster:1}Strang35TYPE" minOccurs="0"/>
 *         &lt;element name="Utdelningsadress2" type="{urn:riv:population:residentmaster:1}Strang35TYPE" minOccurs="0"/>
 *         &lt;element name="PostNr" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="6"/>
 *               &lt;maxLength value="6"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Postort" type="{urn:riv:population:residentmaster:1}Strang27TYPE" minOccurs="0"/>
 *         &lt;element ref="{urn:riv:population:residentmaster:1.1}Folkbokforingsdatum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SvenskAdressTYPE", propOrder = {
    "lanKod",
    "kommunKod",
    "forsamlingKod",
    "fastighetsbeteckning",
    "careOf",
    "utdelningsadress1",
    "utdelningsadress2",
    "postNr",
    "postort",
    "folkbokforingsdatum"
})
public class SvenskAdressTYPE {

    @XmlElement(name = "LanKod")
    protected String lanKod;
    @XmlElement(name = "KommunKod")
    protected String kommunKod;
    @XmlElement(name = "ForsamlingKod")
    protected String forsamlingKod;
    @XmlElement(name = "Fastighetsbeteckning")
    protected String fastighetsbeteckning;
    @XmlElement(name = "CareOf")
    protected String careOf;
    @XmlElement(name = "Utdelningsadress1")
    protected String utdelningsadress1;
    @XmlElement(name = "Utdelningsadress2")
    protected String utdelningsadress2;
    @XmlElement(name = "PostNr")
    protected String postNr;
    @XmlElement(name = "Postort")
    protected String postort;
    @XmlElement(name = "Folkbokforingsdatum", namespace = "urn:riv:population:residentmaster:1.1")
    protected String folkbokforingsdatum;

    /**
     * Gets the value of the lanKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanKod() {
        return lanKod;
    }

    /**
     * Sets the value of the lanKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanKod(String value) {
        this.lanKod = value;
    }

    /**
     * Gets the value of the kommunKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKommunKod() {
        return kommunKod;
    }

    /**
     * Sets the value of the kommunKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKommunKod(String value) {
        this.kommunKod = value;
    }

    /**
     * Gets the value of the forsamlingKod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForsamlingKod() {
        return forsamlingKod;
    }

    /**
     * Sets the value of the forsamlingKod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForsamlingKod(String value) {
        this.forsamlingKod = value;
    }

    /**
     * Gets the value of the fastighetsbeteckning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFastighetsbeteckning() {
        return fastighetsbeteckning;
    }

    /**
     * Sets the value of the fastighetsbeteckning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFastighetsbeteckning(String value) {
        this.fastighetsbeteckning = value;
    }

    /**
     * Gets the value of the careOf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCareOf() {
        return careOf;
    }

    /**
     * Sets the value of the careOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCareOf(String value) {
        this.careOf = value;
    }

    /**
     * Gets the value of the utdelningsadress1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtdelningsadress1() {
        return utdelningsadress1;
    }

    /**
     * Sets the value of the utdelningsadress1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtdelningsadress1(String value) {
        this.utdelningsadress1 = value;
    }

    /**
     * Gets the value of the utdelningsadress2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUtdelningsadress2() {
        return utdelningsadress2;
    }

    /**
     * Sets the value of the utdelningsadress2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUtdelningsadress2(String value) {
        this.utdelningsadress2 = value;
    }

    /**
     * Gets the value of the postNr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostNr() {
        return postNr;
    }

    /**
     * Sets the value of the postNr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostNr(String value) {
        this.postNr = value;
    }

    /**
     * Gets the value of the postort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostort() {
        return postort;
    }

    /**
     * Sets the value of the postort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostort(String value) {
        this.postort = value;
    }

    /**
     * Gets the value of the folkbokforingsdatum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolkbokforingsdatum() {
        return folkbokforingsdatum;
    }

    /**
     * Sets the value of the folkbokforingsdatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolkbokforingsdatum(String value) {
        this.folkbokforingsdatum = value;
    }

}
