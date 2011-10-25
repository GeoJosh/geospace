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

import geospace.control.ControllerState;
import geospace.control.CurrentGameState;
import geospace.gui.GUIManager;
import geospace.gui.WidgetDialog;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class ServiceAgentEndpoint {

    @WebMethod()
    @WebResult(name = "authToken")
    public String connectAgent(@WebParam(name = "agentName") String agentName, @WebParam(name = "agentDescription") String agentDescription) {
        try {
            ServiceAgent agent = ServiceAgentManager.getInstance().requestAgent();
            agent.setAgentName(agentName);
            agent.setAgentDescription(agentDescription);
            GUIManager.getInstance().addWidget(new WidgetDialog("Client connected to service agent: " + agentName));
            return agent.getAuthToken();
        } catch (ServiceAgentManagerException ex) {
            return null;
        }
    }

    @WebMethod()
    @WebResult(name = "lastGameState")
    public CurrentGameState getLastGameState(@WebParam(name = "authToken") String authToken) {
        try {
            return ServiceAgentManager.getInstance().getLastGameState(authToken);
        } catch (ServiceAgentManagerException ex) {
            return null;
        }
    }

    @WebMethod()
    @WebResult(name = "agentId")
    public String getAgentId(@WebParam(name = "authToken") String authToken) {
        try {
            ServiceAgent agent = ServiceAgentManager.getInstance().getServiceAgent(authToken);
            return agent.getAgentId();

        } catch (ServiceAgentManagerException ex) {
            return null;
        }
    }

    @WebMethod()
    public void setControllerState(@WebParam(name = "authToken") String authToken, @WebParam(name = "controllerState") ControllerState controllerState) {
        try {
            ServiceAgentManager.getInstance().getServiceAgent(authToken).setControllerState(controllerState);
        } catch (ServiceAgentManagerException ex) {
        }
    }
}
