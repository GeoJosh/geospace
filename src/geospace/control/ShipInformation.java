package geospace.control;

import geospace.entity.Ship;

public class ShipInformation {
    private String id;
    private float centerX;
    private float centerY;
    private float heading;
    private float velocityX;
    private float velocityY;
    private boolean shielding;
    private int energy;

    public ShipInformation() {
        
    }
    
    public ShipInformation(Ship ship) {
        this.id = ship.getId();
        this.centerX = ship.getCenter().getX();
        this.centerY = ship.getCenter().getY();
        this.heading = ship.getHeading();
        this.velocityX = ship.getVelocity().x;
        this.velocityY = ship.getVelocity().y;
        this.shielding = ship.isShielding();
        this.energy = ship.getEnergy();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        // Method disabled
    }
    
    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        // Method disabled
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        // Method disabled
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        // Method disabled
    }

    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        // Method disabled
    }

    public boolean isShielding() {
        return shielding;
    }

    public void setShielding(boolean shielding) {
        // Method disabled
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        // Method disabled
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        // Method disabled
    }
}
