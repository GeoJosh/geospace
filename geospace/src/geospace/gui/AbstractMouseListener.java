package geospace.gui;

import java.util.LinkedList;
import java.util.List;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public abstract class AbstractMouseListener extends AbstractGuiWidget implements MouseListener {

    private List<AbstractGuiWidget> subscribedListeners;
    
    public AbstractMouseListener() {
        this.subscribedListeners = new LinkedList<AbstractGuiWidget>();
    }
    
    public void addListener(AbstractGuiWidget widget) {
        this.subscribedListeners.add(widget);
    }
    
    public void removeListener(AbstractGuiWidget widget) {
        if(this.subscribedListeners.contains(widget)) {
            this.subscribedListeners.remove(widget);
        }
    }
    
    protected void notifyListeners(String id, EventType eventType) {
        for(AbstractGuiWidget widget : this.subscribedListeners) {
            widget.notifyEvent(id, eventType);
        }
    }
    
    public void mouseWheelMoved(int i) {
    }

    public void mouseClicked(int i, int i1, int i2, int i3) {
    }

    public void mouseDragged(int i, int i1, int i2, int i3) {
    }

    public void setInput(Input input) {
    }

    public boolean isAcceptingInput() {
        return true;
    }

    public void inputEnded() {
    }

    public void inputStarted() {
    }
    
}
