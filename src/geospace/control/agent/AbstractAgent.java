
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
