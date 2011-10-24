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
