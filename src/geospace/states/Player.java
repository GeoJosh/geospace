package geospace.states;

import geospace.control.agent.AbstractAgent;
import geospace.entity.Ship;
import geospace.render.elements.EnergyBar;
import java.util.Random;
import org.newdawn.slick.Color;

public class Player {
    private Color playerColor;
    private Ship ship;
    private EnergyBar energyBar;
    private AbstractAgent agent;

    public Player() {
        Random randomGenerator = new Random();
        this.playerColor = new Color(randomGenerator.nextInt() | 0xFF909090);
    }

    public Color getPlayerColor() {
        return playerColor;
    }
    
    public AbstractAgent getAgent() {
        return agent;
    }

    public AbstractAgent setAgent(AbstractAgent agent) {
        this.agent = agent;
        return this.agent;
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
        if(this.agent != null) {
            this.agent.setAgentId(ship.getId());
        }
        return this.ship = ship;
    }
}
