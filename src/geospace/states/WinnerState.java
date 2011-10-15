package geospace.states;

import geospace.GeoSpace;
import geospace.render.EffectManager;
import geospace.render.FontManager;
import geospace.render.FontManager.FontType;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class WinnerState extends BasicGameState {
    private int stateId;
    private final String winnerMessage = "A WINNER IS";
    private String winnerName;
    
    public WinnerState(int stateId) {
        this.stateId = stateId;
    }
    
    @Override
    public int getID() {
        return this.stateId;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    @Override
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

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        EffectManager.getInstance().update(gc, i);
        
        if(gc.getInput().isKeyDown(Input.KEY_SPACE)) {
            sbg.enterState(GeoSpace.MENU_STATE);
        }
    }    
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        EffectManager.getInstance().render(grphcs);
        
        Font gameFont = FontManager.getInstance().getFont(FontType.DEFAULT, this.winnerMessage + this.winnerName);
        gameFont.drawString(gc.getWidth() / 2 - gameFont.getWidth(this.winnerMessage) / 2, gc.getHeight() / 2 - gameFont.getLineHeight() / 2, this.winnerMessage);
        gameFont.drawString(gc.getWidth() / 2 - gameFont.getWidth(this.winnerName) / 2, gc.getHeight() / 2 + gameFont.getLineHeight() / 2, this.winnerName);
    }
}
