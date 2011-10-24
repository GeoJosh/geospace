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

package geospace.states;

import geospace.control.CurrentGameState;
import geospace.control.agent.AbstractAgent;
import geospace.entity.Ship;
import geospace.render.elements.EnergyBar;
import java.util.Random;
import org.newdawn.slick.Color;

public class Player implements Runnable {

    private Color playerColor;
    private Ship ship;
    private EnergyBar energyBar;
    private int score;
    final private AbstractAgent agent;
    final private CurrentGameState currentGameState;

    public Player(AbstractAgent agent, CurrentGameState currentGameState) {
        this.agent = agent;
        this.currentGameState = currentGameState;

        this.playerColor = new Color(new Random().nextInt() | 0xFF909090);
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void updateScore() {
        this.score++;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public EnergyBar getEnergyBar() {
        return energyBar;
    }

    public EnergyBar setEnergyBar(EnergyBar energyBar) {
        return this.energyBar = energyBar;
    }

    public Ship getShip() {
        return ship;
    }

    public Ship setShip(Ship ship) {
        if (this.agent != null) {
            this.agent.setAgentId(ship.getId());
        }
        return this.ship = ship;
    }

    public AbstractAgent getAgent() {
        return agent;
    }

    @Override
    public void run() {
        boolean continueToRun = true;
        while (continueToRun) {
            synchronized (this.currentGameState) {
                try {
                    this.currentGameState.wait();
                } catch (InterruptedException ex) {
                    continueToRun = false;
                }
            }

            this.agent.informGameState(this.currentGameState);
        }
    }
}
