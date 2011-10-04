package geospace.entity;

import geospace.audio.AudioManager;
import geospace.entity.EntityModel.EntityState;
import geospace.render.EffectManager;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class EntityManager {
    private List<EntityModel> entities;
    private HashMap<EntityModel, ManagementAction> managementQueue;

    private enum ManagementAction {
        ADD,
        REMOVE
    }
    
    private EntityManager() {
        this.entities = new LinkedList<EntityModel>();
        this.managementQueue = new HashMap<EntityModel, ManagementAction>();
    }

    private static class EntityManagerSingleton {

        public static final EntityManager instance = new EntityManager();
    }

    public static EntityManager getInstance() {
        return EntityManagerSingleton.instance;
    }

    public List<EntityModel> getEntities() {
        return this.entities;
    }
    
    public void addEntity(EntityModel entity) {
        for(EntityModel collEntity: this.entities) {
            if(collEntity instanceof CollidableEntityModel) {
                ((CollidableEntityModel)collEntity).evaluateEntity(entity);
                if (entity instanceof CollidableEntityModel) {
                    ((CollidableEntityModel) entity).evaluateEntity(collEntity);
                }
            }
        }

        this.entities.add(entity);
    }

    public void addEntity(List<EntityModel> entities) {
        this.entities.addAll(entities);
    }

    public void removeEntity(EntityModel entity) {
        this.entities.remove(entity);
    }

    public void clearEntities() {
        this.entities.clear();
    }
    
    public void updateEntities(int delta) {
        for (EntityModel entity : this.entities) {
            entity.update(delta);

            for(EntityModel spawnedEntity : entity.getSpawnedEntities()) {
                this.managementQueue.put(spawnedEntity, ManagementAction.ADD);
            }
        }

        this.processEntities();
        this.processManagementQueue();
    }

    private void processEntities() {
        for(EntityModel entity: this.entities) {
            
            if (entity instanceof Field) {
                processField((Field)entity);
            }
            else if (entity instanceof Ship) {
                processShip((Ship)entity);
            }
            else if (entity instanceof Bullet) {
                processBullet((Bullet)entity);
            }
        }
    }

    private void processField(Field field) {
        field.processWatchedEnties();
    }

    private void processShip(Ship ship) {
        ship.processWatchedEnties();

        switch(ship.getState()) {
            case SPAWN:
                EffectManager.getInstance().renderSpawn(ship.getCenter().getX(), ship.getCenter().getY());
                EffectManager.getInstance().renderTrail(ship);
                ship.setState(EntityState.ALIVE);
                break;
            case DEAD:
                AudioManager.getInstance().playEffect(AudioManager.EffectType.DEATH);
                EffectManager.getInstance().renderSpawn(ship.getCenter().getX(), ship.getCenter().getY());
                EffectManager.getInstance().destroyTrail(ship);
                this.managementQueue.put(ship, ManagementAction.REMOVE);
                break;
        }
    }

    private void processBullet(Bullet bullet) {
        switch(bullet.getState()) {
            case SPAWN:
                AudioManager.getInstance().playEffect(AudioManager.EffectType.SHOT);
                bullet.setState(EntityState.ALIVE);
                break;
            case DEAD:
                EffectManager.getInstance().renderHit(bullet.getCenter().getX(), bullet.getCenter().getY());
                this.managementQueue.put(bullet, ManagementAction.REMOVE);
                break;
        }
    }

    private void processManagementQueue() {
        for(Entry<EntityModel, ManagementAction> entry : this.managementQueue.entrySet()) {
            switch(entry.getValue()) {
                case ADD:
                    this.addEntity(entry.getKey());
                    break;
                case REMOVE:
                    this.removeEntity(entry.getKey());
                    break;
            }
        }
        
        this.managementQueue.clear();
    }
}
