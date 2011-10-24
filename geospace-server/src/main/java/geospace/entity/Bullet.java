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

package geospace.entity;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public class Bullet extends EntityModel {

    private Shape baseShape;
    private Point center;
    private Vector2f velocity;
    private Transform translateTransform;
    
    public static final float VELOCITY = 10.0f;

    public Bullet(Point center, float heading, Color color) {
        super();
        this.state = EntityState.SPAWN;
        
        this.center = center;
        this.velocity = new Vector2f((float) (VELOCITY * FastTrig.cos(heading)), (float) (VELOCITY * FastTrig.sin(heading)));
        this.color = color;

        this.translateTransform = Transform.createTranslateTransform(this.center.getX(), this.center.getY());
        this.baseShape = new Ellipse(0.0f, 0.0f, 5.0f, 5.0f);


        this.updateShape();
    }

    public Point getCenter() {
        return center;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    @Override
    public void update() {
        this.center.setX(this.center.getX() + this.velocity.x);
        this.center.setY(this.center.getY() + this.velocity.y);

        this.translateTransform = Transform.createTranslateTransform(this.center.getX(), this.center.getY());

        this.updateShape();

    }

    @Override
    protected void updateShape() {
        this.shape = this.baseShape.transform(this.translateTransform);
    }
}
