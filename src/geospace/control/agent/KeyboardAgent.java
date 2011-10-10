package geospace.control.agent;

import geospace.control.CurrentGameState;
import org.newdawn.slick.Input;

public class KeyboardAgent extends AbstractInputAgent {

    public KeyboardAgent() {
        super();

        this.agentName = "Keyboard Agent";
        this.agentDescription = "A test agent that uses input from the keyboard to control the player.";
    }

    @Override
    public void informGameState(final CurrentGameState cgs) {
        if(this.input != null) {
        
        this.agentController.setThrusting(this.input.isKeyDown(Input.KEY_UP));
        this.agentController.setTurningPort(this.input.isKeyDown(Input.KEY_LEFT));
        this.agentController.setTurningStarboard(this.input.isKeyDown(Input.KEY_RIGHT));
        this.agentController.setShielding(this.input.isKeyDown(Input.KEY_LSHIFT) || this.input.isKeyDown(Input.KEY_RSHIFT));
        this.agentController.setFiring(this.input.isKeyDown(Input.KEY_SPACE));
        }
    }
}
