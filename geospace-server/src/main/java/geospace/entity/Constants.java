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

public class Constants {
    public static final float SHIP_ACCELERATION = 0.004f;
    public static final float SHIP_ACCELERATION_TURBO = SHIP_ACCELERATION * 6;
    public static final float DRAG_DECELERATION = 0.001f; 
    public static final float SHIP_TURN_VELOCITY = (float)Math.PI / 360;
    public static final float SHIP_TURN_VELOCITY_TURBO = SHIP_TURN_VELOCITY * 6;

    public static final int SHIP_MAX_ENERGY = 100000;
    public static final int SHIP_ENERGY_REGEN = Constants.SHIP_MAX_ENERGY / 5000;
    public static final int SHIP_ENERGY_COST_FIRE = 500;
    public static final int SHIP_ENERGY_COST_SHIELD = 1000;
    
    public static float radiansToDegrees(float angle) {
        double degrees = Math.toDegrees(angle) % 360;
        degrees = degrees < 0 ? 360 + degrees : degrees;
        return (float)degrees;
    }
}
