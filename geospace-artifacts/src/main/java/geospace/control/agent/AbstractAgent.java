package geospace.control.agent;

import geospace.control.agent.service.ControllerState;
import geospace.control.agent.service.CurrentGameState;

public abstract class AbstractAgent {

    protected String agentName;
    protected String agentDescription;
    protected String agentId;
    
    protected ControllerState agentController;
    
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

    public ControllerState getAgentController() {
        return agentController;
    }

    public String getAgentDescription() {
        return agentDescription;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentId(String id) {
        this.agentId = id;
    }  
}
