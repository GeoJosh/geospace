package geospace.control.agent.service;

import geospace.control.ControllerState;
import geospace.control.CurrentGameState;
import geospace.control.agent.AbstractAgent;
import java.util.UUID;

public class ServiceAgent extends AbstractAgent {

    protected String authToken;

    public ServiceAgent() {
        this.authToken = UUID.randomUUID().toString();
        
        this.agentName = "Unconnected Service Agent";
        this.agentDescription = "An agent that proxies to a Web Service implementation.";
        
        ServiceAgentManager.getInstance().addAgent(this);
    }

    public void setAgentDescription(String agentDescription) {
        this.agentDescription = agentDescription;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    
    public String getAuthToken() {
        return authToken;
    }
    
    public void setControllerState(ControllerState controllerState) {
        this.agentController = controllerState;
    }
    
    @Override
    public void informGameState(CurrentGameState cgs) {
    }
}
