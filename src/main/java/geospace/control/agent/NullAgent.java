package geospace.control.agent;

import geospace.control.CurrentGameState;

public class NullAgent extends AbstractAgent {

    public NullAgent() {
        this.agentName = "Null Agent";
        this.agentDescription = "A test agent that does nothing useful.";
    }

    @Override
    public void informGameState(final CurrentGameState cgs) {
        this.agentController.setFiring(true);
        this.agentController.setTurningPort(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
        }
    }
}
