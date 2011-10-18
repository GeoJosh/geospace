
package geospace.control.agent.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for shipInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="shipInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="centerX" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="centerY" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="energy" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="heading" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shielding" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="velocityX" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="velocityY" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shipInformation", propOrder = {
    "centerX",
    "centerY",
    "energy",
    "heading",
    "id",
    "shielding",
    "velocityX",
    "velocityY"
})
public class ShipInformation {

    protected float centerX;
    protected float centerY;
    protected int energy;
    protected float heading;
    protected String id;
    protected boolean shielding;
    protected float velocityX;
    protected float velocityY;

    /**
     * Gets the value of the centerX property.
     * 
     */
    public float getCenterX() {
        return centerX;
    }

    /**
     * Sets the value of the centerX property.
     * 
     */
    public void setCenterX(float value) {
        this.centerX = value;
    }

    /**
     * Gets the value of the centerY property.
     * 
     */
    public float getCenterY() {
        return centerY;
    }

    /**
     * Sets the value of the centerY property.
     * 
     */
    public void setCenterY(float value) {
        this.centerY = value;
    }

    /**
     * Gets the value of the energy property.
     * 
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Sets the value of the energy property.
     * 
     */
    public void setEnergy(int value) {
        this.energy = value;
    }

    /**
     * Gets the value of the heading property.
     * 
     */
    public float getHeading() {
        return heading;
    }

    /**
     * Sets the value of the heading property.
     * 
     */
    public void setHeading(float value) {
        this.heading = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
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
     * Gets the value of the velocityX property.
     * 
     */
    public float getVelocityX() {
        return velocityX;
    }

    /**
     * Sets the value of the velocityX property.
     * 
     */
    public void setVelocityX(float value) {
        this.velocityX = value;
    }

    /**
     * Gets the value of the velocityY property.
     * 
     */
    public float getVelocityY() {
        return velocityY;
    }

    /**
     * Sets the value of the velocityY property.
     * 
     */
    public void setVelocityY(float value) {
        this.velocityY = value;
    }

}
