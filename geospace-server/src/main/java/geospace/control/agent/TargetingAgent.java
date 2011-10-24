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

import geospace.control.CurrentGameState;
import geospace.control.ShipInformation;
import geospace.entity.Constants;
import org.newdawn.slick.geom.Vector2f;

public class TargetingAgent extends AbstractAgent {

    private String opponentId;
    private static final float SHIP_TURN_VELOCITY_DEGREE = (float) Math.toDegrees(Constants.SHIP_TURN_VELOCITY);
    private static final float SHIP_TURN_VELOCITY_TURBO_DEGREE = (float) Math.toDegrees(Constants.SHIP_TURN_VELOCITY_TURBO);
    
    public TargetingAgent() {
        this.agentName = "Targeting Agent";
        this.agentDescription = "Agent focused on targeting the opponent.";
        
        this.opponentId = "";
    }

    @Override
    public void informGameState(CurrentGameState cgs) {
        if(this.opponentId.isEmpty() || cgs.getShipInformation(this.opponentId) == null) {
            for(ShipInformation info : cgs.getShips()) {
                if(!info.getId().equals(this.agentId)) {
                    this.opponentId = info.getId();
                }
            }
        }
        
        ShipInformation shipInformation = cgs.getShipInformation(this.agentId);
        ShipInformation opponentInformation = cgs.getShipInformation(this.opponentId);

        if(opponentInformation != null && shipInformation != null) {
            Vector2f targetVector = new Vector2f(opponentInformation.getCenterX() - shipInformation.getCenterX(), opponentInformation.getCenterY() - shipInformation.getCenterY());
            adjustHeading(targetVector.getTheta() - Constants.radiansToDegrees(shipInformation.getHeading()));
        }
        
        this.agentController.setFiring(true);
    }
    
    private void adjustHeading(double headingDiff) {
        headingDiff = headingDiff > 180 ? headingDiff - 360 : headingDiff < -180 ? headingDiff + 360 : headingDiff;
        
        if (headingDiff < 0) {
            this.agentController.setTurningStarboard(false);

            if (headingDiff < -SHIP_TURN_VELOCITY_TURBO_DEGREE) {
                this.agentController.setTurbo(true);
                this.agentController.setTurningPort(true);
            } else if (headingDiff < -SHIP_TURN_VELOCITY_DEGREE) {
                this.agentController.setTurbo(false);
                this.agentController.setTurningPort(true);
            } else {
                this.agentController.setTurningPort(false);
                this.agentController.setTurbo(true);
            }
        } else if (headingDiff > 0) {
            this.agentController.setTurningPort(false);

            if (headingDiff > 2 * SHIP_TURN_VELOCITY_TURBO_DEGREE) {
                this.agentController.setTurbo(true);
                this.agentController.setTurningStarboard(true);
            } else if (headingDiff > SHIP_TURN_VELOCITY_DEGREE) {
                this.agentController.setTurbo(false);
                this.agentController.setTurningStarboard(true);
            } else {
                this.agentController.setTurningStarboard(false);
                this.agentController.setTurbo(true);
            }
        }
    }    
}
