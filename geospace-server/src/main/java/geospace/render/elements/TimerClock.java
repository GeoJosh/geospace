package geospace.render.elements;

import geospace.PropertyManager;
import geospace.render.FontManager;
import geospace.render.FontManager.FontType;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;

/**
 *
 * @author jpenton
 */
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
