
package geospace.control.agent.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for setControllerState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setControllerState">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="controllerState" type="{http://service.agent.control.geospace/}controllerState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setControllerState", propOrder = {
    "authToken",
    "controllerState"
})
public class SetControllerState {

    protected String authToken;
    protected ControllerState controllerState;

    /**
     * Gets the value of the authToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the value of the authToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthToken(String value) {
        this.authToken = value;
    }

    /**
     * Gets the value of the controllerState property.
     * 
     * @return
     *     possible object is
     *     {@link ControllerState }
     *     
     */
    public ControllerState getControllerState() {
        return controllerState;
    }

    /**
     * Sets the value of the controllerState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ControllerState }
     *     
     */
    public void setControllerState(ControllerState value) {
        this.controllerState = value;
    }

}
