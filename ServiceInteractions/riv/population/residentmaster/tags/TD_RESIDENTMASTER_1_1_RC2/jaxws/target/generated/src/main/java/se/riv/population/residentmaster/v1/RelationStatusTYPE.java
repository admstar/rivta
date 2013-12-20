
package se.riv.population.residentmaster.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelationStatusTYPE.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RelationStatusTYPE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NY"/>
 *     &lt;enumeration value="PB"/>
 *     &lt;enumeration value="RD"/>
 *     &lt;enumeration value="AS"/>
 *     &lt;enumeration value="AV"/>
 *     &lt;enumeration value="IV"/>
 *     &lt;enumeration value="AN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RelationStatusTYPE")
@XmlEnum
public enum RelationStatusTYPE {

    NY,
    PB,
    RD,
    AS,
    AV,
    IV,
    AN;

    public String value() {
        return name();
    }

    public static RelationStatusTYPE fromValue(String v) {
        return valueOf(v);
    }

}
