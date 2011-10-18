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
    
    public void shrinkField(float amount) {
        this.fieldWidth -= 2 * amount;
        this.fieldHeight -= 2 * amount;
        
        this.shape = new Rectangle(this.shape.getMinX() + amount, this.shape.getMinY() + amount, this.fieldWidth, this.fieldHeight);
    }

    public float getFieldHeight() {
        return fieldHeight;
    }

    public float getFieldWidth() {
        return fieldWidth;
    }

    @Override
    public boolean collidesWith(EntityModel entity) {

        if(entity instanceof Bullet) {
            return !this.getShape().contains(entity.getShape());
        }
        else if(entity instanceof Ship) {
            return !this.getShape().contains(entity.getShape());
        }
        
        return false;
    }
}
