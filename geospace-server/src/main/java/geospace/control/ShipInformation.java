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

import geospace.entity.Ship;

public class ShipInformation {
    private String id;
    private float centerX;
    private float centerY;
    private float heading;
    private float velocityX;
    private float velocityY;
    private boolean shielding;
    private int energy;

    public ShipInformation() {
        
    }
    
    public ShipInformation(Ship ship) {
        this.id = ship.getId();
        this.centerX = ship.getCenter().getX();
        this.centerY = ship.getCenter().getY();
        this.heading = ship.getHeading();
        this.velocityX = ship.getVelocity().x;
        this.velocityY = ship.getVelocity().y;
        this.shielding = ship.isShielding();
        this.energy = ship.getEnergy();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        // Method disabled
    }
    
    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        // Method disabled
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        // Method disabled
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        // Method disabled
    }

    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        // Method disabled
    }

    public boolean isShielding() {
        return shielding;
    }

    public void setShielding(boolean shielding) {
        // Method disabled
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        // Method disabled
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        // Method disabled
    }
}
