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
import geospace.states.PlayingState.GameMode;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.RoundedRectangle;

public class WidgetPlayerSelector extends AbstractGuiWidget {

    private final static int WIDGET_WIDTH = 500;
    private final static int WIDGET_HEIGHT = 400;
    private final static String PLAYER_ONE_STRING = "Agent One";
    private final static String PLAYER_TWO_STRING = "Agent Two";
    private RoundedRectangle frame;
    private WidgetButton royaleButton;
    private WidgetButton confirmButton;
    private GameMode gameMode;
    private WidgetClickableList playerOneList;
    private WidgetClickableList playerTwoList;
    private boolean readyToStart;
    private List<Entry<String, String>> entries;
    private List<String> labels;

    public WidgetPlayerSelector(Set<Entry<String, String>> entries) {
        this.readyToStart = false;
        this.gameMode = GameMode.DUEL;

        this.entries = new LinkedList<Entry<String, String>>(entries);
        Collections.sort(this.entries, new AgentEntrySorter());
        this.labels = new LinkedList<String>();
        for (Entry<String, String> entry : this.entries) {
            this.labels.add(entry.getValue());
        }
    }

    @Override
    public void render(GameContainer gameContainer) {
        if (this.frame == null) {
            this.frame = new RoundedRectangle(
                    (gameContainer.getWidth() - WIDGET_WIDTH) / 2 - AbstractGuiWidget.WIDGET_PADDING,
                    (gameContainer.getHeight() - WIDGET_HEIGHT) / 2 - AbstractGuiWidget.WIDGET_PADDING,
                    WIDGET_WIDTH + (AbstractGuiWidget.WIDGET_PADDING * 2),
                    WIDGET_HEIGHT + (AbstractGuiWidget.WIDGET_PADDING * 2),
                    5);

            this.playerOneList = new WidgetClickableList(this.labels, this.frame.getCenterX(), this.frame.getY() + 75);
            GUIManager.getInstance().addWidget(this.playerOneList);
            this.playerTwoList = new WidgetClickableList(this.labels, this.frame.getCenterX(), this.frame.getY() + 225);
            GUIManager.getInstance().addWidget(this.playerTwoList);

            this.confirmButton = new WidgetButton("Start Game", this.frame.getCenterX() - (this.frame.getWidth() / 4), this.frame.getMaxY() - 50);
            this.confirmButton.addListener(this);
            GUIManager.getInstance().addWidget(this.confirmButton);

            this.royaleButton = new WidgetButton("Battle Royale", this.frame.getCenterX() + (this.frame.getWidth() / 4), this.frame.getMaxY() - 50);
            this.royaleButton.addListener(this);
            GUIManager.getInstance().addWidget(this.royaleButton);

        }
        gameContainer.getGraphics().setColor(DEFAULT_WIDGET_BGCOLOR);
        gameContainer.getGraphics().fillRoundRect(frame.getMinX(), frame.getMinY(), frame.getWidth(), frame.getHeight(), (int)frame.getCornerRadius());
        gameContainer.getGraphics().setColor(AbstractGuiWidget.DEFAULT_WIDGET_COLOR);
        gameContainer.getGraphics().draw(frame);

        Font widgetFont = FontManager.getInstance().getFont(FontType.WIDGET, PLAYER_ONE_STRING + PLAYER_TWO_STRING);
        widgetFont.drawString(this.frame.getCenterX() - widgetFont.getWidth(PLAYER_ONE_STRING) / 2, this.frame.getY() + AbstractGuiWidget.WIDGET_PADDING, PLAYER_ONE_STRING);
        widgetFont.drawString(this.frame.getCenterX() - widgetFont.getWidth(PLAYER_TWO_STRING) / 2, this.frame.getY() + AbstractGuiWidget.WIDGET_PADDING + 150, PLAYER_TWO_STRING);

    }

    public boolean isReadyToStart() {
        return readyToStart;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public String getPlayerOneClassName() {
        return this.entries.get(this.playerOneList.getIndex()).getKey();
    }

    public String getPlayerTwoClassName() {
        return this.entries.get(this.playerTwoList.getIndex()).getKey();
    }

    @Override
    public void notifyEvent(String id, EventType eventType) {
        switch (eventType) {
            case MOUSE_RELEASE:
                if (id.equals(this.royaleButton.getId())) {
                    this.gameMode = GameMode.BATTLE_ROYALE;

                } else if (id.equals(this.confirmButton.getId())) {
                    this.gameMode = GameMode.DUEL;
                }

                if (true) {
                    GUIManager.getInstance().removeWidget(this.royaleButton);
                    GUIManager.getInstance().removeWidget(this.confirmButton);
                    GUIManager.getInstance().removeWidget(this);

                    this.readyToStart = true;
                }
                break;
        }
    }
}
