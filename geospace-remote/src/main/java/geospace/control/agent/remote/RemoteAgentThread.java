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

package geospace.control.agent.remote;

import geospace.control.agent.AbstractAgent;
import geospace.control.agent.service.CurrentGameState;
import geospace.control.agent.service.GameEvent;
import geospace.control.agent.service.GameEventType;
import geospace.control.agent.service.ServiceAgentEndpoint;
import geospace.control.agent.service.ServiceAgentEndpointService;

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
                    CurrentGameState gameState = this.endpoint.getLastGameState(this.authId);
                    if(gameState != null) {
                        this.agent.informGameState(gameState);
                        
                        for(GameEvent gameEvent : gameState.getGameEvents()) {
                            if(gameEvent.getEvent() == GameEventType.GAME_END) {
                                continueToRun = false;
                            }
                        }
                    }
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
