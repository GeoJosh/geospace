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
