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

package geospace.control;

import geospace.control.GameEvent.GameEventType;
import geospace.entity.Bullet;
import geospace.entity.EntityModel;
import geospace.entity.Ship;
import geospace.entity.EntityManager;
import geospace.entity.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CurrentGameState {

    private List<ShipInformation> ships;
    private List<BulletInformation> bullets;
    private List<GameEvent> gameEvents;
    private FieldInformation field;
    private long timestamp;
    private int timeLeft;

    public CurrentGameState() {
        this.ships = new LinkedList<ShipInformation>();
        this.bullets = new LinkedList<BulletInformation>();
        this.gameEvents = new LinkedList<GameEvent>();
    }

    public void updateState(int timeLeft) {
        this.gameEvents = new LinkedList<GameEvent>();
        
        List<ShipInformation> iterShips = new LinkedList<ShipInformation>();
        List<BulletInformation> iterBullets = new LinkedList<BulletInformation>();

        List<EntityModel> entityList = EntityManager.getInstance().getEntities();
        synchronized(entityList) {
            for (EntityModel entity : EntityManager.getInstance().getEntities()) {
                if (entity instanceof Ship) {
                    iterShips.add(new ShipInformation((Ship) entity));
                } else if (entity instanceof Bullet) {
                    iterBullets.add(new BulletInformation((Bullet) entity));
                } else if (entity instanceof Field) {
                    this.field = new FieldInformation((Field) entity);
                }
            }
        }

        this.ships = iterShips;
        this.bullets = iterBullets;

        this.timestamp = new Date().getTime();
    }

    public List<GameEvent> getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(List<GameEvent> gameEvents) {
        // Method disabled
    }
    
    public void addEvent(GameEventType eventType, String entityId) {
        this.gameEvents.add(new GameEvent(eventType, entityId));
    }
    
    public void addEvents(List<GameEvent> events) {
        this.gameEvents.addAll(events);
    }
    
    public List<ShipInformation> getShips() {
        return ships;
    }

    public void setShips(List<ShipInformation> ships) {
        // Method disabled
    }

    public List<BulletInformation> getBullets() {
        return bullets;
    }

    public void setBullets(List<BulletInformation> bullets) {
        // Method disabled
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        // Method disabled
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        // Method disabled
    }

    public FieldInformation getField() {
        return field;
    }

    public void setField(FieldInformation field) {
        // Method disabled
    }

    public ShipInformation getShipInformation(String id) {
        for (ShipInformation shipInformation : this.ships) {
            if (shipInformation.getId().equals(id)) {
                return shipInformation;
            }
        }

        return null;
    }
}
