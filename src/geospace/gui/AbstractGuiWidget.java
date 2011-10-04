package geospace.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;

public abstract class AbstractGuiWidget {
    protected static final int WIDGET_PADDING = 10;
    protected final static Color DEFAULT_WIDGET_COLOR = Color.magenta;
    protected final static Color DEFAULT_WIDGET_BGCOLOR = Color.black;

    protected enum EventType {
        MOUSE_RELEASE
    }
    
    public AbstractGuiWidget() {
    }
    
    abstract public void render(GameContainer gameContainer);
    
    public void notifyEvent(String id, EventType eventType) {
    }
}
