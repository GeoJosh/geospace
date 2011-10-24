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

import geospace.render.FontManager;
import geospace.render.FontManager.FontType;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.RoundedRectangle;

public class WidgetClickableList extends AbstractMouseListener {

    private List<String> labels;
    private int currentIndex;
    private RoundedRectangle frame;
    private float centerX;
    private float centerY;
    private Color displayColor;
    
    private int labelWidth;
    private int labelHeight;

    public WidgetClickableList(List<String> labels, float centerX, float centerY) {
        super();
        this.labels = labels;
        this.currentIndex = 0;
        this.frame = null;
        this.displayColor = AbstractGuiWidget.DEFAULT_WIDGET_COLOR;
        this.centerX = centerX;
        this.centerY = centerY;
        
        this.labelWidth = 0;
        this.labelHeight = 0;
    }

    @Override
    public void render(GameContainer gameContainer) {
        if(this.labelWidth == 0 && this.labelHeight == 0) {
            recalculateFrame(gameContainer);
        }

        gameContainer.getGraphics().setColor(this.displayColor);
        gameContainer.getGraphics().draw(this.frame);
        FontManager.getInstance().getFont(FontType.WIDGET, this.labels.get(this.currentIndex)).drawString(this.frame.getCenterX() - (this.labelWidth / 2), this.frame.getY() + AbstractGuiWidget.WIDGET_PADDING, this.labels.get(this.currentIndex));
    }

    public int getIndex() {
        return this.currentIndex;
    }
    
    private void recalculateFrame(GameContainer gameContainer) {
        
        Font widgetFont = FontManager.getInstance().getFont(FontType.WIDGET, this.labels.get(this.currentIndex));        
        this.labelWidth = widgetFont.getWidth(this.labels.get(this.currentIndex));
        this.labelHeight = widgetFont.getLineHeight();
        
        this.frame = new RoundedRectangle(
                this.centerX - (this.labelWidth / 2) - AbstractGuiWidget.WIDGET_PADDING,
                this.centerY - (this.labelHeight / 2) - AbstractGuiWidget.WIDGET_PADDING,
                this.labelWidth + (AbstractGuiWidget.WIDGET_PADDING * 2),
                this.labelHeight + (AbstractGuiWidget.WIDGET_PADDING * 2),
                5);
    }

    public void mousePressed(int button, int x, int y) {
    }

    public void mouseReleased(int button, int x, int y) {
        if(this.frame != null && this.frame.contains(x, y)) {
            this.currentIndex = (this.currentIndex + 1) % this.labels.size();
            this.labelWidth = this.labelHeight = 0;
        }
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if (this.frame != null) {
            this.displayColor = this.frame.contains(newx, newy) ? Color.yellow : AbstractGuiWidget.DEFAULT_WIDGET_COLOR;
        }
    }
}
