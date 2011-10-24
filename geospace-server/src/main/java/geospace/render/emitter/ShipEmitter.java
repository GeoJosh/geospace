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

package geospace.render.emitter;

import geospace.entity.Point;
import geospace.entity.Ship;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.util.FastTrig;

public class ShipEmitter extends AbstractBindableEmitter<Ship> {
    private static final float TODEGREE = 180.0f / (float) Math.PI;

    public ShipEmitter(Ship ship, ConfigurableEmitter emitter) {
        super(ship, emitter);
    }

    @Override
    public void update(int delta) {
        float shipHeading = this.entity.getHeading();
        Point shipCenter = this.entity.getCenter();

        this.emitter.angularOffset = new ModifiableSimpleValue((shipHeading * ShipEmitter.TODEGREE) - 90.0f);
        this.emitter.xOffset.setMin(-9 * (float) FastTrig.cos(shipHeading));
        this.emitter.xOffset.setMax(this.emitter.xOffset.getMin());
        this.emitter.yOffset.setMin(-9 * (float) FastTrig.sin(shipHeading));
        this.emitter.yOffset.setMax(this.emitter.yOffset.getMin());

        this.emitter.setPosition(shipCenter.getX() + 1, shipCenter.getY() + 1);
    }

}
