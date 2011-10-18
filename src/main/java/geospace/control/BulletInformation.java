package geospace.control;

import geospace.entity.Bullet;

public class BulletInformation {
    private String id;
    private float centerX;
    private float centerY;
    private float velocityX;
    private float velocityY;

    public BulletInformation() {
    }
    
    public BulletInformation(Bullet bullet) {
        this.id = bullet.getId();
        this.centerX = bullet.getCenter().getX();
        this.centerY = bullet.getCenter().getY();
        this.velocityX = bullet.getVelocity().x;
        this.velocityY = bullet.getVelocity().y;
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
