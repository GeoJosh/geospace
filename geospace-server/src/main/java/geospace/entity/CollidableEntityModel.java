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
