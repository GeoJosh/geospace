package geospace.control.agent;

import org.newdawn.slick.Input;

public abstract class AbstractInputAgent extends AbstractAgent{
    protected Input input;
    
    public AbstractInputAgent() {
        this.input = null;
    }
    
    public void setInput(Input input) {
        this.input = input;
    }

}
