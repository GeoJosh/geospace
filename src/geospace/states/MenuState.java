package geospace.states;

import geospace.GeoSpace;
import geospace.control.agent.AbstractAgent;
import geospace.control.agent.KeyboardAgent;
import geospace.control.agent.service.ServiceAgentManager;
import geospace.gui.GUIManager;
import geospace.gui.WidgetPlayerSelector;
import geospace.states.PlayingState.GameMode;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState {

    private int stateId;
    private Map<String, String> agentClasses;
    private WidgetPlayerSelector playerSelector;

    public MenuState(int id) {
        this.stateId = id;
        this.agentClasses = this.getAgentClasses();
    }

    @Override
    public int getID() {
        return this.stateId;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        super.enter(gc, sbg);
        this.playerSelector = new WidgetPlayerSelector(agentClasses.entrySet());

        ServiceAgentManager.getInstance().clearAgents();

        GUIManager.getInstance().addWidget(this.playerSelector);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame sbg) throws SlickException {
        StateJanitor.cleanupState();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if (this.playerSelector.isReadyToStart()) {

            initializeGameMode(sbg, gc, this.playerSelector.getGameMode());

            if (ServiceAgentManager.getInstance().isManagerReady()) {
                sbg.enterState(GeoSpace.PLAYING_STATE);
            } else {
                sbg.enterState(GeoSpace.WAITING_STATE);
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        GUIManager.getInstance().render();
    }

    private Map<String, String> getAgentClasses() {
        Properties props = new Properties();
        Map<String, String> classes = new HashMap<String, String>();
        try {
            props.load(MenuState.class.getResourceAsStream("/resources/agents.properties"));
            for (String className : props.getProperty("agents").split(",")) {
                AbstractAgent agent = this.instantiateAgentClass(className);
                if (agent != null) {
                    classes.put(className, agent.getAgentName());
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classes;
    }

    private AbstractAgent instantiateAgentClass(String className) {
        try {
            Class agentClass = Class.forName(className);
            return (AbstractAgent) agentClass.getConstructor().newInstance();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private AbstractAgent initializeAgent(GameContainer gc, AbstractAgent agent) {
        if (agent instanceof KeyboardAgent) {
            ((KeyboardAgent) agent).setInput(gc.getInput());
        }

        return agent;
    }

    private void initializeGameMode(StateBasedGame sbg, GameContainer gc, GameMode gameMode) {
        switch (gameMode) {
            case DUEL:
                ((PlayingState) sbg.getState(GeoSpace.PLAYING_STATE)).setPlayerControllers(
                        Arrays.asList(
                        this.initializeAgent(gc, this.instantiateAgentClass(this.playerSelector.getPlayerOneClassName())),
                        this.initializeAgent(gc, this.instantiateAgentClass(this.playerSelector.getPlayerTwoClassName()))));
                break;
            case BATTLE_ROYALE:
                List<AbstractAgent> agents = new LinkedList<AbstractAgent>();
                for(Entry<String, String> agentEntry : this.agentClasses.entrySet()) {
                    agents.add(this.initializeAgent(gc, this.instantiateAgentClass(agentEntry.getKey())));
                }
                
                ((PlayingState) sbg.getState(GeoSpace.PLAYING_STATE)).setPlayerControllers(agents);
                break;
        }
    }
}
