package geospace.gui;

import java.util.UUID;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.RoundedRectangle;

public class WidgetButton extends AbstractMouseListener {
    private String id;
    private String label;    
    private RoundedRectangle frame;
    
    private int labelWidth;
    private int labelHeight;
    private float centerX;
    private float centerY;
    
    private Color displayColor;
    
    public WidgetButton(String label, float centerX, float centerY) {
        super();
        
        this.id = UUID.randomUUID().toString();
        this.label = label;
        this.frame = null;
        this.displayColor = AbstractGuiWidget.DEFAULT_WIDGET_COLOR;
        this.centerX = centerX;
        this.centerY = centerY;        
    }

    public String getId() {
        return id;
    }
    
    @Override
    public void render(GameContainer gameContainer) {
        if(this.frame == null) {
            this.labelWidth = gameContainer.getGraphics().getFont().getWidth(this.label);
            this.labelHeight = gameContainer.getGraphics().getFont().getLineHeight();
            
            this.frame = new RoundedRectangle(
                    this.centerX - (this.labelWidth / 2) - AbstractGuiWidget.WIDGET_PADDING,
                    this.centerY - (this.labelHeight / 2) - AbstractGuiWidget.WIDGET_PADDING,
                    this.labelWidth + (AbstractGuiWidget.WIDGET_PADDING * 2),
                    this.labelHeight + (AbstractGuiWidget.WIDGET_PADDING * 2),
                    5);
        }
        
        gameContainer.getGraphics().setColor(this.displayColor);
        gameContainer.getGraphics().draw(this.frame);
        gameContainer.getGraphics().getFont().drawString(this.frame.getCenterX() - (this.labelWidth / 2), this.frame.getY() + AbstractGuiWidget.WIDGET_PADDING, this.label);
    }

    public void mousePressed(int button, int x, int y) {
    }

    public void mouseReleased(int button, int x, int y) {
        if(this.frame != null && this.frame.contains(x, y)) {
            this.notifyListeners(this.id, EventType.MOUSE_RELEASE);
        }
    }

    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if(this.frame != null) {
            this.displayColor = this.frame.contains(newx, newy) ? Color.yellow : AbstractGuiWidget.DEFAULT_WIDGET_COLOR;
        }
    }
}
