package geospace.entity;

/**
 *
 * @author jpenton
 */
public class Constants {
    public static final float SHIP_ACCELERATION = 0.025f;
    public static final float DRAG_DECELERATION = 0.001f; 
    public static final float SHIP_TURN_VELOCITY = (float)Math.PI / 60;

    public static final int SHIP_MAX_ENERGY = 100000;
    public static final int SHIP_ENERGY_REGEN = Constants.SHIP_MAX_ENERGY / 5000;
    public static final int SHIP_ENERGY_COST_FIRE = 1000;
    public static final int SHIP_ENERGY_COST_SHIELD = 500;
}
