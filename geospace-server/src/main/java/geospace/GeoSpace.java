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
