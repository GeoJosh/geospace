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
