package geospace.render.emitter;

import geospace.entity.EntityModel;
import org.newdawn.slick.particles.ConfigurableEmitter;

public abstract class AbstractBindableEmitter<T extends EntityModel> {
    protected T entity;
    protected ConfigurableEmitter emitter;

    public AbstractBindableEmitter(T entity, ConfigurableEmitter emitter) {
        this.entity = entity;
        this.emitter = emitter;
    }

    public T getBoundEntity() {
        return entity;
    }

    public ConfigurableEmitter getEmitter() {
        return this.emitter;
    }

    abstract public void update(int delta);

}
