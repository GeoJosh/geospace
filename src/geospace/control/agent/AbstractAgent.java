
package geospace.control.agent;

import geospace.control.ControllerState;
import geospace.control.CurrentGameState;
import java.util.Date;

public abstract class AbstractAgent {

    protected String agentName;
    protected String agentDescription;
    protected String agentId;
    
    protected ControllerState agentController;
    
    private long lastFired;
    private static final long firingInterval = 250;

    public AbstractAgent() {
        this.agentController = new ControllerState();
    }

    /**
     * Implement this method to feed your agent the current state of the game.
     * Note that it is the responsibility of the agent to keep track of information
     * between calls if such behavior is desired.
     *
     * @param cgs  An object describing the current state of the game.
     * @see CurrentGameState
     */
    abstract public void informGameState(final CurrentGameState cgs);

    public String getAgentDescription() {
        return agentDescription;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentId(String id) {
        this.agentId = id;
    }

    public String getAgentId() {
        return agentId;
    }
    
    /**
     * Override this method to define the polygon point which describe the shape of your ship.
     * Note that the bounding radius of the ship must be larger than 12.0f or the Ship class will
     * throw a GeoSpaceException on instantiation.
     */
    public float[] getAgentShipDesign() {
        return new float[]{
                            -1.0f, 0.0f,
                            2.0f, 6.0f,
                            10.0f, 6.0f,
                            0.0f, 12.0f,
                            -8.0f, 0.0f,
                            0.0f, -12.0f,
                            10.0f, -6.0f,
                            2.0f, -6.0f,};
    }
    
    public final ControllerState getControls() {
        ControllerState controllerState = this.agentController;

        if (!controllerState.isFiring()) {
            return controllerState;
        }

        Date now = new Date();
        long currentTimestamp = now.getTime();

        if (currentTimestamp - this.lastFired >= AbstractAgent.firingInterval) {
            this.lastFired = currentTimestamp;
        } else {
            controllerState.setFiring(false);
        }

        return controllerState;

    }
    
}
