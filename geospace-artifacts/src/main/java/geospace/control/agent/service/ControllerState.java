
package geospace.control.agent.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for controllerState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="controllerState">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="firing" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="shielding" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="thrusting" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="turbo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="turningPort" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="turningStarboard" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "controllerState", propOrder = {
    "firing",
    "shielding",
    "thrusting",
    "turbo",
    "turningPort",
    "turningStarboard"
})
public class ControllerState {

    protected boolean firing;
    protected boolean shielding;
    protected boolean thrusting;
    protected boolean turbo;
    protected boolean turningPort;
    protected boolean turningStarboard;

    /**
     * Gets the value of the firing property.
     * 
     */
    public boolean isFiring() {
        return firing;
    }

    /**
     * Sets the value of the firing property.
     * 
     */
    public void setFiring(boolean value) {
        this.firing = value;
    }

    /**
     * Gets the value of the shielding property.
     * 
     */
    public boolean isShielding() {
        return shielding;
    }

    /**
     * Sets the value of the shielding property.
     * 
     */
    public void setShielding(boolean value) {
        this.shielding = value;
    }

    /**
     * Gets the value of the thrusting property.
     * 
     */
    public boolean isThrusting() {
        return thrusting;
    }

    /**
     * Sets the value of the thrusting property.
     * 
     */
    public void setThrusting(boolean value) {
        this.thrusting = value;
    }

    /**
     * Gets the value of the turbo property.
     * 
     */
    public boolean isTurbo() {
        return turbo;
    }

    /**
     * Sets the value of the turbo property.
     * 
     */
    public void setTurbo(boolean value) {
        this.turbo = value;
    }

    /**
     * Gets the value of the turningPort property.
     * 
     */
    public boolean isTurningPort() {
        return turningPort;
    }

    /**
     * Sets the value of the turningPort property.
     * 
     */
    public void setTurningPort(boolean value) {
        this.turningPort = value;
    }

    /**
     * Gets the value of the turningStarboard property.
     * 
     */
    public boolean isTurningStarboard() {
        return turningStarboard;
    }

    /**
     * Sets the value of the turningStarboard property.
     * 
     */
    public void setTurningStarboard(boolean value) {
        this.turningStarboard = value;
    }

}
