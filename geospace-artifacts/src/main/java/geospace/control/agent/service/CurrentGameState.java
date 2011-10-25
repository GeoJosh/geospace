
package geospace.control.agent.service;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for currentGameState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="currentGameState">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bullets" type="{http://service.agent.control.geospace/}bulletInformation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="field" type="{http://service.agent.control.geospace/}fieldInformation" minOccurs="0"/>
 *         &lt;element name="gameEvents" type="{http://service.agent.control.geospace/}gameEvent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ships" type="{http://service.agent.control.geospace/}shipInformation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="timeLeft" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "currentGameState", propOrder = {
    "bullets",
    "field",
    "gameEvents",
    "ships",
    "timeLeft",
    "timestamp"
})
public class CurrentGameState {

    @XmlElement(nillable = true)
    protected List<BulletInformation> bullets;
    protected FieldInformation field;
    @XmlElement(nillable = true)
    protected List<GameEvent> gameEvents;
    @XmlElement(nillable = true)
    protected List<ShipInformation> ships;
    protected int timeLeft;
    protected long timestamp;

    /**
     * Gets the value of the bullets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bullets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBullets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BulletInformation }
     * 
     * 
     */
    public List<BulletInformation> getBullets() {
        if (bullets == null) {
            bullets = new ArrayList<BulletInformation>();
        }
        return this.bullets;
    }

    /**
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link FieldInformation }
     *     
     */
    public FieldInformation getField() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldInformation }
     *     
     */
    public void setField(FieldInformation value) {
        this.field = value;
    }

    /**
     * Gets the value of the gameEvents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gameEvents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGameEvents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GameEvent }
     * 
     * 
     */
    public List<GameEvent> getGameEvents() {
        if (gameEvents == null) {
            gameEvents = new ArrayList<GameEvent>();
        }
        return this.gameEvents;
    }

    /**
     * Gets the value of the ships property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ships property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShips().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShipInformation }
     * 
     * 
     */
    public List<ShipInformation> getShips() {
        if (ships == null) {
            ships = new ArrayList<ShipInformation>();
        }
        return this.ships;
    }

    /**
     * Gets the value of the timeLeft property.
     * 
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * Sets the value of the timeLeft property.
     * 
     */
    public void setTimeLeft(int value) {
        this.timeLeft = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     */
    public void setTimestamp(long value) {
        this.timestamp = value;
    }

}
