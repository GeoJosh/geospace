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

import geospace.audio.AudioManager;
import geospace.entity.EntityModel.EntityState;
import geospace.render.EffectManager;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class EntityManager {
    private final List<EntityModel> entities;
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
        if(!this.entities.contains(entity)) {
            for(EntityModel collEntity: this.entities) {
                if(collEntity instanceof CollidableEntityModel) {
                    ((CollidableEntityModel)collEntity).evaluateEntity(entity);
                    if (entity instanceof CollidableEntityModel) {
                        ((CollidableEntityModel) entity).evaluateEntity(collEntity);
                    }
                }
            }

            synchronized(this.entities) {
                this.entities.add(entity);
            }
        }
    }

    public void addEntity(List<EntityModel> entities) {
        synchronized(this.entities) {
            this.entities.addAll(entities);
        }
    }

    public void removeEntity(EntityModel entity) {
        synchronized(this.entities) {
            this.entities.remove(entity);
        }
    }

    public void clearEntities() {
        synchronized(this.entities) {
            this.entities.clear();
        }
    }
    
    public void updateEntities() {
        for (EntityModel entity : this.entities) {
            entity.update();

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
