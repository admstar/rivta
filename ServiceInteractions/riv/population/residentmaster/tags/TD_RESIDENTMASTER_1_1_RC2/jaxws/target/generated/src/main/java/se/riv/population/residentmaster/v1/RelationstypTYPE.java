
package se.riv.population.residentmaster.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelationstypTYPE.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RelationstypTYPE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="B"/>
 *     &lt;enumeration value="MO"/>
 *     &lt;enumeration value="FA"/>
 *     &lt;enumeration value="F"/>
 *     &lt;enumeration value="V"/>
 *     &lt;enumeration value="VF"/>
 *     &lt;enumeration value="M"/>
 *     &lt;enumeration value="P"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RelationstypTYPE")
@XmlEnum
public enum RelationstypTYPE {

    B,
    MO,
    FA,
    F,
    V,
    VF,
    M,
    P;

    public String value() {
        return name();
    }

    public static RelationstypTYPE fromValue(String v) {
        return valueOf(v);
    }

}
