package geospace.render.elements;

import geospace.render.FontManager;
import geospace.render.FontManager.FontType;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/**
 *
 * @author jpenton
 */
public class TimerClock implements Runnable {

    private int timeLeft = 60000;
    private float centerX;
    private float centerY;
    
    private static final int DELAY_VALUE = 100;

    public TimerClock(int timerStart, float x, float y) {
        this.timeLeft = timerStart;
        this.centerX = x;
        this.centerY = y;
    }
    
    public void run() {
        try {
            while(this.timeLeft > 0) {
                Thread.sleep(DELAY_VALUE);
                this.timeLeft -= DELAY_VALUE;
            }
        } catch (InterruptedException ex) {
        }
    }
    
    public void render(Graphics graphics) {
        String timeLeftString = String.valueOf(this.timeLeft / 100);
        Font graphicsFont = FontManager.getInstance().getFont(FontType.TIMER, timeLeftString);
        
        int timeWidth = graphicsFont.getWidth(timeLeftString);
        int timeHeight = graphicsFont.getLineHeight();
                
        graphicsFont.drawString(this.centerX - (timeWidth / 2), this.centerY - (timeHeight / 2), timeLeftString);
    }
}
