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

    public Ship(AbstractAgent controller, float centerX, float centerY, float heading, Color color) throws GeoSpaceException {
        super(Ship.class, Bullet.class);

        this.state = EntityState.SPAWN;
        this.controller = controller;

        this.center = new Point(centerX, centerY);
        this.heading = heading;
        this.color = color;
        this.velocity = new Vector2f(0.0f, 0.0f);
        this.shielding = false;
        this.energy = Constants.SHIP_MAX_ENERGY;

        this.translateTransform = Transform.createTranslateTransform(centerX, centerY);
        this.rotationTransform = Transform.createRotateTransform(this.heading);

        this.baseShape = new Polygon(controller.getAgentShipDesign());
        if(this.baseShape.getBoundingCircleRadius() < 12.0f) {
            throw new GeoSpaceException();
        }

        this.bullets = new LinkedList<Bullet>();
        this.updateShape();
    }

    public Point getCenter() {
        return this.center;
    }

    public float getHeading() {
        return this.heading;
    }

    public void resetShip(float centerX, float centerY, float heading) {
        this.center.setX(centerX);
        this.center.setY(centerY);
        this.heading = heading;
        this.rotationTransform = Transform.createRotateTransform(this.heading);

        this.velocity.x = 0;
        this.velocity.y = 0;
        this.energy = Constants.SHIP_MAX_ENERGY;

        this.state = EntityState.SPAWN;
    }

    public void setHeading(float heading) {
        this.heading = heading;
        this.rotationTransform = Transform.createRotateTransform(this.heading);
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

    public void adjustBounce(Vector2f velocity, Vector2f positionAdjust) {
        this.velocity = velocity;
        this.updatePosition(positionAdjust);
    }

    @Override
    public List<EntityModel> getSpawnedEntities() {
        if (this.bullets.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        LinkedList<EntityModel> entities = new LinkedList<EntityModel>(this.bullets);
        this.bullets.clear();

        return entities;
    }

    public void update() {
        ControllerState controllerState = this.controller.getControls();

        this.energy += Constants.SHIP_ENERGY_REGEN;
        this.energy = this.energy >= Constants.SHIP_MAX_ENERGY ? Constants.SHIP_MAX_ENERGY : this.energy;

        updateLocation(controllerState);

        if (controllerState.isFiring() && this.energy >= Constants.SHIP_ENERGY_COST_FIRE) {
            Bullet firedBullet = new Bullet(new Point(this.center), this.heading, this.color);
            firedBullet.update();
            this.bullets.add(firedBullet);
            this.energy -= Constants.SHIP_ENERGY_COST_FIRE;
        }

        this.shielding = controllerState.isShielding() && this.energy >= Constants.SHIP_ENERGY_COST_SHIELD;
        if (this.shielding) {
            //this.energy -= Constants.SHIP_ENERGY_COST_SHIELD;
        }
    }

    public void updateLocation(ControllerState state) {

        this.updateHeading(state.isTurningPort(), state.isTurningStarboard(), state.isTurbo());
        this.updateVelocity(state.isThrusting(), state.isTurbo());
        this.updatePosition(this.velocity);

        this.updateShape();
    }

    private void updateHeading(boolean turningPort, boolean turningStarboard, boolean turboActivated) {
        if (turningPort) {
            this.heading = (this.heading - (turboActivated ? Constants.SHIP_TURN_VELOCITY_TURBO : Constants.SHIP_TURN_VELOCITY)) % Ship.TWOPI;
        }

        if (turningStarboard) {
            this.heading = (this.heading + (turboActivated ? Constants.SHIP_TURN_VELOCITY_TURBO : Constants.SHIP_TURN_VELOCITY)) % Ship.TWOPI;
        }

        if (turningPort || turningStarboard) {
            this.rotationTransform = Transform.createRotateTransform(this.heading);
        }
    }

    private void updateVelocity(boolean isAccelerating, boolean turboActivated) {
        if (isAccelerating && this.velocity.length() < Bullet.VELOCITY) {
            this.velocity.x += (turboActivated ? Constants.SHIP_ACCELERATION_TURBO : Constants.SHIP_ACCELERATION) * FastTrig.cos(this.heading);
            this.velocity.y += (turboActivated ? Constants.SHIP_ACCELERATION_TURBO : Constants.SHIP_ACCELERATION) * FastTrig.sin(this.heading);
        }

        if (Math.abs(this.velocity.x) > Constants.DRAG_DECELERATION) {
            this.velocity.x += Constants.DRAG_DECELERATION * FastTrig.cos(this.velocity.getTheta() + Math.PI);
        }
        else {
            this.velocity.x = 0;
        }

        if (Math.abs(this.velocity.y) > Constants.DRAG_DECELERATION) {
            this.velocity.y += Constants.DRAG_DECELERATION * FastTrig.sin(this.velocity.getTheta() + Math.PI);
        }
        else {
            this.velocity.y = 0;
        }
    }

    private void updatePosition(Vector2f velocity) {
        this.center.setX(this.center.getX() + velocity.x);
        this.center.setY(this.center.getY() + velocity.y);

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

        if (entity instanceof Bullet) {
            if (this.isShielding() && (this.getShipShield().contains(entity.getShape()) || this.getShipShield().intersects(entity.getShape()))) {
                entity.state = EntityState.DEAD;
                return false;
            }

            boolean collided = this.shape.contains(entity.getShape()) || this.shape.intersects(entity.getShape());
            if (collided) {
                this.state = EntityState.DEAD;
            }
            return collided;
        }

        if (entity instanceof Ship) {

            if (this.shielding) {
                if (!((Ship) entity).isShielding() && this.getShipShield().intersects(entity.getShape())) {
                    return true;
                } else if (((Ship) entity).isShielding() && this.getShipShield().intersects(((Ship) entity).getShipShield())) {
                    collideVelocityVectors(this, (Ship) entity);
                    return false;
                }
            } else {
                if (!((Ship) entity).isShielding() && this.shape.intersects(((Ship) entity).getShape())) {
                    this.state = EntityState.DEAD;
                    return true;
                }
            }
        }

        return false;
    }

    private static void collideVelocityVectors(Ship shipOne, Ship shipTwo) {
        Vector2f velocityOne = new Vector2f(shipOne.getVelocity());
        Vector2f velocityTwo = new Vector2f(shipTwo.getVelocity());

        Vector2f collisionVector = new Vector2f(velocityOne.x - velocityTwo.x, velocityOne.y - velocityTwo.y);
        double collisionTheta = 90 - collisionVector.getTheta();

        velocityOne.sub(collisionTheta);
        velocityTwo.sub(collisionTheta);

        float oneY = velocityOne.y;
        float twoY = velocityTwo.y;

        velocityOne.y = twoY;
        velocityTwo.y = oneY;

        velocityOne.add(collisionTheta);
        velocityTwo.add(collisionTheta);

        Vector2f overlapAdjustVector = collisionVector.getNormal();
        double shipOverlap = Point.distance(shipOne.getCenter(), shipTwo.getCenter()) - shipOne.getShipShield().getBoundingCircleRadius() - shipTwo.getShipShield().getBoundingCircleRadius();
        
        if(shipOverlap < 0) {
            shipOverlap = Math.abs(shipOverlap);
        }
        else {
            shipOverlap = 0;
        }
        
        overlapAdjustVector.scale((float)shipOverlap);
        shipOne.adjustBounce(velocityOne, overlapAdjustVector.negate());
        shipTwo.adjustBounce(velocityTwo, overlapAdjustVector);
    }
}
