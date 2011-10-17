package geospace.control;

import geospace.entity.Bullet;
import geospace.entity.EntityModel;
import geospace.entity.Ship;
import geospace.entity.EntityManager;
import geospace.entity.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CurrentGameState {

    private final List<ShipInformation> ships;
    private final List<BulletInformation> bullets;
    private FieldInformation field;
    private long timestamp;
    private int timeLeft;

    public CurrentGameState() {
        this.ships = new LinkedList<ShipInformation>();
        this.bullets = new LinkedList<BulletInformation>();
    }

    public void updateState(int timeLeft) {

        this.ships.clear();
        this.bullets.clear();

        for (EntityModel entity : EntityManager.getInstance().getEntities()) {
            if (entity instanceof Ship) {
                this.ships.add(new ShipInformation((Ship) entity));
            } else if (entity instanceof Bullet) {
                this.bullets.add(new BulletInformation((Bullet) entity));
            } else if (entity instanceof Field) {
                this.field = new FieldInformation((Field) entity);
            }
        }

        this.timestamp = new Date().getTime();
    }

    public List<ShipInformation> getShips() {
        return ships;
    }

    public void setShips(List<ShipInformation> ships) {
        // Method disabled
    }

    public List<BulletInformation> getBullets() {
        return bullets;
    }

    public void setBullets(List<BulletInformation> bullets) {
        // Method disabled
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        // Method disabled
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        // Method disabled
    }

    public FieldInformation getField() {
        return field;
    }

    public void setField(FieldInformation field) {
        // Method disabled
    }

    public ShipInformation getShipInformation(String id) {
        for (ShipInformation shipInformation : this.ships) {
            if (shipInformation.getId().equals(id)) {
                return shipInformation;
            }
        }

        return null;
    }
}
