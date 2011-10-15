package geospace;

import geospace.audio.AudioManager;
import geospace.control.agent.service.ServiceAgentManager;
import geospace.render.DrawManager;
import geospace.states.LoadingState;
import geospace.states.MenuState;
import geospace.states.PlayingState;
import geospace.states.WaitingState;
import geospace.states.WinnerState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public class GeoSpace extends WrapperAwareGame {

    private GameContainer gameContainer;

    public static final int LOADING_STATE = 0;
    public static final int MENU_STATE = 1;
    public static final int WAITING_STATE = 2;
    public static final int PLAYING_STATE = 3;
    public static final int WINNER_STATE = 4;
    
    public GeoSpace(String title) {
        super(title);

        this.addState(new LoadingState(LOADING_STATE));
        this.addState(new MenuState(MENU_STATE));
        this.addState(new WaitingState(WAITING_STATE));
        this.addState(new PlayingState(PLAYING_STATE));
        this.addState(new WinnerState(WINNER_STATE));

        this.enterState(LOADING_STATE);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.gameContainer = gc;
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);

        switch (c) {
            case '1':
                this.gameContainer.setShowFPS(!this.gameContainer.isShowingFPS());
                break;
            case '2':
                DrawManager.getInstance().setEnableWireframe(!DrawManager.getInstance().isEnableWireframe());
                break;
            case '3':
                AudioManager.getInstance().setManagerEnabled(!AudioManager.getInstance().isManagerEnabled());
                
                if(AudioManager.getInstance().isManagerEnabled()) {
                    AudioManager.getInstance().playRandomMusic(false);
                }
                break;
            case 'r':
            case 'R':
                this.enterState(GeoSpace.MENU_STATE);
                break;
            default:
                break;
        }
    }
    
    @Override
    public boolean closeRequested() {
        ServiceAgentManager.getInstance().shutdownManager();
        return super.closeRequested();
    }
    
}
