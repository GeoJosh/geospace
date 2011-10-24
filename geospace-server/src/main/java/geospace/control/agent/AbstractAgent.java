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

package geospace.control.agent;

import geospace.control.ControllerState;
import geospace.control.CurrentGameState;
import java.util.Date;

public abstract class AbstractAgent {

    protected String agentName;
    protected String agentDescription;
    protected String agentId;
    
    protected ControllerState agentController;
    
    private long lastFired;
    private static final long firingInterval = 250;

    public AbstractAgent() {
        this.agentController = new ControllerState();
    }

    /**
     * Implement this method to feed your agent the current state of the game.
     * Note that it is the responsibility of the agent to keep track of information
     * between calls if such behavior is desired.
     *
     * @param cgs  An object describing the current state of the game.
     * @see CurrentGameState
     */
    abstract public void informGameState(final CurrentGameState cgs);

    public String getAgentDescription() {
        return agentDescription;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentId(String id) {
        this.agentId = id;
    }

    public String getAgentId() {
        return agentId;
    }
    
    /**
     * Override this method to define the polygon point which describe the shape of your ship.
     * Note that the bounding radius of the ship must be larger than 12.0f or the Ship class will
     * throw a GeoSpaceException on instantiation.
     */
    public float[] getAgentShipDesign() {
        return new float[]{
                            -1.0f, 0.0f,
                            2.0f, 6.0f,
                            10.0f, 6.0f,
                            0.0f, 12.0f,
                            -8.0f, 0.0f,
                            0.0f, -12.0f,
                            10.0f, -6.0f,
                            2.0f, -6.0f,};
    }
    
    public final ControllerState getControls() {
        ControllerState controllerState = this.agentController;

        if (!controllerState.isFiring()) {
            return controllerState;
        }

        Date now = new Date();
        long currentTimestamp = now.getTime();

        if (currentTimestamp - this.lastFired >= AbstractAgent.firingInterval) {
            this.lastFired = currentTimestamp;
        } else {
            controllerState.setFiring(false);
        }

        return controllerState;

    }
    
}
