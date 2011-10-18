package geospace.entity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;

public abstract class EntityModel {

    protected Shape shape;
    protected Color color;
    protected EntityState state;

    private String id;

    protected EntityModel() {
        this.id = UUID.randomUUID().toString();
    }

    public enum EntityState {
        FIXED,
        SPAWN,
        ALIVE,
        DEAD
    }

    abstract protected void updateShape();

    public String getId() {
        return this.id;
    }
    
    public Shape getShape() {
        return this.shape;
    }

    public Color getColor() {
        return this.color;
    }

    public void setState(EntityState state) {
        this.state = state;
    }

    public EntityState getState() {
        return this.state;
    }
    
    public void update() {
    }

    public List<EntityModel> getSpawnedEntities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityModel other = (EntityModel) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
