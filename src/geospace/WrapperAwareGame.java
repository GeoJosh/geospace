package geospace;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class WrapperAwareGame extends StateBasedGame {
    protected AppGameContainer gameWrapper;

    public WrapperAwareGame(String title) {
        super(title);
    }
    
    public void setGameWrapper(AppGameContainer gameWrapper) {
        this.gameWrapper = gameWrapper;
    }
}
