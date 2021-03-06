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

package geospace.states;

import geospace.GeoSpace;
import geospace.PropertyManager;
import geospace.control.agent.AbstractAgent;
import geospace.control.agent.AbstractInputAgent;
import geospace.control.agent.service.ServiceAgent;
import geospace.control.agent.service.ServiceAgentManager;
import geospace.entity.GeoSpaceException;
import geospace.entity.Ship;
import geospace.gui.GUIManager;
import geospace.gui.WidgetPlayerSelector;
import geospace.render.EffectManager;
import geospace.states.PlayingState.GameMode;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
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
        EffectManager.getInstance().renderWaiting(gc.getWidth() / 2, gc.getHeight() / 2);

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

        EffectManager.getInstance().update(gc, i);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        EffectManager.getInstance().render(grphcs);
        GUIManager.getInstance().render();
    }

    private Map<String, String> getAgentClasses() {
        Map<String, String> classes = new HashMap<String, String>();
        for (String className : PropertyManager.getInstance().getProperty("system.agents").split(",")) {
            AbstractAgent agent = this.instantiateAgentClass(className);
            if (agent != null) {
                try {
                    new Ship(agent, 0, 0, 0, Color.black);
                    classes.put(className, agent.getAgentName());
                } catch (GeoSpaceException ex) {
                    Logger.getLogger(MenuState.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

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
        if (agent instanceof AbstractInputAgent) {
            ((AbstractInputAgent) agent).setInput(gc.getInput());
        }

        return agent;
    }

    private void initializeGameMode(StateBasedGame sbg, GameContainer gc, GameMode gameMode) {
        ((PlayingState) sbg.getState(GeoSpace.PLAYING_STATE)).setCurrentGameMode(gameMode);
        
        switch (gameMode) {
            case DUEL:
                ((PlayingState) sbg.getState(GeoSpace.PLAYING_STATE)).setPlayerControllers(
                        Arrays.asList(
                        this.initializeAgent(gc, this.instantiateAgentClass(this.playerSelector.getPlayerOneClassName())),
                        this.initializeAgent(gc, this.instantiateAgentClass(this.playerSelector.getPlayerTwoClassName()))));
                break;
            case BATTLE_ROYALE:
                List<AbstractAgent> agents = new LinkedList<AbstractAgent>();
                for(int i=0; i < PropertyManager.getInstance().getInteger("game.royale.service.slots", 2); i++) {
                    agents.add(this.initializeAgent(gc, new ServiceAgent()));
                }
                
                for (Entry<String, String> agentEntry : this.agentClasses.entrySet()) {
                    if(!agentEntry.getKey().equals(ServiceAgent.class.getName())) {
                        agents.add(this.initializeAgent(gc, this.instantiateAgentClass(agentEntry.getKey())));
                    }
                }

                ((PlayingState) sbg.getState(GeoSpace.PLAYING_STATE)).setPlayerControllers(agents);
                break;
        }
    }
}
