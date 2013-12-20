
package se.riv.population.residentmaster.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CivilstandKodTYPE.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CivilstandKodTYPE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OG"/>
 *     &lt;enumeration value="G"/>
 *     &lt;enumeration value="A"/>
 *     &lt;enumeration value="S"/>
 *     &lt;enumeration value="RP"/>
 *     &lt;enumeration value="SP"/>
 *     &lt;enumeration value="EP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CivilstandKodTYPE")
@XmlEnum
public enum CivilstandKodTYPE {

    OG,
    G,
    A,
    S,
    RP,
    SP,
    EP;

    public String value() {
        return name();
    }

    public static CivilstandKodTYPE fromValue(String v) {
        return valueOf(v);
    }

}
