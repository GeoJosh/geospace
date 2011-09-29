package geospace.entity;

import geospace.entity.EntityModel.EntityState;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

abstract public class CollidableEntityModel extends EntityModel {
    
    List<EntityModel> monitorEntities;
    List<Class> monitoringClasses;

    protected CollidableEntityModel(Class ...classes) {
        super();
        this.monitorEntities = new LinkedList<EntityModel>();
        this.monitoringClasses = new LinkedList<Class>();

        Collections.addAll(this.monitoringClasses, classes);
    }

    public void evaluateEntity(EntityModel entity) {
        for(Class clazz : this.monitoringClasses) {
            if(clazz.isInstance(entity)) {
                this.monitorEntities.add(entity);
            }
        }
    }
    
    public void processWatchedEnties() {
        List<EntityModel> entities = new LinkedList<EntityModel>(this.monitorEntities);
        for(EntityModel entity : entities) {
            if(entity.state == EntityState.DEAD) {
                this.monitorEntities.remove(entity);
            }

            if(collidesWith(entity)) {
                entity.setState(EntityState.DEAD);
            }
        }
    }
    abstract public boolean collidesWith(EntityModel entity);
}
