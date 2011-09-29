package geospace.entity;

import geospace.control.agent.AbstractAgent;
import geospace.control.ControllerState;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public class Ship extends CollidableEntityModel {

    private static final float TWOPI = 2 * (float) Math.PI;
    private static final float SHIP_HEADING_DELTA = TWOPI / 100;
    private static final double SHIP_ACCELERATION_FORCE = 0.05;
    private static final double DRAG_FORCE = 0.001;

    public static final int SHIP_MAX_ENERGY = 100000;
    private static final int SHIP_ENERGY_REGEN = Ship.SHIP_MAX_ENERGY / 5000;
    private static final int SHIP_ENERGY_COST_FIRE = 1000;
    private static final int SHIP_ENERGY_COST_SHIELD = 500;

    private AbstractAgent controller;
    private Shape baseShape;
    private Point center;
    private float heading;
    private Vector2f velocity;
    private boolean shielding;
    private int energy;
    private Transform translateTransform;
    private Transform rotationTransform;

    private List<Bullet> bullets;

    public Ship(AbstractAgent controller, float centerX, float centerY, float heading, Color color) {
        super(Ship.class, Bullet.class);
        
        this.state = EntityState.SPAWN;
        this.controller = controller;

        this.center = new Point(centerX, centerY);
        this.heading = heading;
        this.color = color;
        this.velocity = new Vector2f(0.0f, 0.0f);
        this.shielding = false;
        this.energy = Ship.SHIP_MAX_ENERGY;

        this.translateTransform = Transform.createTranslateTransform(centerX, centerY);
        this.rotationTransform = Transform.createRotateTransform(this.heading);

        this.baseShape = new Polygon(new float[]{
                    -1.0f, 0.0f,
                    2.0f, 6.0f,
                    10.0f, 6.0f,
                    0.0f, 12.0f,
                    -8.0f, 0.0f,
                    0.0f, -12.0f,
                    10.0f, -6.0f,
                    2.0f, -6.0f,});

        this.bullets = new LinkedList<Bullet>();
        this.updateShape();
    }

    public Point getCenter() {
        return this.center;
    }

    public float getHeading() {
        return this.heading;
    }

    public Vector2f getVelocity() {
        return this.velocity;
    }

    public boolean isShielding() {
        return shielding;
    }

    public int getEnergy() {
        return energy;
    }

    public Shape getShipShield() {
        return new Circle(this.center.getX(), this.center.getY(), baseShape.getBoundingCircleRadius());
    }

    @Override
    public List<EntityModel> getSpawnedEntities() {
        if(this.bullets.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        
        LinkedList<EntityModel> entities = new LinkedList<EntityModel>(this.bullets);
        this.bullets.clear();

        return entities;
    }

    @Override
    public void update(int delta) {
        ControllerState controllerState = this.controller.getControls();

        this.energy += Ship.SHIP_ENERGY_REGEN;
        this.energy = this.energy >= Ship.SHIP_MAX_ENERGY ? Ship.SHIP_MAX_ENERGY : this.energy;

        updateLocation(controllerState);

        if(controllerState.isFiring() && this.energy >= Ship.SHIP_ENERGY_COST_FIRE) {
            Bullet firedBullet = new Bullet(new Point(this.center), this.heading, this.color);
            firedBullet.update(0);
            this.bullets.add(firedBullet);
            this.energy -= Ship.SHIP_ENERGY_COST_FIRE;
        }

        this.shielding = controllerState.isShielding() && this.energy >= Ship.SHIP_ENERGY_COST_SHIELD;
        if(this.shielding) {
            this.energy -= Ship.SHIP_ENERGY_COST_SHIELD;
        }
    }

    public void updateLocation(ControllerState state) {

        this.updateHeading(state.isTurningPort(), state.isTurningStarboard());
        this.updateVelocity(state.isThrusting());
        this.updatePosition();

        this.updateShape();
    }

    private void updateHeading(boolean turningPort, boolean turningStarboard) {
        if (turningPort) {
            this.heading = (this.heading - Ship.SHIP_HEADING_DELTA) % Ship.TWOPI;
        }

        if (turningStarboard) {
            this.heading = (this.heading + Ship.SHIP_HEADING_DELTA) % Ship.TWOPI;
        }

        if (turningPort || turningStarboard) {
            this.rotationTransform = Transform.createRotateTransform(this.heading);
        }
    }

    private void updateVelocity(boolean isAccelerating) {
        if (isAccelerating && this.velocity.length() < Bullet.VELOCITY) {
            this.velocity.x += Ship.SHIP_ACCELERATION_FORCE * FastTrig.cos(this.heading);
            this.velocity.y += Ship.SHIP_ACCELERATION_FORCE * FastTrig.sin(this.heading);
        }

        if (Math.abs(this.velocity.x) > Ship.DRAG_FORCE) {
            this.velocity.x += Ship.DRAG_FORCE * FastTrig.cos(this.velocity.getTheta() + Math.PI);
        }

        if (Math.abs(this.velocity.y) > Ship.DRAG_FORCE) {
            this.velocity.y += Ship.DRAG_FORCE * FastTrig.sin(this.velocity.getTheta() + Math.PI);
        }
    }

    private void updatePosition() {
        this.center.setX(this.center.getX() + this.velocity.x);
        this.center.setY(this.center.getY() + this.velocity.y);

        this.shape.setCenterX(this.center.getX());
        this.shape.setCenterY(this.center.getY());

        this.translateTransform = Transform.createTranslateTransform(this.center.getX(), this.center.getY());
    }

    @Override
    protected void updateShape() {
        this.shape = this.baseShape.transform(this.rotationTransform);
        this.shape = this.shape.transform(this.translateTransform);
    }

    @Override
    public boolean collidesWith(EntityModel entity) {
        if(entity == this) {
            return false;
        }
        
        if(entity instanceof Bullet) {
            if(this.isShielding() && (this.getShipShield().contains(entity.getShape()) || this.getShipShield().intersects(entity.getShape()))) {
                entity.state = EntityState.DEAD;
                return false;
            }
            
            boolean collided = this.shape.contains(entity.getShape()) || this.shape.intersects(entity.getShape());
            if(collided) {
                this.state = EntityState.DEAD;
            }
            return collided;
        }
        return false;
    }
}
