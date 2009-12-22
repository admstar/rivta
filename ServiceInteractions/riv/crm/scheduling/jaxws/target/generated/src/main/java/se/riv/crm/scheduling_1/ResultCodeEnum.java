
package se.riv.crm.scheduling_1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultCodeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ResultCodeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CONFIRMED"/>
 *     &lt;enumeration value="BOOKING_UNKNOWN"/>
 *     &lt;enumeration value="SLOT_UNAVAILABLE"/>
 *     &lt;enumeration value="SLOT_UNAVAILABLE_FOR_REQUESTED_PERFORMER"/>
 *     &lt;enumeration value="ACTION_CREATE_NOT_ALLOWED"/>
 *     &lt;enumeration value="ACTION_UPDATE_NOT_ALLOWED"/>
 *     &lt;enumeration value="ACTION_CANCEL_NOT_ALLOWED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ResultCodeEnum", namespace = "urn:riv:crm:scheduling:1")
@XmlEnum
public enum ResultCodeEnum {

    CONFIRMED,
    BOOKING_UNKNOWN,
    SLOT_UNAVAILABLE,
    SLOT_UNAVAILABLE_FOR_REQUESTED_PERFORMER,
    ACTION_CREATE_NOT_ALLOWED,
    ACTION_UPDATE_NOT_ALLOWED,
    ACTION_CANCEL_NOT_ALLOWED;

    public String value() {
        return name();
    }

    public static ResultCodeEnum fromValue(String v) {
        return valueOf(v);
    }

}
