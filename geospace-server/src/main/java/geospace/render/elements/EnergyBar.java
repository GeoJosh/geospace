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

import geospace.entity.Constants;
import geospace.entity.Point;
import geospace.entity.Ship;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;

public class EnergyBar {
    private Ship ship;
    private Point topLeft;
    private float height;
    private float width;

    private Shape bar;
    private final static float CORNER_RADIUS = 5.0f;

    public EnergyBar(Ship ship, Point topLeft, float width, float height) {
        this.ship = ship;
        this.topLeft = topLeft;
        this.height = height;
        this.width = width;

        this.bar = new RoundedRectangle(topLeft.getX(), topLeft.getY(), width, height, EnergyBar.CORNER_RADIUS);
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.draw(this.bar);

        graphics.setColor(ship.getColor());
        graphics.fillRect(this.topLeft.getX() + EnergyBar.CORNER_RADIUS, this.topLeft.getY() + EnergyBar.CORNER_RADIUS - 1.0f, (width - (2 * EnergyBar.CORNER_RADIUS)) * ((float)ship.getEnergy() / Constants.SHIP_MAX_ENERGY), (height - (2 * EnergyBar.CORNER_RADIUS)));
    }

    public Point getTopLeft() {
        return topLeft;
    }
    
    public Point getBarCenter() {
        return new Point(this.bar.getCenterX(), this.bar.getCenterY());
    }
}
