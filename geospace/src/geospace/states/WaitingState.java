package geospace.states;

import geospace.GeoSpace;
import geospace.control.agent.service.ServiceAgentManager;
import geospace.gui.GUIManager;
import geospace.render.EffectManager;
import geospace.render.FontManager;
import geospace.render.FontManager.FontType;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class WaitingState extends BasicGameState {
    private int stateId;
    private final static String WAITING_STRING = "Waiting for service agents to connect...";
    
    public WaitingState(int id) {
        this.stateId = id;
    }
    
    @Override
    public int getID() {
        return this.stateId;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        super.enter(gc, sbg);
        
        EffectManager.getInstance().renderWaiting(gc.getWidth() / 2, gc.getHeight() / 2);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame sbg) throws SlickException {
        StateJanitor.cleanupState();
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(ServiceAgentManager.getInstance().isManagerReady()) {
                sbg.enterState(GeoSpace.PLAYING_STATE, new FadeOutTransition(new Color(0, 0, 0, 0), 500), new EmptyTransition());
        }
        EffectManager.getInstance().update(gc, i);
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        Font gameFont = FontManager.getInstance().getFont(FontType.DEFAULT, WAITING_STRING);
        gameFont.drawString(gc.getWidth() / 2 - gameFont.getWidth(WAITING_STRING) / 2, gc.getHeight() / 2 - gameFont.getLineHeight() / 2, WAITING_STRING);
        GUIManager.getInstance().render();

        EffectManager.getInstance().render(grphcs);
    }
}
