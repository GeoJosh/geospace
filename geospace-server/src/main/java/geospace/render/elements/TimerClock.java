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

package geospace.render.elements;

import geospace.PropertyManager;
import geospace.render.FontManager;
import geospace.render.FontManager.FontType;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;


public class TimerClock implements Runnable {

    private int timeLeft;
    private float centerX;
    private float centerY;
    
    private static final int DELAY_VALUE = 100;

    public TimerClock(int timerStart, float x, float y) {
        this.timeLeft = timerStart > 1000 ? timerStart : timerStart * 1000;
        this.centerX = x;
        this.centerY = y;
    }
    
    @Override
    public void run() {
        try {
            while(this.timeLeft > 0) {
                Thread.sleep(DELAY_VALUE);
                if(!PropertyManager.getInstance().getBoolean("system.development")) {
                    this.timeLeft -= DELAY_VALUE;
                }
            }
        } catch (InterruptedException ex) {
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }
    
    public void render(Graphics graphics) {
        String timeLeftString = String.valueOf(this.timeLeft / 100);
        Font graphicsFont = FontManager.getInstance().getFont(FontType.TIMER, timeLeftString);
        
        int timeWidth = graphicsFont.getWidth(timeLeftString);
        int timeHeight = graphicsFont.getLineHeight();
                
        graphicsFont.drawString(this.centerX - (timeWidth / 2), this.centerY - (timeHeight / 2), timeLeftString);
    }
}
