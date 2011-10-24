/*
 * Copyright (c) 2011, GeoJosh - https://github.com/GeoJosh
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
