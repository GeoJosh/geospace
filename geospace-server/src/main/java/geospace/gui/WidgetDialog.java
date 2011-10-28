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
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.RoundedRectangle;

public class WidgetDialog extends AbstractGuiWidget {

    private String message;    
    private RoundedRectangle frame;
    
    private int messageWidth;
    private int messageHeight;
    private WidgetButton dialogButton;
    
        
    public WidgetDialog(String message) {
        super();
        this.message = message;
        this.frame = null;
    }

    @Override
    public void render(GameContainer gameContainer) {
        if(this.frame == null) {

            Font widgetFont = FontManager.getInstance().getFont(FontType.WIDGET, this.message);        
            this.messageWidth = widgetFont.getWidth(this.message);
            this.messageHeight = widgetFont.getLineHeight();
            
            this.frame = new RoundedRectangle(
                    (gameContainer.getWidth() - this.messageWidth) / 2 - AbstractGuiWidget.WIDGET_PADDING,
                    (gameContainer.getHeight() - this.messageHeight) / 2 - AbstractGuiWidget.WIDGET_PADDING,
                    this.messageWidth + (AbstractGuiWidget.WIDGET_PADDING * 2),
                    (this.messageHeight * 2) + (AbstractGuiWidget.WIDGET_PADDING * 5),
                    5);
            
            this.dialogButton = new WidgetButton("OK", gameContainer.getWidth() / 2, (gameContainer.getHeight() / 2) + (this.messageHeight * 2));
            this.dialogButton.addListener(this);
            GUIManager.getInstance().addWidget(this.dialogButton);
        }
        
        gameContainer.getGraphics().setColor(DEFAULT_WIDGET_BGCOLOR);
        gameContainer.getGraphics().fillRoundRect(frame.getMinX(), frame.getMinY(), frame.getWidth(), frame.getHeight(), (int)frame.getCornerRadius());
        gameContainer.getGraphics().setColor(AbstractGuiWidget.DEFAULT_WIDGET_COLOR);
        gameContainer.getGraphics().draw(frame);
        FontManager.getInstance().getFont(FontType.WIDGET, this.message).drawString(this.frame.getCenterX() - (this.messageWidth / 2), this.frame.getY() + AbstractGuiWidget.WIDGET_PADDING, this.message);
    }
    
    @Override
    public void notifyEvent(String id, EventType eventType) {
        switch(eventType) {
            case MOUSE_RELEASE:
                GUIManager.getInstance().removeWidget(this.dialogButton);
                GUIManager.getInstance().removeWidget(this);
                break;
        }
    }
}
