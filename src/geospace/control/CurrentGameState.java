package geospace.control;

import geospace.entity.Bullet;
import geospace.entity.EntityModel;
import geospace.entity.Ship;
import geospace.entity.EntityManager;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CurrentGameState {
    private List<ShipInformation> ships;
    private List<BulletInformation> bullets;
    private long timestamp;

    public CurrentGameState() {
        this.ships = new LinkedList<ShipInformation>();
        this.bullets = new LinkedList<BulletInformation>();
    }

    public void updateState() {
        this.ships.clear();
        this.bullets.clear();
        for(EntityModel entity : EntityManager.getInstance().getEntities()) {
            if(entity instanceof Ship) {
                this.ships.add(new ShipInformation((Ship)entity));
            }
            else if(entity instanceof Bullet) {
                this.bullets.add(new BulletInformation((Bullet)entity));
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
}
