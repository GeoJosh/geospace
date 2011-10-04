package geospace.control.agent;

import geospace.control.ControllerState;
import geospace.control.CurrentGameState;

public class NullAgent extends AbstractAgent {

    public NullAgent() {
        this.agentName = "Null Agent";
        this.agentDescription = "A test agent that does nothing useful.";
    }

    @Override
    public void informGameState(final CurrentGameState cgs) {
    }

    @Override
    public ControllerState readControls() {
        ControllerState controllerState = new ControllerState();
        controllerState.setFiring(true);
        controllerState.setTurningPort(true);
        
        return controllerState;
    }
}
