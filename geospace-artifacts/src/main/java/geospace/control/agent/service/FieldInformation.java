
package geospace.control.agent.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fieldInformation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fieldInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="maxX" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="maxY" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="minX" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="minY" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fieldInformation", propOrder = {
    "maxX",
    "maxY",
    "minX",
    "minY"
})
public class FieldInformation {

    protected float maxX;
    protected float maxY;
    protected float minX;
    protected float minY;

    /**
     * Gets the value of the maxX property.
     * 
     */
    public float getMaxX() {
        return maxX;
    }

    /**
     * Sets the value of the maxX property.
     * 
     */
    public void setMaxX(float value) {
        this.maxX = value;
    }

    /**
     * Gets the value of the maxY property.
     * 
     */
    public float getMaxY() {
        return maxY;
    }

    /**
     * Sets the value of the maxY property.
     * 
     */
    public void setMaxY(float value) {
        this.maxY = value;
    }

    /**
     * Gets the value of the minX property.
     * 
     */
    public float getMinX() {
        return minX;
    }

    /**
     * Sets the value of the minX property.
     * 
     */
    public void setMinX(float value) {
        this.minX = value;
    }

    /**
     * Gets the value of the minY property.
     * 
     */
    public float getMinY() {
        return minY;
    }

    /**
     * Sets the value of the minY property.
     * 
     */
    public void setMinY(float value) {
        this.minY = value;
    }

}
