package geospace.gui;

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
            this.messageWidth = gameContainer.getGraphics().getFont().getWidth(this.message);
            this.messageHeight = gameContainer.getGraphics().getFont().getLineHeight();
            
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
        
        gameContainer.getGraphics().setColor(AbstractGuiWidget.DEFAULT_WIDGET_COLOR);
        gameContainer.getGraphics().draw(frame);
        gameContainer.getGraphics().getFont().drawString(this.frame.getCenterX() - (this.messageWidth / 2), this.frame.getY() + AbstractGuiWidget.WIDGET_PADDING, this.message);
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
