
package se.riv.population.residentmaster.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AvregistreringsorsakKodTYPE.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AvregistreringsorsakKodTYPE">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AV"/>
 *     &lt;enumeration value="GN"/>
 *     &lt;enumeration value="OO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AvregistreringsorsakKodTYPE")
@XmlEnum
public enum AvregistreringsorsakKodTYPE {

    AV,
    GN,
    OO;

    public String value() {
        return name();
    }

    public static AvregistreringsorsakKodTYPE fromValue(String v) {
        return valueOf(v);
    }

}
