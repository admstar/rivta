
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
 * <p>Java class for RelationerTYPE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelationerTYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Relation" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RelationId" type="{urn:riv:population:residentmaster:1}RelationPersonIdTYPE"/>
 *                   &lt;element name="Relationstyp" type="{urn:riv:population:residentmaster:1}RelationstypTYPE"/>
 *                   &lt;element name="RelationFromdatum" type="{urn:riv:population:residentmaster:1}OfullstandigtDatumTYPE" minOccurs="0"/>
 *                   &lt;element name="RelationTomdatum" type="{urn:riv:population:residentmaster:1}OfullstandigtDatumTYPE" minOccurs="0"/>
 *                   &lt;element name="Namn" type="{urn:riv:population:residentmaster:1}NamnTYPE" minOccurs="0"/>
 *                   &lt;element name="Avregistrering" type="{urn:riv:population:residentmaster:1}AvregistreringTYPE" minOccurs="0"/>
 *                   &lt;element name="Status" type="{urn:riv:population:residentmaster:1}RelationStatusTYPE" minOccurs="0"/>
 *                   &lt;any/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelationerTYPE", propOrder = {
    "relation"
})
public class RelationerTYPE {

    @XmlElement(name = "Relation")
    protected List<RelationerTYPE.Relation> relation;

    /**
     * Gets the value of the relation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelationerTYPE.Relation }
     * 
     * 
     */
    public List<RelationerTYPE.Relation> getRelation() {
        if (relation == null) {
            relation = new ArrayList<RelationerTYPE.Relation>();
        }
        return this.relation;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RelationId" type="{urn:riv:population:residentmaster:1}RelationPersonIdTYPE"/>
     *         &lt;element name="Relationstyp" type="{urn:riv:population:residentmaster:1}RelationstypTYPE"/>
     *         &lt;element name="RelationFromdatum" type="{urn:riv:population:residentmaster:1}OfullstandigtDatumTYPE" minOccurs="0"/>
     *         &lt;element name="RelationTomdatum" type="{urn:riv:population:residentmaster:1}OfullstandigtDatumTYPE" minOccurs="0"/>
     *         &lt;element name="Namn" type="{urn:riv:population:residentmaster:1}NamnTYPE" minOccurs="0"/>
     *         &lt;element name="Avregistrering" type="{urn:riv:population:residentmaster:1}AvregistreringTYPE" minOccurs="0"/>
     *         &lt;element name="Status" type="{urn:riv:population:residentmaster:1}RelationStatusTYPE" minOccurs="0"/>
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
    @XmlType(name = "", propOrder = {
        "relationId",
        "relationstyp",
        "relationFromdatum",
        "relationTomdatum",
        "namn",
        "avregistrering",
        "status",
        "any"
    })
    public static class Relation {

        @XmlElement(name = "RelationId", required = true)
        protected RelationPersonIdTYPE relationId;
        @XmlElement(name = "Relationstyp", required = true)
        protected RelationstypTYPE relationstyp;
        @XmlElement(name = "RelationFromdatum")
        protected String relationFromdatum;
        @XmlElement(name = "RelationTomdatum")
        protected String relationTomdatum;
        @XmlElement(name = "Namn")
        protected NamnTYPE namn;
        @XmlElement(name = "Avregistrering")
        protected AvregistreringTYPE avregistrering;
        @XmlElement(name = "Status")
        protected RelationStatusTYPE status;
        @XmlAnyElement(lax = true)
        protected List<Object> any;

        /**
         * Gets the value of the relationId property.
         * 
         * @return
         *     possible object is
         *     {@link RelationPersonIdTYPE }
         *     
         */
        public RelationPersonIdTYPE getRelationId() {
            return relationId;
        }

        /**
         * Sets the value of the relationId property.
         * 
         * @param value
         *     allowed object is
         *     {@link RelationPersonIdTYPE }
         *     
         */
        public void setRelationId(RelationPersonIdTYPE value) {
            this.relationId = value;
        }

        /**
         * Gets the value of the relationstyp property.
         * 
         * @return
         *     possible object is
         *     {@link RelationstypTYPE }
         *     
         */
        public RelationstypTYPE getRelationstyp() {
            return relationstyp;
        }

        /**
         * Sets the value of the relationstyp property.
         * 
         * @param value
         *     allowed object is
         *     {@link RelationstypTYPE }
         *     
         */
        public void setRelationstyp(RelationstypTYPE value) {
            this.relationstyp = value;
        }

        /**
         * Gets the value of the relationFromdatum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRelationFromdatum() {
            return relationFromdatum;
        }

        /**
         * Sets the value of the relationFromdatum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRelationFromdatum(String value) {
            this.relationFromdatum = value;
        }

        /**
         * Gets the value of the relationTomdatum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRelationTomdatum() {
            return relationTomdatum;
        }

        /**
         * Sets the value of the relationTomdatum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRelationTomdatum(String value) {
            this.relationTomdatum = value;
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
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link RelationStatusTYPE }
         *     
         */
        public RelationStatusTYPE getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link RelationStatusTYPE }
         *     
         */
        public void setStatus(RelationStatusTYPE value) {
            this.status = value;
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

}
