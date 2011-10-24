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
