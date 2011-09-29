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
    @WebResult(name="authToken")
    public String connectAgent(@WebParam(name = "agentName") String agentName, @WebParam(name = "agentDescription") String agentDescription) {     
        try {
            ServiceAgent agent = ServiceAgentManager.getInstance().requestAgent();        
            GUIManager.getInstance().addWidget(new WidgetDialog("Client connected to service agent: " + agentName));
            return agent.getAuthToken();
        } catch (ServiceAgentManagerException ex) {
            Logger.getLogger(ServiceAgentEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @WebMethod()
    @WebResult(name="lastGameState")
    public CurrentGameState getLastGameState() {
        return ServiceAgentManager.getInstance().getLastGameState();
    }
    
    @WebMethod()
    @WebResult(name="agentId")
    public String getAgentId(@WebParam(name = "authToken") String authToken) {
        try {
            return ServiceAgentManager.getInstance().getServiceAgent(authToken).getAgentId();
        } catch (ServiceAgentManagerException ex) {
            Logger.getLogger(ServiceAgentEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @WebMethod()
    public void setControllerState(@WebParam(name = "authToken") String authToken, @WebParam(name = "controllerState") ControllerState controllerState) {
        try {
            ServiceAgentManager.getInstance().getServiceAgent(authToken).setControllerState(controllerState);
        } catch (ServiceAgentManagerException ex) {
            Logger.getLogger(ServiceAgentEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
