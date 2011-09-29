package geospace.control.agent.service;

import geospace.control.ControllerState;
import geospace.control.CurrentGameState;
import geospace.control.agent.AbstractAgent;
import java.util.UUID;

public class ServiceAgent extends AbstractAgent {

    protected ControllerState controllerState;
    protected String authToken;

    public ServiceAgent() {
        this.controllerState = new ControllerState();
        this.authToken = UUID.randomUUID().toString();
        
        this.agentName = "Unconnected Service Agent";
        this.agentDescription = "An agent that proxies to a Web Service implementation.";
        
        ServiceAgentManager.getInstance().addAgent(this);
    }

    public String getAuthToken() {
        return authToken;
    }
    
    public void setControllerState(ControllerState controllerState) {
        this.controllerState = controllerState;
    }
    
    @Override
    public void informGameState(CurrentGameState cgs) {
    }

    @Override
    protected ControllerState readControls() {
        return this.controllerState;
    }
}
