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

    private List<AbstractBindableEmitter> bindableEmitters;
    private List<ExpirableEmitter> expirableEmitters;

    private Point offset;

    private EffectManager() {
        try {
            this.particleSystem = ParticleIO.loadConfiguredSystem(EffectManager.class.getResource("/resources/particles/particleSystem.xml").getPath());
            this.particleSystem.setRemoveCompletedEmitters(true);
            this.spawnEmitter = ParticleIO.loadEmitter(EffectManager.class.getResource("/resources/particles/spawn.xml").getPath());
            this.trailEmitter = ParticleIO.loadEmitter(EffectManager.class.getResource("/resources/particles/trail.xml").getPath());
            this.hitEmitter = ParticleIO.loadEmitter(EffectManager.class.getResource("/resources/particles/hit.xml").getPath());

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
