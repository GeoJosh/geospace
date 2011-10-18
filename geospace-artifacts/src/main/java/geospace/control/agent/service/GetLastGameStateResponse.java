
package geospace.control.agent.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLastGameStateResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLastGameStateResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastGameState" type="{http://service.agent.control.geospace/}currentGameState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLastGameStateResponse", propOrder = {
    "lastGameState"
})
public class GetLastGameStateResponse {

    protected CurrentGameState lastGameState;

    /**
     * Gets the value of the lastGameState property.
     * 
     * @return
     *     possible object is
     *     {@link CurrentGameState }
     *     
     */
    public CurrentGameState getLastGameState() {
        return lastGameState;
    }

    /**
     * Sets the value of the lastGameState property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrentGameState }
     *     
     */
    public void setLastGameState(CurrentGameState value) {
        this.lastGameState = value;
    }

}
