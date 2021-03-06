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
