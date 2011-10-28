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

import geospace.render.elements.ImageRender;
import geospace.entity.Bullet;
import geospace.entity.EntityModel;
import geospace.entity.Field;
import geospace.entity.Point;
import geospace.entity.Ship;
import geospace.render.FontManager.FontType;
import geospace.states.Player;
import geospace.states.PlayingState;
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
        if(player.getEnergyBar() != null) {
            player.getEnergyBar().render(graphics);
            FontManager.getInstance().getFont(FontType.GUTTER).drawString(player.getEnergyBar().getTopLeft().getX(), player.getEnergyBar().getTopLeft().getY() + PlayingState.TITLE_HEIGHT * 0.8f, player.getAgent().getAgentName());

            Font infoFont = FontManager.getInstance().getFont(FontType.TIMER, String.valueOf(player.getScore()));
            
            int scoreWidth = infoFont.getWidth(String.valueOf(player.getScore()));
            
            infoFont.drawString(
                    player.getEnergyBar().getBarCenter().getX() - (scoreWidth / 2),
                    player.getEnergyBar().getBarCenter().getY() - (infoFont.getLineHeight() / 2),
                    String.valueOf(player.getScore()));
        }
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
