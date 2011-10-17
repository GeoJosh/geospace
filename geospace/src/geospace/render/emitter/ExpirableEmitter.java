package geospace.render.emitter;

import org.newdawn.slick.particles.ConfigurableEmitter;

public class ExpirableEmitter {
    private ConfigurableEmitter emitter;
    private int expireTime;

    public ExpirableEmitter(ConfigurableEmitter emitter, int expireTime) {
        this.emitter = emitter;
        this.expireTime = expireTime;
    }

    public ConfigurableEmitter getEmitter() {
        return emitter;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public boolean decreaseTime(int delta) {
        expireTime -= delta;
        return expireTime > 0;
    }
}
