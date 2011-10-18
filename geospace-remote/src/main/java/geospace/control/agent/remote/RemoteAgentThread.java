package geospace.control.agent.remote;

import geospace.control.agent.AbstractAgent;
import geospace.control.agent.service.ServiceAgentEndpoint;
import geospace.control.agent.service.ServiceAgentEndpointService;

/**
 *
 * @author jpenton
 */
public final class RemoteAgentThread extends Thread {

    private static int UPDATE_RATE = 16;
    private AbstractAgent agent;
    private ServiceAgentEndpoint endpoint;
    private String authId;
    private String agentId;

    public RemoteAgentThread(AbstractAgent agent) {
        this.agent = agent;
        this.endpoint = new ServiceAgentEndpointService().getServiceAgentEndpointPort();
        this.authId = this.endpoint.connectAgent(this.agent.getAgentName(), this.agent.getAgentDescription());
    }

    @Override
    public void run() {
        boolean continueToRun = true;
        while (continueToRun) {
            try {
                if (this.agentId == null) {
                    this.agentId = this.endpoint.getAgentId(this.authId);
                    if (this.agentId != null) {
                        this.agent.setAgentId(this.agentId);
                    }
                } else {
                    this.agent.informGameState(this.endpoint.getLastGameState());
                    this.endpoint.setControllerState(this.authId, this.agent.getAgentController());
                }
            } catch (Exception ex) {
                continueToRun = false;
            }

            try {
                Thread.sleep(UPDATE_RATE);
            } catch (InterruptedException ex) {
                continueToRun = false;
            }
        }
    }
}
