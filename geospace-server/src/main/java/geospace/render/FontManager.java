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

package geospace.render;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontManager {

    private UnicodeFont defaultFont;
    private UnicodeFont widgetFont;
    private UnicodeFont timerFont;
    private UnicodeFont gutterFont;

    public enum FontType {
        DEFAULT,
        WIDGET,
        TIMER,
        GUTTER
    }

    private FontManager() {
    }

    private static class FontManagerSingleton {

        public static final FontManager instance = new FontManager();
    }

    public static FontManager getInstance() {
        return FontManagerSingleton.instance;
    }

    public UnicodeFont getFont(FontType fontType) {
        switch (fontType) {
            case TIMER:
                return this.timerFont;
            case WIDGET:
                return this.widgetFont;
            case GUTTER:
                return this.gutterFont;
            default:
                return defaultFont;
        }
    }
    public UnicodeFont getFont(FontType fontType, String string) {
        UnicodeFont font = getFont(fontType);
        font.addGlyphs(string);
        try {
            font.loadGlyphs();
        } catch (SlickException ex) {
            Logger.getLogger(FontManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return font;
        
    }

    public void setFont(UnicodeFont font) throws SlickException {
        setFont(FontType.DEFAULT, font);
    }

    public void setFont(FontType fontType, UnicodeFont font) throws SlickException {
        initializeFont(fontType, font, false);

        switch (fontType) {
            case TIMER:
                this.timerFont = font;
                break;
            case WIDGET:
                this.widgetFont = font;
                break;
            case GUTTER:
                this.gutterFont = font;
                break;
            default:
                this.defaultFont = font;
        }
    }

    private void initializeFont(FontType fontType, UnicodeFont font, boolean precacheGlyphs) throws SlickException {

        if(precacheGlyphs) {
            switch (fontType) {
                case TIMER:
                    font.addGlyphs(48, 57);
                    break;
                default:
                    font.addAsciiGlyphs();
            }
        }
        
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        font.loadGlyphs();
    }
}
