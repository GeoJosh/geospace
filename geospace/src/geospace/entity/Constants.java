package geospace.entity;

/**
 *
 * @author jpenton
 */
public class Constants {
    public static final float SHIP_ACCELERATION = 0.004f;
    public static final float SHIP_ACCELERATION_TURBO = SHIP_ACCELERATION * 6;
    public static final float DRAG_DECELERATION = 0.001f; 
    public static final float SHIP_TURN_VELOCITY = (float)Math.PI / 360;
    public static final float SHIP_TURN_VELOCITY_TURBO = SHIP_TURN_VELOCITY * 6;

    public static final int SHIP_MAX_ENERGY = 100000;
    public static final int SHIP_ENERGY_REGEN = Constants.SHIP_MAX_ENERGY / 5000;
    public static final int SHIP_ENERGY_COST_FIRE = 500;
    public static final int SHIP_ENERGY_COST_SHIELD = 1000;
    
    public static float radiansToDegrees(float angle) {
        double degrees = Math.toDegrees(angle) % 360;
        degrees = degrees < 0 ? 360 + degrees : degrees;
        return (float)degrees;
    }
}
