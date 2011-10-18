package geospace.render;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 *
 * @author jpenton
 */
public class FontManager {

    private UnicodeFont defaultFont;
    private UnicodeFont widgetFont;
    private UnicodeFont timerFont;

    public enum FontType {
        DEFAULT,
        WIDGET,
        TIMER
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
