package geospace.render;

import geospace.render.elements.ImageRender;
import geospace.entity.Bullet;
import geospace.entity.EntityModel;
import geospace.entity.Field;
import geospace.entity.Point;
import geospace.entity.Ship;
import geospace.render.FontManager.FontType;
import geospace.states.Player;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class DrawManager {

    private boolean enableWireframe = false;
    private Point offset;
    private List<ImageRender> images;
    private Image bulletImage;

    private DrawManager() {
        this.offset = new Point(0, 0);
        this.images = new LinkedList<ImageRender>();
    }

    private static class DrawManagerSingleton {

        private static final DrawManager instance = new DrawManager();
    }

    public static DrawManager getInstance() {
        return DrawManagerSingleton.instance;
    }

    public void init() {
        try {
            this.bulletImage = new Image("./resources/images/bullet.png");
        } catch (SlickException ex) {
            Logger.getLogger(DrawManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setOffset(Point offset) {
        this.offset = offset;
    }

    public boolean isEnableWireframe() {
        return enableWireframe;
    }

    public void setEnableWireframe(boolean enableWireframe) {
        this.enableWireframe = enableWireframe;
    }

    public void addImage(ImageRender image) {
        this.images.add(image);
    }

    public void addImage(List<ImageRender> images) {
        this.images.addAll(images);
    }

    public void clearImages() {
        this.images.clear();
    }

    public void renderImages() {
        for (ImageRender image : this.images) {
            image.getImage().draw(image.getX(), image.getY());
        }
    }

    public void renderPlayerInfo(Graphics graphics, Player player) {
        player.getEnergyBar().render(graphics);
        
        Font infoFont = FontManager.getInstance().getFont(FontType.TIMER, String.valueOf(player.getScore()));
        infoFont.drawString(
                player.getEnergyBar().getBarCenter().getX(),
                player.getEnergyBar().getBarCenter().getY() - (infoFont.getLineHeight() / 2),
                String.valueOf(player.getScore()));
    }

    public void renderEntities(Graphics graphics, List<EntityModel> entities) {
        for (EntityModel entity : entities) {
            this.renderEntity(graphics, entity);
        }
    }

    public void renderEntity(Graphics graphics, EntityModel entity) {
        graphics.translate(this.offset.getX(), this.offset.getY());

        graphics.setColor(entity.getColor());

        if (entity instanceof Field) {
            this.drawField(graphics, (Field) entity);
        } else if (entity instanceof Ship) {
            this.drawShip(graphics, (Ship) entity);
        } else if (entity instanceof Bullet) {
            this.drawBullet(graphics, (Bullet) entity);
        }

        graphics.resetTransform();
    }

    private void drawField(Graphics graphics, Field field) {
        graphics.setLineWidth(2.0f);
        graphics.draw(field.getShape());
    }

    private void drawShip(Graphics graphics, Ship ship) {
        if (this.enableWireframe) {
            graphics.draw(ship.getShape());
            if (ship.isShielding()) {
                graphics.draw(ship.getShipShield());
            }
        } else {
            graphics.draw(ship.getShape());
            if (ship.isShielding()) {
                graphics.draw(ship.getShipShield());
            }
        }
    }

    private void drawBullet(Graphics graphics, Bullet bullet) {
        graphics.drawImage(this.bulletImage, bullet.getCenter().getX() - (this.bulletImage.getWidth() / 2), bullet.getCenter().getY() - (this.bulletImage.getHeight() / 2));
    }
}
