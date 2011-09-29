package geospace.control.agent;

import geospace.control.ControllerState;
import geospace.control.CurrentGameState;
import org.newdawn.slick.Input;

public class KeyboardAgent extends AbstractInputAgent {
    private ControllerState controllerState;

    public KeyboardAgent() {
        super();

        this.agentName = "Keyboard Agent";
        this.agentDescription = "A test agent that uses input from the keyboard to control the player.";
        
        this.controllerState = new ControllerState();
    }

    @Override
    public void informGameState(final CurrentGameState cgs) {
    }

    @Override
    public ControllerState readControls() {
        if(this.input == null) {
            return this.controllerState;
        }
        
        this.controllerState.setThrusting(this.input.isKeyDown(Input.KEY_UP));
        this.controllerState.setTurningPort(this.input.isKeyDown(Input.KEY_LEFT));
        this.controllerState.setTurningStarboard(this.input.isKeyDown(Input.KEY_RIGHT));
        this.controllerState.setShielding(this.input.isKeyDown(Input.KEY_LSHIFT) || this.input.isKeyDown(Input.KEY_RSHIFT));
        this.controllerState.setFiring(this.input.isKeyDown(Input.KEY_SPACE));

        return this.controllerState;
    }
}
