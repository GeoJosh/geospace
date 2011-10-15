package geospace.control.agent.service;

import geospace.control.CurrentGameState;
import java.util.LinkedList;
import java.util.List;
import javax.xml.ws.Endpoint;

public class ServiceAgentManager {

    private List<ServiceAgent> serviceAgentQueue;
    private List<ServiceAgent> activeServiceAgents;
    private CurrentGameState lastGameState;
    private int timeLeft;
    private Endpoint serviceEndpoint;
    private static int ENDPOINT_PORT = 10508;

    private ServiceAgentManager() {
        this.serviceAgentQueue = new LinkedList<ServiceAgent>();
        this.activeServiceAgents = new LinkedList<ServiceAgent>();
        this.lastGameState = new CurrentGameState();

        this.serviceEndpoint = Endpoint.publish("http://localhost:" + ENDPOINT_PORT + "/AgentService", new ServiceAgentEndpoint());
    }

    private static class ServiceAgentManagerSingleton {

        public static final ServiceAgentManager instance = new ServiceAgentManager();
    }

    public static ServiceAgentManager getInstance() {
        return ServiceAgentManagerSingleton.instance;
    }

    public void shutdownManager() {
        if (this.serviceEndpoint != null) {
            this.serviceEndpoint.stop();
        }
    }

    public boolean isManagerReady() {
        return this.serviceAgentQueue.isEmpty();
    }

    public CurrentGameState getLastGameState() {
        if(this.timeLeft == 0 || this.timeLeft != this.lastGameState.getTimeLeft()) {
            this.lastGameState.updateState(timeLeft);
        }
        
        return this.lastGameState;

    }

    public void updateGameState(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public void addAgent(ServiceAgent agent) {
        this.serviceAgentQueue.add(agent);
    }

    public ServiceAgent requestAgent() throws ServiceAgentManagerException {
        if (this.serviceAgentQueue.isEmpty()) {
            throw new ServiceAgentManagerException();
        }

        ServiceAgent agent = this.serviceAgentQueue.remove(0);
        this.activeServiceAgents.add(agent);
        return agent;
    }

    public void clearAgents() {
        this.serviceAgentQueue.clear();
        this.activeServiceAgents.clear();
    }

    public ServiceAgent getServiceAgent(String authToken) throws ServiceAgentManagerException {
        for (ServiceAgent agent : this.activeServiceAgents) {

            if (agent.getAuthToken().equals(authToken)) {
                return agent;
            }
        }

        throw new ServiceAgentManagerException();
    }
}
