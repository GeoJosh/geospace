package geospace.gui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.newdawn.slick.GameContainer;

public class GUIManager {
    
    private List<AbstractGuiWidget> widgets;
    private Map<AbstractGuiWidget, GuiManagementAction> processQueue;
    private GameContainer gameContainer;

    private enum GuiManagementAction {
        ADD,
        REMOVE
    }
    
    public GUIManager() {
        this.widgets = new LinkedList<AbstractGuiWidget>();
        this.processQueue = new HashMap<AbstractGuiWidget, GuiManagementAction>();
    }
    
    private static class GUIManagerSingleton {

        private static final GUIManager instance = new GUIManager();
    }

    public static GUIManager getInstance() {
        return GUIManagerSingleton.instance;
    }

    
    public void init(GameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }
    
    public void addWidget(AbstractGuiWidget widget) {
        this.processQueue.put(widget, GuiManagementAction.ADD);
    }
    
    public void removeWidget(AbstractGuiWidget widget) {
        this.processQueue.put(widget, GuiManagementAction.REMOVE);
    }
    
    public void clearWidgets() {
        this.widgets.clear();
    }
    
    public void render() {
        for(AbstractGuiWidget widget : this.widgets) {
            widget.render(this.gameContainer);
        }
        
        this.processManagementQueue();
    }
    
    private void processManagementQueue() {
        if(!this.processQueue.isEmpty()) {
            
            for(Entry<AbstractGuiWidget, GuiManagementAction> entry : this.processQueue.entrySet()) {
                AbstractGuiWidget widget = entry.getKey();

                switch(entry.getValue()) {
                    case ADD:
                        this.widgets.add(widget);

                        if(widget instanceof AbstractMouseListener) {
                            this.gameContainer.getInput().addMouseListener((AbstractMouseListener)widget);
                        }
                        break;
                    case REMOVE:
                        if(this.widgets.contains(widget)) {
                            this.widgets.remove(widget);

                            if(widget instanceof AbstractMouseListener) {
                                this.gameContainer.getInput().removeMouseListener((AbstractMouseListener)widget);
                            }
                        }
                        break;
                }
            }
            
            this.processQueue.clear();
        }
        
    }
}
