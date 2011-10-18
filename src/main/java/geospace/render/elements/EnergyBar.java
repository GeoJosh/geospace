package geospace.render.elements;

import geospace.entity.Constants;
import geospace.entity.Point;
import geospace.entity.Ship;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;

public class EnergyBar {
    private Ship ship;
    private Point topLeft;
    private float height;
    private float width;

    private Shape bar;
    private final static float CORNER_RADIUS = 5.0f;

    public EnergyBar(Ship ship, Point topLeft, float width, float height) {
        this.ship = ship;
        this.topLeft = topLeft;
        this.height = height;
        this.width = width;

        this.bar = new RoundedRectangle(topLeft.getX(), topLeft.getY(), width, height, EnergyBar.CORNER_RADIUS);
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.draw(this.bar);

        graphics.setColor(ship.getColor());
        graphics.fillRect(this.topLeft.getX() + EnergyBar.CORNER_RADIUS, this.topLeft.getY() + EnergyBar.CORNER_RADIUS - 1.0f, (width - (2 * EnergyBar.CORNER_RADIUS)) * ((float)ship.getEnergy() / Constants.SHIP_MAX_ENERGY), (height - (2 * EnergyBar.CORNER_RADIUS)));
    }
    
    public Point getBarCenter() {
        return new Point(this.bar.getCenterX(), this.bar.getCenterY());
    }
}
