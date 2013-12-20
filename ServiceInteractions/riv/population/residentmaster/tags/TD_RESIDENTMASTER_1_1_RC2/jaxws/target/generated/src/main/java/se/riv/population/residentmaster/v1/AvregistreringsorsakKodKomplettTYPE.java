
package se.riv.population.residentmaster.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AvregistreringsorsakKodKomplettTYPE.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AvregistreringsorsakKodKomplettTYPE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AV"/>
 *     &lt;enumeration value="UV"/>
 *     &lt;enumeration value="GN"/>
 *     &lt;enumeration value="AN"/>
 *     &lt;enumeration value="AS"/>
 *     &lt;enumeration value="GS"/>
 *     &lt;enumeration value="OB"/>
 *     &lt;enumeration value="TA"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AvregistreringsorsakKodKomplettTYPE")
@XmlEnum
public enum AvregistreringsorsakKodKomplettTYPE {

    AV,
    UV,
    GN,
    AN,
    AS,
    GS,
    OB,
    TA;

    public String value() {
        return name();
    }

    public static AvregistreringsorsakKodKomplettTYPE fromValue(String v) {
        return valueOf(v);
    }

}
