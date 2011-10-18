package geospace.render.elements;

import geospace.entity.EntityManager;
import geospace.entity.Field;
import geospace.entity.Point;
import geospace.render.DrawManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Stage {

    private Point offset;
    private Field bounds;
    private Rectangle clippingShape;


    public Stage(Point offset, float width, float height) {
        this.offset = offset;
        this.bounds = new Field(width, height);

        EntityManager.getInstance().addEntity(this.bounds);
        this.clippingShape = new Rectangle(offset.getX()-1, offset.getY()-1, width+1, height+1);
    }

    public void shrinkStage(float amount) {
        this.bounds.shrinkField(amount);
    }
    
    public Point getStageOffset() {
        return this.offset;
    }

    public float getStageWidth() {
        return this.bounds.getFieldWidth();
    }

    public float getStageHeight() {
        return this.bounds.getFieldHeight();
    }

    public void update(GameContainer gc, int delta) {
    }

    public void setStageClipping(Graphics graphics, boolean isClipping) {
        if (isClipping) {
            graphics.setClip(this.clippingShape);
        }
        else {
            graphics.clearClip();
        }
    }

    public void render(Graphics graphics) {

        EntityManager.getInstance().updateEntities();

        DrawManager.getInstance().setOffset(this.offset);
        DrawManager.getInstance().renderEntity(graphics, this.bounds);

        DrawManager.getInstance().renderEntities(graphics, EntityManager.getInstance().getEntities());
    }
}
