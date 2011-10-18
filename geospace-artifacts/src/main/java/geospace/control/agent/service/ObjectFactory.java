
package geospace.control.agent.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the geospace.control.agent.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetAgentIdResponse_QNAME = new QName("http://service.agent.control.geospace/", "getAgentIdResponse");
    private final static QName _GetAgentId_QNAME = new QName("http://service.agent.control.geospace/", "getAgentId");
    private final static QName _GetLastGameState_QNAME = new QName("http://service.agent.control.geospace/", "getLastGameState");
    private final static QName _SetControllerState_QNAME = new QName("http://service.agent.control.geospace/", "setControllerState");
    private final static QName _ConnectAgent_QNAME = new QName("http://service.agent.control.geospace/", "connectAgent");
    private final static QName _ConnectAgentResponse_QNAME = new QName("http://service.agent.control.geospace/", "connectAgentResponse");
    private final static QName _GetLastGameStateResponse_QNAME = new QName("http://service.agent.control.geospace/", "getLastGameStateResponse");
    private final static QName _SetControllerStateResponse_QNAME = new QName("http://service.agent.control.geospace/", "setControllerStateResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: geospace.control.agent.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BulletInformation }
     * 
     */
    public BulletInformation createBulletInformation() {
        return new BulletInformation();
    }

    /**
     * Create an instance of {@link ControllerState }
     * 
     */
    public ControllerState createControllerState() {
        return new ControllerState();
    }

    /**
     * Create an instance of {@link GetLastGameState }
     * 
     */
    public GetLastGameState createGetLastGameState() {
        return new GetLastGameState();
    }

    /**
     * Create an instance of {@link FieldInformation }
     * 
     */
    public FieldInformation createFieldInformation() {
        return new FieldInformation();
    }

    /**
     * Create an instance of {@link ConnectAgentResponse }
     * 
     */
    public ConnectAgentResponse createConnectAgentResponse() {
        return new ConnectAgentResponse();
    }

    /**
     * Create an instance of {@link GetAgentId }
     * 
     */
    public GetAgentId createGetAgentId() {
        return new GetAgentId();
    }

    /**
     * Create an instance of {@link SetControllerState }
     * 
     */
    public SetControllerState createSetControllerState() {
        return new SetControllerState();
    }

    /**
     * Create an instance of {@link SetControllerStateResponse }
     * 
     */
    public SetControllerStateResponse createSetControllerStateResponse() {
        return new SetControllerStateResponse();
    }

    /**
     * Create an instance of {@link GetAgentIdResponse }
     * 
     */
    public GetAgentIdResponse createGetAgentIdResponse() {
        return new GetAgentIdResponse();
    }

    /**
     * Create an instance of {@link ShipInformation }
     * 
     */
    public ShipInformation createShipInformation() {
        return new ShipInformation();
    }

    /**
     * Create an instance of {@link ConnectAgent }
     * 
     */
    public ConnectAgent createConnectAgent() {
        return new ConnectAgent();
    }

    /**
     * Create an instance of {@link GetLastGameStateResponse }
     * 
     */
    public GetLastGameStateResponse createGetLastGameStateResponse() {
        return new GetLastGameStateResponse();
    }

    /**
     * Create an instance of {@link CurrentGameState }
     * 
     */
    public CurrentGameState createCurrentGameState() {
        return new CurrentGameState();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.agent.control.geospace/", name = "getAgentIdResponse")
    public JAXBElement<GetAgentIdResponse> createGetAgentIdResponse(GetAgentIdResponse value) {
        return new JAXBElement<GetAgentIdResponse>(_GetAgentIdResponse_QNAME, GetAgentIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.agent.control.geospace/", name = "getAgentId")
    public JAXBElement<GetAgentId> createGetAgentId(GetAgentId value) {
        return new JAXBElement<GetAgentId>(_GetAgentId_QNAME, GetAgentId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastGameState }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.agent.control.geospace/", name = "getLastGameState")
    public JAXBElement<GetLastGameState> createGetLastGameState(GetLastGameState value) {
        return new JAXBElement<GetLastGameState>(_GetLastGameState_QNAME, GetLastGameState.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetControllerState }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.agent.control.geospace/", name = "setControllerState")
    public JAXBElement<SetControllerState> createSetControllerState(SetControllerState value) {
        return new JAXBElement<SetControllerState>(_SetControllerState_QNAME, SetControllerState.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectAgent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.agent.control.geospace/", name = "connectAgent")
    public JAXBElement<ConnectAgent> createConnectAgent(ConnectAgent value) {
        return new JAXBElement<ConnectAgent>(_ConnectAgent_QNAME, ConnectAgent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectAgentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.agent.control.geospace/", name = "connectAgentResponse")
    public JAXBElement<ConnectAgentResponse> createConnectAgentResponse(ConnectAgentResponse value) {
        return new JAXBElement<ConnectAgentResponse>(_ConnectAgentResponse_QNAME, ConnectAgentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLastGameStateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.agent.control.geospace/", name = "getLastGameStateResponse")
    public JAXBElement<GetLastGameStateResponse> createGetLastGameStateResponse(GetLastGameStateResponse value) {
        return new JAXBElement<GetLastGameStateResponse>(_GetLastGameStateResponse_QNAME, GetLastGameStateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetControllerStateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.agent.control.geospace/", name = "setControllerStateResponse")
    public JAXBElement<SetControllerStateResponse> createSetControllerStateResponse(SetControllerStateResponse value) {
        return new JAXBElement<SetControllerStateResponse>(_SetControllerStateResponse_QNAME, SetControllerStateResponse.class, null, value);
    }

}
