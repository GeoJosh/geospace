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

import geospace.render.emitter.ExpirableEmitter;
import geospace.entity.Point;
import geospace.entity.Ship;
import geospace.render.emitter.AbstractBindableEmitter;
import geospace.render.emitter.ShipEmitter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class EffectManager {

    private ParticleSystem particleSystem;
    private ConfigurableEmitter spawnEmitter;
    private ConfigurableEmitter trailEmitter;
    private ConfigurableEmitter hitEmitter;
    private ConfigurableEmitter waitingEmitter;

    private List<AbstractBindableEmitter> bindableEmitters;
    private List<ExpirableEmitter> expirableEmitters;

    private Point offset;

    private EffectManager() {
        try {
            this.particleSystem = ParticleIO.loadConfiguredSystem("./resources/particles/particleSystem.xml");
            this.particleSystem.setRemoveCompletedEmitters(true);
            this.spawnEmitter = ParticleIO.loadEmitter("./resources/particles/spawn.xml");
            this.trailEmitter = ParticleIO.loadEmitter("./resources/particles/trail.xml");
            this.hitEmitter = ParticleIO.loadEmitter("./resources/particles/hit.xml");
            this.waitingEmitter = ParticleIO.loadEmitter("./resources/particles/waiting.xml");

            this.bindableEmitters = new LinkedList<AbstractBindableEmitter>();
            this.expirableEmitters = new LinkedList<ExpirableEmitter>();
        } catch (IOException ex) {
            Logger.getLogger(EffectManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.offset = new Point(0, 0);
    }

    private static class EffectManagerSingleton {
        public static final EffectManager instance = new EffectManager();
    }

    public static EffectManager getInstance() {
        return EffectManagerSingleton.instance;
    }

    public void setOffset(Point offset) {
        this.offset = offset;
    }

    public void clearEffects() {
        for(AbstractBindableEmitter emitter : this.bindableEmitters) {
            emitter.getEmitter().wrapUp();
        }
        this.bindableEmitters.clear();
        
        for(ExpirableEmitter emitter : this.expirableEmitters) {
            emitter.getEmitter().wrapUp();
        }
        this.expirableEmitters.clear();
        this.particleSystem.removeAllEmitters();
    }
    
    public void renderSpawn(float x, float y) {
        ConfigurableEmitter emitter = this.spawnEmitter.duplicate();
        emitter.setPosition(x, y);
        this.particleSystem.addEmitter(emitter);
        this.expirableEmitters.add(new ExpirableEmitter(emitter, 100));
    }

    public void renderTrail(Ship ship) {
        ConfigurableEmitter emitter = this.trailEmitter.duplicate();
        emitter.setPosition(ship.getCenter().getX(), ship.getCenter().getY());
        this.particleSystem.addEmitter(emitter);
        this.bindableEmitters.add(new ShipEmitter(ship, emitter));
    }

    public void renderWaiting(float x, float y) {
        ConfigurableEmitter emitter = this.waitingEmitter.duplicate();
        emitter.setPosition(x, y);
        this.particleSystem.addEmitter(emitter);
    }

    public void renderHit(float x, float y) {
        ConfigurableEmitter emitter = this.hitEmitter.duplicate();
        emitter.setPosition(x, y);
        this.particleSystem.addEmitter(emitter);
        this.expirableEmitters.add(new ExpirableEmitter(emitter, 50));
    }

    public void destroyTrail(Ship ship) {
        ShipEmitter shipEmitter = null;

        for(AbstractBindableEmitter emitter : this.bindableEmitters) {
            if(emitter.getBoundEntity() == ship) {
                shipEmitter = (ShipEmitter)emitter;
                break;
            }
        }

        if(shipEmitter != null) {
            shipEmitter.getEmitter().wrapUp();
            this.bindableEmitters.remove(shipEmitter);
        }
    }

    public void update(GameContainer gc, int delta) {
        if (!this.expirableEmitters.isEmpty()) {
            List<ExpirableEmitter> removableEmitters = new LinkedList<ExpirableEmitter>();

            for (ExpirableEmitter ece : this.expirableEmitters) {
                if (!ece.decreaseTime(delta)) {
                    ece.getEmitter().wrapUp();
                    removableEmitters.add(ece);
                }
            }

            if (!removableEmitters.isEmpty()) {
                this.expirableEmitters.removeAll(removableEmitters);
            }
        }

        for(AbstractBindableEmitter abe : this.bindableEmitters) {
            abe.update(delta);
        }
        
        this.particleSystem.update(delta);
    }

    public void render(Graphics graphics) {
        graphics.translate(this.offset.getX(), this.offset.getY());

        this.particleSystem.render();
    }
}
