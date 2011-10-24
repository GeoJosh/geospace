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

package geospace.artifacts.remote.example;

import geospace.control.agent.service.ControllerState;
import geospace.control.agent.service.CurrentGameState;
import geospace.control.agent.service.ServiceAgentEndpoint;
import geospace.control.agent.service.ServiceAgentEndpointService;
import geospace.control.agent.service.ShipInformation;

public class Main {

    private static class AgentStateThread implements Runnable {

        private static int UPDATE_RATE = 17;
        private ServiceAgentEndpoint endpoint;
        private String authId;
        private String agentId;
        private String opponentId;
        private CurrentGameState lastGameState;
        private ControllerState controllerState;
        private static final float SHIP_TURN_VELOCITY_DEGREE = (float) Math.toDegrees((float) Math.PI / 360);
        private static final float SHIP_TURN_VELOCITY_TURBO_DEGREE = (float) Math.toDegrees((float) Math.PI / 60);
        private static final double TWOPI = Math.PI * 2;

        public AgentStateThread() {
            this.endpoint = new ServiceAgentEndpointService().getServiceAgentEndpointPort();
            this.controllerState = new ControllerState();
            this.controllerState.setTurbo(true);
            this.authId = this.endpoint.connectAgent("Targeting Remote Agent", "Remote Targeting Agent");
            this.opponentId = "";
        }

        @Override
        public void run() {
            boolean continueToRun = true;

            while (continueToRun) {
                if (this.agentId == null) {
                    this.agentId = this.endpoint.getAgentId(this.authId);
                } else {
                    this.lastGameState = this.endpoint.getLastGameState();

                    if (this.opponentId.isEmpty() || getShipInformation(this.lastGameState, this.opponentId) == null) {
                        for (ShipInformation info : this.lastGameState.getShips()) {
                            if (!info.getId().equals(this.agentId)) {
                                this.opponentId = info.getId();
                            }
                        }
                    }

                    ShipInformation shipInformation = getShipInformation(this.lastGameState, this.agentId);
                    ShipInformation opponentInformation = getShipInformation(this.lastGameState, this.opponentId);

                    if (opponentInformation != null && shipInformation != null) {
                        float targetHeading = (float)getTheta(opponentInformation.getCenterX() - shipInformation.getCenterX(), opponentInformation.getCenterY() - shipInformation.getCenterY());
                        adjustHeading(radiansToDegrees(targetHeading - shipInformation.getHeading()));
                    }

                    this.controllerState.setFiring(!this.controllerState.isTurningPort() && !this.controllerState.isTurningStarboard());

                    this.endpoint.setControllerState(this.authId, this.controllerState);
                }

                try {
                    Thread.sleep(UPDATE_RATE);
                } catch (InterruptedException ex) {
                    continueToRun = false;
                }
            }
        }

        public double getTheta(double x, double y) {
		double theta = StrictMath.atan2(y, x);
		if ((theta < -TWOPI) || (theta > TWOPI)) {
			theta = theta % TWOPI;
		}
		if (theta < 0) {
			theta = TWOPI + theta;
		}

		return theta;
	}
        
        private void adjustHeading(double headingDiff) {
            headingDiff = headingDiff > 180 ? headingDiff - 360 : headingDiff < -180 ? headingDiff + 360 : headingDiff;

            if (headingDiff < 0) {
                this.controllerState.setTurningStarboard(false);

                if (headingDiff < -SHIP_TURN_VELOCITY_TURBO_DEGREE) {
                    this.controllerState.setTurbo(true);
                    this.controllerState.setTurningPort(true);
                } else if (headingDiff < -SHIP_TURN_VELOCITY_DEGREE) {
                    this.controllerState.setTurbo(false);
                    this.controllerState.setTurningPort(true);
                } else {
                    this.controllerState.setTurningPort(false);
                    this.controllerState.setTurbo(true);
                }
            } else if (headingDiff > 0) {
                this.controllerState.setTurningPort(false);

                if (headingDiff > 2 * SHIP_TURN_VELOCITY_TURBO_DEGREE) {
                    this.controllerState.setTurbo(true);
                    this.controllerState.setTurningStarboard(true);
                } else if (headingDiff > SHIP_TURN_VELOCITY_DEGREE) {
                    this.controllerState.setTurbo(false);
                    this.controllerState.setTurningStarboard(true);
                } else {
                    this.controllerState.setTurningStarboard(false);
                    this.controllerState.setTurbo(true);
                }
            }
        }
    }

    private static float radiansToDegrees(float angle) {
        double degrees = Math.toDegrees(angle) % 360;
        degrees = degrees < 0 ? 360 + degrees : degrees;
        return (float) degrees;
    }

    private static ShipInformation getShipInformation(CurrentGameState cgs, String agentId) {
        for (ShipInformation shipInformation : cgs.getShips()) {
            if (shipInformation.getId().equals(agentId)) {
                return shipInformation;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Thread stateThread = new Thread(new AgentStateThread());
        Runtime.getRuntime().addShutdownHook(stateThread);

        stateThread.start();
        while (stateThread.isAlive()) {
        }
    }
}
