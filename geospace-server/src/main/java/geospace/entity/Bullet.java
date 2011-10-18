package geospace.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public class Bullet extends EntityModel {

    private Shape baseShape;
    private Point center;
    private Vector2f velocity;
    private Transform translateTransform;
    
    public static final float VELOCITY = 10.0f;

    public Bullet(Point center, float heading, Color color) {
        super();
        this.state = EntityState.SPAWN;
        
        this.center = center;
        this.velocity = new Vector2f((float) (VELOCITY * FastTrig.cos(heading)), (float) (VELOCITY * FastTrig.sin(heading)));
        this.color = color;

        this.translateTransform = Transform.createTranslateTransform(this.center.getX(), this.center.getY());
        this.baseShape = new Ellipse(0.0f, 0.0f, 5.0f, 5.0f);


        this.updateShape();
    }

    public Point getCenter() {
        return center;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    @Override
    public void update() {
        this.center.setX(this.center.getX() + this.velocity.x);
        this.center.setY(this.center.getY() + this.velocity.y);

        this.translateTransform = Transform.createTranslateTransform(this.center.getX(), this.center.getY());

        this.updateShape();

    }

    @Override
    protected void updateShape() {
        this.shape = this.baseShape.transform(this.translateTransform);
    }
}
