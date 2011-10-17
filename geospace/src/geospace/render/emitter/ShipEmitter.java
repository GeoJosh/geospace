package geospace.render.emitter;

import geospace.entity.Point;
import geospace.entity.Ship;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.util.FastTrig;

public class ShipEmitter extends AbstractBindableEmitter<Ship> {
    private static final float TODEGREE = 180.0f / (float) Math.PI;

    public ShipEmitter(Ship ship, ConfigurableEmitter emitter) {
        super(ship, emitter);
    }

    public void update(int delta) {
        float shipHeading = this.entity.getHeading();
        Point shipCenter = this.entity.getCenter();

        this.emitter.angularOffset = new ModifiableSimpleValue((shipHeading * ShipEmitter.TODEGREE) - 90.0f);
        this.emitter.xOffset.setMin(-9 * (float) FastTrig.cos(shipHeading));
        this.emitter.xOffset.setMax(this.emitter.xOffset.getMin());
        this.emitter.yOffset.setMin(-9 * (float) FastTrig.sin(shipHeading));
        this.emitter.yOffset.setMax(this.emitter.yOffset.getMin());

        this.emitter.setPosition(shipCenter.getX() + 1, shipCenter.getY() + 1);
    }

}
