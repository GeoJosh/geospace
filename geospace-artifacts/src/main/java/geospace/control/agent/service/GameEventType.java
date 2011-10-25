
package geospace.control.agent.service;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for gameEventType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="gameEventType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DIED"/>
 *     &lt;enumeration value="SPAWNED"/>
 *     &lt;enumeration value="GAME_START"/>
 *     &lt;enumeration value="GAME_END"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "gameEventType")
@XmlEnum
public enum GameEventType {

    DIED,
    SPAWNED,
    GAME_START,
    GAME_END;

    public String value() {
        return name();
    }

    public static GameEventType fromValue(String v) {
        return valueOf(v);
    }

}
