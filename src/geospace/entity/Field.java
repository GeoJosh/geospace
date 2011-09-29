package geospace.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

public class Field extends CollidableEntityModel {

    private float fieldWidth;
    private float fieldHeight;

    public Field(float width, float height) {
        super(Ship.class, Bullet.class);

        this.state = EntityState.FIXED;

        this.fieldWidth = width;
        this.fieldHeight = height;
        this.color = Color.magenta;

        this.updateShape();
    }

    @Override
    protected void updateShape() {
        this.shape = new Rectangle(0, 0, this.fieldWidth, this.fieldHeight);
    }

    public float getFieldHeight() {
        return fieldHeight;
    }

    public float getFieldWidth() {
        return fieldWidth;
    }

    @Override
    public boolean collidesWith(EntityModel entity) {

        if(entity == this) {
            return false;
        }
        
        if(entity instanceof Bullet) {
            return !this.getShape().contains(entity.getShape());
        }
        else if(entity instanceof Ship) {
            //TODO: Implement shield for ship
            return !this.getShape().contains(entity.getShape());
        }
        
        return false;
    }
}
